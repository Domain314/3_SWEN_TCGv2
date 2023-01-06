package at.domain314.models.users;

import at.domain314.utils.Constants;

public class User extends Player implements Comparable<User> {
    int userID;
    String username;
    String password;
    String last_token;

    public User() {
        userID = 0;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

//    public User(int id, int gamesCounter, int winCounter, int elo, String userName, String sessionToken) {
//        super(id, gamesCounter, winCounter, elo);
//        this.username = userName;
//        this.sessionToken = sessionToken;
//    }

    public int getUserID() { return userID; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getToken() { return last_token; }


    public void setUserID(int id) { this.userID = id; }
    public void setUsername(String username) { this.username = username; }
    public void resetPassword() { this.password = "--"; }
    public void setToken(String token) { this.last_token = token; }


    @Override
    public int compareTo(User user) {
        return user.getElo() - this.getElo();
    }

}
