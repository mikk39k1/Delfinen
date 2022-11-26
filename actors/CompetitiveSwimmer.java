package actors;


import utility.Controller;
import utility.UI;

import java.util.ArrayList;

public class CompetitiveSwimmer extends Member {
    // Attributes --------------------------------------

    Controller controller = new Controller();
    private final ArrayList<SwimmingDiscipline> SwimmingDisciplineList = new ArrayList<>();


    ArrayList<SwimmingResult> swimmingResultsList = new ArrayList<>();




    public CompetitiveSwimmer(UI in) {
        in.print("Please enter name: ");
        setName(in.readLine());
        in.print("Please enter phone number: ");
        setPhoneNumber(in.readLine());
        setAge(in.setDate());
        in.print("Please enter if membership is active? true/false: ");
        setIsMembershipActive(in.readBoolean());


        // Need method to add Coach to database on FILE so database can load array with active Coaches
    }



    public ArrayList<SwimmingDiscipline> getSwimmingDisciplineList() {
        return SwimmingDisciplineList;
    }
}