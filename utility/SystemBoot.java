package utility;

import actors.*;
import database.SwimmerCoachDatabase;

public class SystemBoot {
    //  Attributes -----------------------------------------------
    private Employee.RoleType temporaryRole;
    private Employee.PrivilegeType temporaryPrivilege;

    // Getter ----------------------------------
    public Employee.RoleType getTemporaryRole() {
        return temporaryRole;
    }

    public Employee.PrivilegeType getTemporaryPrivilege() {
        return temporaryPrivilege;
    }

    // Setter ----------------------------------
    public void setTemporaryRole(Employee.RoleType temporaryRole) {
        this.temporaryRole = temporaryRole;
    }

    public void setTemporaryPrivilege(Employee.PrivilegeType temporaryPrivilege) {
        this.temporaryPrivilege = temporaryPrivilege;
    }

    public void setRoleAndPrivilege(String username, String password) {
        // Switch statement set role and privilege based on correct password and username
    }

    private void startSystem() {
        // Utility / Controller ------------------
        Controller controller = new Controller();

        // Database ---------------
        SwimmerCoachDatabase swimmerCoachDatabase = new SwimmerCoachDatabase();

        // Staff -----------------
        employees.add(new Chairman(Employee.RoleType.ADMIN, Employee.PrivilegeType.ADMINISTRATOR));
        employees.add(new Treasurer(Employee.RoleType.ACCOUNTANT, Employee.PrivilegeType.ECONOMYMANAGEMENT));
        employees.add(new Coach("Thomas", "+45 01 23 58 13",
                "thomas123", "swimCoach123"));
        employees.add(new Coach("Marry", "+45 01 23 58 13",
                "Marry123", "swimCoach123"));
        employees.add(new Coach("Jen", "+45 01 23 58 13",
                "Jen123", "swimCoach123"));

        while (true) {
            String user = controller.isLoggedIn();
            if (!user.equals("0")) {
                setRoleAndPrivilege(user);
            }
            System.out.println(temporaryPrivilege);
            System.out.println(temporaryRole);
        }

    }

    public static void main(String[] args) {
        new SystemBoot().startSystem();
    }
}
