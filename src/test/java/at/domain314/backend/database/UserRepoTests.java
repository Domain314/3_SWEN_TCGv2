package at.domain314.backend.database;

import at.domain314.backend.repositories.UserRepo;
import at.domain314.models.users.Player;
import at.domain314.models.users.User;
import at.domain314.testUtils.Creator;
import at.domain314.testUtils.Logger;
import org.junit.jupiter.api.*;

import java.sql.SQLException;

@TestMethodOrder(MethodOrderer.DisplayName.class)
public class UserRepoTests {
    UserRepo userRepo = new UserRepo();

    @BeforeAll
    static void prepareDB() throws SQLException {
        DataBase.getConnection().setAutoCommit(false);
    }

    @AfterAll
    static void rollbackDB() throws SQLException {
        DataBase.getConnection().rollback();
    }

    @Test
    void test1() {
        Logger.printHeader("Create User in DB");
        User testUser = Creator.createUser();
        Assertions.assertEquals(1, this.userRepo.create(testUser));
        Assertions.assertEquals(0, this.userRepo.create(testUser));
        Logger.print(testUser);
        Logger.print(true);
    }

    @Test
    void test2() {
        Logger.printHeader("Get ID for Token");
        User testUser = Creator.createUser();
        Assertions.assertEquals(0, this.userRepo.getIdForToken(testUser.getUsername(), testUser.getToken()));
        Assertions.assertEquals(0, this.userRepo.getIdForToken(testUser.getToken()));
        Logger.print(testUser.getToken());
        Logger.print(true);
    }

    @Test
    void test3() throws SQLException {
        Logger.printHeader("Update User in DB");
        User testUser = Creator.createUser();
        testUser.setAll(-1, "ROXXOR", "NEW BIO", ":D", 420, Creator.createArray(), Creator.createArray(), 5, 5, 6969  );
        Player testedPlayer = this.userRepo.update(testUser);
        Assertions.assertEquals("ROXXOR", testedPlayer.getName());
        Assertions.assertEquals("NEW BIO", testedPlayer.getBio());
        Assertions.assertEquals(":D", testedPlayer.getImage());
        Assertions.assertEquals(420, testedPlayer.getCredits());
        Assertions.assertEquals(5, testedPlayer.getGamesCounter());
        Assertions.assertEquals(5, testedPlayer.getWinCounter());
        Assertions.assertEquals(6969, testedPlayer.getElo());
        Logger.print(testedPlayer);
        Logger.print(true);
    }




}
