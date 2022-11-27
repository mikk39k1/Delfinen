package actors;

import database.SwimmerCoachDatabase;
import utility.UI;

public class Coach extends Employee {


    // Constructor ---------------------------------
    public Coach(UI in) {
        in.print("Please enter name of Coach: ");
        setName(in.readLine());
        setRole(RoleType.COACHING);
        setPrivilege(PrivilegeType.COMPETITIVE_SWIMMER_MANAGEMENT);
        in.print("Please enter a phone number: ");
        setPhoneNumber(in.readLine());
        in.print("Please enter a username: ");
        setUsername(in.readLine());
        in.print("Please enter a password: ");

        // Need method to add Coach to database on FILE so database can load array with active Coaches
    }

    public Coach(String name, String phoneNumber, String username) {
        setName(name);
        setRole(RoleType.COACHING);
        setPrivilege(PrivilegeType.COMPETITIVE_SWIMMER_MANAGEMENT);
        setPhoneNumber(phoneNumber);
        setUsername(username);

        // Need method to add Coach to database on FILE so database can load array with active Coaches
    }

    public Coach() {

    }


    // Behaviors (Methods) --------------------------
    public void checkTrainingResults() {

    }

    public void checkCompetitionResults() {

    }


    public CompetitiveSwimmer foundSwimmer(UI ui, SwimmerCoachDatabase swimmerCoachDatabase) {

        ui.printLn("Please enter name of swimmer you wish to get result of: ");
        String swimmerName = ui.readLine();

        for (Member memberNames : swimmerCoachDatabase.getMemberList().swimmers) {
            if (memberNames instanceof CompetitiveSwimmer) {
                if (memberNames.getName().equals(swimmerName)) {
                    return (CompetitiveSwimmer) memberNames;
                }
            }
        }
        return null;
    }

    public CompetitiveSwimmer loadSwimmer(UI ui, SwimmerCoachDatabase swimmerCoachDatabase) {

        ui.printLn("Please enter name of swimmer you wish to add result to: ");
        String swimmerName = ui.readLine();

        for (Member memberNames : swimmerCoachDatabase.getMemberList().swimmers) {
            if (memberNames instanceof CompetitiveSwimmer) {
                if (memberNames.getName().equalsIgnoreCase(swimmerName)) {
                    return (CompetitiveSwimmer) memberNames;
                }
            }
        }
        return null;
    }


    public void addSwimResult(UI ui, SwimmerCoachDatabase swimmerCoachDatabase) {

        // Prints out the whole list of competitors
        System.out.println(swimmerCoachDatabase.getSwimmersCoachAssociationList().toString());
        CompetitiveSwimmer swimmer = loadSwimmer(ui, swimmerCoachDatabase);

        for (int i = 0; i < swimmerCoachDatabase.getSwimmersCoachAssociationList().size(); i++) {
            if (swimmerCoachDatabase.getSwimmersCoachAssociationList().containsKey(swimmer)) {
                SwimmingDiscipline.SwimmingDisciplineTypes swimmingDiscipline = ui.setSwimmingDisciplineType();
                //second loop is iterate through swimmers list of discipline and get the right one to get needed
                //swimresult list
                for (int j = 0; j < swimmer.getSwimmingDisciplineList().size(); j++) {
                    if (swimmer.getSwimmingDisciplineList().get(j).getSwimmingDiscipline().equals(swimmingDiscipline)) {
                        swimmer.getSwimmingDisciplineList().get(j).getSwimmingDisciplineResults().add(new SwimmingResult(ui));
                    } else if (j == swimmer.getSwimmingDisciplineList().size() - 1){
                        ui.printLn("Svømmeren er ikke konkurrerende i denne disciplin");
                    }
                }
            }
        }

    }

    public void checkCompetitorSwimResults(CompetitiveSwimmer competitiveSwimmer) {
        System.out.println(competitiveSwimmer.getSwimmingDisciplineList());
        for (int i = 0; i < competitiveSwimmer.getSwimmingDisciplineList().size(); i++) {
            System.out.println(competitiveSwimmer.getSwimmingDisciplineList().get(i).getSwimmingDisciplineResults());
        }
    }


    // Interface ------------------- unique username/password loader
}