package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PreferenceTest {

    @Test
    void testPreferenceMatchesSuccess() {
        Preference p1 = new Preference();
        p1.setPreferedGame("LoL");
        p1.setPreferedVoiceChat(true);
        p1.setRank("Gold");

        Preference p2 = new Preference();
        p2.setPreferedGame("LoL");
        p2.setPreferedVoiceChat(true);
        p2.setRank("Gold");

        assertTrue(p1.matches(p2));
    }

    @Test
    void testPreferenceMatchesFailureDifferentGame() {
        Preference p1 = new Preference();
        p1.setPreferedGame("LoL");
        p1.setPreferedVoiceChat(true);

        Preference p2 = new Preference();
        p2.setPreferedGame("CS:GO");
        p2.setPreferedVoiceChat(true);

        assertFalse(p1.matches(p2));
    }
}
