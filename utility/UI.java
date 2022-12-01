package utility;

import actors.SwimmingDiscipline;

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
	private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");


	public String readLine() {
		return in.nextLine();
	}

	// Behaviours (Read Primitive DataTypes Methods) -----------------------------------------

	/*
	* This method keeps iterating over again until a valid integer value has been given, then returning that int value
	 */
	public int readInt() {
		while(true) {
			if (in.hasNextInt()) {
				int readInt = in.nextInt();
				in.nextLine();				// Scanner Bug avoidance
				return readInt;
			} else {
				System.out.println("Not valid input, please enter a number instead");
				in.nextLine();				// Scanner Bug avoidance
			} // End of if / else statement
	  	} // End of while loop
	} // End of method


	/*
	* This method keeps iterating over again until a valid boolean value has been given, then returning that boolean value
	 */
	public boolean readBoolean() {
		while(true) {
			if (in.hasNextBoolean()) {
				boolean readBoolean = in.nextBoolean();
				in.nextLine();				// Scanner Bug avoidance
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


	/*
	* This method requests minutes and seconds and returns a String containing input
	 */
	public String setTime() {
		print("minutes: ");
		int minutes = in.nextInt();
		print("Please enter seconds: ");
		int seconds = in.nextInt();
		return minutes + " minutes and " + seconds + " seconds";
	} // End of method



	// DATE TIME READER ----------------------------------------
	/*
	* This method sets the date based on 3 methods to request: year, month and day. Then formatting it nicely for console
	 */
	public String setDate() {
		LocalDateTime dateTime = LocalDateTime.of(readYear(), readMonth(), readDay(), 0, 0);
		return dateTime.format(dateFormat);
	} // end of method



	/*
	* This method requests and returns the input value representing a specific year of birth
	 */
	public int readYear() {
		print("Please enter year:  ");
		while(true) {
			int input = readInt();
			if (input <= LocalDateTime.now().getYear()) {
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
		while(true) {
			int input = readInt();
			if (input <= 12) {
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
		while(true) {
			int input = readInt();
			if (input <= 31) {
				return input;
			} else {
				printLn("You've entered an invalid day");
			} // End of if / else statement
		} // End of while loop
	} // End of method




	// ENUM READER ---------------------------------------------
	/*
	* This method checks input to match Enum value of SwimingDiscplineType
	 */
	public SwimmingDiscipline.SwimmingDisciplineTypes setSwimmingDisciplineType() {
		printLn("Enter Swimming discipline: Crawl, Butterfly, Breaststroke, Backcrawl or Freestyle: ");
		while(true) {
			try {
				return SwimmingDiscipline.SwimmingDisciplineTypes.valueOf(in.nextLine().toUpperCase());
			} catch (Exception e) {
				printLn("Not a valid Swim Discipline");
			} // End of try / catch statement
		} // End of while loop
	} // End of method
}