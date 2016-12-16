package models;

/**
 * Created by Ejer on 28-11-2016.
 */
public class UserLogin {
    private String username;
    private String password;
//getters og setters til brugerlogin
    public UserLogin(String username, String password) {
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

