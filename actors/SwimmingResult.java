package actors;

import utility.UI;

import java.lang.module.Configuration;

/*
* This class represents what each result contains which any swimming discipline can perform
*  - Whenever a swimmer is active within a swimming discipline, results to each swimming discipline can be added, and
*    thus having its own entity, is to better be able to compare attributes against other swimmers results
 */
public class SwimmingResult {
    // Attributes ---------------------------------------
    private final int distance;
    private final String date;
    private final int minutes;
    private final int seconds;
    private final boolean isCompetitive;
    private int rank = 0;


    // Constructor --------------------------------------
    public SwimmingResult(UI ui) {
        ui.print("Please enter distance: ");
        this.distance = ui.setDistance();

        this.date = ui.setDate();

        ui.printLn("Please enter the swim time");
        ui.print("Mintues: ");
        this.minutes = ui.readInt();
        ui.print("Seconds: ");
        this.seconds = ui.readInt();

        ui.print("Please enter was this in a competition - true/false: ");
        this.isCompetitive = ui.readBoolean();

        if (isCompetitive) {
            ui.print("Please enter rank placement: ");
            this.rank = ui.readInt();
        }
    }

    // Constructor for fileWriter -----------------------

    public SwimmingResult(int distance, String date, int minutes, int seconds, boolean isCompetitive, int rank) {
        this.distance = distance;
        this.minutes = minutes;
        this.seconds = seconds;
        this.date = date;
        this.isCompetitive = isCompetitive;
        if (isCompetitive) {
            this.rank = rank;
        }
    }

    // Getters ------------------------------------------
    public int getDistance() {
        return distance;
    }

    public String getDate() {
        return date;
    }
    public int getMinutes() {
        return minutes;
    }
    public int getSeconds() {
        return seconds;
    }

    public boolean isCompetitive() {
        return isCompetitive;
    }

    public int getRank() {
        return rank;
    }

    public void printResults() {

        System.out.printf("Distance: %-10d Minutes: %-10d Seconds: %10d Competition: %-15s Rank: %-10d\n",
                distance, minutes, seconds, isCompetitive, rank);
    }

    @Override
    public String toString() {
        return "SwimmingResult{" +
                "distance=" + distance +
                ", date='" + date + '\'' +
                ", minutes=" + minutes +
                ", seconds=" + seconds +
                ", isCompetitive=" + isCompetitive +
                ", rank=" + rank +
                '}' + "\n";
    }
}