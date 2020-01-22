package ua.quiz.model;

import jdk.nashorn.internal.ir.CallNode;
import ua.quiz.model.dao.*;
import ua.quiz.model.dao.impl.GameDaoImpl;
import ua.quiz.model.dao.impl.PhaseDaoImpl;
import ua.quiz.model.dao.impl.QuestionDaoImpl;
import ua.quiz.model.dao.impl.UserDaoImpl;
import ua.quiz.model.dto.Game;
import ua.quiz.model.dto.Role;
import ua.quiz.model.dto.Status;
import ua.quiz.model.dto.User;
import ua.quiz.model.service.GameService;
import ua.quiz.model.service.UserService;
import ua.quiz.model.service.encoder.PasswordEncoder;
import ua.quiz.model.service.impl.GameServiceImpl;
import ua.quiz.model.service.impl.UserServiceImpl;
import ua.quiz.model.service.mapper.GameMapper;
import ua.quiz.model.service.mapper.PhaseMapper;
import ua.quiz.model.service.mapper.QuestionMapper;
import ua.quiz.model.service.mapper.UserMapper;
import ua.quiz.model.service.validator.UserValidator;

import java.util.List;

public class Driver {
    public static void main(String[] args) {
        DBConnector dbConnector = new DBConnector("database");
        UserDao userDao = new UserDaoImpl(dbConnector);
        PasswordEncoder passwordEncoder = new PasswordEncoder();
        UserValidator userValidator = new UserValidator();
        UserMapper userMapper = new UserMapper();
        UserService userService = new UserServiceImpl(userDao, userValidator, passwordEncoder, userMapper);

//        userService.register(User.builder()
//                .withEmail("Maria@gmail.com")
//                .withPassword("Qwerty123#")
//                .withName("Masha")
//                .withSurname("Shervchenko")
//                .build());

        GameDao gameDao = new GameDaoImpl(dbConnector);
        GameMapper gameMapper = new GameMapper();
        QuestionDao questionDao = new QuestionDaoImpl(dbConnector);
        QuestionMapper questionMapper = new QuestionMapper();
        PhaseDao phaseDao = new PhaseDaoImpl(dbConnector);
        PhaseMapper phaseMapper = new PhaseMapper();


        GameService gameService = new GameServiceImpl(gameDao, phaseDao, questionDao, gameMapper, phaseMapper, questionMapper);

        Long aLong = gameDao.countAllByTeamId(5L);
        System.out.println(aLong);
        phaseDao.findAll(0L, 10L);
        List<Game> gameList = gameService.findAll(2L, 10L);


    }
}
