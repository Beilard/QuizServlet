package ua.quiz.model.service.mapper;

import ua.quiz.model.domain.Game;
import ua.quiz.model.domain.Team;
import ua.quiz.model.domain.User;
import ua.quiz.model.entity.GameEntity;
import ua.quiz.model.entity.TeamEntity;
import ua.quiz.model.entity.UserEntity;

import java.util.List;
import java.util.stream.Collectors;

public class TeamMapper {
    private final GameMapper gameMapper = new GameMapper();
    private final UserMapper userMapper = new UserMapper();

    public Team mapTeamEntityToTeam (TeamEntity teamEntity) {
        if (teamEntity == null) {
            return null;
        }
        return Team.builder()
                .withId(teamEntity.getId())
                .withCaptainId(teamEntity.getCaptainId())
                .withGames(gameEntityConverter(teamEntity.getGameEntities()))
                .withMembers(userEntityConverter(teamEntity.getMembers()))
                .build();
    }

    public TeamEntity mapTeamToTeamEntity (Team team) {
        return TeamEntity.builder()
                .withId(team.getId())
                .withCaptainId(team.getCaptainId())
                .withGames(gameConverter(team.getGames()))
                .withMembers(userConverter(team.getMembers()))
                .build();
    }

    private List<Game> gameEntityConverter(List<GameEntity> gameEntities){
        return gameEntities.stream()
                .map(gameMapper::mapGameEntityToGame)
                .collect(Collectors.toList());
    }

    private List<GameEntity> gameConverter(List<Game> games){
        return games.stream()
                .map(gameMapper::mapGameToGameEntity)
                .collect(Collectors.toList());
    }

    private List<User> userEntityConverter(List<UserEntity> userEntities){
        return userEntities.stream()
                .map(userMapper::mapUserEntityToUser)
                .collect(Collectors.toList());
    }

    private List<UserEntity> userConverter(List<User> users){
        return users.stream()
                .map(userMapper::mapUserToUserEntity)
                .collect(Collectors.toList());
    }
}
