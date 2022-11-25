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
        Chairman chairman = new Chairman(Employee.RoleType.ADMIN, Employee.PrivilegeType.ADMINISTRATOR);
        Treasurer treasurer = new Treasurer(Employee.RoleType.ACCOUNTANT, Employee.PrivilegeType.ECONOMYMANAGEMENT);
        Coach coachThomas = new Coach("Thomas","+45 01 23 58 13", "thomas123", "swimCoach123");
        Coach coachMarry = new Coach("Marry","+45 01 23 58 13", "Marry123", "swimCoach123");
        Coach coachJen = new Coach("Jen","+45 01 23 58 13", "Jen123", "swimCoach123");

        SwimmingDiscipline swimmingDiscipline = new SwimmingDiscipline(controller.ui);

    }

    public static void main(String[] args) {
        new SystemBoot().startSystem();
    }
}
