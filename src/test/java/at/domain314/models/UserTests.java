package at.domain314.models;

import at.domain314.models.users.Player;
import at.domain314.models.users.User;
import at.domain314.testUtils.Creator;
import at.domain314.testUtils.Logger;
import org.junit.jupiter.api.*;


public class UserTests {
    @Test
    void createUser() {
        Logger.printHeader("Create User");
        User testUser = new User();
        Assertions.assertEquals(0, testUser.getUserID());
        Logger.print(true);
    }

    @Test
    void SetUser() {
        Logger.printHeader("Set User");
        User testUser = new User("TEST", "12345");
        Assertions.assertEquals("TEST", testUser.getUsername());
        Assertions.assertEquals("12345", testUser.getPassword());
        Logger.print(true);
    }

    @Test
    void SetUserSingle() {
        Logger.printHeader("Set User Single");
        User testUser = new User("TEST", "12345");
        testUser.setUserID(123);
        testUser.setUsername("EDITED");
        testUser.setToken("TOKEN_123");
        testUser.resetPassword();
        Assertions.assertEquals(123, testUser.getUserID());
        Assertions.assertEquals("EDITED", testUser.getUsername());
        Assertions.assertEquals("--", testUser.getPassword());
        Assertions.assertEquals("TOKEN_123", testUser.getToken());
        Logger.print(true);
    }
}
