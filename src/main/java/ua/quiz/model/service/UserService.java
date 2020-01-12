package ua.quiz.model.service;

import ua.quiz.model.dto.User;

public interface UserService {
    void register(User user);
    User login(String email, String password);

}
