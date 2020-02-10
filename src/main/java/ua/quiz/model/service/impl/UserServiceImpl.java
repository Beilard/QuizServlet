package ua.quiz.model.service.impl;

import org.apache.log4j.Logger;
import ua.quiz.model.dao.UserDao;
import ua.quiz.model.dto.User;
import ua.quiz.model.entity.RoleEntity;
import ua.quiz.model.entity.UserEntity;
import ua.quiz.model.exception.EmailAlreadyTakenException;
import ua.quiz.model.exception.EntityNotFoundException;
import ua.quiz.model.exception.InvalidCredentialsException;
import ua.quiz.model.service.UserService;
import ua.quiz.model.service.encoder.PasswordEncoder;
import ua.quiz.model.service.mapper.UserMapper;
import ua.quiz.model.service.validator.Validator;

import java.util.Collections;
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
            LOGGER.warn("Incorrect credentials");
            throw new InvalidCredentialsException("Incorrect credentials");
        }

        Optional<UserEntity> entity = userDao.findByEmail(email);

        if (!entity.isPresent()) {
            LOGGER.warn("User wasn't found with email: " + email);
            throw new EntityNotFoundException("User wasn't found with email: " + email);
        }

        final String encryptedPassword = passwordEncoder.encrypt(password);

        if (entity.get().getPassword().equals(encryptedPassword)) {
            return userMapper.mapUserEntityToUser(entity.get());
        } else {
            LOGGER.warn("Incorrect credentials: " + email);
            throw new InvalidCredentialsException("Incorrect credentials");
        }
    }

    @Override
    public void update(User user) {
        if (user == null) {
            LOGGER.warn("User passed to update is null");
            throw new IllegalArgumentException("User passed to update is null");
        }

        userDao.update(userMapper.mapUserToUserEntity(user));
    }

    @Override
    public User findByEmail(String email) {
        if (email == null) {
            LOGGER.warn("Email passed is null");
            throw new IllegalArgumentException("Email passed is null");
        }

        return userDao.findByEmail(email)
                .map(userMapper::mapUserEntityToUser)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<User> findByTeamId(Long teamId) {
        if (teamId == null) {
            LOGGER.warn("Team ID passed is null");
            throw new IllegalArgumentException("Team ID passed is null");
        }

        final List<UserEntity> entities = userDao.findAllByTeamId(teamId);

        return mapUserEntityListToUserList(entities);
    }

    private List<User> mapUserEntityListToUserList(List<UserEntity> entities) {
        return entities == null ? Collections.emptyList() : entities.stream()
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
}
