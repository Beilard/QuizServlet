package ua.quiz.model.service;

import ua.quiz.model.dto.Team;
import ua.quiz.model.dto.User;

import java.util.List;

public interface TeamService {
    Team createTeam(User captain, String teamName);

    void changeCaptain(Long teamId, User newCaptain, User oldCaptain);

    Team findTeamByName(String name);

}
