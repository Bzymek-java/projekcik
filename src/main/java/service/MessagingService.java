package service;

import model.Message;
import model.User;
import repository.CsvMessageRepository;

import java.time.LocalDateTime;

public class MessagingService {
    private final CsvMessageRepository messageRepository;

    public MessagingService(CsvMessageRepository messageRepository) {this.messageRepository = messageRepository;}

    public void sendMessage(User recipient, User sender, String text){
        Message message = new Message();
        message.setSender(sender);
        message.setRecipient(recipient);
        message.setText(text);
        message.setTimestamp(LocalDateTime.now());
        messageRepository.save(message);
    }
}