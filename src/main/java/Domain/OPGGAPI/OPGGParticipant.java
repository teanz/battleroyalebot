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
public class OPGGParticipant {
    
    private String _id;
    
    private OPGGStats stats;

    private String team_id;

    public String get_id ()
    {
        return _id;
    }

    public void set_id (String _id)
    {
        this._id = _id;
    }

    public OPGGStats getStats ()
    {
        return stats;
    }

    public void setStats (OPGGStats stats)
    {
        this.stats = stats;
    }

    public String getTeam_id ()
    {
        return team_id;
    }

    public void setTeam_id (String team_id)
    {
        this.team_id = team_id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [_id = "+_id+", stats = "+stats+", team_id = "+team_id+"]";
    }
    
}
