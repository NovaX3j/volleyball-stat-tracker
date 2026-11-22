import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class DataStore {
    // The program is run from src/, so the data folder is one level up
    private static final String PLAYERS_FILE = "../data/players.csv";
    private static final String STATS_FILE = "../data/stats.csv";

    // Rewrites both files completely. At this size that is simpler than appending,
    // and it is the only easy way to handle removed or edited players.
    public static void save(Roster roster) {
        try (PrintWriter out = new PrintWriter(new FileWriter(PLAYERS_FILE))) {
            out.println("name,jersey");
            for (Player player : roster.getPlayers()) {
                out.println(player.getName() + "," + player.getJerseyNumber());
            }
        } catch (IOException e) {
            IO.println("Warning: could not save players (" + e.getMessage() + ").");
        }
        try (PrintWriter out = new PrintWriter(new FileWriter(STATS_FILE))) {
            out.println("jersey,stat,date");
            for (StatEntry entry : roster.getEntries()) {
                out.println(entry.getJerseyNumber() + "," + entry.getStatType() + "," + entry.getDate());
            }
        } catch (IOException e) {
            IO.println("Warning: could not save stats (" + e.getMessage() + ").");
        }
    }

    public static void load(Roster roster) {
        loadPlayers(roster);
        // Stats load second because each stat needs its player to exist already
        loadStats(roster);
    }

    private static void loadPlayers(Roster roster) {
        File file = new File(PLAYERS_FILE);
        if (!file.exists()) {
            return; // First run: nothing has been saved yet
        }
        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            in.readLine(); // Skip the header row
            String line;
            int lineNumber = 1;
            while ((line = in.readLine()) != null) {
                lineNumber++;
                if (!loadPlayerLine(roster, line)) {
                    IO.println("Skipped bad line " + lineNumber + " in " + PLAYERS_FILE);
                }
            }
        } catch (IOException e) {
            IO.println("Warning: could not read players (" + e.getMessage() + ").");
        }
    }

    private static boolean loadPlayerLine(Roster roster, String line) {
        String[] parts = line.split(",");
        if (parts.length != 2) {
            return false;
        }
        try {
            int jersey = Integer.parseInt(parts[1].trim());
            return roster.addPlayer(parts[0].trim(), jersey);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static void loadStats(Roster roster) {
        File file = new File(STATS_FILE);
        if (!file.exists()) {
            return;
        }
        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            in.readLine(); // Skip the header row
            String line;
            int lineNumber = 1;
            while ((line = in.readLine()) != null) {
                lineNumber++;
                if (!loadStatLine(roster, line)) {
                    IO.println("Skipped bad line " + lineNumber + " in " + STATS_FILE);
                }
            }
        } catch (IOException e) {
            IO.println("Warning: could not read stats (" + e.getMessage() + ").");
        }
    }

    private static boolean loadStatLine(Roster roster, String line) {
        String[] parts = line.split(",");
        if (parts.length != 3) {
            return false;
        }
        try {
            int jersey = Integer.parseInt(parts[0].trim());
            String stat = parts[1].trim();
            LocalDate date = LocalDate.parse(parts[2].trim());
            if (!StatEntry.isValidStat(stat)) {
                return false;
            }
            return roster.logStat(jersey, stat, date);
        } catch (NumberFormatException | DateTimeParseException e) {
            return false;
        }
    }
}