package at.domain314.models.users;

public class User extends Player {
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

    public int getUserID() { return userID; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getToken() { return last_token; }


    public void setUserID(int id) { this.userID = id; }
    public void setUsername(String username) { this.username = username; }
    public void resetPassword() { this.password = "--"; }
    public void setToken(String token) { this.last_token = token; }



}
