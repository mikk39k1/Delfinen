package utility;

import actors.CompetitiveSwimmer;
import actors.LeisureSwimmer;
import actors.Member;
import actors.SwimmingDiscipline;
import database.Database;

import javax.xml.crypto.Data;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FileHandler {
    private final File memberArrayListFile = new File("files/fullMembersList.txt");
    private final File memberResultFile = new File("files/results.txt");
    private final File sharksPrint = new File("files/sharksPrint.txt");

    private PrintStream printToFile;
    private PrintStream appendPrintToFile;
    private Scanner readFromFile;


    /*
     * This method reads from file and is verifying username exist within secret file
     */
    public String checkUsername(String username) {
        try {
            Scanner readLoginCredentials = new Scanner(new File("files/passwd.txt"));

            while (readLoginCredentials.hasNextLine()) {
                String loadedUsername = readLoginCredentials.next();    // Stores temporary username as a String
                if (username.equals(loadedUsername)) {
                    readLoginCredentials.close();       // If username matches, closes the Scanner and return value
                    return loadedUsername;
                } else {
                    readLoginCredentials.next();
                } // End of if / else statement
            } // End of while loop
        } catch (Exception e) {
            System.out.println("File doesn't exist\n");
        } // End of try / catch statement
        System.out.println("Username doesn't exist\n");
        return "0";
    } // End of method


    /*
     * This method reads from file and is verifying that prompted password works with username within secret file, then returns password if match
     */
    public String checkPassword(String password) {
        try {
            Scanner readLoginCredentials = new Scanner(new File("files/passwd.txt"));

            while (readLoginCredentials.hasNextLine()) {
                String loadedPassword = readLoginCredentials.next();    // Stores temporary password as a String
                if (loadedPassword.equals(password)) {
                    readLoginCredentials.close();       // If password matches closes the Scanner and returns value
                    return loadedPassword;
                } // End of if statement
            } // End of while loop
        } catch (Exception e) {
            System.out.println("File doesn't exist\n");
        } // End of try / catch statement
        System.out.println("Password is incorrect  ");
        return "0";
    } // End of method


    /*
     * This method writes an added member to the fullMemberList file, whenever one is created/added to Database
     */
    public void writeToFullMembersList(ArrayList<Member> swimmers) {
        try {
            printToFile = new PrintStream(memberArrayListFile);

            for (Member swimmer : swimmers) {

                if (swimmer instanceof CompetitiveSwimmer) {
                    printToFile.print("true");  // Adds true as first part in file if swimmer is competitive
                } else {
                    printToFile.print("false"); // Adds false as first part in file if swimmer is leisure swimmer
                } // End of if / else statement

                printToFile.print(";" + swimmer.getUniqueID());     // Delimiting with UniqueID value of swimmer
                printToFile.print(";" + swimmer.getName());         // Delimiting with name value of swimmer
                printToFile.print(";" + swimmer.getPhoneNumber());  // Delimiting with phoneNumber value of swimmer
                printToFile.print(";" + swimmer.getAge());          // Delimiting with age value of swimmer
                printToFile.print(";" + swimmer.isIsMembershipActive());    // Delimiting with member state value of swimmer
                printToFile.print(";" + swimmer.isHasPaid());       // Delimiting with paid status value of swimmer

                if (swimmer instanceof CompetitiveSwimmer)
                    for (SwimmingDiscipline type : ((CompetitiveSwimmer) swimmer).getSwimmingDisciplineList()) {
                        printToFile.print((";") + (type));
                    } // End of inner for loop
                printToFile.println();
                printToFile.close();    // Closes the PrintStream
            } // End of outer for loop
        } catch (FileNotFoundException e) {
            System.out.println("Noget gik galt");
        } // End of try / catch statement
    } // End of method


    /*public void writeToResults(ArrayList<>){
        for (int i = 0; i < type.getSwimmingDisciplineResults().size(); i++) {
            try {
                appendPrintToFile = new PrintStream(new FileOutputStream(memberResultFile,true));

                appendPrintToFile.print(type.getSwimmingDisciplineResults().get(i).getDistance() + ";");
                appendPrintToFile.print(type.getSwimmingDisciplineResults().get(i).getDate() + ";");
                appendPrintToFile.print(type.getSwimmingDisciplineResults().get(i).getSwimTime() + ";");
                if (type.getSwimmingDisciplineResults().get(i).isCompetitive()) {
                    appendPrintToFile.print(type.getSwimmingDisciplineResults().get(i).isCompetitive() + ";");
                    appendPrintToFile.print(type.getSwimmingDisciplineResults().get(i).getRank() + ";");
                    appendPrintToFile.println();
                } else {
                    appendPrintToFile.print(type.getSwimmingDisciplineResults().get(i).isCompetitive() + ";");
                    appendPrintToFile.println();
                }
            } catch (Exception e) {
                System.out.println(e);
                System.out.println("Ingen resultater at hente");
            }
        }
    } */


    /*
     * This method loads the members of the fullMemberList file into an arraylist, so we can manipulate again with
     *    member data inside the program.
     */
    public ArrayList<Member> loadMemberList(ArrayList<Member> membersList) {
        try {
            readFromFile = new Scanner(memberArrayListFile);
            while (readFromFile.hasNextLine()) {
                String s = readFromFile.nextLine();         // Stores the whole line containing a member to temporary String
                String[] arrOfStr = s.split(";");     // Delimiting by semicolon sign and adds to a temporary array
                int id = Integer.parseInt(arrOfStr[1]);  // Stores ID value of Member
                if (arrOfStr[0].equalsIgnoreCase("true")) {     // Checks if member is Leisure or Competitive

                    String name = arrOfStr[2];              // Stores the name of Member in a temporary String
                    String phone = arrOfStr[3];             // Stores phone number of member in a temporary String
                    String dob = arrOfStr[4];               // Stores date of birth of member in a temporary String
                    boolean membershipActive = Boolean.parseBoolean(arrOfStr[5]);   // Stores state of membership in a temporary boolean
                    boolean hasPaid = Boolean.parseBoolean(arrOfStr[6]);    // Stores paid status of membership in a temporary boolean

                    CompetitiveSwimmer compSwimmer = new CompetitiveSwimmer(id,
                            name, phone, dob, membershipActive, hasPaid);   // Creates the member with attributes

                    int disciplineAmount = arrOfStr.length - 7;     //Stores amounts of active Discipline types of Member
                    for (int i = 0; i < disciplineAmount; i++) {
                        compSwimmer.getSwimmingDisciplineList().add(new SwimmingDiscipline
                                (arrOfStr[7 + i])); // magicNumber is to get the start pos enumSwimDiscipline in array
                    } // End of for loop
                    membersList.add(compSwimmer);           // Adds the created swimmerMember to memberList Array
                } else {
                    String name = arrOfStr[2];              // Stores the name of Member in a temporary String
                    String phone = arrOfStr[3];             // Stores phone number of member in a temporary String
                    String dob = arrOfStr[4];                // Stores date of birth of member in a temporary String
                    boolean membershipActive = Boolean.parseBoolean(arrOfStr[5]);   // Stores state of membership in a temporary boolean
                    boolean hasPaid = Boolean.parseBoolean(arrOfStr[6]);    // Stores paid status of membership in a temporary boolean
                    LeisureSwimmer leisureSwimmer = new LeisureSwimmer(id,
                            name, phone, dob, membershipActive, hasPaid);   // Creates the member with attributes
                    membersList.add(leisureSwimmer);        // Adds the created swimmerMember to memberList Array
                } // End of if / else statement
            }   // End of while loop
            return membersList;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } // End of try / catch statement
        return membersList;
    } // End of method


    /*
     * This method ensures that unique ID always continues from last MemberID within fullMemberList file
     */
    public int loadID() {
        ArrayList<Integer> idArray = new ArrayList<>();
        try {
            readFromFile = new Scanner(memberArrayListFile);
            while (readFromFile.hasNextLine()) {
                String s = readFromFile.nextLine();
                String[] arrOfStr = s.split(";");
                idArray.add(Integer.parseInt(arrOfStr[1])); // This adds the ID value to the temporary arrayList idArray
            } // End of while loop
            return idArray.get(idArray.size() - 1) + 1; // Returns the ID value + 1 of the last member within the fullMemberList file
        } catch (IndexOutOfBoundsException | FileNotFoundException e) {
            return 1000;        // Returns 1000 for the static Member ID if file contains no members
        } // End of try / catch statement
    } // End of method




    /*
    * This method prints a welcome emoji presenting creators of this project
     */
    public void printWelcomeSharks(){
        try {
            readFromFile = new Scanner(sharksPrint);
            while (readFromFile.hasNextLine()) {
                System.out.println(readFromFile.nextLine());
            } // End of while loop
        }catch (FileNotFoundException e){
            e.printStackTrace();
        } // End of try / catch statement
    } // End of method

} // End of class