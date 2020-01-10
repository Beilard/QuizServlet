package ua.quiz.controller.servlet;

import org.apache.log4j.Logger;
import ua.quiz.controller.command.Command;

import javax.servlet.http.HttpServlet;
import java.util.List;
import java.util.Map;

public class AbstractServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(AbstractServlet.class);

    private final Map<String, Command> commandNameToCommand;
    private final Command defaultCommand;
    private final List<String> redirectCommands;
    private final List<String> redirectPages;
}
