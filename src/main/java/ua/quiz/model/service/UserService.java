package ua.quiz.model.service;

import ua.quiz.model.dto.User;

import java.util.List;

public interface UserService {
    void register(User user);

    User login(String email, String password);

    List<User> findByTeamId(Long teamId);

    User findByEmail(String email);

    void update(User user);
}
