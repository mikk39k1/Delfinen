package actors;

import database.Database;
import utility.UI;

public class Treasurer extends Employee {


	// Constructors-------------------------------------------------
	public Treasurer(RoleType role, PrivilegeType privilege) {
		setRole(role);
		setPrivilege(privilege);
		setPhoneNumber("+45 01 23 58 13");
		setUsername("Acc001");
	}
	public Treasurer(){

	}

	// Treasure Behaviors (Methods) --------------------------------

	/*
	* This method checks all members within the Database memberList if they have arrears
	 */
	public void checkMemberArrears(Database swimmerCoachDatabase) {
		System.out.printf("  %-19s %-10s %-12s %-7s %-4s%n", "[NAME]", "[STATE]","[TYPE]","[AGE]", "[AMOUNT TO PAY]");
		for (Member member : swimmerCoachDatabase.getMemberList()){
			if (!member.isHasPaid()) {
				String[] arr;
				arr = memberAnalysis(member);	// Stores the result of memberAnalysis method inside String array arr
				System.out.printf("- %-20s %-10s %-12s %-7s %s%n", member.getName(), arr[2],
						(arr[3] == null ? "-" : arr[3]), arr[0], arr[1]);
			} // End of if statement
		} // End of for loop
	} // End of method


	/*
	* This method changes and sets the arrears status of a chosen member from Database memberList
	 */
	public void setMemberArrears(Database swimmerCoachDatabase, UI ui) {
		System.out.printf("  %-20s %-10s %-12s %-20s %-10s%n", "[NAME]", "[STATE]","[TYPE]","[AGE]",
				"[HAS PAID?]");
		int count = 0;
		for (Member member : swimmerCoachDatabase.getMemberList()){
			count++;
			String[] arr;
			String hasPaid = (member.isHasPaid() ? "TRUE":"FALSE");	 // Stores temporary statement of paid state
			arr = memberAnalysis(member); // Stores the member inside String array arr
			System.out.printf("%d# %-20s %-10s %-12s %-20s %-10s%n",count, member.getName(),arr[2],
					(arr[3]==null?"-":arr[3]),arr[0],hasPaid);  // Prints the status of member
		} // End of for loop
		ui.printLn("For which member do you wish to toggle the payment?");
		swimmerCoachDatabase.getMemberList().get(ui.readInt()-1).toggleHasPaid();	// Changes paid status of chosen member
	} // End of method



	/*
	This method does an analysis of each member it gets invoked upon
	 */
	private String[] memberAnalysis(Member member){
		String[] arr = new String[4]; 					// Stores temporary the member data of membership
		arr[0] = Integer.toString(member.getRealAge());
		if (!member.isIsMembershipActive()) {
			arr[1]="500";arr[2] = "INACTIVE";arr[3]=null; // If member isn't active: price is 500
		} else if (Integer.parseInt(arr[0])<18) {
			arr[1]="1000";arr[2] = "ACTIVE";arr[3]="Child"; // If member is active and minor (under 18): cost 1000
		} else if (Integer.parseInt(arr[0])>=60) {
			arr[1]= "1200";arr[2] = "ACTIVE";arr[3]="Pensioner"; // if member is active and a senior (above 60): cost 1200
		} else {
			arr[1]= "1600";arr[2] ="ACTIVE";arr[3]="Adult"; // If member is active and adult (between 18 - 60): cost 1600
		} // End of if- else/if statement
		return arr;
	} // End of method

} // End of class