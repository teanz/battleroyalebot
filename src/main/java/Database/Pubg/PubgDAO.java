package Database.Pubg;

import Database.Generic.JdbcConnection;
import Database.Generic.ServerDetails;
import Domain.PubgDuo;
import Domain.PubgSettings;
import Domain.PubgSquad;
import Domain.PubgUsers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PubgDAO {

    private String url = "jdbc:h2:tcp://localhost/~/BattleRoyaleBot;query_timeout=10000";

    public boolean defaultPubgSetup(String serverID) {

        String statement = "INSERT into PUBG_SETTINGS(SERVERID,DEFAULTREGION,MODE) values (?,?,?)";
        try (
                Connection con = JdbcConnection.getConnection(url);
                PreparedStatement stmt = con.prepareStatement(statement);
        ) {

            stmt.setString(1, serverID);
            stmt.setString(2, "na");
            stmt.setString(3, "mixed");
            stmt.executeUpdate();
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(ServerDetails.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean removePubgSettings(String serverID) {
        String statement = "DELETE FROM PUBG_SETTINGS WHERE SERVERID = (?)";
        try (
                Connection con = JdbcConnection.getConnection(url);
                PreparedStatement stmt = con.prepareStatement(statement);
        ) {

            stmt.setString(1, serverID);
            stmt.executeUpdate();
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(ServerDetails.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public PubgSettings getPubgSettings(String serverID) {
        String statement = "SELECT * FROM PUBG_SETTINGS where serverID = (?)";
        try (
                Connection con = JdbcConnection.getConnection(url);
                PreparedStatement stmt = con.prepareStatement(statement);
        ) {
            stmt.setString(1, serverID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                PubgSettings settings = new PubgSettings();
                settings.setServerID(rs.getString("SERVERID"));
                settings.setDefaultRegion(rs.getString("DEFAULTREGION"));
                settings.setMode(rs.getString("MODE"));
                settings.setRenameUsers(rs.getBoolean("RENAMEUSERS"));
                settings.setTagUsers(rs.getBoolean("TAGUSERS"));
                return settings;
            }
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(ServerDetails.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public boolean updatePubgSettings(PubgSettings settings) {
        String statement = "UPDATE PUBG_SETTINGS SET DEFAULTREGION =(?), MODE =(?), RENAMEUSERS =(?), TAGUSERS= (?) where SERVERID =(?)";

        try (
                Connection con = JdbcConnection.getConnection(url);
                PreparedStatement stmt = con.prepareStatement(statement);
        ) {

            stmt.setString(1, settings.getDefaultRegion());
            stmt.setString(2, settings.getMode());
            stmt.setBoolean(3, settings.isRenameUsers());
            stmt.setBoolean(4, settings.isTagUsers());
            stmt.setString(5, settings.getServerID());
            stmt.executeUpdate();
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(ServerDetails.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean updatePlayer(PubgUsers user) {
        String statement = "merge into PUBG_USERS (ID,SERVERID, OPGGID, REGION, PLAYERNAME,GAMEMODE,RANK,RATING,ADR,KDR,LASTUPDATEDATE,SQUADID,DUOID) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try (
                Connection con = JdbcConnection.getConnection(url);
                PreparedStatement stmt = con.prepareStatement(statement);
        ) {

            stmt.setString(1, user.getUserID());
            stmt.setString(2, user.getServerID());
            stmt.setString(3, user.getUserOPGGID());
            stmt.setString(4, user.getUserRegion());
            stmt.setString(5, user.getUserPlayerName());
            stmt.setString(6, user.getUserGameMode());
            stmt.setString(7, user.getUserRank());
            stmt.setString(8, user.getUserRating());
            stmt.setString(9, user.getUserADR());
            stmt.setString(10, user.getUserKDR());
            stmt.setString(11, user.getLastUpdateDate());
            stmt.setInt(12, user.getSquadID());
            stmt.setInt(13, user.getDuoID());

            stmt.executeUpdate();
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(ServerDetails.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean removePlayer(String userID) {
        String statement = "DELETE FROM PUBG_USERS WHERE ID = (?)";
        try (
                Connection con = JdbcConnection.getConnection(url);
                PreparedStatement stmt = con.prepareStatement(statement);
        ) {

            stmt.setString(1, userID);
            stmt.executeUpdate();
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(ServerDetails.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean playerExists(String userID) {
        String statement = "SELECT * FROM PUBG_USERS where ID = (?)";
        try (
                Connection con = JdbcConnection.getConnection(url);
                PreparedStatement stmt = con.prepareStatement(statement);
        ) {
            stmt.setString(1, userID);
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

    public PubgUsers getPlayer(String userID) {
        String statement = "SELECT * FROM PUBG_USERS where ID = (?)";
        try (
                Connection con = JdbcConnection.getConnection(url);
                PreparedStatement stmt = con.prepareStatement(statement);
        ) {
            stmt.setString(1, userID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                PubgUsers user = new PubgUsers();
                user.setUserID(rs.getString("ID"));
                user.setServerID(rs.getString("SERVERID"));
                user.setUserOPGGID(rs.getString("OPGGID"));
                user.setUserRegion(rs.getString("REGION"));
                user.setUserPlayerName(rs.getString("PLAYERNAME"));
                user.setUserGameMode(rs.getString("GAMEMODE"));
                user.setUserRank(rs.getString("RANK"));
                user.setUserRating(rs.getString("RATING"));
                user.setUserADR(rs.getString("ADR"));
                user.setUserKDR(rs.getString("KDR"));
                user.setLastUpdateDate(rs.getString("LASTUPDATEDATE"));
                user.setSquadID(rs.getInt("SQUADID"));
                user.setDuoID(rs.getInt("DUOID"));
                return user;
            }
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(ServerDetails.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public ArrayList<PubgUsers> getAllPlayers(String serverID) {
        String statement = "SELECT * FROM PUBG_USERS where SERVERID = (?)";
        try (
                Connection con = JdbcConnection.getConnection(url);
                PreparedStatement stmt = con.prepareStatement(statement);
        ) {
            stmt.setString(1, serverID);
            ResultSet rs = stmt.executeQuery();
            ArrayList<PubgUsers> players = new ArrayList<>();
            while (rs.next()) {
                PubgUsers user = new PubgUsers();
                user.setUserID(rs.getString("ID"));
                user.setServerID(rs.getString("SERVERID"));
                user.setUserOPGGID(rs.getString("OPGGID"));
                user.setUserRegion(rs.getString("REGION"));
                user.setUserPlayerName(rs.getString("PLAYERNAME"));
                user.setUserGameMode(rs.getString("GAMEMODE"));
                user.setUserRank(rs.getString("RANK"));
                user.setUserRating(rs.getString("RATING"));
                user.setUserADR(rs.getString("ADR"));
                user.setUserKDR(rs.getString("KDR"));
                user.setLastUpdateDate(rs.getString("LASTUPDATEDATE"));
                players.add(user);
            }
            return players;
        } catch (SQLException ex) {
            Logger.getLogger(ServerDetails.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public boolean createSquad(PubgSquad squad) {

        String statement = "INSERT into PUBG_SQUAD(ID,TEAMNAME,LEADERID,TOURNAMENTID) values (?,?,?,?)";
        try (
                Connection con = JdbcConnection.getConnection(url);
                PreparedStatement stmt = con.prepareStatement(statement);
        ) {

            stmt.setInt(1, squad.getId());
            stmt.setString(2, squad.getTeamName());
            stmt.setString(3, squad.getLeaderID());
            stmt.setInt(4, squad.getTournamentID());
            stmt.executeUpdate();
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(ServerDetails.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean createDuo(PubgDuo duo) {

        String statement = "INSERT into PUBG_DUO(ID,TEAMNAME,LEADERID,TOURNAMENTID) values (?,?,?,?)";
        try (
                Connection con = JdbcConnection.getConnection(url);
                PreparedStatement stmt = con.prepareStatement(statement);
        ) {

            stmt.setInt(1, duo.getId());
            stmt.setString(2, duo.getTeamName());
            stmt.setString(3, duo.getLeaderID());
            stmt.setInt(4, duo.getTournamentID());
            stmt.executeUpdate();
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(ServerDetails.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean deleteSquad(int squadID){
        String statement = "DELETE FROM PUBG_SQUAD WHERE ID = (?)";
        try (
                Connection con = JdbcConnection.getConnection(url);
                PreparedStatement stmt = con.prepareStatement(statement);
        ) {

            stmt.setInt(1, squadID);
            stmt.executeUpdate();
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(ServerDetails.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean deleteDuo(int duoID){
        String statement = "DELETE FROM PUBG_Duo WHERE ID = (?)";
        try (
                Connection con = JdbcConnection.getConnection(url);
                PreparedStatement stmt = con.prepareStatement(statement);
        ) {

            stmt.setInt(1, duoID);
            stmt.executeUpdate();
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(ServerDetails.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public PubgSquad getSquad(int squadID){
        String statement = "SELECT * FROM PUBG_SQUAD where ID = (?)";
        try (
                Connection con = JdbcConnection.getConnection(url);
                PreparedStatement stmt = con.prepareStatement(statement);
        ) {
            stmt.setInt(1, squadID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                PubgSquad squad = new PubgSquad();
                squad.setId(rs.getInt("ID"));
                squad.setLeaderID(rs.getString("LEADERID"));
                squad.setTeamName(rs.getString("TEAMNAME"));
                squad.setTournamentID(rs.getInt("TOURNAMENTID"));
                return squad;
            }
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(ServerDetails.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public PubgDuo getDuo(int duoID){
        String statement = "SELECT * FROM PUBG_SQUAD where ID = (?)";
        try (
                Connection con = JdbcConnection.getConnection(url);
                PreparedStatement stmt = con.prepareStatement(statement);
        ) {
            stmt.setInt(1, duoID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                PubgDuo duo = new PubgDuo();
                duo.setId(rs.getInt("ID"));
                duo.setLeaderID(rs.getString("LEADERID"));
                duo.setTeamName(rs.getString("TEAMNAME"));
                duo.setTournamentID(rs.getInt("TOURNAMENTID"));
                return duo;
            }
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(ServerDetails.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public ArrayList<PubgUsers> getSquadMembers(int squadID){
        String statement = "SELECT * FROM PUBG_USERS where SQUADID = (?)";
        try (
                Connection con = JdbcConnection.getConnection(url);
                PreparedStatement stmt = con.prepareStatement(statement);
        ) {
            stmt.setInt(1, squadID);
            ResultSet rs = stmt.executeQuery();
            ArrayList<PubgUsers> players = new ArrayList<>();
            while (rs.next()) {
                PubgUsers user = new PubgUsers();
                user.setUserID(rs.getString("ID"));
                user.setServerID(rs.getString("SERVERID"));
                user.setUserOPGGID(rs.getString("OPGGID"));
                user.setUserRegion(rs.getString("REGION"));
                user.setUserPlayerName(rs.getString("PLAYERNAME"));
                user.setUserGameMode(rs.getString("GAMEMODE"));
                user.setUserRank(rs.getString("RANK"));
                user.setUserRating(rs.getString("RATING"));
                user.setUserADR(rs.getString("ADR"));
                user.setUserKDR(rs.getString("KDR"));
                user.setLastUpdateDate(rs.getString("LASTUPDATEDATE"));
                players.add(user);
                return players;
            }
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(ServerDetails.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public ArrayList<PubgUsers> getDuoMembers(int duoID){
        String statement = "SELECT * FROM PUBG_USERS where DUOID = (?)";
        try (
                Connection con = JdbcConnection.getConnection(url);
                PreparedStatement stmt = con.prepareStatement(statement);
        ) {
            stmt.setInt(1, duoID);
            ResultSet rs = stmt.executeQuery();
            ArrayList<PubgUsers> players = new ArrayList<>();
            while (rs.next()) {
                PubgUsers user = new PubgUsers();
                user.setUserID(rs.getString("ID"));
                user.setServerID(rs.getString("SERVERID"));
                user.setUserOPGGID(rs.getString("OPGGID"));
                user.setUserRegion(rs.getString("REGION"));
                user.setUserPlayerName(rs.getString("PLAYERNAME"));
                user.setUserGameMode(rs.getString("GAMEMODE"));
                user.setUserRank(rs.getString("RANK"));
                user.setUserRating(rs.getString("RATING"));
                user.setUserADR(rs.getString("ADR"));
                user.setUserKDR(rs.getString("KDR"));
                user.setLastUpdateDate(rs.getString("LASTUPDATEDATE"));
                players.add(user);
                return players;
            }
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(ServerDetails.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }


}
