package actors;

import utility.UI;

import java.lang.module.Configuration;

public class SwimmingResult {
    // Attributes ---------------------------------------
    private final int distance;
    private final String date;
    private final String swimTime;
    private final boolean isCompetitive;
    private int rank = 0;


    // Constructor --------------------------------------
    public SwimmingResult(UI ui) {
        ui.print("Please enter distance: ");
        this.distance = ui.readInt();

        this.date = ui.setDate();

        ui.print("Please enter the swim time - ");
        this.swimTime = ui.setTime();

        ui.print("Please enter was this in a competition - true/false: ");
        this.isCompetitive = ui.readBoolean();

        if (isCompetitive) {
            ui.print("Please enter rank placement: " );
            this.rank = ui.readInt();
        }
    }

    // Constructor for fileWriter -----------------------

    public SwimmingResult(int distance, String swimTime, String date, boolean isCompetitive, int rank){
        this.distance = distance;
        this.swimTime = swimTime;
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

    public String getSwimTime() {
        return swimTime;
    }

    public boolean isCompetitive() {
        return isCompetitive;
    }

    public int getRank() {
        return rank;
    }

    public void printResults() {
        System.out.printf("Distance: %-10d Swim Time: %-30s Competition: %-15s Rank: %-10d\n",
                distance,swimTime,isCompetitive,rank);
    }
}
