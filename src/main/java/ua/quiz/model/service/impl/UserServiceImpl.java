package ua.quiz.model.service.impl;

import org.apache.log4j.Logger;
import ua.quiz.model.dao.UserDao;
import ua.quiz.model.dto.User;
import ua.quiz.model.entity.UserEntity;
import ua.quiz.model.excpetion.EmailAlreadyTakenException;
import ua.quiz.model.excpetion.InvalidCredentialsExcpetion;
import ua.quiz.model.service.UserService;
import ua.quiz.model.service.encoder.PasswordEncoder;
import ua.quiz.model.service.mapper.UserMapper;
import ua.quiz.model.service.validator.Validator;

import java.util.Optional;

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
        userValidator.validate(user);
        if (userDao.findByEmail(user.getEmail()).isPresent()) {
            LOGGER.warn("User with such email already exists");
            throw new EmailAlreadyTakenException("User with such email already exists");
        }

        final String encryptedPassword = passwordEncoder.encrypt(user.getPassword());
        final User userWithEncryptedPassword = User.builder()
                .withEmail(user.getEmail())
                .withPassword(encryptedPassword)
                .withName(user.getName())
                .withSurname(user.getSurname())
                .build();
        final UserEntity userEntity = userMapper.mapUserToUserEntity(userWithEncryptedPassword);

        userDao.save(userEntity);
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
}
