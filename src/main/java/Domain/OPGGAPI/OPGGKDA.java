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
public class OPGGKDA {
    
    private String kills;

    public String getKills ()
    {
        return kills;
    }

    public void setKills (String kills)
    {
        this.kills = kills;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [kills = "+kills+"]";
    }
    
}
