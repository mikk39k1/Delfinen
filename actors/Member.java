package actors;

public abstract class Member extends Person {

	// Attributes -------------------------------------------
	private static int ID = 1000;
	private final int uniqueID = Member.ID++;
	private String birthOfYear;
	private boolean isMembershipActive;

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

	// Setters ------------------------------------------------
	public void setAge(String birthOfYear) {
		this.birthOfYear = birthOfYear;
	}
	public void setIsMembershipActive(boolean isMembershipActive) {
		this.isMembershipActive = isMembershipActive;
	}
}