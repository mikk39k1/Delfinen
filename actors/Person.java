package actors;

public abstract class Person {

	// Attributes -------------------------------------
	private String name;
	private String phoneNumber;


	// Getters ----------------------------------------
	public String getName() {
		return this.name;
	}
	public String getPhoneNumber() {
		return this.phoneNumber;
	}


	// Setters ----------------------------------------
	protected void setName(String name) {
		this.name = name;
	}
	protected void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}