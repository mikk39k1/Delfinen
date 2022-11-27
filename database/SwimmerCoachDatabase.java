package database;

import actors.*;

import java.util.HashMap;

public class SwimmerCoachDatabase {

	// Attributes ---------------------------------------------
	private final MemberList memberList = new MemberList();
	private final CoachList coachList = new CoachList();
	private final HashMap<Member, Coach> SwimmersCoachAssociationList = new HashMap<>();


	// Getters ------------------------------------------------
	public MemberList getMemberList() {
		return this.memberList;
	}
	public CoachList getCoachList() {
		return this.coachList;
	}
	public HashMap<Member, Coach> getSwimmersCoachAssociationList() {
		return this.SwimmersCoachAssociationList;
	}


}