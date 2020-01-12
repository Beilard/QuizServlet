package ua.quiz.model.service.mapper;

import ua.quiz.model.dto.Team;
import ua.quiz.model.entity.TeamEntity;

public class TeamMapper {

    private final UserMapper userMapper = new UserMapper();

    public Team mapTeamEntityToTeam(TeamEntity teamEntity) {
        if (teamEntity == null) {
            return null;
        }
        return new Team(teamEntity.getId(), teamEntity.getTeamName(), teamEntity.getCaptainId());
    }

    public TeamEntity mapTeamToTeamEntity(Team team) {
        return new TeamEntity(team.getId(), team.getTeamName(), team.getCaptainId());
    }

}
