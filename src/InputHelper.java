import java.time.LocalDate;
import java.time.format.DateTimeParseException;

// The InputHelper class is a mini helper class that handles input validation for the program.
public class InputHelper {

    // Reads an integer from the user and ensires that it is within a specific range.
    public int readInt(String prompt, int min, int max) {
        while (true) {
            String line = IO.readln(prompt).trim();
            try {
                int value = Integer.parseInt(line);
                if (value >= min && value <= max) {
                    return value;
                }
            } catch (NumberFormatException e) {}
            IO.println("Please enter a whole number from " + min + " to " + max + ".");
        }
    }

    // Reads a name from the user and ensures that it is not empty.
    public String readName(String prompt) {
        while (true) {
            String line = IO.readln(prompt).trim();
            if (!line.isEmpty()) {
                return line;
            }
            IO.println("Name cannot be empty.");
        }
    }

    // Reads a stat from the user and ensures that it is one of the valid stats from the StatEntry class.
    public String readStat(String prompt) {
        while (true) {
            String line = IO.readln(prompt).trim().toLowerCase();
            for (String valid : StatEntry.VALID_STATS) {
                if (valid.equals(line)) {
                    return line;
                }
            }
            IO.println("Stat must be one of: " + String.join(", ", StatEntry.VALID_STATS));
        }
    }

    // Reads a date from the user and ensures its format.
    public LocalDate readDate(String prompt) {
        while (true) {
            String line = IO.readln(prompt).trim();
            if (line.isEmpty()) {
                return LocalDate.now();
            }
            try {
                return LocalDate.parse(line);
            } catch (DateTimeParseException e) {
                IO.println("Use the format yyyy-MM-dd, for example 2026-11-17.");
            }
        }
    }
}