package utility;

import actors.*;
import database.Database;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/*
* This class is representing the whole FileHandling of the system.
* All loads, reads and file manipulations is being handled by this class. The overall principle of this class is to
* load members / employees, and write new data to each entity whenever updates or inputs are being fed.
* We also use this class to read and compare data whenever a user is trying to log in, thus securing safety and avoiding breach
* by securing no passwords, or usernames are available to be read directly from the code.
 */
public class FileHandler {
    private final File memberArrayListFile = new File("files/fullMembersList.txt");
    private final File memberResultFile = new File("files/results.txt");
    private final File sharksPrint = new File("files/sharksPrint.txt");
    private final File swimmerCoachAssociationList = new File("files/swimmerCoachAssociationList.txt");
    private final File coachListFile = new File("files/coachList.txt");
    private final File passwordList = new File("files/passwd.txt");
    private final File logFile = new File("files/log.txt");


    private PrintStream printToFile;
    private  FileOutputStream appendToFile;
    private Scanner readFromFile;


    /*
     * This method reads from file and is verifying username exist within secret file
     */
    public String checkUsername(String username) {
        try {
            Scanner readLoginCredentials = new Scanner(passwordList);

            while (readLoginCredentials.hasNextLine()) {
                String[] loadedUsername = readLoginCredentials.nextLine().split(";");    // Stores temporary username to arr
                String loadUsername = loadedUsername[0];
                if (loadUsername.equals(username)) {
                    readLoginCredentials.close();       // If username matches, closes the Scanner and return value
                    return loadUsername;
                    } // End of if /  else {
                } // End of if / else statemen
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
                String[] loadedPassword = readLoginCredentials.nextLine().split(";");    // Stores temporary password as a String
                if (loadedPassword[1].equals(password)) {
                    readLoginCredentials.close();       // If password matches closes the Scanner and returns value
                    return password;
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
                printToFile.print(";" + swimmer.getDateOfBirth());          // Delimiting with age value of swimmer
                printToFile.print(";" + swimmer.isIsMembershipActive());    // Delimiting with member state value of swimmer
                printToFile.print(";" + swimmer.isHasPaid());       // Delimiting with paid status value of swimmer

                if (swimmer instanceof CompetitiveSwimmer)
                    for (SwimmingDiscipline type : ((CompetitiveSwimmer) swimmer).getSwimmingDisciplineList()) {
                        printToFile.print((";") + (type));
                    } // End of inner for loop
                printToFile.println();
            } // End of outer for loop
            printToFile.close();    // Closes the PrintStream
        } catch (FileNotFoundException e) {
            System.out.println("Noget gik galt");
        } // End of try / catch statement
    } // End of method


    /*
    * This method writes coach employee credentials to coachList file
     */
    public void writeToCoachList(ArrayList<Coach> coaches) {
        try {
            printToFile = new PrintStream(coachListFile);

            for (Coach coach : coaches) {
                printToFile.print(coach.getUsername() + ";");
                printToFile.print(coach.getName() + ";");         // Write Name to coachList file
                printToFile.print(coach.getPhoneNumber());  // Write Phone number to coachList file
                printToFile.println();
            } // End of for loop
            printToFile.close();    // Closes the PrintStream
        } catch (FileNotFoundException e) {
            System.out.println("Noget gik galt");
        } // End of try / catch statement
    } // End of method


    /*
    * This method writes coach username and password to passwd file
     */
    public void writeCoachUserAndPassToList(String coachUsername, String coachPassword){
        try{
            printToFile = new PrintStream(new FileOutputStream(passwordList,true));
                printToFile.print(coachUsername + ";");
                printToFile.print(coachPassword);
                printToFile.println();
                printToFile.close();
        } catch (Exception e){
            e.printStackTrace();
        } // End of try / catch statement
    } // End of method



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
                String name = arrOfStr[2];              // Stores the name of Member in a temporary String
                String phone = arrOfStr[3];             // Stores phone number of member in a temporary String
                LocalDate dob = LocalDate.parse(arrOfStr[4]);               // Stores date of birth of member in a temporary String
                boolean membershipActive = Boolean.parseBoolean(arrOfStr[5]);   // Stores state of membership in a temporary boolean
                boolean hasPaid = Boolean.parseBoolean(arrOfStr[6]);    // Stores paid status of membership in a temporary boolean

