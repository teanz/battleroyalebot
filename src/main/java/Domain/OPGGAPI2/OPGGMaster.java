package Domain.OPGGAPI2;

public class OPGGMaster {

    private Ranks ranks;

    private Max_ranks max_ranks;

    private Stats stats;

    private Tier tier;

    private int grade;

    private String gameMode;

    private String type;

    public Ranks getRanks() {
        return ranks;
    }

    public void setRanks(Ranks ranks) {
        this.ranks = ranks;
    }

    public Max_ranks getMax_ranks() {
        return max_ranks;
    }

    public void setMax_ranks(Max_ranks max_ranks) {
        this.max_ranks = max_ranks;
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public Tier getTier() {
        return tier;
    }

    public void setTier(Tier tier) {
        this.tier = tier;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getGameMode() {
        return gameMode;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
