package at.domain314.backend.repositories;

import at.domain314.backend.database.DataBase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ScoreRepo {
    public String getScore() {
        String result = "";
        String sql = """
                SELECT name,elo FROM score ORDER BY elo DESC LIMIT 10;
                """;
        try {
            PreparedStatement statement = DataBase.getConnection().prepareStatement(sql);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                result += buildRow(results.getString(1),results.getInt(2));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String buildRow(String name, int elo) {
        switch(Integer.signum((name.length()-8))) {
            case -1: return name + "\t " + elo + "\n";
            case 0: return name + " " + elo + "\n";
            case 1: return name.substring(0,6) + ".. " + elo + "\n";
            default: return "-";
        }
    }
}
