package models;

/**
 * Created by Ejer on 24-11-2016.
 */
public class userLogin {
    private String username;
    private String password;

    public userLogin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
