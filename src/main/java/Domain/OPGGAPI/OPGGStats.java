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
public class OPGGStats {
    
    private String kills_sum;

    private String damage_dealt_avg;

    private String win_matches_cnt;

    private String deaths_sum;

    private String rank_avg;

    private String _model_0;

    private String time_survived_avg;

    private String longest_kill_max;

    private String headshot_kills_sum;

    private Integer rating;

    private String matches_cnt;

    private String assists_sum;

    private String topten_matches_cnt;

    private String kills_max;

    public String getKills_sum ()
    {
        return kills_sum;
    }

    public void setKills_sum (String kills_sum)
    {
        this.kills_sum = kills_sum;
    }

    public String getDamage_dealt_avg ()
    {
        return damage_dealt_avg;
    }

    public void setDamage_dealt_avg (String damage_dealt_avg)
    {
        this.damage_dealt_avg = damage_dealt_avg;
    }

    public String getWin_matches_cnt ()
    {
        return win_matches_cnt;
    }

    public void setWin_matches_cnt (String win_matches_cnt)
    {
        this.win_matches_cnt = win_matches_cnt;
    }

    public String getDeaths_sum ()
    {
        return deaths_sum;
    }

    public void setDeaths_sum (String deaths_sum)
    {
        this.deaths_sum = deaths_sum;
    }

    public String getRank_avg ()
    {
        return rank_avg;
    }

    public void setRank_avg (String rank_avg)
    {
        this.rank_avg = rank_avg;
    }

    public String get_model_0 ()
    {
        return _model_0;
    }

    public void set_model_0 (String _model_0)
    {
        this._model_0 = _model_0;
    }

    public String getTime_survived_avg ()
    {
        return time_survived_avg;
    }

    public void setTime_survived_avg (String time_survived_avg)
    {
        this.time_survived_avg = time_survived_avg;
    }

    public String getLongest_kill_max ()
    {
        return longest_kill_max;
    }

    public void setLongest_kill_max (String longest_kill_max)
    {
        this.longest_kill_max = longest_kill_max;
    }

    public String getHeadshot_kills_sum ()
    {
        return headshot_kills_sum;
    }

    public void setHeadshot_kills_sum (String headshot_kills_sum)
    {
        this.headshot_kills_sum = headshot_kills_sum;
    }

    public Integer getRating ()
    {
        return rating;
    }

    public void setRating (Integer rating)
    {
        this.rating = rating;
    }

    public String getMatches_cnt ()
    {
        return matches_cnt;
    }

    public void setMatches_cnt (String matches_cnt)
    {
        this.matches_cnt = matches_cnt;
    }

    public String getAssists_sum ()
    {
        return assists_sum;
    }

    public void setAssists_sum (String assists_sum)
    {
        this.assists_sum = assists_sum;
    }

    public String getTopten_matches_cnt ()
    {
        return topten_matches_cnt;
    }

    public void setTopten_matches_cnt (String topten_matches_cnt)
    {
        this.topten_matches_cnt = topten_matches_cnt;
    }

    public String getKills_max ()
    {
        return kills_max;
    }

    public void setKills_max (String kills_max)
    {
        this.kills_max = kills_max;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [kills_sum = "+kills_sum+", damage_dealt_avg = "+damage_dealt_avg+", win_matches_cnt = "+win_matches_cnt+", deaths_sum = "+deaths_sum+", rank_avg = "+rank_avg+", _model_0 = "+_model_0+", time_survived_avg = "+time_survived_avg+", longest_kill_max = "+longest_kill_max+", headshot_kills_sum = "+headshot_kills_sum+", rating = "+rating+", matches_cnt = "+matches_cnt+", assists_sum = "+assists_sum+", topten_matches_cnt = "+topten_matches_cnt+", kills_max = "+kills_max+"]";
    }
    
}
