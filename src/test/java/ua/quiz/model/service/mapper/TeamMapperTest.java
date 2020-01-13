package ua.quiz.model.service.mapper;

import org.junit.Test;
import ua.quiz.model.dto.Team;
import ua.quiz.model.entity.TeamEntity;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class TeamMapperTest {
    private static final Long ID = 0L;

    private static final String TEAM_NAME = "NAME";

    private static final Long CAPTAIN_ID = 1L;

    private final TeamMapper teamMapper = new TeamMapper();

    @Test
    public void mapEntityToTeamShouldReturnTeam() {
        final TeamEntity teamEntity = new TeamEntity(TEAM_NAME, CAPTAIN_ID);
        teamEntity.setId(ID);

        final Team team = teamMapper.mapTeamEntityToTeam(teamEntity);

        assertThat("mapping id has failed", team.getId(), is(ID));
        assertThat("mapping team name has failed", team.getTeamName(), is(TEAM_NAME));
        assertThat("mapping captainId has failed", team.getCaptainId(), is(CAPTAIN_ID));
    }

    @Test
    public void mapTeanToEntityShouldReturnEntity() {
        final Team team = new Team(TEAM_NAME, CAPTAIN_ID);
        team.setId(ID);

        final TeamEntity teamEntity = teamMapper.mapTeamToTeamEntity(team);

        assertThat("mapping id has failed", teamEntity.getId(), is(ID));
        assertThat("mapping team name has failed", teamEntity.getTeamName(), is(TEAM_NAME));
        assertThat("mapping captainId has failed", teamEntity.getCaptainId(), is(CAPTAIN_ID));
    }
}