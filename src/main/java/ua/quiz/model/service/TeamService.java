package ua.quiz.model.service;

import ua.quiz.model.dto.Team;
import ua.quiz.model.dto.User;

public interface TeamService {
    Team createTeam(User captain);
}
