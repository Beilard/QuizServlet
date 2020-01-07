package ua.quiz.model.domain;

import java.util.List;
import java.util.Objects;

public class Team {
    private final Long id;
    private final List<User> members;
    private final String teamName;
    private final Long captainId;

    public Team(TeamBuilder teamBuilder) {
        this.id = teamBuilder.id;
        this.members = teamBuilder.members;
        this.captainId = teamBuilder.captainId;
        this.teamName = teamBuilder.teamName;
    }

    public static TeamBuilder builder(){
        return new TeamBuilder();
    }

    public Long getId() {
        return id;
    }

    public List<User> getMembers() {
        return members;
    }

    public Long getCaptainId() {
        return captainId;
    }

    public String getTeamName() {
        return teamName;
    }

    public static class TeamBuilder {
        private Long id;
        private List<User> members;
        private Long captainId;
        private String teamName;

        private TeamBuilder() {
        }

        public Team build() {
            return new Team(this);
        }

        public TeamBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public TeamBuilder withMembers(List<User> members) {
            this.members = members;
            return this;
        }

        public TeamBuilder withTeamName(String teamName) {
            this.teamName = teamName;
            return this;
        }

        public TeamBuilder withCaptainId(Long captainId) {
            this.captainId = captainId;
            return this;
        }
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
                Objects.equals(members, team.members) &&
                Objects.equals(teamName, team.teamName) &&
                Objects.equals(captainId, team.captainId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, members, teamName, captainId);
    }

    @Override
    public String toString() {
        return  "id=" + id +
                ", members=" + members +
                ", captainId=" + captainId +
                '}';
    }
}
