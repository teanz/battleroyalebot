/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

import com.google.gson.annotations.SerializedName;

/**
 *
 * @author ac340
 */
public class OPGGCombat {
    
   // @SerializedName("distance_traveled")
    private OPGGDistanceTraveled distance_traveled;
     
    private OPGGDamage damage;
     
    private OPGGKDA kda;

    public OPGGDistanceTraveled getDistance_traveled ()
    {
        return distance_traveled;
    }

    public void setDistance_traveled (OPGGDistanceTraveled distance_traveled)
    {
        this.distance_traveled = distance_traveled;
    }

    public OPGGDamage getDamage ()
    {
        return damage;
    }

    public void setDamage (OPGGDamage damage)
    {
        this.damage = damage;
    }

    public OPGGKDA getKda ()
    {
        return kda;
    }

    public void setKda (OPGGKDA kda)
    {
        this.kda = kda;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [distance_traveled = "+distance_traveled+", damage = "+damage+", kda = "+kda+"]";
    }
    
}
