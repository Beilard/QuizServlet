package ua.quiz.controller.command.player;

import ua.quiz.controller.command.Command;
import ua.quiz.model.dto.Role;
import ua.quiz.model.dto.User;
import ua.quiz.model.service.GameService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        Long currentPage = convertParameterToLong(request);

        if (user.getTeamId() != null) {
            Long numberOfElements = gameService.countAllByTeamId(user.getTeamId());
            request.getSession().setAttribute("allGamesOfTeam", gameService.findAllByTeamId(user.getTeamId(), currentPage, DEFAULT_RECORDS_PER_PAGE));
            request.getSession().setAttribute("countOfElements", getCountElement(numberOfElements));
        }

        if (user.getRole() == Role.PLAYER || user.getRole() == Role.JUDGE) {
            return "/game?command=player-PageForm";
        }
        return "/game?command=registrationForm";
    }

    private Long convertParameterToLong(HttpServletRequest request) {
        String currentPage = request.getParameter("page");
        if (currentPage == null) {
            return DEFAULT_PAGE_NUMBER;
        }
        return Long.parseLong(currentPage);
    }

    private Long getCountElement(Long countRow) {
        return (countRow % ProfilePageFormCommand.DEFAULT_RECORDS_PER_PAGE == 0) ? countRow / ProfilePageFormCommand.DEFAULT_RECORDS_PER_PAGE : countRow / ProfilePageFormCommand.DEFAULT_RECORDS_PER_PAGE + 1;
    }
}
