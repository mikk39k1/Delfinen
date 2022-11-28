package actors;

import utility.UI;

import java.time.format.DateTimeFormatter;

public class LeisureSwimmer extends Member {

    public LeisureSwimmer(UI in) {
        in.print("Please enter name: ");
        setName(in.readLine());
        in.print("Please enter phone number: ");
        setPhoneNumber(in.readLine());
        in.print("Please enter age: ");//Tnis shouldn't be here - it's not prompting for age, but birfday :D
        in.printLn("What is the members birthday");
        setAge(in.setDate());
        in.print("Please enter if membership is active? true/false: ");
        setIsMembershipActive(in.readBoolean());

        // Need method to add Coach to database on FILE so database can load array with active Coaches
    }
    public LeisureSwimmer(String name, String phoneNumber, String birthday) {//this is for the loadmethod

    }
}