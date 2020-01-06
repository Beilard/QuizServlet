package ua.quiz.model.service;

import ua.quiz.model.domain.User;

public interface UserService {
    User register(User user);
    User login(String email, String password);

}
