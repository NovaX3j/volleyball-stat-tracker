import java.time.LocalDate;
import java.util.ArrayList;

public class Roster {
    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<StatEntry> entries = new ArrayList<>();

    public boolean addPlayer(String name, int jerseyNumber) {
        if (findJerseyNumber(jerseyNumber) != null || findName(name) != null) {
            return false;
        }

        players.add(new Player(name, jerseyNumber));

        return true;
    }

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

    public Player findJerseyNumber(int jerseyNumber) {
        for (Player player : players) {
            if (player.getJerseyNumber() == jerseyNumber) {
                return player;
            }
        }
        return null;
    }

    public Player findName(String name) {
        for (Player player : players) {
            if (player.getName().equalsIgnoreCase(name)) {
                return player;
            }
        }
        return null;
    }

    public boolean logStat(int jerseyNumber, String statType, LocalDate date) {
        if (findJerseyNumber(jerseyNumber) == null) {
            return false;
        }
        entries.add(new StatEntry(jerseyNumber, statType, date));
        return true;
    }

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