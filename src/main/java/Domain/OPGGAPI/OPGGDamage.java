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
public class OPGGDamage {
    
    private String damage_dealt;

    public String getDamage_dealt ()
    {
        return damage_dealt;
    }

    public void setDamage_dealt (String damage_dealt)
    {
        this.damage_dealt = damage_dealt;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [damage_dealt = "+damage_dealt+"]";
    }
    
}
