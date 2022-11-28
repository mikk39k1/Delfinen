package actors;


import utility.UI;

import java.util.ArrayList;

public class CompetitiveSwimmer extends Member {
    // Attributes --------------------------------------
    private final ArrayList<SwimmingDiscipline> swimmingDisciplineList = new ArrayList<>();


    public CompetitiveSwimmer(UI in) {
        in.print("Please enter name: ");
        setName(in.readLine());
        in.print("Please enter phone number: ");
        setPhoneNumber(in.readLine());
        in.printLn("What is the members birthday");
        setAge(in.setDate());
        in.print("Please enter if membership is active? true/false: ");
        setIsMembershipActive(in.readBoolean());

        // Need method to add Coach to database on FILE so database can load array with active Coaches
    }


    public ArrayList<SwimmingDiscipline> getSwimmingDisciplineList() {
        return swimmingDisciplineList;
    }

    public void printSwimDisciplineList(){
        for (int i = 0; i <swimmingDisciplineList.size(); i++){
            System.out.print(swimmingDisciplineList.get(i).getSwimmingDiscipline() + ", ");

        }
        System.out.println("|");
    }
}