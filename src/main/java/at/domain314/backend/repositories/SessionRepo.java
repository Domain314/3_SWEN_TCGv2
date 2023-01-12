package at.domain314.backend.repositories;

import at.domain314.backend.database.DataBase;
import at.domain314.models.users.User;
import at.domain314.utils.Constants;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SessionRepo {
    public int login(User user) {
        if (!userPassword(user)) {
            return 1;
        }
        user.setToken(Constants.createToken(user.getUsername()));
        String sql = """
                UPDATE users SET last_token=? WHERE username=?
                """;
        try {
            PreparedStatement statement = DataBase.getConnection().prepareStatement(sql);
            statement.setString(1, user.getToken());
            statement.setString(2, user.getUsername());
            statement.execute();
        } catch (SQLException e) {
            return 0;
        }
        return 2;
    }

    private boolean userPassword(User user) {
        String sql = """
                SELECT count(*) FROM users WHERE username=? AND password=?
                """;
        try {
            PreparedStatement statement = DataBase.getConnection().prepareStatement(sql);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                if (results.getInt(1) > 0) {
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
