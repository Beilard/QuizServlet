package ua.quiz.model.context;

import ua.quiz.controller.command.Command;
import ua.quiz.controller.command.DefaultCommand;
import ua.quiz.controller.command.authentication.*;
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

    private static final LogInCommand LOG_IN_COMMAND = new LogInCommand(USER_SERVICE);

    private static final LogInFormCommand LOG_IN_FORM_COMMAND = new LogInFormCommand();

    private static final RegistrationCommand REGISTRATION_COMMAND = new RegistrationCommand(USER_SERVICE);

    private static final RegistrationFormCommand REGISTRATION_FORM_COMMAND = new RegistrationFormCommand();

    private static final LogOutCommand LOG_OUT_COMMAND = new LogOutCommand();

    private static final DefaultCommand DEFAULT_COMMAND = new DefaultCommand();

    private static final Map<String, Command> AUTHENTICATION_COMMAND_NAME_TO_COMMAND = mapAuthenticationCommand();


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

    private static Map<String, Command> mapAuthenticationCommand() {
        Map<String, Command> authenticationCommandToCommand = new HashMap<>();
        authenticationCommandToCommand.put("login", LOG_IN_COMMAND);
        authenticationCommandToCommand.put("register", REGISTRATION_COMMAND);
        authenticationCommandToCommand.put("logout", LOG_OUT_COMMAND);
        authenticationCommandToCommand.put("loginForm", LOG_IN_FORM_COMMAND);
        authenticationCommandToCommand.put("registrationForm", REGISTRATION_FORM_COMMAND);
        authenticationCommandToCommand.put("user-start-game", REGISTRATION_FORM_COMMAND);
        authenticationCommandToCommand.put("default", DEFAULT_COMMAND);

        return authenticationCommandToCommand;
    }

    public Map<String, Command> getAuthenticationCommands() {
        return AUTHENTICATION_COMMAND_NAME_TO_COMMAND;
    }

    public DefaultCommand getDefaultCommand() {
        return DEFAULT_COMMAND;
    }
}
