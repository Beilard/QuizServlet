package ua.quiz.controller.command.judge;

import org.apache.log4j.Logger;
import ua.quiz.controller.command.Command;
import ua.quiz.model.dto.Role;
import ua.quiz.model.dto.User;
import ua.quiz.model.service.GameService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewAllGamesCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(ViewAllGamesCommand.class);

    private static final Long DEFAULT_PAGE_NUMBER = 1L;
    private static final Long DEFAULT_RECORDS_PER_PAGE = 10L;

    private final GameService gameService;

    public ViewAllGamesCommand(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");

        if (user.getRole() != Role.JUDGE) {
            LOGGER.info("Not judge user managed to click show all games");
            return "/game?command=playerPageForm";
        }
        Long currentPage = convertParameterToLong(request);

        Long numberOfElements = gameService.countAllEntries();

        request.getSession().setAttribute("allGames", gameService.findAll(currentPage, DEFAULT_RECORDS_PER_PAGE));
        request.getSession().setAttribute("countOfElements", getCountElement(numberOfElements));
        return "/game?command=judge-viewAllGamesForm&page=1";
    }

    private Long convertParameterToLong(HttpServletRequest request) {
        String currentPage = request.getParameter("page");
        if (currentPage == null) {
            return DEFAULT_PAGE_NUMBER;
        }
        return Long.parseLong(currentPage) <= 0 ? DEFAULT_PAGE_NUMBER : Long.parseLong(currentPage);
    }

    private Long getCountElement(Long countRow) {
        return (countRow % DEFAULT_RECORDS_PER_PAGE == 0) ? countRow / DEFAULT_RECORDS_PER_PAGE : countRow / DEFAULT_RECORDS_PER_PAGE + 1;
    }
}
