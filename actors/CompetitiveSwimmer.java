package actors;


import utility.SingletonUI;

import java.time.LocalDate;
import java.util.ArrayList;

/*
* This class is representing a competitive swimmer.
* In our design, a competitive swimmer, has a list of disciplines he/she can be active within. This way we can easily
* sort through different activity parameters when such representation seems appropriate
 */
public class CompetitiveSwimmer extends Member {
    // Attributes --------------------------------------
    private final ArrayList<SwimmingDiscipline> swimmingDisciplineList = new ArrayList<>();


    public CompetitiveSwimmer(SingletonUI ui) {
        ui.print("Please enter name: ");
        setName(ui.readLine());
        ui.print("Please enter phone number: ");
        setPhoneNumber(ui.readLine());
        ui.printLn("What is the members birthday");
        setDateOfBirth(ui.setDate());
        ui.print("Please enter if membership is active? true/false: ");
        setIsMembershipActive(ui.readBoolean());
        setHasPaid(false);

        // Need method to add Coach to database on FILE so database can load array with active Coaches
    }

    public CompetitiveSwimmer(int uniqueID, String name, String phoneNumber,
                              LocalDate birthday, boolean isActive, boolean hasPaid) {//this is for the load method
        setUniqueID(uniqueID);
        setName(name);
        setPhoneNumber(phoneNumber);
        setDateOfBirth(birthday);
        setIsMembershipActive(isActive);
        setHasPaid(hasPaid);
    }

    public CompetitiveSwimmer(String name, String phonenumber, LocalDate birthday, boolean isActive, boolean hasPaid) {
        setName(name);
        setPhoneNumber(phonenumber);
        setDateOfBirth(birthday);
        setIsMembershipActive(isActive);
        setHasPaid(hasPaid);
    }

    public ArrayList<SwimmingDiscipline> getSwimmingDisciplineList() {
        return swimmingDisciplineList;
    }

    public void printSwimDisciplineList() {
        for (SwimmingDiscipline swimmingDiscipline : swimmingDisciplineList) {
            System.out.print(" | " + swimmingDiscipline.getSwimmingDisciplineType());
        }
        System.out.print("\n");
    }
}