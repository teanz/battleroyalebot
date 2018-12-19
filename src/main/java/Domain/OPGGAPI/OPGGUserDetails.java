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
public class OPGGUserDetails {
    
    private String _id;

    private OPGGSeasons seasons;

    private String nickname;

    private OPGGServers[] servers;

    private String preferred_server;

    private String is_banned;

    public String get_id ()
    {
        return _id;
    }

    public void set_id (String _id)
    {
        this._id = _id;
    }

    public OPGGSeasons getSeasons ()
    {
        return seasons;
    }

    public void setSeasons (OPGGSeasons seasons)
    {
        this.seasons = seasons;
    }

    public String getNickname ()
    {
        return nickname;
    }

    public void setNickname (String nickname)
    {
        this.nickname = nickname;
    }

    public OPGGServers[] getServers ()
    {
        return servers;
    }

    public void setServers (OPGGServers[] servers)
    {
        this.servers = servers;
    }

    public String getPreferred_server ()
    {
        return preferred_server;
    }

    public void setPreferred_server (String preferred_server)
    {
        this.preferred_server = preferred_server;
    }

    public String getIs_banned ()
    {
        return is_banned;
    }

    public void setIs_banned (String is_banned)
    {
        this.is_banned = is_banned;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [_id = "+_id+", seasons = "+seasons+", nickname = "+nickname+", servers = "+servers+", preferred_server = "+preferred_server+", is_banned = "+is_banned+"]";
    }
    
}
