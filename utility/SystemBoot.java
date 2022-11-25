package utility;

import actors.*;
import database.SwimmerCoachDatabase;

import java.util.ArrayList;

public class SystemBoot {
    //  Attributes -----------------------------------------------
    // Utility / Controller ------------------
    Controller controller = new Controller();
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

        // Staff -----------------
        employees.add(new Chairman(Employee.RoleType.ADMIN, Employee.PrivilegeType.ADMINISTRATOR));
        employees.add(new Treasurer(Employee.RoleType.ACCOUNTANT, Employee.PrivilegeType.ECONOMYMANAGEMENT));
        employees.add(new Coach("Thomas", "+45 01 23 58 13",  "thomas123"));
        employees.add(new Coach("Marry", "+45 01 23 58 13","Marry123"));
        employees.add(new Coach("Jen", "+45 01 23 58 13","Jen123"));

        while (true) {
            String user = controller.isLoggedIn();
            if (!user.equals("0")) {
                setRoleAndPrivilege(user);

                System.out.println(currentUser.getUsername());
                System.out.println(currentUser.getRole());
                System.out.println(currentUser.getPrivilege());
            }
        }
    }

    public static void main(String[] args) {
        new SystemBoot().startSystem();
    }
}
