package ua.quiz.model.context;

import ua.quiz.controller.command.Command;
import ua.quiz.controller.command.DefaultCommand;
import ua.quiz.controller.command.authentication.*;
import ua.quiz.controller.command.error.PageNotFoundFormCommand;
import ua.quiz.controller.command.judge.JudgePageFormCommand;
import ua.quiz.controller.command.player.CreateTeamFormCommand;
import ua.quiz.controller.command.player.PlayerPageFormCommand;
import ua.quiz.controller.command.player.ProfilePageFormCommand;
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

    private static final PageNotFoundFormCommand PAGE_NOT_FOUND_FORM_COMMAND = new PageNotFoundFormCommand();

    private static final PlayerPageFormCommand PLAYER_PAGE_FORM = new PlayerPageFormCommand();

    private static final JudgePageFormCommand JUDGE_PAGE_FORM = new JudgePageFormCommand();

    private static final ProfilePageFormCommand PROFILE_PAGE_FORM = new ProfilePageFormCommand();

    private static final CreateTeamFormCommand CREATE_TEAM_FORM_COMMAND = new CreateTeamFormCommand();

    private static final IndexPageFormCommand INDEX_PAGE_FORM_COMMAND = new IndexPageFormCommand();

    private static final DefaultCommand DEFAULT_COMMAND = new DefaultCommand();

    private static final Map<String, Command> COMMAND_NAME_TO_COMMAND = mapCommands();


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

    private static Map<String, Command> mapCommands() {
        Map<String, Command> authenticationCommandToCommand = new HashMap<>();
        authenticationCommandToCommand.put("login", LOG_IN_COMMAND);
        authenticationCommandToCommand.put("registration", REGISTRATION_COMMAND);
        authenticationCommandToCommand.put("logout", LOG_OUT_COMMAND);
        authenticationCommandToCommand.put("loginForm", LOG_IN_FORM_COMMAND);
        authenticationCommandToCommand.put("registrationForm", REGISTRATION_FORM_COMMAND);
        authenticationCommandToCommand.put("default", DEFAULT_COMMAND);
        authenticationCommandToCommand.put("pageNotFoundForm", PAGE_NOT_FOUND_FORM_COMMAND);
        authenticationCommandToCommand.put("player-PageForm", PLAYER_PAGE_FORM);
        authenticationCommandToCommand.put("judge-PageForm", JUDGE_PAGE_FORM);
        authenticationCommandToCommand.put("player-profilePageForm", PROFILE_PAGE_FORM);
        authenticationCommandToCommand.put("indexPageForm", INDEX_PAGE_FORM_COMMAND);
        authenticationCommandToCommand.put("player-createTeam", CREATE_TEAM_FORM_COMMAND);



        return authenticationCommandToCommand;
    }

    public Map<String, Command> getCommandsMap() {
        return COMMAND_NAME_TO_COMMAND;
    }

    public DefaultCommand getDefaultCommand() {
        return DEFAULT_COMMAND;
    }
}
