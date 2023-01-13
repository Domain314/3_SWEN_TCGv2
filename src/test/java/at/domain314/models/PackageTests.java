package at.domain314.models;

import at.domain314.game.CardGenerator;
import at.domain314.models.cards.Card;
import at.domain314.models.cards.CardPackage;
import at.domain314.testUtils.Logger;
import at.domain314.utils.Constants;
import org.junit.jupiter.api.*;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class PackageTests {

    @Test
    void createEmptyPackage() {
        Logger.printHeader("Create Package");
        CardPackage cardPackage = new CardPackage(1234, new ArrayList<>());
        Assertions.assertEquals(1234, cardPackage.getID());
        Logger.print(true);
    }

    @Test
    void createFullPackage() {
        Logger.printHeader("Create Package");
        List<String> cardIDs = new ArrayList<>();
        cardIDs.add("abc");
        cardIDs.add("def");
        cardIDs.add("ghi");
        cardIDs.add("jkl");
        CardPackage cardPackage = new CardPackage(1234, cardIDs);
        Assertions.assertEquals("abc", cardPackage.getCardIDs().get(0));
        Assertions.assertEquals("def", cardPackage.getCardIDs().get(1));
        Assertions.assertEquals("ghi", cardPackage.getCardIDs().get(2));
        Assertions.assertEquals("jkl", cardPackage.getCardIDs().get(3));
        Logger.print(true);
    }

    @Test
    void createRandomPackage() {
        Logger.printHeader("Create Package");
        List<Card> rndPackage = CardGenerator.generatePackage();
        Assertions.assertEquals(Constants.CARDS_PER_PACKAGE, rndPackage.size());
        Logger.print(true);
    }



}
