package actors;

import java.time.LocalDate;
import java.time.Period;
import java.time.chrono.ChronoLocalDate;

public abstract class Member extends Person {

	// Attributes -------------------------------------------
	private static int ID;
	private int uniqueID = Member.ID++;
	private String birthOfYear;
	private boolean isMembershipActive;
	private boolean hasPaid;

	// Getters ----------------------------------------------
	public int getUniqueID() {
		return this.uniqueID;
	}
	public String getAge() {
		return this.birthOfYear;
	}
	public boolean isIsMembershipActive() {
		return this.isMembershipActive;
	}
	public boolean isHasPaid() {
		return hasPaid;
	}

	public int getRealAge(){
		String s = this.birthOfYear;
		String[] arrOfStr = s.split("-");
		int year = Integer.parseInt(arrOfStr[0]);
		int month = Integer.parseInt(arrOfStr[1]);
		int day = Integer.parseInt(arrOfStr[2]);
		LocalDate dob = LocalDate.of(year,month,day);
		LocalDate now = LocalDate.now();
		return Period.between(dob, now).getYears();
	}

	// Setters -----------------------------------------------
	public void setAge(String birthOfYear) {
		this.birthOfYear = birthOfYear;
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
	public void setUniqueID(int uniqueID) {
		this.uniqueID = uniqueID;
	}
}