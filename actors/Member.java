package actors;

public abstract class Member extends Person {

	// Attributes -------------------------------------------
	private static int ID = 1000;
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
}