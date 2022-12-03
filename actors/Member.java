package actors;

import java.time.LocalDate;
import java.time.Period;

/*
* This class represent all the raw data any member MUST have. This way we can make sure that whenever a swimmer is created
* they all have the same search pairs, and attributes for the most common information being age, ID, membership status etc.
 */
public abstract class Member extends Person {

	// Attributes -------------------------------------------
	private static int ID;
	private int uniqueID = Member.ID++;
	private LocalDate dateOfBirth;
	private boolean isMembershipActive;
	private boolean hasPaid;

	// Getters ----------------------------------------------
	public int getUniqueID() {
		return this.uniqueID;
	}
	public LocalDate getDateOfBirth() {
		return this.dateOfBirth;
	}
	public boolean isIsMembershipActive() {
		return this.isMembershipActive;
	}
	public boolean isHasPaid() {
		return hasPaid;
	}

	// Setters -----------------------------------------------
	public void setDateOfBirth(LocalDate birthOfYear) {
		this.dateOfBirth = birthOfYear;
	}

	public void setHasPaid(boolean hasPaid) {
		this.hasPaid = hasPaid;
	}

	public void setIsMembershipActive(boolean isMembershipActive) {
		this.isMembershipActive = isMembershipActive;
	}

	public static void setID(int ID) {
		Member.ID = ID;
	}
	public void toggleHasPaid(){
		if (this.hasPaid) {this.hasPaid=false;}
		else this.hasPaid=true;
	}
	public void setUniqueID(int uniqueID) {
		this.uniqueID = uniqueID;
	}

	// Member Behavior (Method) ------------------------------

	/*
	* This method extracts the exact age based on the date of birth
	 */

	/*
	public int getAge() {
		String s = dateOfBirth;  // Stores the birthOfYear attribute temporary as a String
		String[] arrOfStr = s.split("-");		// Splits and adds components to a temporary String array
		int year = Integer.parseInt(arrOfStr[0]); 	// Stores first value in array as year
		int month = Integer.parseInt(arrOfStr[1]);	// Stores second value in array as month
		int day = Integer.parseInt(arrOfStr[2]);		// Stores third value in array as day
		LocalDate dob = LocalDate.of(year,month,day); 	// Stores the format based on temporary attributes as a Local Date
		LocalDate now = LocalDate.now();				// Stores the Date today
		return Period.between(dob, now).getYears();		// Calculates the year difference based on inputs
	} // End of method

	 */

	public int getAge() {
		return Period.between(dateOfBirth, LocalDate.now()).getYears();
	}


}