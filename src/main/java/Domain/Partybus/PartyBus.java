package Domain.Partybus;

public class PartyBus {

    private Details details;

    private Stats[] stats;

    public PartyBus() {
    }

    public Details getDetails() {
        return details;
    }

    public void setDetails(Details details) {
        this.details = details;
    }

    public Stats[] getStats() {
        return stats;
    }

    public void setStats(Stats[] stats) {
        this.stats = stats;
    }
}
