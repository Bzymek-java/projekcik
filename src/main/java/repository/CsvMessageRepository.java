package repository;

import model.Message;
import model.User;

import java.io.BufferedReader;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CsvMessageRepository implements MessageRepository {

    private final String filePath = "messages.csv";

    public CsvMessageRepository() {
        // ensure file exists
        try {
            File f = new File(filePath);
            if (!f.exists()) f.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Message> findAll() {
        List<Message> messagesList = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] data = line.split(",", -1);
                if (data.length < 5) continue;

                try {
                    int id = Integer.parseInt(data[0]);
                    LocalDateTime dateTime = LocalDateTime.parse(data[1]);
                    String senderLogin = unescape(data[2]);
                    String recipientLogin = unescape(data[3]);
                    String text = unescape(data[4]);

                    User sender = new User();
                    sender.setLogin(senderLogin);
                    User recipient = new User();
                    recipient.setLogin(recipientLogin);

                    Message message = new Message(id, dateTime, sender, recipient, text);
                    messagesList.add(message);
                } catch (Exception ignored) {
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return messagesList;
    }

    @Override
    public synchronized void save(Message message) {
        if (message == null) throw new IllegalArgumentException("message is null");
        
        List<Message> all = findAll();
        int maxId = 0;
        for (Message m : all) if (m.getId() > maxId) maxId = m.getId();
        if (message.getId() <= 0) message.setId(maxId + 1);

        String line = String.format(
                "%d,%s,%s,%s,%s",
                message.getId(),
                message.getTimestamp(),
                escape(message.getSender().getLogin()),
                escape(message.getRecipient().getLogin()),
                escape(message.getText())
        );
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            bw.write(line);
            bw.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Message> findByUser(User user) {
        List<Message> userMessages = new ArrayList<>();
        if (user == null) return userMessages;
        
        List<Message> all = findAll();
        for (Message m : all) {
            if (m.getSender().getLogin().equals(user.getLogin()) ||
                m.getRecipient().getLogin().equals(user.getLogin())) {
                userMessages.add(m);
            }
        }
        return userMessages;
    }

    private String escape(String s) {
        if (s == null) return "";
        return s.replace("\n", " ").replace(",", ";");
    }

    private String unescape(String s) {
        if (s == null) return "";
        return s;
    }
}