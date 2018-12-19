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
public class OPGGSeasons {
    
    @SerializedName("2017-pre5")
    private String season5;

    public String getSeason5 ()
    {
        return season5;
    }

    public void season5 (String season)
    {
        this.season5 = season;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [2017-pre5 = "+season5+"]";
    }
    
}
