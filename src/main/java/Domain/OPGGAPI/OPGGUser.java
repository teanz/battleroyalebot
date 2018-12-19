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
public class OPGGUser {
    
    private String profile_url;

    private String nickname;

    private String is_banned;

    public String getProfile_url ()
    {
        return profile_url;
    }

    public void setProfile_url (String profile_url)
    {
        this.profile_url = profile_url;
    }

    public String getNickname ()
    {
        return nickname;
    }

    public void setNickname (String nickname)
    {
        this.nickname = nickname;
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
        return "ClassPojo [profile_url = "+profile_url+", nickname = "+nickname+", is_banned = "+is_banned+"]";
    }
    
}
