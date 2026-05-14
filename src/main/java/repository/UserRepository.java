package repository;

import model.User;

import java.util.List;

public interface UserRepository {

    User findByLogin(String login);

    List<User> findAll();

    void save(User user);
}