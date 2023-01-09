package at.domain314.testUtils;

import at.domain314.models.users.Player;
import at.domain314.models.users.User;

public class Logger {

    public static void printHeader(String content) {
        System.out.println("Unit Test -- " + content);
    }

    public static void print(String content) {
        System.out.println(content);
    }

    public static void print(boolean isDone) {
        System.out.println("Test successfully finished.\n");
    }

    public static final void print(User user) {
        System.out.println( "-- Print User --\n" +
                "UserID: " + user.getUserID() + "\n" +
                "UserName: " + user.getUsername() + "\n" +
                "Password: " + user.getPassword() + "\n" +
                "token: " + user.getToken() + "\n" +
                "PlayerID: " + user.getID() + "\n" +
                "PlayerName: " + user.getName() + "\n" +
                "Bio: " + user.getBio() + "\n" +
                "Image: " + user.getImage() + "\n" +
                "Credits: " + user.getCredits() + "\n" +
                "Game counter: " + user.getGamesCounter() + "\n" +
                "Win counter: " + user.getWinCounter() + "\n" +
                "Elo: " + user.getElo() + "\n" +
                "Deck IDs: " + user.getDeckIDs() + "\n" +
                "Stack IDs: " + user.getStackIDs()
        );
    }

    public static final void print(Player player) {
        System.out.println( "-- Print Player --\n" +
                "PlayerID: " + player.getID() + "\n" +
                "PlayerName: " + player.getName() + "\n" +
                "Bio: " + player.getBio() + "\n" +
                "Image: " + player.getImage() + "\n" +
                "Credits: " + player.getCredits() + "\n" +
                "Game counter: " + player.getGamesCounter() + "\n" +
                "Win counter: " + player.getWinCounter() + "\n" +
                "Elo: " + player.getElo() + "\n" +
                "Deck IDs: " + player.getDeckIDs() + "\n" +
                "Stack IDs: " + player.getStackIDs()
        );
    }

}
