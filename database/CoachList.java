package database;

import actors.*;

import java.util.ArrayList;

public class CoachList {

	private ArrayList<Coach> coaches = new ArrayList<>();

	public ArrayList<Coach> getCoaches() {
		// Need method to get Coach Credentials to database on FILE so database can load and set loginInfo for all Coaches
		return this.coaches;
	}

}