import java.time.LocalDate;
import java.util.ArrayList;

// The Roster class holds the list of players and their stats.
// The first 3 methods are the main methods for this class,
// and the rest are helper methods.

public class Roster {
    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<StatEntry> entries = new ArrayList<>();

    // Adds a player to the roster if the jersey number and name are new. Returns true on success and false otherwise.
    public boolean addPlayer(String name, int jerseyNumber) {
        if (findJerseyNumber(jerseyNumber) != null || findName(name) != null) {
            return false;
        }

        players.add(new Player(name, jerseyNumber));

        return true;
    }

    // Removes a player from the roster and all their stats. Returns true on success and false otherwise.
    public boolean removePlayer(int jerseyNumber) {
        Player player = findJerseyNumber(jerseyNumber);
        if (player == null) {
            return false;
        }

        players.remove(player);

        for (int i = entries.size() - 1; i >= 0; i--) {
            if (entries.get(i).getJerseyNumber() == jerseyNumber) {
                entries.remove(i);
            }
        }

        return true;
    }

    // Edits a player's name/jersey number and updates all their stats. Returns true on success and false otherwise.
    public boolean editPlayer(int oldJersey, String newName, int newJersey) {
        Player player = findJerseyNumber(oldJersey);
        Player sameName = findName(newName);
        if (sameName != null && sameName != player) {
            return false;
        }
        if (player == null) {
            return false;
        }
        if (newJersey != oldJersey && findJerseyNumber(newJersey) != null) {
            return false;
        }

        player.setName(newName);
        player.setJerseyNumber(newJersey);
        for (StatEntry entry : entries) {
            if (entry.getJerseyNumber() == oldJersey) {
                entry.setJerseyNumber(newJersey);
            }
        }
        return true;
    }

    // Finds the jersey number of a player. Returns null if not found.
    public Player findJerseyNumber(int jerseyNumber) {
        for (Player player : players) {
            if (player.getJerseyNumber() == jerseyNumber) {
                return player;
            }
        }
        return null;
    }

    // Finds a player by name (case-insensitive). Returns null if not found.
    public Player findName(String name) {
        for (Player player : players) {
            if (player.getName().equalsIgnoreCase(name)) {
                return player;
            }
        }
        return null;
    }

    // Logs a stat for a player. Returns true on success and false otherwise.
    public boolean logStat(int jerseyNumber, String statType, LocalDate date) {
        if (findJerseyNumber(jerseyNumber) == null) {
            return false;
        }
        entries.add(new StatEntry(jerseyNumber, statType, date));
        return true;
    }

    // Counts the number of times a player has a specific stat. Returns 0 if the player has 0 of that stat or if the player is not found.
    public int countStat(int jerseyNumber, String statType) {
        int count = 0;
        for (StatEntry entry : entries) {
            if (entry.getJerseyNumber() == jerseyNumber && entry.getStatType().equals(statType)) {
                count++;
            }
        }
        return count;
    }

    public ArrayList<Player> getPlayers() { return players; }
    public ArrayList<StatEntry> getEntries() { return entries; }
}