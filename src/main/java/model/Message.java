package model;

import java.time.LocalDateTime;

public class Message {
    private int id;
    private String text;
    private LocalDateTime timestamp;
    private User sender;
    private User recipient;

    public Message(){ }
    public Message(int id, LocalDateTime timestamp, User sender, User recipient,String text) {
        this.id = id;
        this.timestamp = timestamp;
        this.sender = sender;
        this.recipient = recipient;
        this.text = text;
    }
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public String getText() {return text;}
    public void setText(String text) {this.text = text;}
    public LocalDateTime getTimestamp() {return timestamp;}
    public void setTimestamp(LocalDateTime timestamp) {this.timestamp = timestamp;}
    public User getSender() {return sender;}
    public void setSender(User sender) {this.sender = sender;}
    public User getRecipient() {return recipient;}
    public void setRecipient(User recipient) {this.recipient = recipient;}
    //toString do testowania
    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", timestamp=" + timestamp +
                ", sender=" + sender +
                ", recipient=" + recipient +
                '}';
    }
}