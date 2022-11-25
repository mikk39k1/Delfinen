package database;

import actors.*;

import java.util.HashMap;

public class SwimmerCoachDatabase {

	// Attributes ---------------------------------------------
	private MemberList memberList = new MemberList();
	private CoachList coachList = new CoachList();
	private HashMap<Member, Coach> SwimmersCoachAssosiationList = new HashMap<>();


	// Getters ------------------------------------------------
	public MemberList getMemberList() {
		return this.memberList;
	}
	public CoachList getCoaches() {
		return this.coachList;
	}
	public HashMap<Member, Coach> getSwimmersCoachAssosiationList() {
		return this.SwimmersCoachAssosiationList;
	}

}