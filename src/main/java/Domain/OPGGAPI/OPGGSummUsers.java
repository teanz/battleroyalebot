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
public class OPGGSummUsers {
    
    private OPGGSummUser[] users;

    public OPGGSummUser[] getUsers ()
    {
        return users;
    }

    public void setUsers (OPGGSummUser[] users)
    {
        this.users = users;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [users = "+users+"]";
    }
    
}
