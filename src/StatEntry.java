import java.time.LocalDate;

// The StatEntry class holds the structure of a stat entry for a certain player.
public class StatEntry {
    public static final String[] VALID_STATS = {"kill", "ace", "block", "dig", "assist"};

    private int jerseyNumber;
    private String statType;
    private LocalDate date;

    public StatEntry(int jerseyNumber, String statType, LocalDate date) {
        this.jerseyNumber = jerseyNumber;
        this.statType = statType;
        this.date = date;
    }

    // Checks if a stat is valid by comparing it to the list of valid stats. Returns true if valid and false otherwise.
    public static boolean isValidStat(String stat) {
        for (String valid : VALID_STATS) {
            if (valid.equals(stat)) {
                return true;
            }
        }
        return false;
    }

    public int getJerseyNumber() { return jerseyNumber; }
    public String getStatType() { return statType; }
    public LocalDate getDate() { return date; }
    public void setJerseyNumber(int jerseyNumber) { this.jerseyNumber = jerseyNumber; }
}