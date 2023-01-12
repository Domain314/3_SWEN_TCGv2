package at.domain314.backend.database;

import at.domain314.backend.repositories.SessionRepo;
import at.domain314.backend.repositories.UserRepo;
import at.domain314.models.users.User;
import at.domain314.testUtils.Creator;
import at.domain314.testUtils.Logger;
import org.junit.jupiter.api.*;

import java.sql.SQLException;

public class SessionRepoTests {
    SessionRepo sessionRepo = new SessionRepo();
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
        Logger.print(true);
    }

    @Test
    void test2() {
        Logger.printHeader("Login User in DB");
        User testUser = Creator.createUser();
        Assertions.assertEquals(2, this.sessionRepo.login(testUser));
        testUser.resetPassword();
        Assertions.assertEquals(1, this.sessionRepo.login(testUser));
        Logger.print(true);
    }
}
