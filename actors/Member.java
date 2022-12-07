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

	public int getAge() {
		return Period.between(dateOfBirth, LocalDate.now()).getYears();
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
		this.hasPaid= !this.hasPaid;
	}
	public void setUniqueID(int uniqueID) {
		this.uniqueID = uniqueID;
	}
}