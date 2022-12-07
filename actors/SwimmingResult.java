package actors;

import utility.UI;

import java.time.LocalDate;

/*
* This class represents what each result contains which any swimming discipline can perform
*  - Whenever a swimmer is active within a swimming discipline, results to each swimming discipline can be added, and
*    thus having its own entity, is to better be able to compare attributes against other swimmers results
 */
public class SwimmingResult {
    // Attributes ---------------------------------------
    private final int distance;
    private final LocalDate date;
    private final int swimTime;
    private final boolean isCompetitive;
    private int rank = 0;


    // Constructor --------------------------------------
    public SwimmingResult(UI ui) {
        ui.print("Please enter distance: ");
        this.distance = ui.setDistance();

        this.date = ui.setDate();

        ui.print("Please enter the swim time - ");
        this.swimTime = ui.setTime();

        ui.print("Please enter was this in a competition - true/false: ");
        this.isCompetitive = ui.readBoolean();

        if (isCompetitive) {
            ui.print("Please enter rank placement: ");
            this.rank = ui.readInt();
        }
        ui.printLn("The swim result was added!");
    }

    // Constructor for fileWriter -----------------------

    public SwimmingResult(int distance, String swimTime, LocalDate date, boolean isCompetitive, int rank) {
        this.distance = distance;
        this.swimTime = Integer.parseInt(swimTime);
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

    public LocalDate getDate() {
        return date;
    }

    public int getSwimTime() {
        return swimTime;
    }

    public boolean isCompetitive() {
        return isCompetitive;
    }

    public int getRank() {
        return rank;
    }

    public void printResults() {

        System.out.printf("Distance: %-10d Swim Time: %-10s Competition: %-10s Rank: %-10d\n",
                distance, secondsToMinutesAndSeconds(swimTime), isCompetitive, rank);
    }


    @Override
    public String toString() {
        return "\nResult - " +
                "distance: " + distance +
                " date: " + date  +
                " swimTime: " + secondsToMinutesAndSeconds(swimTime) +
                " isCompetitive: " + isCompetitive +
                " rank: " + (isCompetitive?rank:"-");
    }

    private String secondsToMinutesAndSeconds(int seconds){
        String result = "";

        return result + ((seconds/60)<10? "0" + (seconds/60):(seconds/60)) + ":" + ((seconds%60)<10? "0" +
                (seconds%60):(seconds%60));
    }
}