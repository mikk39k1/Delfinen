package database;

import actors.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Database {

    // Attributes ---------------------------------------------
    ArrayList<Member> memberList = new ArrayList<>();
    ArrayList<Coach> coachList = new ArrayList<>();
    private final HashMap<Member, Coach> SwimmersCoachAssociationList = new HashMap<>();


    // Getters ------------------------------------------------
    public ArrayList<Member> getMemberList() {
        return this.memberList;
    }

    public ArrayList<Coach> getCoachList() {
        return this.coachList;
    }

    public HashMap<Member, Coach> getSwimmersCoachAssociationList() {
        return this.swimmersCoachAssociationList;
    }

    // Setters ------------------------------------------------
    public void setMemberList(ArrayList<Member> memberList) {
        this.memberList = memberList;
    }
}