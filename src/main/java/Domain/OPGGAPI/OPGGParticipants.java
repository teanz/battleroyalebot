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
public class OPGGParticipants {
    
    private OPGGParticipant participant;

    private OPGGUser user;

    public OPGGParticipant getParticipant ()
    {
        return participant;
    }

    public void setParticipant (OPGGParticipant participant)
    {
        this.participant = participant;
    }

    public OPGGUser getUser ()
    {
        return user;
    }

    public void setUser (OPGGUser user)
    {
        this.user = user;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [participant = "+participant+", user = "+user+"]";
    }
    
}
