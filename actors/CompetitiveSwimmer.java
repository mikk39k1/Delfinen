package actors;


import utility.UI;

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


    public CompetitiveSwimmer(UI in) {
        in.print("Please enter name: ");
        setName(in.readLine());
        in.print("Please enter phone number: ");
        setPhoneNumber(in.readLine());
        in.printLn("What is the members birthday");
        setDateOfBirth(in.setDate());
        in.print("Please enter if membership is active? true/false: ");
        setIsMembershipActive(in.readBoolean());
        setHasPaid(false);

        // Need method to add Coach to database on FILE so database can load array with active Coaches
    }

    public CompetitiveSwimmer(int uniqueID, String name, String phoneNumber,
                              LocalDate birthday, boolean isActive, boolean hasPaid) {//this is for the loadmethod
        setUniqueID(uniqueID);
        setName(name);
        setPhoneNumber(phoneNumber);
        setDateOfBirth(birthday);
        setIsMembershipActive(isActive);
        setHasPaid(hasPaid);

    }

    public ArrayList<SwimmingDiscipline> getSwimmingDisciplineList() {
        return swimmingDisciplineList;
    }

    public void printSwimDisciplineList() {
        for (SwimmingDiscipline swimmingDiscipline : swimmingDisciplineList) {
            System.out.print(" | " + swimmingDiscipline.getSwimmingDiscipline());
        }
        System.out.print("\n");
    }
    public int getAmountOfLoggedResults(){
        int count = 0;
        for (SwimmingDiscipline swimmingDiscipline : swimmingDisciplineList) {
            for (int j = 0; j < swimmingDiscipline.getSwimmingDisciplineResults().size(); j++) {
                count++;
            }
        }
        return count;
    }
}