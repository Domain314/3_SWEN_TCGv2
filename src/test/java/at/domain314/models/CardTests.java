package at.domain314.models;

import at.domain314.models.cards.Card;
import at.domain314.models.cards.Element;
import at.domain314.models.cards.Type;
import at.domain314.testUtils.Logger;
import org.junit.jupiter.api.*;

public class CardTests {
    @Test
    void CreateCardEnums() {
        Logger.printHeader("Create Card with Enums");
        Card testCard = new Card("1234abc", "TEST_CARD", "CARD_DESCR", 100, Type.SPELL, Element.ICE);
        Assertions.assertEquals("1234abc", testCard.getID());
        Assertions.assertEquals("TEST_CARD", testCard.getName());
        Assertions.assertEquals("CARD_DESCR", testCard.getDescription());
        Assertions.assertEquals(100, testCard.getDamage());
        Assertions.assertEquals(Type.SPELL, testCard.getType());
        Assertions.assertEquals(Element.ICE, testCard.getElement());
        Logger.print(true);
    }

    @Test
    void CreateCardStrings() {
        Logger.printHeader("Create Card with Strings");
        Card testCard = new Card("1234abc2", "TEST_CARD2", "CARD_DESCR2", 100, "ORK", "WIND");
        Assertions.assertEquals("1234abc2", testCard.getID());
        Assertions.assertEquals("TEST_CARD2", testCard.getName());
        Assertions.assertEquals("CARD_DESCR2", testCard.getDescription());
        Assertions.assertEquals(100, testCard.getDamage());
        Assertions.assertEquals(Type.ORK, testCard.getType());
        Assertions.assertEquals(Element.WIND, testCard.getElement());
        Logger.print(true);
    }

    @Test
    void SetCard() {
        Logger.printHeader("Create Card");
        Card testCard = new Card();
        testCard.setID("1234abc");
        testCard.setName("TEST_CARD");
        testCard.setDescription("CARD_DESCR");
        testCard.setDamage(100);
        Assertions.assertEquals("1234abc", testCard.getID());
        Assertions.assertEquals("TEST_CARD", testCard.getName());
        Assertions.assertEquals("CARD_DESCR", testCard.getDescription());
        Assertions.assertEquals(100, testCard.getDamage());
        Logger.print(true);
    }
}
