package actors;

import utility.UI;

/*
* This class represent a leisure swimmer. Since our design coaches can only be associated with competitive swimmers, there
* seems no reason to mix leisure and swimmer with the same class representation.
 */
public class LeisureSwimmer extends Member {

    public LeisureSwimmer(UI in) {
        in.print("Please enter name: ");
        setName(in.readLine());
        in.print("Please enter phone number: ");
        setPhoneNumber(in.readLine());
        in.print("Please enter age: ");//Tnis shouldn't be here - it's not prompting for age, but birfday :D
        in.printLn("What is the members birthday");
        setDateOfBirth(in.setDate());
        in.print("Please enter if membership is active? true/false: ");
        setIsMembershipActive(in.readBoolean());
        setHasPaid(false);

    }


    public LeisureSwimmer(int uniqueID, String name, String phoneNumber,String birthday, boolean isActive, boolean hasPaid) {
        setUniqueID(uniqueID);
        setName(name);
        setPhoneNumber(phoneNumber);
        setDateOfBirth(birthday);
        setIsMembershipActive(isActive);
        setHasPaid(hasPaid);
    }

}