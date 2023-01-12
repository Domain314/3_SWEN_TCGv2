package at.domain314.backend.repositories;

import at.domain314.backend.database.DataBase;
import at.domain314.models.users.Player;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class BattleRepo {

//    Update both players stats and elo
    public void updateAfterBattle(List<Player> players) {

        String sql = """
                UPDATE players SET games_counter = ?, win_counter = ?, elo = ?, deck = ?, stack = ? where user_id = ?;
                UPDATE score SET elo = ? WHERE user_id = ?;
                UPDATE players SET games_counter = ?, win_counter = ?, elo = ?, deck = ?, stack = ? where user_id = ?;
                UPDATE score SET elo = ? WHERE user_id = ?;
                """;
        try {
            Player player = players.get(0);
            PreparedStatement statement = DataBase.getConnection().prepareStatement(sql);
            statement.setInt(1, player.getGamesCounter());
            statement.setInt(2, player.getWinCounter());
            statement.setInt(3, player.getElo());
            statement.setArray(4, DataBase.getConnection().createArrayOf("VARCHAR", player.getDeckIDs().toArray()));
            statement.setArray(5, DataBase.getConnection().createArrayOf("VARCHAR", player.getStackIDs().toArray()));
            statement.setInt(6, player.getID());

            statement.setInt(7, player.getElo());
            statement.setInt(8, player.getID());

            player = players.get(1);
            statement.setInt(9, player.getGamesCounter());
            statement.setInt(10, player.getWinCounter());
            statement.setInt(11, player.getElo());
            statement.setArray(12, DataBase.getConnection().createArrayOf("VARCHAR", player.getDeckIDs().toArray()));
            statement.setArray(13, DataBase.getConnection().createArrayOf("VARCHAR", player.getStackIDs().toArray()));
            statement.setInt(14, player.getID());

            statement.setInt(15, player.getElo());
            statement.setInt(16, player.getID());

            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
