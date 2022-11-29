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
			if (!member.isHasPaid()) {
				String[] arr;
				arr = memberAnalysis(member);
				System.out.printf("- %-20s %-10s %-12s %-7s %s%n", member.getName(), arr[2],
						(arr[3] == null ? "-" : arr[3]), arr[0], arr[1]);
			}
		}
	}

	public void setMemberArrears(Database swimmerCoachDatabase, UI ui) {
		System.out.printf("  %-20s %-10s %-12s %-20s %-10s%n", "[NAME]", "[STATE]","[TYPE]","[AGE]",
				"[HAS PAID?]");
		int count = 0;
		for (Member member : swimmerCoachDatabase.getMemberList()){
			count++;
			String[] arr;
			String hasPaid = (member.isHasPaid() ? "TRUE":"FALSE");
			arr = memberAnalysis(member);
			System.out.printf("%d# %-20s %-10s %-12s %-20s %-10s%n",count, member.getName(),arr[2],
					(arr[3]==null?"-":arr[3]),arr[0],hasPaid);
		}
		ui.printLn("For which member do you wish to toggle the payment?");
		swimmerCoachDatabase.getMemberList().get(ui.readInt()-1).toggleHasPaid();
	}


	private String[] memberAnalysis(Member member){
		String[] arr = new String[4];
		arr[0] = Integer.toString(member.getRealAge());
		if (!member.isIsMembershipActive()) {arr[1]="500";arr[2] = "INACTIVE";arr[3]=null;}
		else if (Integer.parseInt(arr[0])<18) {arr[1]="1000";arr[2] = "ACTIVE";arr[3]="Child";}
		else if (Integer.parseInt(arr[0])>=60) {arr[1]= "1200";arr[2] = "ACTIVE";arr[3]="Pensioner";}
		else {arr[1]= "1600";arr[2] ="ACTIVE";arr[3]="Adult";}
		return arr;
	}


	// Interface ------------------- unique username/password loader

}