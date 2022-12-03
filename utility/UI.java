package utility;

import actors.SwimmingDiscipline;

import java.io.PrintStream;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/*
 * This class is a UI (utility) class, the sole purpose is to use configurated methods to ensure read data from inputs
 *  doesn't compile errors and bugs. This way we can avoid Scanner Bugs, input errors, Enum Type input errors etc.
 * By using this utility for the exact situations we see scope to, we can also predefine requests inform of text, to have a more
 * fluently, user-friendly and usability oriented experience. We also increase availability and performances of the overall
 * software by using these pre configurated methods.
 */
public class UI {

    // Attributes ---------------------------------------------------
    private Scanner in = new Scanner(System.in);

    public String readLine() {
        return in.nextLine();
    }

    // Behaviours (Read Primitive DataTypes Methods) -----------------------------------------

    /*
     * This method keeps iterating over again until a valid integer value has been given, then returning that int value
     */
    public int readInt() {
        while (true) {
            if (in.hasNextInt()) {
                int readInt = in.nextInt();
                in.nextLine();                // Scanner Bug avoidance
                return readInt;
            } else {
                System.out.println("Not valid input, please enter a number instead");
                in.nextLine();                // Scanner Bug avoidance
            } // End of if / else statement
        } // End of while loop
    } // End of method


    /*
     * This method keeps iterating over again until a valid boolean value has been given, then returning that boolean value
     */
    public boolean readBoolean() {
        while (true) {
            if (in.hasNextBoolean()) {
                boolean readBoolean = in.nextBoolean();
                in.nextLine();                // Scanner Bug avoidance
                return readBoolean;
            } else {
                System.out.println("Not a valid input, enter true / false ");
                in.nextLine();                // Scanner Bug avoidance
            } // End of if / else statement
        } // End of while loop
    } // End of method

    // PRINT TYPES --------------------------------------------

    public void print(String msg) {
        System.out.print(msg);
    }

    public void printLn(String msg) {
        System.out.println(msg);
    }

    public void printf(PrintStream msgHolder, String input) {
        msgHolder.printf(input);
    }


    /*
     * This method requests minutes and seconds and returns a String containing input
     */
    public int setTime() {
        print("minutes: ");
        int minutes = in.nextInt();
        print("Please enter seconds: ");
        int seconds = in.nextInt();
        return (minutes*60)+seconds;
    } // End of method


    // DATE TIME READER ----------------------------------------
    /*
     * This method sets the date based on 3 methods to request: year, month and days in a month.
     */
    public LocalDate setDate() {
        while(true) {
            try {
                return LocalDate.of(readYear(), readMonth(), readDay());
            } catch (DateTimeException e) {
                // The DateTimeException can only prompt wrong day inputs, since we already set boundaries within year / month methods.
                printLn("Theres not that many days in the month, please try again");
            } // End of try / catch statement
        } // ENd of while loop
    } // end of method


    /*
     * This method requests and returns the input value representing a specific year of birth
     * //130 is what we consider as ulitmate age in years for a person
     */
    public int readYear() {
        print("Please enter year:  ");
        while (true) {
            int input = readInt();
            if (input <= LocalDate.now().getYear() && input >= LocalDate.now().getYear() - 130) {
                return input;
            } else {
                printLn("You've entered an invalid year");
            } // End of if / else statement
        } // End of while loop
    } // End of method


    /*
     * This method requests and returns the input value representing a month from a year
     */
    public int readMonth() {
        print("Please enter month: ");
        while (true) {
            int input = readInt();
            if (input <= 12 && input > 0) {
                return input;
            } else {
                printLn("You've entered an invalid month");
            } // End of if / else statement
        } // End of while loop
    } // End of method


    /*
     * This method requests and returns the input value representing a day in a month
     */
    public int readDay() {
        print("Please enter day: ");
        while (true) {
            int input = readInt();
            if (input <= 31 && input > 0) {
                return input;
            } else {
                printLn("You've entered an invalid day");
            } // End of if / else statement
        } // End of while loop
    } // End of method

    public int setDistance() {
        while (true) {
            printLn("Chose a distance:\n" +
                    "1. 100m\n" +
                    "2. 200m\n" +
                    "3. 500m");

            switch (readInt()) {
                case 1 -> {return 100;}
                case 2 -> {return 200;}
                case 3 -> {return 500;}
            }
            printLn("Not valid input");
        } // End of while loop
    } // End of method

    public int setRank() {
        while (true) {
            printLn("Chose a Rank:\n" +
                    "1. Top 3\n" +
                    "2. Top 5\n" +
                    "3. Top 10");

            switch (readInt()) {
                case 1 -> {return 3;}
                case 2 -> {return 5;}
                case 3 -> {return 10;}
            } // End of switch statement
            printLn("Not valid input");
        } // End of while loop
    } // End of method

    public boolean setCompetitiveness() {
        while(true) {
            printLn("Choose either:\n" +
                    "1. Training-session\n" +
                    "2. Competition");

            switch (readInt()) {
                case 1 -> {return false;}
                case 2 -> {return true;}
            } // End of switch statement
            printLn("Not a valid input");
        } // End of while loop
    } // End of method

    public int chooseTimeFrame() {
        while(true) {
            printLn("Choose results from\n" +
                    "1. Last Three Months\n" +
                    "2. Last Six Months\n" +
                    "3. This Year\n" +
                    "4. From Specific Year Til Now\n" +
                    "5. Check Specific Year\n" +
                    "6. Check Specific Day");

            switch (readInt()) {
                case 1 -> {return 1;}
                case 2 -> {return 2;}
                case 3 -> {return 3;}
                case 4 -> {return 4;}
                case 5 -> {return 5;}
                case 6 -> {return 6;}
                default -> printLn("Invalid input");
            } // End of switch statement
        } // End of while loop
    } // End of method


    // ENUM READER ---------------------------------------------
    /*
     * This method checks input to match Enum value of SwimingDiscplineType
     */
    public SwimmingDiscipline.SwimmingDisciplineTypes setSwimmingDisciplineType() {
        System.out.print("Please enter Swimming Discipline/s ");
        while (true) {
            try {
                return SwimmingDiscipline.SwimmingDisciplineTypes.valueOf(in.nextLine().toUpperCase());
            } catch (Exception e) {
                printLn("Not a valid Swim Discipline");
            } // End of try / catch statement
        } // End of while loop
    } // End of method
}