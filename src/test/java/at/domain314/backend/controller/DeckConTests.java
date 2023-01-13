package at.domain314.backend.controller;

import at.domain314.backend.httpserver.server.Response;
import at.domain314.backend.repositories.DeckRepo;
import at.domain314.models.users.Player;
import at.domain314.testUtils.Logger;
import at.domain314.utils.Constants;
import org.junit.jupiter.api.*;

public class DeckConTests {
    @Test
    void updateDeckWR() {
        Logger.printHeader("Battle Con - Queue test");
        DeckController deckController = new DeckController(new DeckRepo());
        Response response = deckController.updateDeckWithCards(new String[0], new Player());
        Assertions.assertEquals(Constants.RESPONSE_BAD_CARDS_NOT_4, response.getContent());
        Logger.print(true);
    }
}
