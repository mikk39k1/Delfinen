package utility;

import actors.SwimmingDiscipline;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class UI {

	// Attributes ---------------------------------------------------
	private Scanner in = new Scanner(System.in);
	private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	// Behaviours (Read Primitive DataTypes Methods) -----------------------------------------
	public String readLine() {
		return in.nextLine();
	}

	public int readInt() {
		while(true) {
			if (in.hasNextInt()) {
				int readInt = in.nextInt();
				in.nextLine();
				return readInt;
			} else {
				System.out.println("Not valid input, please enter a number instead");
				in.nextLine();
			}
	  	}
	}

	public boolean readBoolean() {
		while(true) {
			if (in.hasNextBoolean()) {
				boolean readBoolean = in.nextBoolean();
				in.nextLine();
				return readBoolean;
			} else {
				System.out.println("Not a valid input, enter true / false ");
				in.nextLine();
			}
		}
	}

	// Behavior (Print Methods) --------------------------------------------
	public void print(String msg) {
		System.out.print(msg);
	}
	public void printLn(String msg) {
		System.out.println(msg);
	}

	public String setTime() {
		print("minutes: ");
		int minutes = in.nextInt();
		print("Please enter seconds: ");
		int seconds = in.nextInt();

		return minutes + " minutes and " + seconds + " seconds";
	}

	// DATE TIME READER ----------------------------------------
	public String setDate() {
		LocalDateTime dateTime = LocalDateTime.of(readYear(), readMonth(), readDay(), 0, 0);
		return dateTime.format(dateFormat);
	}

	public int readYear() {
		print("Please enter year:  ");
		while(true) {
			int input = readInt();
			if (input <= LocalDateTime.now().getYear()) {
				return input;
			} else {
				printLn("You've entered an invalid year");
			}
		}
	}

	public int readMonth() {
		print("Please enter month: ");
		while(true) {
			int input = readInt();
			if (input <= 12) {
				return input;
			} else {
				printLn("You've entered an invalid month");
			}
		}
	}

	public int readDay() {
		print("Please enter day: ");
		while(true) {
			int input = readInt();
			if (input <= 31) {
				return input;
			} else {
				printLn("You've entered an invalid day");
			}
		}
	}


	// ENUM READER ---------------------------------------------
	public SwimmingDiscipline.SwimmingDisciplineTypes setSwimmingDisciplineType() {
		printLn("Enter Swimming discipline: Crawl, Butterfly, Breaststroke, Backstroke or Freestyle: ");
		while(true) {
			try {
				return SwimmingDiscipline.SwimmingDisciplineTypes.valueOf(in.nextLine().toUpperCase());
			} catch (Exception e) {
				printLn("Not a valid Swim Discipline");
			}
		}
	}
}