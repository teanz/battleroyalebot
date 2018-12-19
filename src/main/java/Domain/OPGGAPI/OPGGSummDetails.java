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
public class OPGGSummDetails {
    
    private String profile_url;

    private String nickname;

    private String identity_id;

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

    public String getIdentity_id ()
    {
        return identity_id;
    }

    public void setIdentity_id (String identity_id)
    {
        this.identity_id = identity_id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [profile_url = "+profile_url+", nickname = "+nickname+", identity_id = "+identity_id+"]";
    }
    
}
