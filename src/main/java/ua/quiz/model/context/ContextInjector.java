package ua.quiz.model.context;

import ua.quiz.controller.command.Command;
import ua.quiz.controller.command.DefaultCommand;
import ua.quiz.controller.command.authentication.*;
import ua.quiz.controller.command.error.PageNotFoundFormCommand;
import ua.quiz.controller.command.game.*;
import ua.quiz.controller.command.judge.JudgePageFormCommand;
import ua.quiz.controller.command.player.*;
import ua.quiz.model.dao.*;
import ua.quiz.model.dao.impl.*;
import ua.quiz.model.service.GameService;
import ua.quiz.model.service.PhaseService;
import ua.quiz.model.service.TeamService;
import ua.quiz.model.service.UserService;
import ua.quiz.model.service.encoder.PasswordEncoder;
import ua.quiz.model.service.impl.GameServiceImpl;
import ua.quiz.model.service.impl.PhaseServiceImpl;
import ua.quiz.model.service.impl.TeamServiceImpl;
import ua.quiz.model.service.impl.UserServiceImpl;
import ua.quiz.model.service.mapper.*;
import ua.quiz.model.service.validator.UserValidator;

import java.util.HashMap;
import java.util.Map;

public class ContextInjector {
    private static final DBConnector DB_CONNECTOR = new DBConnector("database");

    private static final UserDao USER_DAO = new UserDaoImpl(DB_CONNECTOR);

    private static final TeamDao TEAM_DAO = new TeamDaoImpl(DB_CONNECTOR);

    private static final GameDao GAME_DAO = new GameDaoImpl(DB_CONNECTOR);

    private static final PhaseDao PHASE_DAO = new PhaseDaoImpl(DB_CONNECTOR);

    private static final QuestionDao QUESTION_DAO = new QuestionDaoImpl(DB_CONNECTOR);

    private static final PasswordEncoder PASSWORD_ENCODER = new PasswordEncoder();

    private static final UserValidator USER_VALIDATOR = new UserValidator();

    private static final PhaseMapper PHASE_MAPPER = new PhaseMapper();

    private static final QuestionMapper QUESTION_MAPPER = new QuestionMapper();

    private static final UserMapper USER_MAPPER = new UserMapper();

    private static final TeamMapper TEAM_MAPPER = new TeamMapper();

    private static final GameMapper GAME_MAPPER = new GameMapper();

    private static final TeamService TEAM_SERVICE =
            new TeamServiceImpl(USER_DAO, TEAM_DAO, TEAM_MAPPER, USER_MAPPER);

    private static final UserService USER_SERVICE = new UserServiceImpl(USER_DAO, USER_VALIDATOR,
            PASSWORD_ENCODER, USER_MAPPER);

    private static final PhaseService PHASE_SERVICE =
            new PhaseServiceImpl(PHASE_DAO, QUESTION_DAO, PHASE_MAPPER, QUESTION_MAPPER);

    private static final GameService GAME_SERVICE =
            new GameServiceImpl(GAME_DAO, PHASE_DAO, QUESTION_DAO, GAME_MAPPER, PHASE_MAPPER, QUESTION_MAPPER);

    private static final LogInCommand LOG_IN_COMMAND = new LogInCommand(USER_SERVICE);

    private static final LogInFormCommand LOG_IN_FORM_COMMAND = new LogInFormCommand();

    private static final RegistrationCommand REGISTRATION_COMMAND = new RegistrationCommand(USER_SERVICE);

    private static final RegistrationFormCommand REGISTRATION_FORM_COMMAND = new RegistrationFormCommand();

    private static final LogOutCommand LOG_OUT_COMMAND = new LogOutCommand();

    private static final PageNotFoundFormCommand PAGE_NOT_FOUND_FORM_COMMAND = new PageNotFoundFormCommand();

    private static final PlayerPageFormCommand PLAYER_PAGE_FORM = new PlayerPageFormCommand();

    private static final JudgePageFormCommand JUDGE_PAGE_FORM = new JudgePageFormCommand();

    private static final ProfilePageFormCommand PROFILE_PAGE_FORM = new ProfilePageFormCommand(GAME_SERVICE);

    private static final CreateTeamFormCommand CREATE_TEAM_FORM_COMMAND = new CreateTeamFormCommand();

    private static final IndexPageFormCommand INDEX_PAGE_FORM_COMMAND = new IndexPageFormCommand();

    private static final ConfigureGameFormCommand CONFIGURE_GAME_FORM_COMMAND = new ConfigureGameFormCommand();

    private static final StartGameCommand START_GAME_COMMAND = new StartGameCommand(GAME_SERVICE);

    private static final GeneratePhaseForm GENERATE_PHASE_FORM = new GeneratePhaseForm(GAME_SERVICE, PHASE_SERVICE);

    private static final CreateTeamCommand CREATE_TEAM_COMMAND
            = new CreateTeamCommand(USER_SERVICE, TEAM_SERVICE);

    private static final ViewPhaseFormCommand VIEW_PHASE_FORM_COMMAND = new ViewPhaseFormCommand();

    private static final FinishPhaseCommand FINISH_PHASE_COMMAND = new FinishPhaseCommand(PHASE_SERVICE, GAME_SERVICE);

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
        Map<String, Command> commandToCommandMap = new HashMap<>();
        commandToCommandMap.put("login", LOG_IN_COMMAND);
        commandToCommandMap.put("registration", REGISTRATION_COMMAND);
        commandToCommandMap.put("logout", LOG_OUT_COMMAND);
        commandToCommandMap.put("loginForm", LOG_IN_FORM_COMMAND);
        commandToCommandMap.put("registrationForm", REGISTRATION_FORM_COMMAND);
        commandToCommandMap.put("default", DEFAULT_COMMAND);
        commandToCommandMap.put("pageNotFoundForm", PAGE_NOT_FOUND_FORM_COMMAND);
        commandToCommandMap.put("player-PageForm", PLAYER_PAGE_FORM);
        commandToCommandMap.put("judge-PageForm", JUDGE_PAGE_FORM);
        commandToCommandMap.put("player-profilePageForm", PROFILE_PAGE_FORM);
        commandToCommandMap.put("indexPageForm", INDEX_PAGE_FORM_COMMAND);
        commandToCommandMap.put("player-createTeamForm", CREATE_TEAM_FORM_COMMAND);
        commandToCommandMap.put("player-createTeam", CREATE_TEAM_COMMAND);
        commandToCommandMap.put("player-configureGameForm", CONFIGURE_GAME_FORM_COMMAND);
        commandToCommandMap.put("player-startGame", START_GAME_COMMAND);
        commandToCommandMap.put("player-viewPhase", VIEW_PHASE_FORM_COMMAND);
        commandToCommandMap.put("player-generatePhase", GENERATE_PHASE_FORM);
        commandToCommandMap.put("player-finishPhase", FINISH_PHASE_COMMAND);


        return commandToCommandMap;
    }

    public Map<String, Command> getCommandsMap() {
        return COMMAND_NAME_TO_COMMAND;
    }

    public DefaultCommand getDefaultCommand() {
        return DEFAULT_COMMAND;
    }
}
