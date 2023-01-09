package at.domain314.backend.repositories;

import at.domain314.backend.database.DataBase;
import at.domain314.models.cards.Card;
import at.domain314.models.cards.CardPackage;
import at.domain314.models.cards.Element;
import at.domain314.models.cards.Type;
import at.domain314.models.users.Player;
import at.domain314.utils.Constants;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static at.domain314.game.CardGenerator.generatePackage;

public class PackageRepo {

    public List<Card> createPackage(Card[] cards) {
        List<Card> cardsList;
        if (cards == null) {
            cardsList = generatePackage();
        } else {
            cardsList = new ArrayList<>();
            for (Card card : cards) {
                if (card.getName().contains("Water")) { card.setElement(Element.WATER); }
                else if (card.getName().contains("Fire")) { card.setElement(Element.FIRE); }
                else { card.setElement(Element.NORMAL); }

                if (card.getName().contains("Goblin")) { card.setType(Type.GOBLIN); }
                if (card.getName().contains("Ork")) { card.setType(Type.ORK); }
                if (card.getName().contains("Dragon")) { card.setType(Type.DRAGON); }
                if (card.getName().contains("Spell")) { card.setType(Type.SPELL); }
                cardsList.add(card);
            }
        }
        return cardsList;
    }

    public int uploadPackage(List<String> cardIDs) {
        int id = Constants.RANDOM.nextInt(10000000, 99999999);
        String sql = """
                INSERT INTO packages (package_id, cards, created_on) VALUES (?,?,?);
                """;
        try {
            PreparedStatement statement = DataBase.getConnection().prepareStatement(sql);
            statement.setInt(1, id);
            statement.setArray(2, DataBase.getConnection().createArrayOf("VARCHAR", cardIDs.toArray()));
            statement.setTimestamp(3, Timestamp.from(Instant.now()));

            statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 1;
    }

    public CardPackage getOneAvailablePackage() {
        String sql = """
                SELECT * FROM packages WHERE created_on = (SELECT MIN(created_on) FROM packages) LIMIT 1;
                """;
        try {
            PreparedStatement statement = DataBase.getConnection().prepareStatement(sql);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                return new CardPackage(results.getInt(1), results.getArray(2));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public int transferPackage(CardPackage cardPackage, Player player) {
        if (cardPackage.getCardIDs().size() != Constants.CARDS_PER_PACKAGE) { return 0; }

        String sql = """
                DELETE FROM packages WHERE package_id = ?; 
                UPDATE players
                SET stack = array_cat(stack, ?), credits = ?
                WHERE user_id = ?;
                """;
        try {
            PreparedStatement statement = DataBase.getConnection().prepareStatement(sql);
            statement.setInt(1, cardPackage.getID());
            statement.setArray(2, DataBase.getConnection().createArrayOf("VARCHAR", cardPackage.getCardIDs().toArray()));
            statement.setInt(3, player.getCredits()-5);
            statement.setInt(4, player.getID());

            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 1;
    }
}
