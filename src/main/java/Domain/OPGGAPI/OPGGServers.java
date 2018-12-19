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
public class OPGGServers {
    
    private String server_name;

    private String started_at_max;

    private String server;

    private String user_profile_url;

    private String matches_count;

    public String getServer_name ()
    {
        return server_name;
    }

    public void setServer_name (String server_name)
    {
        this.server_name = server_name;
    }

    public String getStarted_at_max ()
    {
        return started_at_max;
    }

    public void setStarted_at_max (String started_at_max)
    {
        this.started_at_max = started_at_max;
    }

    public String getServer ()
    {
        return server;
    }

    public void setServer (String server)
    {
        this.server = server;
    }

    public String getUser_profile_url ()
    {
        return user_profile_url;
    }

    public void setUser_profile_url (String user_profile_url)
    {
        this.user_profile_url = user_profile_url;
    }

    public String getMatches_count ()
    {
        return matches_count;
    }

    public void setMatches_count (String matches_count)
    {
        this.matches_count = matches_count;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [server_name = "+server_name+", started_at_max = "+started_at_max+", server = "+server+", user_profile_url = "+user_profile_url+", matches_count = "+matches_count+"]";
    }
    
}
