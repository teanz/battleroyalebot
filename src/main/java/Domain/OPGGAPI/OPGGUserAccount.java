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
public class OPGGUserAccount {
    
    private OPGGUserDetails user;

    public OPGGUserDetails getUser ()
    {
        return user;
    }

    public void setUser (OPGGUserDetails user)
    {
        this.user = user;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [user = "+user+"]";
    }
    
}
