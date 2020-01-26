package ua.quiz.model.service.mapper;

import ua.quiz.model.dto.Team;
import ua.quiz.model.entity.TeamEntity;

public class TeamMapper {
    public Team mapTeamEntityToTeam(TeamEntity teamEntity) {
        if (teamEntity == null) {
            return null;
        }
        Team team = new Team(teamEntity.getTeamName());
        team.setId(teamEntity.getId());
        return team;

    }

    public TeamEntity mapTeamToTeamEntity(Team team) {
        if (team == null) {
            return null;
        }

        TeamEntity teamEntity = new TeamEntity(team.getTeamName());
        teamEntity.setId(team.getId());
        return teamEntity;
    }

}
