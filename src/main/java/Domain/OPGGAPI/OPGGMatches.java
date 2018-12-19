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
public class OPGGMatches {
    
    private String _id;

    private String started_at;

    private String queue_size;
    
    private OPGGParticipants[] participants;

    private String total_rank;

    private String mode;

    public String get_id ()
    {
        return _id;
    }

    public void set_id (String _id)
    {
        this._id = _id;
    }

    public String getStarted_at ()
    {
        return started_at;
    }

    public void setStarted_at (String started_at)
    {
        this.started_at = started_at;
    }

    public String getQueue_size ()
    {
        return queue_size;
    }

    public void setQueue_size (String queue_size)
    {
        this.queue_size = queue_size;
    }

    public OPGGParticipants[] getParticipants ()
    {
        return participants;
    }

    public void setParticipants (OPGGParticipants[] participants)
    {
        this.participants = participants;
    }

    public String getTotal_rank ()
    {
        return total_rank;
    }

    public void setTotal_rank (String total_rank)
    {
        this.total_rank = total_rank;
    }

    public String getMode ()
    {
        return mode;
    }

    public void setMode (String mode)
    {
        this.mode = mode;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [_id = "+_id+", started_at = "+started_at+", queue_size = "+queue_size+", participants = "+participants+", total_rank = "+total_rank+", mode = "+mode+"]";
    }
    
}
