#!/bin/bash
# Writes each player's stats from the last 7 days to data/weekly_report.txt

# Variable to hold the file directory
SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
DATA_DIR="$SCRIPT_DIR/../data"
PLAYERS="$DATA_DIR/players.csv"
STATS="$DATA_DIR/stats.csv"
REPORT="$DATA_DIR/weekly_report.txt"


# Checks that the data files exist
if [ ! -f "$PLAYERS" ] || [ ! -f "$STATS" ]; then
    echo "No data found. Run the program and log stats first." >&2
    exit 1
fi

# Dates are yyyy-MM-dd, so plain string comparison orders them correctly
START=$(date -d "6 days ago" +%F)
TODAY=$(date +%F)

# First decides whoch lines the rule applies to and then what action is done on the line
# The BEGIN block is run before any lines are processed, and the END block is run after all lines are processed.
awk -F, -v start="$START" -v today="$TODAY" '
BEGIN { n = split("kill ace block dig assist", stats, " ") }
{ gsub(/\r/, "") }
FILENAME == ARGV[1] && FNR > 1 { order[++np] = $2; name[$2] = $1 }
FILENAME == ARGV[2] && FNR > 1 && $3 >= start && $3 <= today { c[$1, $2]++ }
END {
    print "Weekly report: " start " to " today
    print ""
    printf "%-20s %-5s", "Player", "#"
    for (s = 1; s <= n; s++) printf " %6s", stats[s]
    printf "\n"
    for (i = 1; i <= np; i++) {
        j = order[i]
        printf "%-20s %-5s", name[j], j
        for (s = 1; s <= n; s++) {
            printf " %6d", c[j, stats[s]]
            total[s] += c[j, stats[s]]
        }
        printf "\n"
    }
    printf "%-20s %-5s", "TEAM TOTAL", ""
    for (s = 1; s <= n; s++) printf " %6d", total[s]
    printf "\n"
}' "$PLAYERS" "$STATS" > "$REPORT"

echo "Report written to $REPORT"