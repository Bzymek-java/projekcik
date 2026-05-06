import java.time.LocalDateTime;
//zastanawialem sie dlaczego nic mi sie nie zaznacza, okazalo sie ze nie mialem zalaczonego modulu javy, mocne


public class Message extends User{
    //chyba pola clasy massage jdk =)
    private int id;
    private String text;
    private LocalDateTime timestamp;
    private User sender;
    private User recipient;

    //getery setery zobaczy które sie wywali
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
}