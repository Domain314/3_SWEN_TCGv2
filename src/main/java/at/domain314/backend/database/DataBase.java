package at.domain314.backend.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBase extends AbstractDataBase {

    private static Connection _connection = null;

    // Get connection function (use of openConnection) returning Type Connection
    // Docker env will use different url and password
    public static Connection getConnection() {
        if (_connection == null) {
            try {
                if (System.getenv("IS_DOCKER") != null) {
                    openConnection("jdbc:postgresql://postgresql:5432/tcg", "postgres", "123456");
                } else {
                    openConnection("jdbc:postgresql://localhost:5432/tcg", "postgres", "abc1234");
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return _connection;
    }

    public static void openConnection(String url, String user, String password) throws SQLException {
        _connection = DriverManager.getConnection(url, user, password);
    }

    public static void closeConnection() throws SQLException {
        _connection.close();
        _connection = null;
    }
}
