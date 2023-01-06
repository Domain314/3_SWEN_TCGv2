package at.domain314.backend.repositories;

import at.domain314.backend.database.DataBase;
import at.domain314.models.users.Player;
import at.domain314.models.users.User;
import at.domain314.utils.Constants;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;

public class UserRepo {

    public int create(User user) {
        user.setUserID(Constants.RANDOM.nextInt(10000000, 99999999));
        if (playerExists(user)) {
            return 0;
        }
        user.setToken(Constants.createToken(user.getUsername()));
        String sql = """
                INSERT INTO users (user_id,username,password,last_token,created_on) VALUES (?,?,?,?,?);
                INSERT INTO players (user_id) VALUES (?);
                """;
        try {
            PreparedStatement statement = DataBase.getConnection().prepareStatement(sql);
            statement.setInt(1, user.getUserID());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getToken());
            statement.setTimestamp(5, Timestamp.from(Instant.now()));
            statement.setInt(6, user.getUserID());

            statement.execute();

            user.resetPassword();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 1;
    }

    public Player update(User user) {
        user.setUserID(getIdForToken(user.getUsername(), user.getToken()));
        String sql = """
                UPDATE players SET name = ?, bio = ?, image = ?, credits = ?, games_counter = ?, win_counter = ?, elo = ? where user_id=?
                """;
        try {
            PreparedStatement statement = DataBase.getConnection().prepareStatement(sql);
            statement.setString(1, user.getName());
            statement.setString(2, user.getBio());
            statement.setString(3, user.getImage());
            statement.setInt(4, user.getCredits());
            statement.setInt(5, user.getGamesCounter());
            statement.setInt(6, user.getWinCounter());
            statement.setInt(7, user.getElo());
            statement.setInt(8, user.getUserID());

            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return getPlayer(user);
    }

    public Player getPlayer(User user) {
        if (user.getUserID() == 0) {
            user.setUserID(getIdForToken(user.getToken()));
            if (user.getUserID() == 0) { return null; }
        }


        String sql = """
                select * from players where user_id=?
                """;
        try {
            PreparedStatement statement = DataBase.getConnection().prepareStatement(sql);
            statement.setInt(1, user.getUserID());
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                return new Player(
                        results.getInt(1),
                        results.getString(2),
                        results.getString(3),
                        results.getString(4),
                        results.getInt(5),
                        results.getArray(6),
                        results.getArray(7),
                        results.getInt(8),
                        results.getInt(9),
                        results.getInt(10)
                        );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return null;
    }

    public User getUser(User user) {
        if (user.getUserID() == 0) {
            user.setUserID(getIdForToken(user.getUsername(), user.getToken()));
        }
        if (user.getUserID() == 0) { return null; }

        String sql = """
                select * from players where user_id=?
                """;
        try {
            PreparedStatement statement = DataBase.getConnection().prepareStatement(sql);
            statement.setInt(1, user.getUserID());
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                user.setAll(
                        results.getInt(1),
                        results.getString(2),
                        results.getString(3),
                        results.getString(4),
                        results.getInt(5),
                        results.getArray(6),
                        results.getArray(7),
                        results.getInt(8),
                        results.getInt(9),
                        results.getInt(10)
                );
                return user;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    boolean playerExists(User user) {
        String sql = """ 
                SELECT count(*) FROM users WHERE username=?;
                """;
        try {
            PreparedStatement statement = DataBase.getConnection().prepareStatement(sql);
            statement.setString(1, user.getUsername());
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                return (results.getInt(1) > 0);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public boolean playerExists(String token) {
        String sql = """ 
                SELECT count(*) FROM users WHERE last_token=?;
                """;
        try {
            PreparedStatement statement = DataBase.getConnection().prepareStatement(sql);
            statement.setString(1, token);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                return (results.getInt(1) > 0);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public int getIdForToken(String username, String token) {
        int id = 0;
        String sql = """
                SELECT user_id FROM users WHERE username=? AND last_token=?
                """;
        try {
            PreparedStatement statement = DataBase.getConnection().prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, token);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                if (results.getInt(1) > 0) {
                    id = results.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }
    public int getIdForToken(String token) {
        int id = 0;
        String sql = """
                SELECT user_id FROM users WHERE last_token=?
                """;
        try {
            PreparedStatement statement = DataBase.getConnection().prepareStatement(sql);
            statement.setString(1, token);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                if (results.getInt(1) > 0) {
                    id = results.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }
}
