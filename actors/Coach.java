package actors;

import database.SingleTonDatabase;
import utility.SingleTonUI;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

/*
* This class represent a coach. The coach is supposed to be able to pick out competition swimmers based on their performances
* In our design the coach can also add swimming results for both for competitive as training sessions.
* This class is also subject to the principle of "The Least privilege", securing that an encapsulating principle won't allow
*  the coach to meddle with methods and changes not within a coach's responsibility
 */
public class Coach extends Employee {

	// Constructors ---------------------------------

	// Constructor to create a Coach with Password.
	public Coach(String name, String phonenumber, String username, String password) {
		setName(name);
		setRole(RoleType.COACHING);
		setPrivilege(PrivilegeType.COMPETITIVE_SWIMMER_MANAGEMENT);
		setPhoneNumber(phonenumber);
		setUsername(username);
		setPassword(password);
	}

	public Coach() {

	}

	// Coach Behaviors (Methods) --------------------------

	/*
	* This method looks up a swimmer from Database memberList
	 */
	public CompetitiveSwimmer lookupSwimmer(SingleTonUI singleTonUi, SingleTonDatabase singleTonDatabase) {

		//Finds member name
		String swimmerName = findSwimmerByName(singleTonUi, singleTonDatabase); // Stores temporary swimmer name in a String

		System.out.print("Please enter ID on the member: ");
		int swimmerID = singleTonUi.readInt();
		for (Member member : singleTonDatabase.getMemberList()) {
			if (member instanceof CompetitiveSwimmer) {
				if (member.getName().equalsIgnoreCase(swimmerName) && member.getUniqueID() == swimmerID) {
					System.out.printf("%nID: %-8d Name: %-30s Date of Birth: %-15s Tel: %-15s Membership Status: %-10b Discipline: ",
							member.getUniqueID(), member.getName(), member.getDateOfBirth(), member.getPhoneNumber(),
							member.isIsMembershipActive());
					((CompetitiveSwimmer) member).printSwimDisciplineList();
					return (CompetitiveSwimmer) member;
				} // End of inner if statement
			} // End of outer if statement
		} // End of for loop
		return null;
	} // End of method


	/*
	* This method
	 */
	public CompetitiveSwimmer loadSwimmer(SingleTonUI singleTonUi, SingleTonDatabase singleTonDatabase) {
		System.out.println("\nPlease enter ID on the member you wish to add result to: ");
		int swimmerID = singleTonUi.readInt();
		for (Member member : singleTonDatabase.getMemberList()) {
			if (member instanceof CompetitiveSwimmer) {
				if (member.getUniqueID() == swimmerID) {
					System.out.printf("%nID: %-8d Name: %-30s Date of Birth: %-15s Tel: %-15s Membership Status: %-10b ",
							member.getUniqueID(), member.getName(), member.getDateOfBirth(), member.getPhoneNumber(),
							member.isIsMembershipActive());
					return (CompetitiveSwimmer) member;
				} // End of inner if statement
			} // End of outer if statement
		} // End of for loop
		return null;
	} // End of method


	/*
	* This method adds swimming results of a competitive swimmer, to the corresponding searched swimming discipline type
	 */
	public void addSwimResult(SingleTonUI singleTonUi, CompetitiveSwimmer competitiveSwimmer, SwimmingDiscipline.SwimmingDisciplineTypes disciplineType) {

		if (competitiveSwimmer.getSwimmingDisciplineList().stream().filter(swimmingDiscipline ->
				swimmingDiscipline.getSwimmingDisciplineType().equals(disciplineType)).count() == 1) {

			competitiveSwimmer.getSwimmingDisciplineList().forEach(swimmingDiscipline -> {
				if (swimmingDiscipline.getSwimmingDisciplineType().equals(disciplineType)) {
					swimmingDiscipline.getSwimmingDisciplineResults().add(new SwimmingResult(singleTonUi));
				}
			});
		} else {
			singleTonUi.printLn("Swimmer is not active within that discipline");
		}
	} // End of method



