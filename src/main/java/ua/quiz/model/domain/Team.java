package ua.quiz.model.domain;

import java.util.List;
import java.util.Objects;

public class Team {
    private final Long id;
    private final List<User> members;
    private final List<Game> games;
    private final Long captainId;

    public Team(TeamBuilder teamBuilder) {
        this.id = teamBuilder.id;
        this.members = teamBuilder.members;
        this.games = teamBuilder.games;
        this.captainId = teamBuilder.captainId;
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

    public List<Game> getGames() {
        return games;
    }

    public static class TeamBuilder {
        private Long id;
        private List<User> members;
        private List<Game> games;
        private Long captainId;

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

        public TeamBuilder withGames(List<Game> games) {
            this.games = games;
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
                Objects.equals(games, team.games) &&
                Objects.equals(captainId, team.captainId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, members, games, captainId);
    }

    @Override
    public String toString() {
        return  "id=" + id +
                ", members=" + members +
                ", games=" + games +
                ", captainId=" + captainId +
                '}';
    }
}
