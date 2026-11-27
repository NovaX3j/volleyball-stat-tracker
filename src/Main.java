import java.time.LocalDate;

// Runs the main program.
public class Main {
    private static final Roster roster = new Roster();
    private static final InputHelper input = new InputHelper();

    // The main methos.
    public static void main(String[] args) {
        DataStore.load(roster);
        boolean running = true;
        while (running) {
            printMenu();
            int choice = input.readInt("Choose an option: ", 1, 7);
            switch (choice) {
                case 1: addPlayerMenu(); break;
                case 2: removePlayerMenu(); break;
                case 3: editPlayerMenu(); break;
                case 4: viewRoster(); break;
                case 5: logStatMenu(); break;
                case 6: viewStatsMenu(); break;
                case 7: running = false; break;
            }
        }
        IO.println("Goodbye!");
    }

    // Creates the main menu for a user.
    private static void printMenu() {
        IO.println();
        IO.println("=== Volleyball Stat Tracker ===");
        IO.println("1. Add player");
        IO.println("2. Remove player");
        IO.println("3. Edit player");
        IO.println("4. View roster");
        IO.println("5. Log a stat");
        IO.println("6. View player stats");
        IO.println("7. Exit");
    }

    // The next 6 methods are the menu options for the user after they select an option from the main menu.
    private static void addPlayerMenu() {
        String name = input.readName("Player name: ");
        int jersey = input.readInt("Jersey number (0-99): ", 0, 99);
        if (roster.findName(name) != null) {
            IO.println("A player named " + name + " already exists.");
        } else if (roster.addPlayer(name, jersey)) {
            DataStore.save(roster);
            IO.println("Added " + name + " (#" + jersey + ").");
        } else {
            IO.println("Jersey #" + jersey + " is already taken.");
        }
    }

    private static void removePlayerMenu() {
        Player player = findPlayerMenu("Remove");
        if (player == null) {
            IO.println("Player not found.");
            return;
        }
        roster.removePlayer(player.getJerseyNumber());
        DataStore.save(roster);
        IO.println("Removed " + player.getName() + " (#" + player.getJerseyNumber() + ") and their stats.");
    }

    private static void editPlayerMenu() {
        int oldJersey = input.readInt("Jersey number of the player to edit: ", 0, 99);
        if (roster.findJerseyNumber(oldJersey) == null) {
            IO.println("No player with jersey #" + oldJersey + ".");
            return;
        }
        String newName = input.readName("New name: ");
        int newJersey = input.readInt("New jersey number (0-99): ", 0, 99);
        if (roster.editPlayer(oldJersey, newName, newJersey)) {
            DataStore.save(roster);
            IO.println("Updated player.");
        } else {
            IO.println("That name or jersey number belongs to another player.");
        }
    }

    private static void viewRoster() {
        if (roster.getPlayers().isEmpty()) {
            IO.println("No players yet.");
            return;
        }
        for (Player player : roster.getPlayers()) {
            IO.println("#" + player.getJerseyNumber() + " " + player.getName());
        }
    }

    private static void logStatMenu() {
        int jersey = input.readInt("Jersey number: ", 0, 99);
        if (roster.findJerseyNumber(jersey) == null) {
            IO.println("No player with jersey #" + jersey + ".");
            return;
        }
        String stat = input.readStat("Stat (" + String.join("/", StatEntry.VALID_STATS) + "): ");
        LocalDate date = input.readDate("Date (yyyy-MM-dd, blank for today): ");
        int count = input.readInt("How many (1-50): ", 1, 50);
        for (int i = 0; i < count; i++) {
            roster.logStat(jersey, stat, date);
        }
        DataStore.save(roster);
        IO.println("Logged " + count + " " + stat + "(s) for #" + jersey + " on " + date + ".");
    }

    private static void viewStatsMenu() {
        Player player = findPlayerMenu("Search");
        if (player == null) {
            IO.println("Player not found.");
            return;
        }
        IO.println(player.getName() + " (#" + player.getJerseyNumber() + ")");
        for (String stat : StatEntry.VALID_STATS) {
            IO.println("  " + stat + ": " + roster.countStat(player.getJerseyNumber(), stat));
        }
    }

    // Shared by remove and view-stats so the jersey/name choice is written once
    private static Player findPlayerMenu(String action) {
        int method = input.readInt(action + " by 1) jersey number or 2) name: ", 1, 2);
        if (method == 1) {
            return roster.findJerseyNumber(input.readInt("Jersey number: ", 0, 99));
        }
        return roster.findName(input.readName("Player name: "));
    }
}
