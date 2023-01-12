package at.domain314.backend.repositories;

import at.domain314.backend.database.DataBase;
import at.domain314.models.cards.Card;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CardRepo {

    public List<String> createCards(List<Card> cards) {
        String sql = "INSERT INTO cards (card_id, name, description, damage, type, element) VALUES ";
        List<String> cardIDs = new ArrayList<>();
        for (int i = 0; i < cards.size(); i++) {
            sql += createCardString(cards.get(i));
            if (i != cards.size() - 1) { sql += ","; }
            else { sql += ";"; }
            cardIDs.add(cards.get(i).getID());
        }

        try {
            PreparedStatement statement = DataBase.getConnection().prepareStatement(sql);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cardIDs;
    }

    public List<Card> getCards(List<String> cardIDs) {
        List<Card> cards = new ArrayList<>();
        String sql = "SELECT * FROM cards WHERE card_id IN (";
        for (int i = 0; i < cardIDs.size(); i++) {
            sql += "'" + cardIDs.get(i) + "'";
            if (i != cardIDs.size() - 1) { sql += ","; }
        }
        sql += ");";

        try {
            PreparedStatement statement = DataBase.getConnection().prepareStatement(sql);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                cards.add(new Card(
                        results.getString(1),
                        results.getString(2),
                        results.getString(3),
                        results.getInt(4),
                        results.getString(5),
                        results.getString(6)
                        ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cards;
    }

    private String createCardString(Card card) {
        return "('" + card.getID() + "','" + card.getName() + "','" + card.getDescription() + "'," + card.getDamage()  + ",'" + card.getType() + "','" + card.getElement() + "')";
    }
}
