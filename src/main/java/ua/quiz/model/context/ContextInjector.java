package ua.quiz.model.context;

import org.omg.PortableInterceptor.USER_EXCEPTION;
import ua.quiz.controller.command.Command;
import ua.quiz.model.dao.DBConnector;
import ua.quiz.model.dao.UserDao;
import ua.quiz.model.dao.impl.UserDaoImpl;
import ua.quiz.model.service.UserService;
import ua.quiz.model.service.encoder.PasswordEncoder;
import ua.quiz.model.service.impl.UserServiceImpl;
import ua.quiz.model.service.mapper.UserMapper;
import ua.quiz.model.service.validator.UserValidator;

import java.util.HashMap;
import java.util.Map;

public class ContextInjector {
    private static final DBConnector DB_CONNECTOR = new DBConnector("database");

    private static final UserDao USER_DAO = new UserDaoImpl(DB_CONNECTOR);

    private static final PasswordEncoder PASSWORD_ENCODER = new PasswordEncoder();

    private static final UserValidator USER_VALIDATOR = new UserValidator();

    private static final UserMapper USER_MAPPER = new UserMapper();

    private static final UserService USER_SERVICE = new UserServiceImpl(USER_DAO, USER_VALIDATOR,
            PASSWORD_ENCODER, USER_MAPPER);

    private static ContextInjector contextInjector;

    private ContextInjector() {
    }

    public static ContextInjector getInstance() {
        if (contextInjector == null) {
            synchronized (ContextInjector.class) {
                if (contextInjector == null) {
                    contextInjector = new ContextInjector();
                }
            }
        }
        return contextInjector;
    }

    private static Map<String, Command> mapAuthenticationCommand(){
        Map<String, Command> authenticationCommandToCommand = new HashMap<>();

    }
}
