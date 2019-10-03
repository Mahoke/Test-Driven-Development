package nl.hanze.hive;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class StoneTest {
    @Test
    void ifthisthentrue(){
        Stone a = new Stone(Hive.Player.BLACK);
        Stone b = new Stone(Hive.Player.WHITE);

        assertTrue(a.equals(b));
    }
}
