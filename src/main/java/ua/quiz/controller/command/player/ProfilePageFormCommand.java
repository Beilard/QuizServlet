package ua.quiz.controller.command.player;

import ua.quiz.controller.command.Command;
import ua.quiz.model.dto.Game;
import ua.quiz.model.dto.Role;
import ua.quiz.model.dto.User;
import ua.quiz.model.service.GameService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ProfilePageFormCommand implements Command {
    private static final Long DEFAULT_PAGE_NUMBER = 1L;
    private static final Long DEFAULT_RECORDS_PER_PAGE = 10L;
    private final GameService gameService;

    public ProfilePageFormCommand(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        Long currentPage = convertParameterToLong(request, "page");

        Long numberOfElements = gameService.countAllEntries();

        request.getSession().setAttribute("allGames", gameService.findAll(currentPage, DEFAULT_RECORDS_PER_PAGE));
        request.getSession().setAttribute("countOfElements", getCountElement(DEFAULT_RECORDS_PER_PAGE, numberOfElements));

        if (user.getRole() == Role.PLAYER) {
            return "/game?command=playerPageForm";
        } else if (user.getRole() == Role.JUDGE) {
            return "/game?command=judgePageForm";
        }
        return "/game?command=registrationForm";
    }

    private Long convertParameterToLong(HttpServletRequest request, String parameterName) {
        String currentPage = request.getParameter(parameterName);
        if (currentPage == null) {
            return DEFAULT_PAGE_NUMBER;
        }
        return Long.parseLong(currentPage);
    }

    private Long getCountElement(Long recordsPerPage, Long countRow) {
        return (countRow % recordsPerPage == 0) ? countRow / recordsPerPage : countRow / recordsPerPage + 1;
    }
}
