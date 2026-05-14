package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testUserGettersAndSetters() {
        User user = new User();
        user.setId(1);
        user.setLogin("testUser");
        user.setPassword("pass123");
        user.setAge(25);
        user.setSex("Male");
        user.setLanguage("Polish");
        user.setServer("EUNE");

        assertEquals(1, user.getId());
        assertEquals("testUser", user.getLogin());
        assertEquals("pass123", user.getPassword());
        assertEquals(25, user.getAge());
        assertEquals("Male", user.getSex());
        assertEquals("Polish", user.getLanguage());
        assertEquals("EUNE", user.getServer());
    }
}
