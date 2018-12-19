package Database.Generic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerDetails {

    private String url = "jdbc:h2:tcp://localhost/~/BattleRoyaleBot;query_timeout=10000";

    // check guild exists or not
    public boolean doesGuildExist(String serverID) {
        String statement = "SELECT * FROM SERVERDETAILS where serverID = (?)";
        try (
                Connection con = JdbcConnection.getConnection(url);
                PreparedStatement stmt = con.prepareStatement(statement);
        ) {
            stmt.setString(1, serverID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                return true;
            }
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(ServerDetails.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean setGuildInfo(Domain.ServerDetails details) {
        String statement = "merge into SERVERDETAILS (SERVERID,SERVEROWNER, SERVERNAME, SERVERMEMBERCOUNT, SERVERPREMIUM) values (?,?,?,?,?)";
        try (
                Connection con = JdbcConnection.getConnection(url);
                PreparedStatement stmt = con.prepareStatement(statement);
        ) {

            stmt.setString(1, details.getServerID());
            stmt.setString(2, details.getServerOwner());
            stmt.setString(3, details.getServerName());
            stmt.setString(4, details.getServerMemberCount());
            stmt.setBoolean(5, false);

            stmt.executeUpdate();
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(ServerDetails.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public void updateGuildInfo(Domain.ServerDetails details) {

        String statement = "UPDATE SERVERDETAILS SET SERVEROWNER =(?), SERVERNAME =(?), SERVERMEMBERCOUNT=(?) where SERVERID =(?)";

        try (
                Connection con = JdbcConnection.getConnection(url);
                PreparedStatement stmt = con.prepareStatement(statement);
        ) {

            stmt.setString(1, details.getServerOwner());
            stmt.setString(2, details.getServerName());
            stmt.setString(3, details.getServerMemberCount());
            stmt.setString(4, details.getServerID());
            stmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ServerDetails.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    public Domain.ServerDetails getServerDetails(String serverID){
        String statement = "SELECT * FROM SERVERDETAILS where serverID = (?)";
        try (
                Connection con = JdbcConnection.getConnection(url);
                PreparedStatement stmt = con.prepareStatement(statement);
        ) {
            stmt.setString(1, serverID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Domain.ServerDetails details  = new Domain.ServerDetails();
                details.setServerID(rs.getString("SERVERID"));
                details.setServerOwner(rs.getString("SERVEROWNER"));
                details.setServerName(rs.getString("SERVERNAME"));
                details.setServerMemberCount(rs.getString("SERVERMEMBERCOUNT"));
                details.setServerPremium(rs.getBoolean("SERVERPREMIUM"));
                details.setPubgSetup(rs.getBoolean("PUBGSETUP"));
                details.setFortniteSetup(rs.getBoolean("FORTNITESETUP"));
                details.setRealmRoyaleSetup(rs.getBoolean("REALMROYALESETUP"));
                return details;
            }
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(ServerDetails.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public void updateGameSetupStatusPUBG(Domain.ServerDetails details) {

        String statement = "UPDATE SERVERDETAILS SET PUBGSETUP =(?) where SERVERID =(?)";

        try (
                Connection con = JdbcConnection.getConnection(url);
                PreparedStatement stmt = con.prepareStatement(statement);
        ) {

            stmt.setBoolean(1, details.isPubgSetup());
            stmt.setString(2, details.getServerID());

            stmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ServerDetails.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    public void updateGameSetupStatusFortnite(Domain.ServerDetails details) {

        String statement = "UPDATE SERVERDETAILS SET FORTNITESETUP =(?) where SERVERID =(?)";

        try (
                Connection con = JdbcConnection.getConnection(url);
                PreparedStatement stmt = con.prepareStatement(statement);
        ) {

            stmt.setBoolean(1, details.isFortniteSetup());
            stmt.setString(2, details.getServerID());

            stmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ServerDetails.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    public void updateGameSetupStatusRealm(Domain.ServerDetails details) {

        String statement = "UPDATE SERVERDETAILS SET REALMROYALESETUP =(?) where SERVERID =(?)";

        try (
                Connection con = JdbcConnection.getConnection(url);
                PreparedStatement stmt = con.prepareStatement(statement);
        ) {

            stmt.setBoolean(1, details.isRealmRoyaleSetup());
            stmt.setString(2, details.getServerID());

            stmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ServerDetails.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    public void updateStats(int serverCount, int userCount){
        String statement = "merge into BOTSTATS (SERVERCOUNT,USERCOUNT) values (?,?)";
        try (
                Connection con = JdbcConnection.getConnection(url);
                PreparedStatement stmt = con.prepareStatement(statement);
        ) {

            stmt.setInt(1, serverCount);
            stmt.setInt(2, userCount);
            stmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ServerDetails.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
