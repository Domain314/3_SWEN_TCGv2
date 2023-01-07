package at.domain314;

import java.io.IOException;
import java.sql.SQLException;

import at.domain314.backend.httpserver.server.Server;
import at.domain314.backend.httpserver.utils.Router;
import at.domain314.backend.services.*;

import static at.domain314.backend.init.InitDatabase.createDatabase;
import static at.domain314.backend.init.InitDatabase.createTables;


public class Main {
    public static void main(String[] args) throws SQLException {

//        createDatabase();
        createTables();

        Server server = new Server(10002, configureRouter());
        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println();
    }

    private static Router configureRouter() {
        Router router = new Router();
        router.addService("/users", new UserService());
        router.addService("/users/{username}", new UserService());
        router.addService("/sessions", new SessionService());
        router.addService("/packages", new PackageService());
        router.addService("/transactions", new PackageService());
        router.addService("/cards", new CardService());
        router.addService("/deck", new DeckService());
        router.addService("/battles", new BattleService());
        router.addService("/stats", new BattleService());
        router.addService("/score", new ScoreService());

        return router;
    }
}