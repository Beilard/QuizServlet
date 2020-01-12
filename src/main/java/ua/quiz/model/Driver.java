package ua.quiz.model;

import ua.quiz.model.dao.DBConnector;
import ua.quiz.model.dao.UserDao;
import ua.quiz.model.dao.impl.UserDaoImpl;
import ua.quiz.model.dto.Role;
import ua.quiz.model.dto.User;
import ua.quiz.model.service.UserService;
import ua.quiz.model.service.encoder.PasswordEncoder;
import ua.quiz.model.service.impl.UserServiceImpl;
import ua.quiz.model.service.mapper.UserMapper;
import ua.quiz.model.service.validator.UserValidator;

public class Driver {
    public static void main(String[] args) {
        DBConnector dbConnector = new DBConnector("database");
        UserDao userDao = new UserDaoImpl(dbConnector);
        PasswordEncoder passwordEncoder = new PasswordEncoder();
        UserValidator userValidator = new UserValidator();
        UserMapper userMapper = new UserMapper();
        UserService userService = new UserServiceImpl(userDao, userValidator, passwordEncoder, userMapper);

        userService.register(User.builder()
                .withEmail("Vlad@mail.com")
                .withPassword("Qwerty123#")
                .withName("Vlad")
                .withSurname("Khurtin")
                .withTeamId(0L)
                .withRole(Role.JUDGE)
                .build());
    }
}
