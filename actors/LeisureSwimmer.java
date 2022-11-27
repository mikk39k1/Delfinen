package actors;

import utility.UI;

public class LeisureSwimmer extends Member {

    public LeisureSwimmer(UI in) {
        in.print("Please enter name: ");
        setName(in.readLine());
        in.print("Please enter phone number: ");
        setPhoneNumber(in.readLine());
        in.print("Please enter age: ");
        in.printLn("What is the members birthday");
        setAge(in.setDate());
        in.print("Please enter if membership is active? true/false: ");
        setIsMembershipActive(in.readBoolean());

        // Need method to add Coach to database on FILE so database can load array with active Coaches
    }
}