package at.domain314.models;

import at.domain314.models.cards.Deck;
import at.domain314.models.users.Player;
import at.domain314.testUtils.Creator;
import at.domain314.testUtils.Logger;
import at.domain314.utils.Constants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class PlayerTests {
    @Test
    void createPlayer() throws SQLException {
        Logger.printHeader("Create Player");
        Player testPlayer = Creator.createPlayer();
        Assertions.assertEquals(-1, testPlayer.getID());
        Assertions.assertEquals("ROXXOR", testPlayer.getName());
        Assertions.assertEquals("NEW BIO", testPlayer.getBio());
        Assertions.assertEquals(":D", testPlayer.getImage());
        Assertions.assertEquals(420, testPlayer.getCredits());
        Assertions.assertEquals(6969, testPlayer.getElo());
        Assertions.assertEquals(5, testPlayer.getGamesCounter());
        Assertions.assertEquals(5, testPlayer.getWinCounter());
        Logger.print(true);
    }

    @Test
    void setPlayer() throws SQLException {
        Logger.printHeader("Set Player");
        Player testPlayer = new Player();
        testPlayer.setAll(-12, "ROXXOR2", "NEW BIO2", ":D2", 4202, Creator.createArray(), Creator.createArray(), 52, 52, 69692);
        Assertions.assertEquals(-12, testPlayer.getID());
        Assertions.assertEquals("ROXXOR2", testPlayer.getName());
        Assertions.assertEquals("NEW BIO2", testPlayer.getBio());
        Assertions.assertEquals(":D2", testPlayer.getImage());
        Assertions.assertEquals(4202, testPlayer.getCredits());
        Assertions.assertEquals(69692, testPlayer.getElo());
        Assertions.assertEquals(52, testPlayer.getGamesCounter());
        Assertions.assertEquals(52, testPlayer.getWinCounter());
        Logger.print(true);
    }

    @Test
    void setPlayerSingle() {
        Logger.printHeader("Set Player Single");
        Player testPlayer = new Player();
        testPlayer.setID(-13);
        testPlayer.setName("ROXXOR3");
        testPlayer.setBio("NEW BIO3");
        testPlayer.setImage(":D3");
        testPlayer.setCredits(4203);
        testPlayer.setElo(69693);
        testPlayer.setGamesCounter(53);
        testPlayer.setWinCounter(53);
        testPlayer.changeElo(-1000);
        Assertions.assertEquals(-13, testPlayer.getID());
        Assertions.assertEquals("ROXXOR3", testPlayer.getName());
        Assertions.assertEquals("NEW BIO3", testPlayer.getBio());
        Assertions.assertEquals(":D3", testPlayer.getImage());
        Assertions.assertEquals(4203, testPlayer.getCredits());
        Assertions.assertEquals(68693, testPlayer.getElo());
        Assertions.assertEquals(53, testPlayer.getGamesCounter());
        Assertions.assertEquals(53, testPlayer.getWinCounter());
        Logger.print(true);
    }

    @Test
    void playerLose() {
        Logger.printHeader("Player Lose");
        Player testPlayer = new Player();
        Deck enemyDeck = Creator.createDeck(1, Constants.CARDS_PER_DECK);
        testPlayer.setStackIDs(Creator.createCardIDs(2, 10));
        testPlayer.setDeck(Creator.createDeck(3, Constants.CARDS_PER_DECK));
        testPlayer.endGame(10, enemyDeck);
        Assertions.assertEquals(10, testPlayer.getElo());
        Assertions.assertEquals(1, testPlayer.getGamesCounter());
        Assertions.assertEquals(0, testPlayer.getWinCounter());
        Logger.print(true);
    }

    @Test
    void playerWin() {
        Logger.printHeader("Player Lose");
        Player testPlayer = new Player();
        testPlayer.setStackIDs(Creator.createCardIDs(1, 10));
        testPlayer.setDeck(Creator.createDeck(2, Constants.CARDS_PER_DECK));
        testPlayer.endGame(20, true);
        Assertions.assertEquals(20, testPlayer.getElo());
        Assertions.assertEquals(1, testPlayer.getGamesCounter());
        Assertions.assertEquals(1, testPlayer.getWinCounter());
        Logger.print(true);
    }
}
