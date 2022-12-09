package actors;

import database.SingletonDatabase;
import utility.SingletonUI;

/*
* This class represents the treasurer, which is the accountant in the system.
* Being the accountant the only status he / she can manipulate and is paid status and see all members in debt
 */
public class Treasurer extends Employee {


	// Constructors-------------------------------------------------
			//This constructor is used when the Treasurer logs in, to set privilege and role.
	public Treasurer(RoleType role, PrivilegeType privilege) {
		setRole(role);
		setPrivilege(privilege);
		setPhoneNumber("+45 01 23 58 13");
		setUsername("Acc001");
	}
			//This constructor is used when the Chairman wants to use the Treasurers methods.
	public Treasurer(){

	}

	// Treasure Behaviors (Methods) --------------------------------

	/*
	* This method checks all members within the Database memberList if they have arrears
	 */
	public void checkMemberArrears(SingletonDatabase database) {
		System.out.printf("  %-19s %-10s %-12s %-7s %-4s%n", "[NAME]", "[STATE]","[TYPE]","[AGE]", "[AMOUNT TO PAY]");
		for (Member member : database.getMemberList()){
			if (!member.isHasPaid()) {
				String[] arr;  					// Temporary array created
				arr = memberAnalysis(member);	// Stores the result of memberAnalysis method inside String array arr
				System.out.printf("- %-20s %-10s %-12s %-7s %s%n", member.getName(), arr[2],
						(arr[3] == null ? "-" : arr[3]), arr[0], arr[1]);
			} // End of if statement
		} // End of for loop
	} // End of method


	/*
	* This method changes and sets the arrears status of a chosen member from Database memberList
	 */
	public void setMemberArrears(SingletonDatabase database, SingletonUI singleTonUi) {
		System.out.printf("%-6s %-30s %-10s %-12s %-20s %-10s%n", "[ID]", "[NAME]", "[STATE]","[TYPE]","[AGE]",
				"[HAS PAID?]");
		for (int i = 0; i < database.getMemberList().size(); i++) {
			String[] arr;											// Temporary array created
			String hasPaid = (database.getMemberList().get(i).isHasPaid() ? "TRUE":"FALSE");	 // Stores temporary statement of paid state
			arr = memberAnalysis(database.getMemberList().get(i)); // Stores the member inside String array arr
			System.out.printf("%-6s %-30s %-10s %-12s %-20s %-10s%n",
					database.getMemberList().get(i).getUniqueID(),
					database.getMemberList().get(i).getName(),arr[2],
					(arr[3].equals("null") ?"-":arr[3]),arr[0],hasPaid); // Prints the status of member
		}
		singleTonUi.printLn("For which member do you wish to toggle the payment? Enter their ID");
		int choice = singleTonUi.readInt();
		boolean exist = false;
		for (int i = 0; i < database.getMemberList().size(); i++) {
			if (choice==database.getMemberList().get(i).getUniqueID()){
				database.getMemberList().get(i).toggleHasPaid();
				singleTonUi.printLn("Payment has been toggled");
				exist = true;
			}
		}
		if (!exist) {
			singleTonUi.printLn("Wrong Member ID");
		}

			// Changes paid status of chosen member
	} // End of method


	/*
	This method does an analysis of each member it gets invoked upon
	 */
	private String[] memberAnalysis(Member member){
		String[] arr = new String[4]; 					// Stores temporary the member data of membership
		arr[0] = Integer.toString(member.getAge());
		if (!member.isIsMembershipActive()) {
			arr[1]="500";arr[2] = "INACTIVE";arr[3]="null"; // If member isn't active: price is 500
		} else if (Integer.parseInt(arr[0])<18) {
			arr[1]="1000";arr[2] = "ACTIVE";arr[3]="Child"; // If member is active and minor (under 18): cost 1000
		} else if (Integer.parseInt(arr[0])>=60) {
			arr[1]= "1200";arr[2] = "ACTIVE";arr[3]="Pensioner"; // if member is active and a senior (above 60): cost 1200
		} else {
			arr[1]= "1600";arr[2] ="ACTIVE";arr[3]="Adult"; // If member is active and adult (between 18 - 60): cost 1600
		} // End of if- else/if statement
		return arr;
	} // End of method


