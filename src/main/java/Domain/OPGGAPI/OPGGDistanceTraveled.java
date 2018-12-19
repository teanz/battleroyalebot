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
public class OPGGDistanceTraveled {
    
    private String walk_distance;

    private String ride_distance;

    public String getWalk_distance ()
    {
        return walk_distance;
    }

    public void setWalk_distance (String walk_distance)
    {
        this.walk_distance = walk_distance;
    }

    public String getRide_distance ()
    {
        return ride_distance;
    }

    public void setRide_distance (String ride_distance)
    {
        this.ride_distance = ride_distance;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [walk_distance = "+walk_distance+", ride_distance = "+ride_distance+"]";
    }
    
}
