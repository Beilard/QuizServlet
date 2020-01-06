package ua.quiz.model.entity;

import java.util.List;
import java.util.Objects;

public class TeamEntity {
    private final Long id;
    private final List<UserEntity> members;
    private final List<GameEntity> gameEntities;
    private final Long captainId;

    public TeamEntity(TeamEntityBuilder teamEntityBuilder) {
        this.id = teamEntityBuilder.id;
        this.members = teamEntityBuilder.members;
        this.gameEntities = teamEntityBuilder.gameEntities;
        this.captainId = teamEntityBuilder.captainId;
    }

    public static TeamEntityBuilder builder(){
        return new TeamEntityBuilder();
    }

    public Long getId() {
        return id;
    }

    public List<UserEntity> getMembers() {
        return members;
    }

    public Long getCaptainId() {
        return captainId;
    }

    public List<GameEntity> getGameEntities() {
        return gameEntities;
    }

    public static class TeamEntityBuilder {
        private Long id;
        private List<UserEntity> members;
        private List<GameEntity> gameEntities;
        private Long captainId;

        private TeamEntityBuilder() {
        }

        public TeamEntity build() {
            return new TeamEntity(this);
        }

        public TeamEntityBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public TeamEntityBuilder withMembers(List<UserEntity> members) {
            this.members = members;
            return this;
        }

        public TeamEntityBuilder withGames(List<GameEntity> gameEntities) {
            this.gameEntities = gameEntities;
            return this;
        }

        public TeamEntityBuilder withCaptainId(Long captainId) {
            this.captainId = captainId;
            return this;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TeamEntity)) {
            return false;
        }
        TeamEntity teamEntity = (TeamEntity) o;
        return id.equals(teamEntity.id) &&
                Objects.equals(members, teamEntity.members) &&
                Objects.equals(gameEntities, teamEntity.gameEntities) &&
                Objects.equals(captainId, teamEntity.captainId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, members, gameEntities, captainId);
    }

    @Override
    public String toString() {
        return  "id=" + id +
                ", members=" + members +
                ", games=" + gameEntities +
                ", captainId=" + captainId +
                '}';
    }
}
