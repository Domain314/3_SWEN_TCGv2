package at.domain314.backend.controller;

import at.domain314.backend.httpserver.server.Response;
import at.domain314.backend.repositories.BattleRepo;
import at.domain314.models.cards.Deck;
import at.domain314.models.users.Player;
import at.domain314.testUtils.Creator;
import at.domain314.testUtils.Logger;
import at.domain314.utils.Constants;
import org.junit.jupiter.api.*;

import java.sql.SQLException;

public class BattleConTests {
    @Test
    void queueTest() throws SQLException  {
        Logger.printHeader("Battle Con - Queue test");
        Player player1 = Creator.createPlayer();
        player1.setDeck(new Deck(Creator.createCards(10, Constants.CARDS_PER_DECK)));
        player1.setDeckIDs(Creator.createCardIDs(10, Constants.CARDS_PER_DECK));
        BattleController battleController = new BattleController(new BattleRepo());
        Response response1 = battleController.queueForBattle(player1);
        Assertions.assertEquals("Queued up for battle.\n", response1.getContent());
        Logger.print(true);
    }

    @Test
    void queueForBattle() throws SQLException  {
        Logger.printHeader("Battle Con - Queue test");
        Player player1 = Creator.createPlayer();
        Player player2 = Creator.createPlayer();
        player1.setDeck(new Deck(Creator.createCards(10, Constants.CARDS_PER_DECK)));
        player1.setDeckIDs(Creator.createCardIDs(10, Constants.CARDS_PER_DECK));
        player2.setDeck(new Deck(Creator.createCards(20, Constants.CARDS_PER_DECK)));
        player2.setDeckIDs(Creator.createCardIDs(20, Constants.CARDS_PER_DECK));
        BattleController battleController = new BattleController(new BattleRepo());
        Response response1 = battleController.queueForBattle(player1);
        Response response2 = battleController.queueForBattle(player2);
        Assertions.assertEquals("Queued up for battle.\n", response1.getContent());
        Assertions.assertNotEquals("Queued up for battle.\n", response2.getContent());
        Logger.print(true);
    }

}
