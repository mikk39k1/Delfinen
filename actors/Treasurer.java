package actors;

import database.Database;
import utility.UI;

public class Treasurer extends Employee {


	public Treasurer(RoleType role, PrivilegeType privilege) {
		setRole(role);
		setPrivilege(privilege);
		setPhoneNumber("+45 01 23 58 13");
		setUsername("Acc001");

		// Need method to add Coach to database on FILE so database can load array with active Coaches

	}
	public Treasurer(){

	}

	public void checkMemberArrears(Database swimmerCoachDatabase) {
		System.out.printf("  %-19s %-10s %-12s %-7s %-4s%n", "[NAME]", "[STATE]","[TYPE]","[AGE]", "[AMOUNT TO PAY]");
		for (Member member : swimmerCoachDatabase.getMemberList()){
			int age = member.getRealAge();
			String state = "";
			String type = null;
			int amount = 0;
			if (!member.isIsMembershipActive()) {amount = 500;state = "INACTIVE";}
			else if (age<18) {amount = 1000;state = "ACTIVE";type="Child";}
			else if (age>=60) {amount =1200;state = "ACTIVE";type="Pensioner";}
			else {amount = 1600;state="ACTIVE";type="Adult";}


			System.out.printf("- %-20s %-10s %-12s %-7s %s%n",member.getName(),state,(type==null?"-":type),age,amount);

		}
	}

	public void setMemberArrears(Database swimmerCoachDatabase, UI ui) {

	}



	// Interface ------------------- unique username/password loader

}