package ua.quiz.controller.command.authentication;

import ua.quiz.controller.command.Command;
import ua.quiz.model.dto.User;
import ua.quiz.model.excpetion.EmailAlreadyTakenException;
import ua.quiz.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

public class RegistrationCommand implements Command {
    private final UserService userService;

    public RegistrationCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        final String email = request.getParameter("email");
        final String password = request.getParameter("password");
        final String passwordConfirm = request.getParameter("confirmPassword");
        final String name = request.getParameter("name");
        final String surname = request.getParameter("surname");
        request.setAttribute("role", "player");

        if (!Objects.equals(password, passwordConfirm)) {
            request.setAttribute("registrationMessage", "Password should match");
            return "registration?command=registrationForm";
        }

        final User user = User.builder()
                .withEmail(email)
                .withPassword(password)
                .withName(name)
                .withSurname(surname)
                .build();
        try {
            userService.register(user);
        }  catch (EmailAlreadyTakenException e) {
            request.setAttribute("registrationMessage", "Email is taken");
            return "register?command=registrationForm";
        }
        return "/";
    }
}
