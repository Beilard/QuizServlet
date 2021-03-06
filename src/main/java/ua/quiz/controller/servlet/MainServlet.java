package ua.quiz.controller.servlet;

import ua.quiz.controller.command.Command;
import ua.quiz.model.context.ContextInjector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/game")
public class MainServlet extends HttpServlet {
    private final Map<String, Command> commandNameToCommand;
    private final Command defaultCommand;


    public MainServlet() {
        final ContextInjector injector = ContextInjector.getInstance();
        commandNameToCommand = injector.getCommandsMap();
        defaultCommand = injector.getDefaultCommand();
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        forward(req, resp);
    }

    private void forward(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final String commandName = request.getParameter("command");
        final String page = commandNameToCommand.getOrDefault(commandName, defaultCommand).execute(request, response);
        request.getRequestDispatcher(page).forward(request, response);
    }

}
