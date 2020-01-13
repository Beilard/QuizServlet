package ua.quiz.model.service.impl;

import org.apache.log4j.Logger;
import ua.quiz.model.dao.TeamDao;
import ua.quiz.model.dto.Team;
import ua.quiz.model.dto.User;
import ua.quiz.model.entity.TeamEntity;
import ua.quiz.model.service.TeamService;
import ua.quiz.model.service.mapper.TeamMapper;

import java.util.List;
import java.util.Optional;

public class TeamServiceImpl implements TeamService {
    private static final Logger LOGGER = Logger.getLogger(TeamServiceImpl.class);

    private final TeamDao teamDao;
    private final TeamMapper teamMapper;

    public TeamServiceImpl(TeamDao teamDao, TeamMapper teamMapper) {
        this.teamDao = teamDao;
        this.teamMapper = teamMapper;
    }

    @Override
    public Team createTeam(User captain, String teamName) {
        if (teamName.length() > 31 || captain == null)  {
            LOGGER.warn("Provided arguments are incorrect: " + teamName);
            throw new IllegalArgumentException("Provided arguments are incorrect: ");
        }

        final Long captainId = captain.getId();
        final Team team = new Team(teamName, captainId);

        teamDao.save(teamMapper.mapTeamToTeamEntity(team));

        return team;
    }

    @Override
    public void changeCaptain(Long teamId, User newCaptain) {
        if (teamId == null || newCaptain == null) {
            LOGGER.warn("New captain or teamId passed were null");
            throw new IllegalArgumentException("New captain or teamId passed were null");
        }
        final TeamEntity team = teamDao.findById(teamId).get();
        final TeamEntity modifiedTeam = new TeamEntity(team.getTeamName(), newCaptain.getId());
        modifiedTeam.setId(team.getId());

        teamDao.update(modifiedTeam);
    }

    @Override
    public Team findTeamByName(String name) {
        if (name == null) {
            LOGGER.warn("String provided for findByName was null");
            throw new IllegalArgumentException("String provided for findByName was null");
        }
        final TeamEntity teamEntity = teamDao.findByTeamName(name).get();

        return teamMapper.mapTeamEntityToTeam(teamEntity);
    }

    @Override
    public void deleteTeam(Long teamId) {
        if (teamId == null || teamId >= 0) {
            LOGGER.warn("Passed teamId is not valid");
            throw new IllegalArgumentException("Passed teamId is not valid");
        }

        teamDao.deleteById(teamId);
    }
}
