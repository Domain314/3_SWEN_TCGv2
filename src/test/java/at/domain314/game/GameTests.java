package at.domain314.game;

import at.domain314.models.users.Player;
import at.domain314.testUtils.Creator;
import at.domain314.testUtils.Logger;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;


public class GameTests {
    @Test
    void playGameWin() {
        Logger.printHeader("Play Game");
        List<Player> playerList = new ArrayList<>();
        playerList.add( new Player(
                1,
                "PLAYER_1",
                Creator.createDeck(10, 4),
                createIDList(1, 4),
                createIDList(1, 4),
                0,
                0,
                1500)
        );
        playerList.add( new Player(
                2,
                "PLAYER_2",
                Creator.createDeck(20, 4),
                createIDList(2, 4),
                createIDList(2, 4),
                0,
                0,
                1500)
        );
        Game testGame = new Game(playerList);
        Assertions.assertEquals("-",testGame.makeRound());
        Assertions.assertEquals("-",testGame.makeRound());
        Assertions.assertEquals("-",testGame.makeRound());
        Logger.print(true);
    }

    @Test
    void playGameDraw() {
        Logger.printHeader("Play Game");
        List<Player> playerList = new ArrayList<>();
        playerList.add( new Player(
                1,
                "PLAYER_1",
                Creator.createDeck(10, 4),
                createIDList(1, 4),
                createIDList(1, 4),
                0,
                0,
                1500)
        );
        playerList.add( new Player(
                2,
                "PLAYER_2",
                Creator.createDeck(10, 4),
                createIDList(1, 4),
                createIDList(1, 4),
                0,
                0,
                1500)
        );
        Game testGame = new Game(playerList);
        for (int i = 0; i < 99; i++) {
            Assertions.assertEquals("-",testGame.makeRound());
        }
        Assertions.assertEquals("Draw! - Rounds: 99",testGame.makeRound());
        Logger.print(true);
    }

    private List<String> createIDList(int playerNum, int amount) {
        List<String> newList = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            newList.add(String.format("%d%d", playerNum, i));
        }
        return newList;
    }
}
