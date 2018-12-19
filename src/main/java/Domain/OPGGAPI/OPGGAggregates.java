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
public class OPGGAggregates {
    
    
    
    private String gameMode;
    
    private String type;



    private OPGGStats stats;
    
    private OPGGRanks ranks;

    public OPGGRanks getRanks() {
        return ranks;
    }

    public void setRanks(OPGGRanks ranks) {
        this.ranks = ranks;
    }
    
    


    public OPGGStats getStats ()
    {
        return stats;
    }

    public void setStats (OPGGStats stats)
    {
        this.stats = stats;
    }

    public String getGameMode() {
        return gameMode;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    
    
}
