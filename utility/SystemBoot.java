package utility;

import actors.*;
import database.SwimmerCoachDatabase;

import java.util.ArrayList;

public class SystemBoot {
    //  Attributes -----------------------------------------------
    // Utility / Controller ------------------
    Controller controller = new Controller();
    MenuRun menuRun = new MenuRun();
    private Employee currentUser;
    ArrayList<Employee> enigmaUsers = new ArrayList<>();


    // Getter ----------------------------------
    public Employee getCurrentUser() {
        return currentUser;
    }

    // Setter -----------------------------------
    public void setRoleAndPrivilege(String username) {
        // Switch statement set role and privilege based on correct username
        for (Employee user : enigmaUsers) {
            if (user.getUsername().equals(username)) {
                currentUser = user;
            }
        }
    }


    private void startSystem() {
        // Database ---------------
        SwimmerCoachDatabase swimmerCoachDatabase = new SwimmerCoachDatabase();


        // Staff -----------------
        enigmaUsers.add(new Chairman(Employee.RoleType.ADMIN, Employee.PrivilegeType.ADMINISTRATOR));
        enigmaUsers.add(new Treasurer(Employee.RoleType.ACCOUNTANT, Employee.PrivilegeType.ECONOMYMANAGEMENT));
        enigmaUsers.add(new Coach("Thomas", "+45 01 23 58 13",  "thomas123"));
        enigmaUsers.add(new Coach("Marry", "+45 01 23 58 13","Marry123"));
        enigmaUsers.add(new Coach("Jen", "+45 01 23 58 13","Jen123"));

        // Add coaches to coach list -------------------------------------------
        for (Employee user : enigmaUsers) {
            if (user instanceof Coach) {
                swimmerCoachDatabase.getCoachList().getCoaches().add((Coach) user);
            }
        }
        // TEMPORARY ADD MEMBER TO MEMBER-LIST ----------------------------------


        /*
        CompetitiveSwimmer test = new CompetitiveSwimmer(controller.ui);
        test.getSwimmingDisciplineList().add(new SwimmingDiscipline(controller.ui));



        // Testing purposes --------------------------
        swimmerCoachDatabase.getSwimmersCoachAssociationList().put(test, ((Coach) employees.get(3)));
        swimmerCoachDatabase.getMemberList().swimmers.add(test);

        ((Coach) employees.get(3)).addSwimResult(controller.ui, swimmerCoachDatabase);
        ((Coach) employees.get(3)).checkCompetitorSwimResults(((Coach) employees.get(3)).foundSwimmer(controller.ui, swimmerCoachDatabase));




        for (int i = 0; i < controller.memberList.swimmers.size(); i++){
            controller.ui.printLn(controller.memberList.swimmers.get(i).getName());
        }*/



       while (true) {
            String user = controller.isLoggedIn();
            if (!user.equals("0")) {
                setRoleAndPrivilege(user);

                System.out.println(currentUser.getUsername());
                System.out.println(currentUser.getRole());
                System.out.println(currentUser.getPrivilege());

                menuRun.menuLooping(currentUser, controller.ui, swimmerCoachDatabase);
            }
        }
    }

    public static void main(String[] args) {
        new SystemBoot().startSystem();
    }
}
