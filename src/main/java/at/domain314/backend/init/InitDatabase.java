package at.domain314.backend.init;

import at.domain314.backend.database.DataBase;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InitDatabase {
    private static String initDBSQL = """
            CREATE DATABASE tcg;
            """;
    private static String initTablesSQL = """
            CREATE TABLE IF NOT EXISTS users (
            	user_id INTEGER PRIMARY KEY,
            	username VARCHAR ( 50 ) UNIQUE NOT NULL,
            	password VARCHAR ( 50 ) NOT NULL,
            	last_token VARCHAR ( 50 ) NOT NULL,
            	created_on TIMESTAMP NOT NULL
            );
            CREATE TABLE IF NOT EXISTS players (
            	user_id INTEGER PRIMARY KEY,
            	name VARCHAR (50),
            	bio VARCHAR (100),
            	image VARCHAR (100),
            	credits INTEGER DEFAULT 20,
            	deck VARCHAR (50)[],
            	stack VARCHAR (50)[],
            	games_counter INTEGER DEFAULT 0,
            	win_counter INTEGER DEFAULT 0,
            	elo INTEGER DEFAULT 1500
            );
            CREATE TABLE IF NOT EXISTS cards (
            	card_id VARCHAR (50) PRIMARY KEY,
            	name VARCHAR (50),
            	description VARCHAR (100),
            	damage INTEGER,
            	type VARCHAR (20),
            	element VARCHAR (20)
            );
            CREATE TABLE IF NOT EXISTS packages (
            	package_id INTEGER PRIMARY KEY,
            	cards VARCHAR (50)[],
            	created_on TIMESTAMP NOT NULL
            );
            CREATE TABLE IF NOT EXISTS score (
            	user_id INTEGER PRIMARY KEY,
            	name VARCHAR (50),
            	elo INTEGER
            );
            """;

    public static void createDatabase() {
        try {
            PreparedStatement statement = DataBase.getConnection().prepareStatement(initDBSQL);
            statement.execute();
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void createTables() {
        try {
            PreparedStatement statement = DataBase.getConnection().prepareStatement(initTablesSQL);
            statement.execute();
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
