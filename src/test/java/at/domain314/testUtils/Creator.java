package at.domain314.testUtils;

import at.domain314.backend.database.DataBase;
import at.domain314.models.cards.Card;
import at.domain314.models.cards.Deck;
import at.domain314.models.users.Player;
import at.domain314.models.users.User;
import at.domain314.utils.Constants;

import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Creator {

    public static final User createUser() {
        User testUser = new User("TEST", "123456789");
        testUser.setUserID(-1);
        testUser.setToken("Basic TEST-mtcgToken");
        return testUser;
    }

    public static final Player createPlayer() throws SQLException {
        return new Player(-1, "ROXXOR", "NEW BIO", ":D", 420, createArray(), createArray(), 5, 5, 6969);
    }

    public static final Array createArray() throws SQLException {
        return DataBase.getConnection().createArrayOf("VARCHAR", new ArrayList<>().toArray(new String[0]));
    }

    public static final ArrayList<String> createCardIDs(int id, int amount) {
        ArrayList<String> testDeck = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            testDeck.add(String.format("CARD_ID_%d-%d", id, id));
        }
        return testDeck;
    }

    public static final ArrayList<Card> createCards(int id, int amount) {
        ArrayList<Card> testDeck = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            testDeck.add(createCard(String.format("%d", id)));
        }
        return testDeck;
    }

    public static final Deck createDeck(int id, int amount) {
        return new Deck(createCards(id, amount));
    }

    public static final Card createCard(String id) {
        return new Card(id, String.format("CARD_%s", id), "DESCR", Integer.parseInt(id), "KNIGHT", "NORMAL");
    }

//    public static final Deck createDeck(int id, int amount, boolean ) {
//        return new Deck(createCards(id, amount));
//    }
}
