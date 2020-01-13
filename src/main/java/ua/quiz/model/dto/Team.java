package ua.quiz.model.dto;

import java.util.Objects;

public class Team {
    private Long id;
    private final String teamName;
    private final Long captainId;

    public Team(String teamName, Long captainId) {
        this.teamName = teamName;
        this.captainId = captainId;
    }

    public Long getId() {
        return id;
    }

    public Long getCaptainId() {
        return captainId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Team)) {
            return false;
        }
        Team team = (Team) o;
        return id.equals(team.id) &&
                Objects.equals(teamName, team.teamName) &&
                Objects.equals(captainId, team.captainId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, teamName, captainId);
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", teamName='" + teamName + '\'' +
                ", captainId=" + captainId +
                '}';
    }
}
