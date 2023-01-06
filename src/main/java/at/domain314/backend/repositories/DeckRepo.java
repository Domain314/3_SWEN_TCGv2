package at.domain314.backend.repositories;

import at.domain314.backend.database.DataBase;
import at.domain314.models.cards.Card;
import at.domain314.models.users.Player;

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
            statement.setInt(2, id);

            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public boolean update(Player player) {

        String sql = """
                UPDATE players SET deck = ? WHERE user_id = ?;
                """;
        try {
            PreparedStatement statement = DataBase.getConnection().prepareStatement(sql);
            statement.setArray(1, DataBase.getConnection().createArrayOf("VARCHAR", player.getDeckIDs().toArray(new String[0])));
            statement.setInt(2, player.getID());

            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
