package at.domain314.backend.repositories;

import at.domain314.backend.database.DataBase;
import at.domain314.models.cards.Card;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeckRepo {
    public boolean update(String[] cards, int id) {

        String sql = """
                UPDATE players SET deck = ? WHERE user_id = ?;
                """;
        try {
            PreparedStatement statement = DataBase.getConnection().prepareStatement(sql);
            statement.setArray(1, DataBase.getConnection().createArrayOf("VARCHAR", cards));
            statement.setInt(1, id);

            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