	/*
	* This method prints a specific competitive swimmers results for each swimming discipline
	 */
	public void checkCompetitorSwimResults(CompetitiveSwimmer competitiveSwimmer) {
		for (int i = 0; i < competitiveSwimmer.getSwimmingDisciplineList().size(); i++) {
			System.out.printf("%nID: %-8d Name: %-30s Date of Birth: %-15s Tel: %-15s State: %-5b Discipline: %-10s%n",
					competitiveSwimmer.getUniqueID(),competitiveSwimmer.getName(),
					competitiveSwimmer.getDateOfBirth(),competitiveSwimmer.getPhoneNumber(), competitiveSwimmer.isIsMembershipActive(),
					competitiveSwimmer.getSwimmingDisciplineList().get(i).getSwimmingDisciplineType());
			for (SwimmingResult result : competitiveSwimmer.getSwimmingDisciplineList().get(i).getSwimmingDisciplineResults()) {
				System.out.print(competitiveSwimmer.getSwimmingDisciplineList().get(i).getSwimmingDisciplineType()+": ");
				result.printResults();
			} // End of inner for loop
		} // End of outer for loop
	} // End of method

	/*
	* This method verifies name of a competitive swimmer, by checking if it exists within the Database memberList
	 */
	private String findSwimmerByName(SingleTonUI singleTonUi, SingleTonDatabase singleTonDatabase) {

		singleTonUi.print("Please enter name of swimmer you wish lookup: ");
		String swimmerName = singleTonUi.readLine();		// Stores temporary swimmerName we are searching for

		for (Member member : singleTonDatabase.getMemberList()) {
			if (member instanceof CompetitiveSwimmer) {
				if (member.getName().equalsIgnoreCase(swimmerName)) {
					singleTonUi.printLn("ID: " + member.getUniqueID() +  " Name: " + member.getName());
				} // End of inner if statement
			} // End of outer if statement
		} // End of for loop
		return swimmerName;
	} // End of method


	/*
	* This method finds and prints all members belonging to the coach logged in.
	 */
	public void findMembersOfCoach(SingleTonDatabase singleTonDatabase, Coach coach) {
		System.out.println("Coach " + this.getName() + ", has the following swimmers:");
		for (Member key : singleTonDatabase.getSwimmersCoachAssociationList().keySet()) {
			if (singleTonDatabase.getSwimmersCoachAssociationList().get(key).equals(coach)) {

				AtomicInteger i = new AtomicInteger();

				((CompetitiveSwimmer)key).getSwimmingDisciplineList().
						forEach(swimmingDiscipline -> swimmingDiscipline.getSwimmingDisciplineResults().
								forEach(swimmingResult -> i.addAndGet(1)));


				System.out.printf("ID: %-8d Name: %-30s Date of Birth: %-15s Tel: " +
								"%-15s Membership status: %-8s Total Swim Results: %-5d%n",
						key.getUniqueID(),
						key.getName(),
						key.getDateOfBirth(),
						key.getPhoneNumber(),
						(key.isIsMembershipActive()) ? "active" : "passive",
						i.get());
			} // End of if statement
		} // End of for loop
	} // End of method

	public int getMemberAmountForCoach(SingleTonDatabase swimmerCoachSingleTonDatabase, Coach coach) {
        int memberAmount = 0;
		for (Member key : swimmerCoachSingleTonDatabase.getSwimmersCoachAssociationList().keySet()) {
			if (swimmerCoachSingleTonDatabase.getSwimmersCoachAssociationList().get(key).equals(coach)) {
                memberAmount++;
			} // End of if statement
		} // End of for loop
    return memberAmount;
	} // End of method


	public String loadCoachOfMember(HashMap<Member, Coach> memberCoachHashMap, Member member) {
		for (Coach values : memberCoachHashMap.values()) {
			if (memberCoachHashMap.get(member).equals(values)) {
				return values.getName();
			} // End of if statement
		} // End of for loop
		return "No Coach";
	}

} // End of class