package ua.quiz.model.service.impl;

import org.apache.log4j.Logger;
import sun.rmi.runtime.Log;
import ua.quiz.model.dao.TeamDao;
import ua.quiz.model.dto.Team;
import ua.quiz.model.dto.User;
import ua.quiz.model.service.TeamService;

public class TeamServiceImpl implements TeamService {
    private static final Logger LOGGER = Logger.getLogger(TeamServiceImpl.class);

    private final TeamDao teamDao;

    public TeamServiceImpl(TeamDao teamDao) {
        this.teamDao = teamDao;
    }

    @Override
    public Team createTeam(User captain) {
        return null;
    }
}
