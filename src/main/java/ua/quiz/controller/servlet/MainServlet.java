package ua.quiz.controller.servlet;

import org.apache.log4j.Logger;
import ua.quiz.controller.command.Command;
import ua.quiz.model.context.ContextInjector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MainServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(MainServlet.class);

    private final Map<String, Command> commandNameToCommand;
    private final Command defaultCommand;
    private final List<String> redirectCommands;
    private final List<String> redirectPages;

    public MainServlet(String commands) {
        final ContextInjector injector = ContextInjector.getInstance();

        switch (commands) {
            case "authentication": {
                commandNameToCommand = injector.getAuthenticationCommands();
                break;
            }
            default: {
                LOGGER.warn("No commands provided for the name");
                throw new IllegalArgumentException("No commands provided for the name");
            }
        }
        defaultCommand = injector.getDefaultCommand();
        redirectCommands = Arrays.asList("logout");
        redirectPages = Arrays.asList("player", "judge", "/");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

}