	private int[][] gatherPaymentInfoForMembers(SingletonDatabase database) {
		int[][] multiArray = new int[8][2];								// Stores payment data
		for (Member member : database.getMemberList()){
			String[] arrAnalysis = memberAnalysis(member);				// Stores strings from the analysis
			if (member.isHasPaid()){
				switch (arrAnalysis[3]){			// Adds Info about members who haven't paid
					case "Child" -> {multiArray[1][0]++;multiArray[1][1]+=1000;} // Adds a child + payment to statistics
					case "Adult" -> {multiArray[2][0]++;multiArray[2][1]+=1600;} // Adds an adult + payment to statistics
					case "Pensioner" -> {multiArray[3][0]++;multiArray[3][1]+=1200;} // Adds a pensioneer + payment to statistics
					default -> {multiArray[0][0]++;multiArray[0][1]+=500;} // Adds an inactive + payment to statistics
				} // End of switch
			} 	  // End of if statement
			else {
				switch (arrAnalysis[3]){			// Adds Info about members who have paid
					case "Child" -> {multiArray[5][0]++;multiArray[5][1]+=1000;} // Adds a child + payment to statistics
					case "Adult" -> {multiArray[6][0]++;multiArray[6][1]+=1600;} // Adds an adult + payment to statistics
					case "Pensioner" -> {multiArray[7][0]++;multiArray[7][1]+=1200;} // Adds a pensioneer + payment to statistics
					default -> {multiArray[4][0]++;multiArray[4][1]+=500;} // Adds an inactive + payment to statistics
				} // End of switch
			} // End of else statement
		} // End of for loop
		return multiArray;
	}

	public void printEconomyInfo(SingletonDatabase database) {
		int[][] list =	gatherPaymentInfoForMembers(database); // Creates a 2d int-array to store Eco-Info
		String[] names = {"Inactive","Child","Adult","Senior"};			   // Temporary name-array for name-looping
		System.out.println("Members who have paid:");					   // TeXt
		System.out.printf("%-10s %-15s %-14s%n","TYPE","# OF MEMBERS","AMOUNT PAID"); // Print header
		for (int i = 0; i < 4; i++) {
			System.out.printf("%-10s %-15s %-14s%n",names[i],list[i][0],list[i][1]);    // Prints each member
		}	// End of for loop. This loop prints all the members who HAVEN'T paid, in the order stated in line 111.
		System.out.println("------------------------------------------------------\n"); // Print divider
		System.out.println("Members who haven't paid:"); 								// TeXt
		System.out.printf("%-10s %-15s %-14s%n","TYPE","# OF MEMBERS","AMOUNT TO PAY");	// Print new header
		for (int i = 4; i < 8; i++) {
			System.out.printf("%-10s %-15s %-14s%n",names[i-4],list[i][0],list[i][1]);
		}// End of for loop. This loop prints all the members who HAVE paid, in the order stated in line 111.
		System.out.println("------------------------------------------------------"); // Print divider
		int numberOfMember = database.getMemberList().size(); // Stores the amount of members in local var
		int paidTotal = 0; // Temp var for number of paying members
		int owedTotal = 0; // Temp var for number of owing members
		for (int i = 0; i < 4; i++) {
			paidTotal += list[i][1];
		} //End of for loop. Counts all paying members
		for (int i = 4; i < 8; i++) {
			owedTotal += list[i][1];
		} //End of for loop. Counts all owing members
		System.out.println("\nTOTALS:");
		System.out.printf("%-15s %-15s %-14s%n","# OF MEMBERS","AMOUNT PAID","AMOUNT TO PAY");
		System.out.printf("%-15s %-15s %-14s%n",numberOfMember,paidTotal,owedTotal);			//Prints data of all
		System.out.println("------------------------------------------------------");			//members
	} // End of Method
} // End of class