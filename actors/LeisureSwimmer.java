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

    }


    public LeisureSwimmer(int uniqueID, String name, String phoneNumber, String birthday, boolean isActive) {//this is for the loadmethod
        setUniqueID(uniqueID);
        setName(name);
        setPhoneNumber(phoneNumber);
        setAge(birthday);
        setIsMembershipActive(isActive);
    }




}