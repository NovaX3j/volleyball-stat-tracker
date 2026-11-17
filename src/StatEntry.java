import java.time.LocalDate;

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

    public int getJerseyNumber() { return jerseyNumber; }
    public String getStatType() { return statType; }
    public LocalDate getDate() { return date; }
    public void setJerseyNumber(int jerseyNumber) { this.jerseyNumber = jerseyNumber; }
}