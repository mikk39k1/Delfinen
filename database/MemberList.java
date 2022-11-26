package database;

import actors.*;

import java.util.ArrayList;

public class MemberList {

	public ArrayList<Member> swimmers = new ArrayList<>();

	public ArrayList<Member> getSwimmers() {
		return this.swimmers;
	}

	public void setSwimmers(ArrayList<Member> swimmers) {
		this.swimmers = swimmers;
	}

}