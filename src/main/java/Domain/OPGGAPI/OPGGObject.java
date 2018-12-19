/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

/**
 *
 * @author ac340
 */
public class OPGGObject {
    
    private String rank;
    private String ratingChange;
    private String totalTeams;
    private String distanceWalked;
    private String distanceDriven;
    private String mode;
    private String playersOverall;
    private String gameMode;
    private String kills;
    private String damageDone;
    
    public OPGGObject(){
        
    }
    
    public OPGGObject(String rank,String dw,String dd,String mode,String playersOverall,String gameMode,String kills,String damageDone,String ratingChange,String totalTeams){
        
        this.damageDone = damageDone;
        this.rank = rank;
        this.distanceDriven = dd;
        this.distanceWalked = dw;
        this.kills = kills;
        this.playersOverall = playersOverall;
        this.mode = mode;
        this.ratingChange = ratingChange;
        this.totalTeams = totalTeams;
        
        switch (Integer.parseInt(gameMode)) {
            case 1:
                this.gameMode = "Solo";
                break;
            case 2:
                this.gameMode = "Duo";
                break;
            case 4:
                this.gameMode = "Squad";
                break;
            default:
                break;
        }
        
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getRatingChange() {
        return ratingChange;
    }

    public void setRatingChange(String ratingChange) {
        this.ratingChange = ratingChange;
    }

    public String getTotalTeams() {
        return totalTeams;
    }

    public void setTotalTeams(String totalTeams) {
        this.totalTeams = totalTeams;
    }

    public String getDistanceWalked() {
        return distanceWalked;
    }

    public void setDistanceWalked(String distanceWalked) {
        this.distanceWalked = distanceWalked;
    }

    public String getDistanceDriven() {
        return distanceDriven;
    }

    public void setDistanceDriven(String distanceDriven) {
        this.distanceDriven = distanceDriven;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getPlayersOverall() {
        return playersOverall;
    }

    public void setPlayersOverall(String playersOverall) {
        this.playersOverall = playersOverall;
    }

    public String getGameMode() {
        return gameMode;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    public String getKills() {
        return kills;
    }

    public void setKills(String kills) {
        this.kills = kills;
    }

    public String getDamageDone() {
        return damageDone;
    }

    public void setDamageDone(String damageDone) {
        this.damageDone = damageDone;
    }

    @Override
    public String toString() {
        return "OPGGObject{" + "rank=" + rank + ", ratingChange=" + ratingChange + ", totalTeams=" + totalTeams + ", distanceWalked=" + distanceWalked + ", distanceDriven=" + distanceDriven + ", mode=" + mode + ", playersOverall=" + playersOverall + ", gameMode=" + gameMode + ", kills=" + kills + ", damageDone=" + damageDone + '}';
    }

  
    
    
    
}
