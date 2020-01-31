package ua.quiz.controller.command.judge;

import ua.quiz.controller.command.Command;
import ua.quiz.model.dto.Game;
import ua.quiz.model.dto.Phase;
import ua.quiz.model.dto.Question;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PreparePhaseForReviewCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        final Game game = (Game) request.getSession().getAttribute("gameForReview");

        request.getSession().setAttribute("reviewedQuestion", getQuestion(game));
        request.getSession().setAttribute("reviewedPhase", getCurrentPhase(game));


        return "/game?command=judge-reviewForm";
    }

    private Phase getCurrentPhase(Game game) {
        return game.getPhases().get(game.getCurrentPhase());
    }

    private Question getQuestion(Game game) {
        return getCurrentPhase(game).getQuestion();
    }
}
