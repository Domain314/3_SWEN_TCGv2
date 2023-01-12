package at.domain314.models;

import at.domain314.models.cards.Deck;
import at.domain314.testUtils.Creator;
import at.domain314.testUtils.Logger;
import org.junit.jupiter.api.*;

public class DeckTests {
    @Test
    void createDeck() {
        Logger.printHeader("Create Deck");
        Deck testDeck = Creator.createDeck(10, 4);
        Assertions.assertEquals(4, testDeck.getCards().size());
        Assertions.assertEquals(true, testDeck.isCardInCollection("10"));
        Logger.print(true);
    }

    @Test
    void drawCardsFromDeck() {
        Logger.printHeader("Draw Cards Deck");
        Deck testDeck = Creator.createDeck(10, 4);
        Assertions.assertEquals("10", testDeck.drawCard().getID());
        Assertions.assertEquals("10", testDeck.drawCard().getID());
        Assertions.assertEquals("10", testDeck.drawCard().getID());
        Assertions.assertEquals("10", testDeck.drawCard().getID());
        Assertions.assertEquals(null, testDeck.drawCard());
        Logger.print(true);
    }
}
