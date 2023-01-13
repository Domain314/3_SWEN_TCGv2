package at.domain314.backend.controller;

import at.domain314.backend.httpserver.server.Response;
import at.domain314.backend.repositories.CardRepo;
import at.domain314.testUtils.Creator;
import at.domain314.testUtils.Logger;
import at.domain314.utils.Constants;
import org.junit.jupiter.api.*;

public class CardConTests {

    @Test
    void getCards() {
        Logger.printHeader("Battle Con - Queue test");
        CardController cardController = new CardController(new CardRepo());
        Response response = cardController.getCards(Creator.createCardIDs(10, 10));
        Assertions.assertEquals(Constants.RESPONSE_BAD_CARDS, response.getContent());
        Logger.print(true);
    }

    @Test
    void getCardsPlain() {
        Logger.printHeader("Battle Con - Queue test");
        CardController cardController = new CardController(new CardRepo());
        Response response = cardController.getCardsPlainFormat(Creator.createCardIDs(10, 10));
        Assertions.assertEquals(Constants.RESPONSE_BAD_CARDS, response.getContent());
        Logger.print(true);
    }

}
