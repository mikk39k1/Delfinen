package database;

import actors.*;

import java.util.ArrayList;
import java.util.HashMap;

/*
* This class is representing a database.
* Our structure is designed around a member list, and a coach list. and a swimmer/coach association list.
* This design ensures that whenever we want to work with members and results, we only need to work with the association list,
* since all members with coaches ar considered competitive swimmers. This design helps us achieve a faster design, and less
* search pairs to work with if or whenever this program had to scale member amounts.
 */
public class SingletonDatabase {

    // Attributes ---------------------------------------------

    private static final database.SingletonDatabase SingletonDatabase = new SingletonDatabase();

    private SingletonDatabase() {

    }

    public static database.SingletonDatabase getSingletonDatabase() {
        return SingletonDatabase;
    }


    private ArrayList<Member> memberList = new ArrayList<>();
    private ArrayList<Coach> coachList = new ArrayList<>();

    private HashMap<Member, Coach> swimmersCoachAssociationList = new HashMap<>();


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

    public void setCoachList(ArrayList<Coach> coachList) {
        this.coachList = coachList;
    }

    public void setSwimmersCoachAssociationList(HashMap<Member, Coach> swimmersCoachAssociationList){
        this.swimmersCoachAssociationList = swimmersCoachAssociationList;
    }

}