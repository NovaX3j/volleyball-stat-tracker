// The player class holds the player number and name.

public class Player {
    private String name;
    private int jerseyNumber;

    public Player(String name, int jerseyNumber) {
        this.name = name;
        this.jerseyNumber = jerseyNumber;
    }

    public String getName() { return name; }
    public int getJerseyNumber() { return jerseyNumber; }
    public void setName(String name) { this.name = name; }
    public void setJerseyNumber(int jerseyNumber) { this.jerseyNumber = jerseyNumber; }
}