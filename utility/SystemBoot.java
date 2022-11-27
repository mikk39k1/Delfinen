package utility;

import actors.*;
import database.SwimmerCoachDatabase;

import java.util.ArrayList;
import java.util.concurrent.atomic.DoubleAdder;

public class SystemBoot {
    //  Attributes -----------------------------------------------
    // Utility / Controller ------------------
    Controller controller = new Controller();
    MenuRun menuRun = new MenuRun();
    private Employee currentUser;
    ArrayList<Employee> employees = new ArrayList<>();


    // Getter ----------------------------------
    public Employee getCurrentUser() {
        return currentUser;
    }

    // Setter -----------------------------------
    public void setCurrentUser(Employee employee) {
        this.currentUser = employee;
    }

    public void setRoleAndPrivilege(String username) {
        // Switch statement set role and privilege based on correct username
        for (Employee employee : employees) {
            if (employee.getUsername().equals(username)) {
                setCurrentUser(employee);
            }
        }
    }


    private void startSystem() {
        // Database ---------------
        SwimmerCoachDatabase swimmerCoachDatabase = new SwimmerCoachDatabase();
        /*

        // Staff -----------------
        employees.add(new Chairman(Employee.RoleType.ADMIN, Employee.PrivilegeType.ADMINISTRATOR));
        employees.add(new Treasurer(Employee.RoleType.ACCOUNTANT, Employee.PrivilegeType.ECONOMYMANAGEMENT));
        employees.add(new Coach("Thomas", "+45 01 23 58 13",  "thomas123"));
        employees.add(new Coach("Marry", "+45 01 23 58 13","Marry123"));
        employees.add(new Coach("Jen", "+45 01 23 58 13","Jen123"));

        // TEMPORARY ADD MEMBER TO MEMBERLIST ----------------------------------

        CompetitiveSwimmer test = new CompetitiveSwimmer(controller.ui);
        test.getSwimmingDisciplineList().add(new SwimmingDiscipline(controller.ui));



        // Testing purposes --------------------------
        swimmerCoachDatabase.getSwimmersCoachAssociationList().put(test, ((Coach)employees.get(3)));
        swimmerCoachDatabase.getMemberList().swimmers.add(test);

        ((Coach) employees.get(3)).addSwimResult(controller.ui, swimmerCoachDatabase);
        ((Coach) employees.get(3)).checkCompetitorSwimResults(((Coach) employees.get(3)).foundSwimmer(controller.ui, swimmerCoachDatabase));

        */

        /*
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