                if (arrOfStr[0].equalsIgnoreCase("true")) {     // Checks if member is Leisure or Competitive
                    CompetitiveSwimmer compSwimmer = new CompetitiveSwimmer(id,
                            name, phone, dob, membershipActive, hasPaid);   // Creates the member with attributes

                    int disciplineAmount = arrOfStr.length - 7;     //Stores amounts of active Discipline types of Member
                    for (int i = 0; i < disciplineAmount; i++) {
                        compSwimmer.getSwimmingDisciplineList().add(new SwimmingDiscipline
                                (arrOfStr[7 + i])); // MAGIC NUMBER 7 is to get the start position of enumSwimDiscipline in array
                    } // End of for loop
                    membersList.add(compSwimmer);           // Adds the created swimmerMember to memberList Array
                } else {
                    LeisureSwimmer leisureSwimmer = new LeisureSwimmer(id,
                            name, phone, dob, membershipActive, hasPaid);   // Creates the member with attributes
                    membersList.add(leisureSwimmer);        // Adds the created swimmerMember to memberList Array
                } // End of if / else statement
            }   // End of while loop
            return membersList;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Load Member Fejl");
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
                String[] arr = readFromFile.nextLine().split(";"); // Stores line from file to arr
                idArray.add(Integer.parseInt(arr[1])); // This adds the ID value to the temporary arrayList idArray
            } // End of while loop
            return idArray.get(idArray.size() - 1) + 1; // Returns the ID value + 1 of the last member within the fullMemberList file
        } catch (IndexOutOfBoundsException | FileNotFoundException e) {
            return 1000;        // Returns 1000 for the static Member ID if file contains no members
        } // End of try / catch statement
    } // End of method

    public void appendResult(Employee employee, Database database, CompetitiveSwimmer swimmer, int hasSwimDiscipline) {
        Coach temporaryCoach = new Coach();

        try {
            appendToFile = new FileOutputStream(memberResultFile, true);
            StringBuilder sb = new StringBuilder();
            /*
             * To illustrate which parameters are being stores this bulky setup seems appropriate
             * The method stores all valuable attributes from being uniqueID, coach and result parameters
             * Then the method append each attribute to the results file
             */
            int swimmerUniqueID = swimmer.getUniqueID();                                    // Store uniqueID
            String coachName = temporaryCoach.loadCoachOfMember(database,swimmer);  // Store coach name
            String swimmingDisciplineType = swimmer.getSwimmingDisciplineList().get(hasSwimDiscipline)+";"; // Store SwimmingDisciplineType
            int numberInArray = swimmer.getSwimmingDisciplineList().get(hasSwimDiscipline).
                    getSwimmingDisciplineResults().size()-1;    // Store the last entry of added results
            int distance = swimmer.getSwimmingDisciplineList().get(hasSwimDiscipline).
                    getSwimmingDisciplineResults().get(numberInArray).getDistance();    // Stores distance of result
            String time = String.valueOf(swimmer.getSwimmingDisciplineList().get(hasSwimDiscipline).
                    getSwimmingDisciplineResults().get(numberInArray).getSwimTime());    // Stores time of result
            LocalDate date = swimmer.getSwimmingDisciplineList().get(hasSwimDiscipline).
                    getSwimmingDisciplineResults().get(numberInArray).getDate();        // Stores date of result
            boolean isCompetitive = swimmer.getSwimmingDisciplineList().get(hasSwimDiscipline).
                    getSwimmingDisciplineResults().get(numberInArray).isCompetitive(); // Stores competitiveness of result
            int rank = swimmer.getSwimmingDisciplineList().get(hasSwimDiscipline).
                    getSwimmingDisciplineResults().get(numberInArray).getRank();        // Stores rank placement of result

            sb.append(swimmerUniqueID).append(";");                 // Appends ID to StringBuilder
            sb.append(coachName).append(";");                       // Appends Coach name to StringBuilder
            sb.append(swimmingDisciplineType);          // Appends Swimming Discipline Type to StringBuilder
            sb.append(distance).append(";");            // Appends distance from result to StringBuilder
            sb.append(time).append(";");                // Appends time from result to StringBuilder
            sb.append(date).append(";");                // Appends date from result to StringBuilder
            sb.append(isCompetitive).append(";");       // Appends competitiveness from result to StringBuilder
            sb.append(rank);                            // Appends rank from result to StringBuilder
            sb.append("\n");                            // Appends a new line to StringBuilder

            appendToFile.write(sb.toString().getBytes());
            appendToFile.close();

        } catch (IOException e) {
            System.out.println("File not found");
        } // End of try / catch statement
    } // End of method

