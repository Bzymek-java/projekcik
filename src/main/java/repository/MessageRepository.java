package repository;

import model.Message;
import model.User;

import java.util.List;

public interface MessageRepository {

    void save(Message message);

    List<Message> findByUser(User user);
}