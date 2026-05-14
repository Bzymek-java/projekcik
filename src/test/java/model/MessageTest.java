package model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class MessageTest {

    @Test
    void testMessageContentAndTimestamp() {
        Message message = new Message();
        LocalDateTime now = LocalDateTime.now();
        
        message.setText("Hello World");
        message.setTimestamp(now);
        
        assertEquals("Hello World", message.getText());
        assertEquals(now, message.getTimestamp());
    }

    @Test
    void testMessageSenderAndRecipient() {
        Message message = new Message();
        User sender = new User();
        sender.setLogin("senderUser");
        
        User recipient = new User();
        recipient.setLogin("recipientUser");
        
        message.setSender(sender);
        message.setRecipient(recipient);
        
        assertNotNull(message.getSender());
        assertEquals("senderUser", message.getSender().getLogin());
        assertNotNull(message.getRecipient());
        assertEquals("recipientUser", message.getRecipient().getLogin());
    }
}
