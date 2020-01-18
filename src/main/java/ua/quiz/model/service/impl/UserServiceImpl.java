package ua.quiz.model.service.impl;

import org.apache.log4j.Logger;
import ua.quiz.model.dao.UserDao;
import ua.quiz.model.dto.User;
import ua.quiz.model.entity.RoleEntity;
import ua.quiz.model.entity.UserEntity;
import ua.quiz.model.exception.EmailAlreadyTakenException;
import ua.quiz.model.exception.InvalidCredentialsExcpetion;
import ua.quiz.model.service.UserService;
import ua.quiz.model.service.encoder.PasswordEncoder;
import ua.quiz.model.service.mapper.UserMapper;
import ua.quiz.model.service.validator.Validator;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class);

    private final UserDao userDao;
    private final Validator<User> userValidator;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserServiceImpl(UserDao userDao, Validator<User> userValidator,
                           PasswordEncoder passwordEncoder, UserMapper userMapper) {

        this.userDao = userDao;
        this.userValidator = userValidator;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Override
    public void register(User user) {
        if (user == null) {
            LOGGER.warn("Null user passed to register");
            throw new IllegalArgumentException("Null user passed to register");
        }
        userValidator.validate(user);
        if (userDao.findByEmail(user.getEmail()).isPresent()) {
            LOGGER.warn("User with such email already exists");
            throw new EmailAlreadyTakenException("User with such email already exists");
        }

        final String encryptedPassword = passwordEncoder.encrypt(user.getPassword());

        userDao.save(mapUserToUserEntity(user, encryptedPassword));
    }

    @Override
    public User login(String email, String password) {
        if (email == null || password == null) {
            LOGGER.warn("Null email or password passed");
            throw new InvalidCredentialsExcpetion("Incorrect email or password provided");
        }

        Optional<UserEntity> entity = userDao.findByEmail(email);

        if (!entity.isPresent()) {
            LOGGER.warn("Incorrect credentials: " + email);
            throw new InvalidCredentialsExcpetion("Incorrect credentials");
        }

        final String encryptedPassword = passwordEncoder.encrypt(password);

        if (entity.get().getPassword().equals(encryptedPassword)) {
            return userMapper.mapUserEntityToUser(entity.get());
        } else {
            LOGGER.warn("Incorrect credentials: " + email);
            throw new InvalidCredentialsExcpetion("Incorrect credentials");
        }
    }

    @Override
    public boolean joinTeam(User user, Long teamId) {
        if (user == null || teamId == null || teamId <= 0) {
            LOGGER.warn("User  or teamId passed to join team are null or illegal");
            throw new IllegalArgumentException("User  or teamId passed to join team are null or illegal");
        }
        final UserEntity userEntity = userDao.findById(user.getId()).get();

        final UserEntity modifiedUserEntity = updateUserTeam(teamId, userEntity);

        userDao.update(modifiedUserEntity);

        return true;
    }

    @Override
    public List<User> findByTeamId(Long teamId) {
        if (teamId == null) {
            LOGGER.warn("team ID passed is null");
            throw new IllegalArgumentException("team ID passed is null");
        }

        final List<UserEntity> entities = userDao.findAllByTeamId(teamId);

        return mapUserEntityListToUserList(entities);
    }

    private List<User> mapUserEntityListToUserList(List<UserEntity> entities) {
        return entities.stream()
                .map(userMapper::mapUserEntityToUser)
                .collect(Collectors.toList());
    }

    private UserEntity mapUserToUserEntity(User user, String encryptedPassword) {
        return UserEntity.builder()
                .withEmail(user.getEmail())
                .withPassword(encryptedPassword)
                .withName(user.getName())
                .withSurname(user.getSurname())
                .withRole(RoleEntity.valueOf(user.getRole().name()))
                .withTeamId(user.getTeamId())
                .build();
    }

    private UserEntity updateUserTeam(Long teamId, UserEntity userEntity) {
        return UserEntity.builder()
                .withId(userEntity.getId())
                .withEmail(userEntity.getEmail())
                .withPassword(userEntity.getPassword())
                .withName(userEntity.getName())
                .withSurname(userEntity.getSurname())
                .withTeamId(teamId)
                .withRoleEntity(userEntity.getRoleEntity())
                .build();
    }
}
