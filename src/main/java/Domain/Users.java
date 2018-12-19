package Domain;

public class Users {

    String userID;
    String serverID;
    String userDiscordName;
    String userDiscordNickName;

    public Users(String userID, String serverID, String userDiscordName, String userDiscordNickName) {
        this.userID = userID;
        this.serverID = serverID;
        this.userDiscordName = userDiscordName;
        this.userDiscordNickName = userDiscordNickName;
    }

    public Users() {
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getServerID() {
        return serverID;
    }

    public void setServerID(String serverID) {
        this.serverID = serverID;
    }

    public String getUserDiscordName() {
        return userDiscordName;
    }

    public void setUserDiscordName(String userDiscordName) {
        this.userDiscordName = userDiscordName;
    }

    public String getUserDiscordNickName() {
        return userDiscordNickName;
    }

    public void setUserDiscordNickName(String userDiscordNickName) {
        this.userDiscordNickName = userDiscordNickName;
    }
}
