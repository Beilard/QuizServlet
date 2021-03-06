package ua.quiz.controller.command.authentication;

import org.apache.log4j.Logger;
import ua.quiz.controller.command.Command;
import ua.quiz.model.dto.User;
import ua.quiz.model.exception.EntityNotFoundException;
import ua.quiz.model.exception.InvalidCredentialsException;
import ua.quiz.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogInCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(LogInCommand.class);

    private final UserService userService;

    public LogInCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        final String email = request.getParameter("email");
        final String password = request.getParameter("password");

        if (email == null || password == null
                || email.length() > 63 || password.length() > 63) {
            LOGGER.info("User tried logging in with invalid credential length");
            request.setAttribute("loginInvalid", true);
            return "/game?command=loginForm";
        }

        User user;

        try {
            user = userService.login(email, password);
        } catch (InvalidCredentialsException | EntityNotFoundException e) {
            request.setAttribute("loginInvalid", true);
            return "/game?command=loginForm";
        }

        User userWithoutPassword = removePassword(user);

        request.getSession().setAttribute("user", userWithoutPassword);
        request.getSession().setAttribute("isLoggedIn", true);

        return "/game?command=indexPageForm";
    }

    private User removePassword(User user) {
        return User.builder(user)
                .withPassword(null)
                .build();
    }
}
