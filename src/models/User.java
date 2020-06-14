package models;

public class User {
    private String userId;
    private String username;
    private String password;
    private String active;

    public User(String userId, String username, String password, String active) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.active = active;
    }

    public User() {}

    public void setUsername(String username) {
        this.username = username;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setActive(String active) { this.active = active; }

    public String getUserId() {
        return userId;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getActive() { return active; }

//    INSERT INTO user (userName, password, active, createDate, createdBy, lastUpdate, lastUpdateBy) VALUES ("test", "test", 1, "2004-05-23T14:25:10", "me", "2004-05-23T14:25:10", "me");


}
