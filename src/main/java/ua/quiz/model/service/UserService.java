package ua.quiz.model.service;

import ua.quiz.model.dto.User;

import java.util.List;

public interface UserService {
    void register(User user);

    User login(String email, String password);

    boolean joinTeam(User user, Long teamId);

    List<User> findByTeamId(Long teamId);
}
