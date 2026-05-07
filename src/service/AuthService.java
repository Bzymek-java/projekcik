package service;

import model.User;
import repository.UserRepository;

public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository repo) {
        this.userRepository = repo;
    }

    public void register(User user) {

        if (user == null) {
            throw new IllegalArgumentException("User is null");
        }

        if (userRepository.findByLogin(user.getLogin()) != null) {
            throw new IllegalArgumentException("Login already exists");
        }

        userRepository.save(user);
    }

    public User login(String login, String password) {

        User user = userRepository.findByLogin(login);

        if (user == null) {
            return null;
        }

        if (checkPassword(password, user.getPassword())) {
            return user;
        }

        return null;
    }

    private boolean checkPassword(String input, String stored) {
        return input.equals(stored);
    }
}