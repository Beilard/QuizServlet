package ua.quiz.model.entity;

import java.util.Objects;

public class TeamEntity {
    private final Long id;
    private final String teamName;
    private final Long captainId;

    public TeamEntity(Long id, String teamName, Long captainId) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TeamEntity)) {
            return false;
        }
        TeamEntity that = (TeamEntity) o;
        return id.equals(that.id) &&
                Objects.equals(teamName, that.teamName) &&
                Objects.equals(captainId, that.captainId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, teamName, captainId);
    }

    @Override
    public String toString() {
        return "TeamEntity{" +
                "id=" + id +
                ", teamName='" + teamName + '\'' +
                ", captainId=" + captainId +
                '}';
    }
}
