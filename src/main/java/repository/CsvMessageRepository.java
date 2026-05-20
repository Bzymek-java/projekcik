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

public class CsvMessageRepository implements MessageRepository{

    private final String filePath;

    public CsvMessageRepository(String filePath) {
        this.filePath = filePath;
        // ensure file exists
        try {
            File f = new File(filePath);
            if (!f.exists()) f.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public synchronized void save(Message message) {
        if (message == null) throw new IllegalArgumentException("message is null");
        String line = String.format(
                "%d,%s,%s,%s,%s",
                message.getId(),
                message.getTimestamp(),
                message.getSender().getLogin(),
                message.getRecipient().getLogin(),
                message.getText()
        );
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath,true))){
            bw.write(line);
            bw.newLine();
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Message> findByUser(User user) {
        List<Message> messagesList = new ArrayList<>();
        if (user == null){return null;}
        try (BufferedReader br = Files.newBufferedReader(Paths.get(filePath))){

            String line;
            while ((line = br.readLine()) != null){
                if (line.trim().isEmpty()) continue;
                String[] data = line.split(",");
                int id = Integer.parseInt(data[0]);
                LocalDateTime dateTime = LocalDateTime.parse(data[1]);
                String senderLogin = data[2];
                String recipientLogin = data[3];
                String text = data[4];

                User sender = new User();
                sender.setLogin(senderLogin);
                User recipient = new User();
                recipient.setLogin(recipientLogin);

                if (senderLogin.equals(user.getLogin()) ||
                        recipientLogin.equals(user.getLogin())) {
                    Message message = new Message(
                            id,dateTime,sender,recipient,text
                    );
                    messagesList.add(message);
                }

            }

        }
        catch (IOException e){
            throw new RuntimeException(e);
        }
        return messagesList;
    }
}