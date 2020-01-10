package ua.quiz.controller.command.authentication;

import ua.quiz.controller.command.Command;
import ua.quiz.model.domain.Role;
import ua.quiz.model.domain.User;
import ua.quiz.model.excpetion.InvalidCredentialsExcpetion;
import ua.quiz.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogInCommand implements Command {

    private final UserService userService;

    public LogInCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
         final String email = request.getParameter("email");
         final String password = request.getParameter("password");

         User user;

         try {
             user = userService.login(email, password);
         } catch (InvalidCredentialsExcpetion e) {
             request.setAttribute("loginMessage", "Invalid user credentials");
             return "login?Command=LoginForm";
         }

         final Role role = user.getRole();

         if (role == Role.PLAYER) {
             return "player";
         } else {
             return "judge";
         }
    }
}