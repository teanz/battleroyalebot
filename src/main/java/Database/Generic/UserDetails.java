package Database.Generic;

import Domain.Users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDetails {

    private String url = "jdbc:h2:tcp://localhost/~/BattleRoyaleBot;query_timeout=10000";

    public boolean doesUserExist(String userID){
        String statement = "SELECT * FROM USERS where USERID = (?)";
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

    public boolean freshUserDetails(Users user){
        String statement = "merge into USERS (USERID,SERVERID, USERDISCORDNAME, USERDISCORDNICKNAME) values (?,?,?,?)";
        try (
                Connection con = JdbcConnection.getConnection(url);
                PreparedStatement stmt = con.prepareStatement(statement);
        ) {

            stmt.setString(1, user.getUserID());
            stmt.setString(2, user.getServerID());
            stmt.setString(3, user.getUserDiscordName());
            stmt.setString(4, user.getUserDiscordNickName());

            stmt.executeUpdate();
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(ServerDetails.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    public boolean updateUserDetails(Users user){

        String statement = "UPDATE USERS SET SERVERID =(?), USERDISCORDNAME =(?),USERDISCORDNICKNAME=(?) where USERID =(?)";
        try (
                Connection con = JdbcConnection.getConnection(url);
                PreparedStatement stmt = con.prepareStatement(statement);
        ) {
            stmt.setString(1, user.getServerID());
            stmt.setString(2, user.getUserDiscordName());
            stmt.setString(3, user.getUserDiscordNickName());
            stmt.setString(4, user.getUserID());

            stmt.executeUpdate();
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(ServerDetails.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