/*
    //MyFinestSmadderkODE *atc
    public void loadResults(HashMap<Member, Coach> swimmersCoachAssociationList) {
        try {
            readFromFile = new Scanner(memberResultFile);
            while (readFromFile.hasNextLine()) { //while loop begin
                String[] resultParam = readFromFile.nextLine().split(";");
                for (Map.Entry<Member, Coach> set : swimmersCoachAssociationList.entrySet()) {
                    if (set.getKey().getUniqueID() == Integer.parseInt(resultParam[0])) {
                        for (int i = 0; i < ((CompetitiveSwimmer) set.getKey()).
                                getSwimmingDisciplineList().size(); i++) {
                            if (((CompetitiveSwimmer) set.getKey()).getSwimmingDisciplineList().get(i)
                                    .equals(resultParam[2])) {
                                ((CompetitiveSwimmer) set.getKey()).getSwimmingDisciplineList().get(i).
                                        getSwimmingDisciplineResults().add(new SwimmingResult
                                                (Integer.parseInt(resultParam[3]),
                                                        resultParam[4], resultParam[5], Boolean.parseBoolean(resultParam[6]),
                                                        Integer.parseInt(resultParam[7])));
                            }
                        }
                    }
                }
            }//while loop ends
        } catch (FileNotFoundException e) {
            System.out.println("Load Result Fejl 1");
        }
    }

 */


    /*
    * This method loads all results from results file, and adds them to corresponding member with matching ID from file
     */
    public void loadResultMethod(Database database) {

        try {
            readFromFile = new Scanner(memberResultFile);
            while (readFromFile.hasNextLine()) {
                String[] arr = readFromFile.nextLine().split(";");  // Stores whole line from file to arr
                int uniqueId = Integer.parseInt(arr[0]);    // Stores UniqueID from arr
                String swimDiscipline = arr[2];                // Stores SwimmingDisciplineType from arr
                int distance = Integer.parseInt(arr[3]);    // Stores distance from arr
                String swimTime = arr[4];
                LocalDate date = LocalDate.parse(arr[5]);                          // Stores date from arr
                // Stores swimTime from arr
                boolean isCompetitive = Boolean.parseBoolean(arr[6]);   // Stores competitiveness from arr
                int rank = Integer.parseInt(arr[7]);        // Stores rank position from arr

                /*
                * The method first starts iterating through the HashMap containing the association between the swimmers and coaches
                *  - Then the method identifies the match between UniqueID from result with Key value from HashMap
                *  - Then the method verifies the SwimmingDisciplineType name from results with the Key value from hashMap
                *  - Then the method conditionally chooses between boolean statement of competitiveness the constructor
                *  - The constructor chosen will be filled with stored attributes
                 */
                for (Map.Entry<Member, Coach> set : database.getSwimmersCoachAssociationList().entrySet()) {
                    if (set.getKey().getUniqueID() == uniqueId) {
                        for (int i = 0; i < ((CompetitiveSwimmer)set.getKey()).getSwimmingDisciplineList().size(); i++) {
                            if (((CompetitiveSwimmer) set.getKey()).getSwimmingDisciplineList().get(i).
                                    getSwimmingDiscipline().
                                    equals(SwimmingDiscipline.SwimmingDisciplineTypes.valueOf(swimDiscipline))) {
                                if (isCompetitive) {
                                    ((CompetitiveSwimmer) set.getKey()).getSwimmingDisciplineList().get(i).
                                            getSwimmingDisciplineResults().
                                            add(new SwimmingResult(distance, swimTime, date, true, rank));
                                } else {
                                    ((CompetitiveSwimmer) set.getKey()).getSwimmingDisciplineList().get(i).
                                            getSwimmingDisciplineResults().
                                            add(new SwimmingResult(distance, swimTime, date, false, 0));
                                } // End of inner if / else statement
                            } // End of second inner if statement
                        } // of for loop
                    } // End of outer if statement
                } // End of HashMap for loop iteration
            } // End of while loop
        } catch (FileNotFoundException e) {
            System.out.println("File doesn't exist");
        } // End of try / catch statement
    } // End of method





    public ArrayList<Coach> loadCoachList(ArrayList<Coach> coachList) {
        try {
            readFromFile = new Scanner(coachListFile);
            while (readFromFile.hasNextLine()) {
                String s = readFromFile.nextLine();
                // Stores the whole line containing a member to temporary String
                String[] arrOfStr = s.split(";");     // Delimiting by semicolon sign and adds to a temporary array
                String username = arrOfStr[0];              //Stores the username
                String name = arrOfStr[1];                  // Stores the name of Coach in a temporary String
                String phone = arrOfStr[2];                 // Stores phone number of Coach in a temporary String


                Scanner readFromPassFile = new Scanner(passwordList);   // finds username matching from coach list
                String psswPassword = "";                                     // Placeholder for password matching username

                while(readFromPassFile.hasNextLine()) {
                    String[] arr = readFromPassFile.nextLine().split(";");
                    if (arr[0].equalsIgnoreCase(username)) {   // If username from password file matches username from coach list
                        psswPassword = arr[1];                            // Stores matching password for coach
                        readFromPassFile.close();
                        break;                                          // breaks out of while loop
                    } // End of if statement
                } // End of while loop
                Coach coachToList = new Coach(name,phone, username, psswPassword); // Creates a coach with these attributes
                coachList.add(coachToList);
            }   // End of while loop
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } // End of try / catch statement
        return coachList;
    } // End of method


    /*
     * This method loads SwimmerCoachAssociation based on result ID and coachList
     */
    public HashMap<Member, Coach> loadSwimmerCoachAssociationList(HashMap<Member, Coach> swimmerCoachList, Database database) {

        try {
            readFromFile = new Scanner(swimmerCoachAssociationList);
            while (readFromFile.hasNextLine()) {
                String[] arr = readFromFile.nextLine().split(";");    // Stores line from file to arr
                int swimmerID = Integer.parseInt(arr[0]);                // Stores swimmers unique ID from arr
                String coachName = arr[1];                                  // Stores coach name from arr

                /*
                * If a uniqueID from the current memberList matches that of the uniqueID stored in file
                * The method goes on to see if a coach candidate also matches in pair value of coach name
                * if both parameters have a match, the two entities are put inside the HashMap from Database class
                 */
                for (Member member : database.getMemberList()) {
                    if (member.getUniqueID() == swimmerID) {
                        for (Coach coach : database.getCoachList()) {
                            if (coach.getName().equals(coachName)) {
                                swimmerCoachList.put(member, coach);
                            } // End of inner if statement
                        } // End of inner for loop
                    } // End of outer if statement
                } // End of outer for loop
            } // End of while loop
            return swimmerCoachList;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } // End of try / catch statement
        return swimmerCoachList;
    }


    /*
    * This method writes the association link between a member and coach whenever a competitive swimmer is added
     */
    public void writeToSwimmerCoachAssociationFile(Database associationList) {
        try {
            printToFile = new PrintStream(swimmerCoachAssociationList);

            /*
            * Whenever the HashMap from database has had placed Key / Value pairs, an iteration and file writing happens
            * of each key / value pair.
             */
            for (Map.Entry<Member, Coach> set : associationList.getSwimmersCoachAssociationList().entrySet()) {
                printToFile.println(set.getKey().getUniqueID() + ";" + set.getValue().getName());
            } // End of for loop
            printToFile.close();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } // End of try / catch statement
    } // End of method

    public void deleteCoachLoginFromFile(String username) {
        try{
            ArrayList<String> usernamePassText = new ArrayList<>();
            readFromFile = new Scanner(passwordList);
            while(readFromFile.hasNextLine()) {
                String[] tokens = readFromFile.nextLine().split(";");
                usernamePassText.add(tokens[0]);
                usernamePassText.add(tokens[1]);
            }
            readFromFile.close();
            printToFile = new PrintStream(passwordList);
            for (int i = 0; i < usernamePassText.size(); i += 2) {
                if (usernamePassText.get(i).equals(username)){
                    usernamePassText.remove(i);
                    usernamePassText.remove(i);
                }
            }
            for (int i = 0; i < usernamePassText.size(); i += 2) {
                printToFile.println(usernamePassText.get(i) + ";" + usernamePassText.get(i+1));
            }
            printToFile.close();
        } catch (FileNotFoundException e){
            System.out.println(e);
        }
    }

    public void loggingAction(String action) {
        try {
            printToFile = new PrintStream(new FileOutputStream(logFile, true));
            printToFile.print(LocalDateTime.now().format(DateTimeFormatter.ofPattern("u:MM:dd:HH:mm:ss")));
            printToFile.println(" " + action);
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }

    /*
     * This method prints a welcome emoji presenting creators of this project
     */
    public void printWelcomeSharks() {
        try {
            readFromFile = new Scanner(sharksPrint);
            while (readFromFile.hasNextLine()) {
                System.out.println(readFromFile.nextLine());
            } // End of while loop
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } // End of try / catch statement
    } // End of method

} // End of class