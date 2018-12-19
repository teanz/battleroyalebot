package Domain;

public class ServerDetails {

    private String serverID;
    private String serverOwner;
    private String serverName;
    private String serverMemberCount;
    private boolean serverPremium;
    private boolean pubgSetup;
    private boolean fortniteSetup;
    private boolean realmRoyaleSetup;

    public ServerDetails() {
    }

    public ServerDetails(String serverID, String serverOwner, String serverName, String serverMemberCount, boolean serverPremium, boolean pubgSetup, boolean fortniteSetup, boolean realmRoyaleSetup) {
        this.serverID = serverID;
        this.serverOwner = serverOwner;
        this.serverName = serverName;
        this.serverMemberCount = serverMemberCount;
        this.serverPremium = serverPremium;
        this.pubgSetup = pubgSetup;
        this.fortniteSetup = fortniteSetup;
        this.realmRoyaleSetup = realmRoyaleSetup;
    }

    public String getServerID() {
        return serverID;
    }

    public void setServerID(String serverID) {
        this.serverID = serverID;
    }

    public String getServerOwner() {
        return serverOwner;
    }

    public void setServerOwner(String serverOwner) {
        this.serverOwner = serverOwner;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getServerMemberCount() {
        return serverMemberCount;
    }

    public void setServerMemberCount(String serverMemberCount) {
        this.serverMemberCount = serverMemberCount;
    }

    public boolean getServerPremium() {
        return serverPremium;
    }

    public void setServerPremium(boolean serverPremium) {
        this.serverPremium = serverPremium;
    }

    public boolean isPubgSetup() {
        return pubgSetup;
    }

    public void setPubgSetup(boolean pubgSetup) {
        this.pubgSetup = pubgSetup;
    }

    public boolean isFortniteSetup() {
        return fortniteSetup;
    }

    public void setFortniteSetup(boolean fortniteSetup) {
        this.fortniteSetup = fortniteSetup;
    }

    public boolean isRealmRoyaleSetup() {
        return realmRoyaleSetup;
    }

    public void setRealmRoyaleSetup(boolean realmRoyaleSetup) {
        this.realmRoyaleSetup = realmRoyaleSetup;
    }
}
