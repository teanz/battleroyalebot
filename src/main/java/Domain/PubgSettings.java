package Domain;

public class PubgSettings {

    String serverID;
    String defaultRegion;
    // tpp fpp or mixed
    String mode;
    boolean renameUsers;
    boolean tagUsers;

    public PubgSettings() {
    }

    public String getServerID() {
        return serverID;
    }

    public void setServerID(String serverID) {
        this.serverID = serverID;
    }

    public String getDefaultRegion() {
        return defaultRegion;
    }

    public void setDefaultRegion(String defaultRegion) {
        this.defaultRegion = defaultRegion;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public boolean isRenameUsers() {
        return renameUsers;
    }

    public void setRenameUsers(boolean renameUsers) {
        this.renameUsers = renameUsers;
    }

    public boolean isTagUsers() {
        return tagUsers;
    }

    public void setTagUsers(boolean tagUsers) {
        this.tagUsers = tagUsers;
    }


}
