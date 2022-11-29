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
     * This method is verifying username exist within secret file
     */
    public String checkUsername(String username) {//reads from passwd file and gets usernames
        try {
            Scanner readLoginCredentials = new Scanner(new File("files/passwd.txt"));

            while (readLoginCredentials.hasNextLine()) {
                String loadedUsername = readLoginCredentials.next();
                if (username.equals(loadedUsername)) {
                    readLoginCredentials.close();
                    return loadedUsername;
                } else {
                    readLoginCredentials.next();
                }
            } // End of while loop

        } catch (Exception e) {
            System.out.println("File doesn't exist\n");
        }
        System.out.println("Username doesn't exist\n");
        return "0";
    } // End of method


    /*
     * This method verifies that prompted password works with username within secret file, then returns password if match
     */
    public String checkPassword(String password) {
        try {
            Scanner readLoginCredentials = new Scanner(new File("files/passwd.txt"));

            while (readLoginCredentials.hasNextLine()) {
                String loadedPassword = readLoginCredentials.next();
                if (loadedPassword.equals(password)) {
                    readLoginCredentials.close();
                    return loadedPassword;
                }
            } // End of while loop
        } catch (Exception e) {
            System.out.println("File doesn't exist\n");
        }
        System.out.println("Password is incorrect  ");
        return "0";
    } // End of method


    /*
     * This method writes any added member in to the fullMemberList file
     */
    public void writeToFullMembersList(ArrayList<Member> swimmers) {
        try {
            printToFile = new PrintStream(memberArrayListFile);

            for (Member swimmer : swimmers) {

                if (swimmer instanceof CompetitiveSwimmer) {
                    printToFile.print("true");
                } else {
                    printToFile.print("false");
                }

                printToFile.print(";" + swimmer.getUniqueID());
                printToFile.print(";" + swimmer.getName());
                printToFile.print(";" + swimmer.getPhoneNumber());
                printToFile.print(";" + swimmer.getAge());
                printToFile.print(";" + swimmer.isIsMembershipActive());
                printToFile.print(";" + swimmer.isHasPaid());

                if (swimmer instanceof CompetitiveSwimmer)
                    for (SwimmingDiscipline type : ((CompetitiveSwimmer) swimmer).getSwimmingDisciplineList()) {
                        printToFile.print((";") + (type));
                    } // End of inner for loop
                printToFile.println("");
            } // End of outer for loop
        } catch (FileNotFoundException e) {
            System.out.println("Noget gik galt");
        }
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
                String s = readFromFile.nextLine();
                String[] arrOfStr = s.split(";");
                int id = Integer.parseInt(arrOfStr[1]);
                if (arrOfStr[0].equalsIgnoreCase("true")) {

                    String name = arrOfStr[2];
                    String phone = arrOfStr[3];
                    String dob = arrOfStr[4];
                    boolean membershipActive = Boolean.parseBoolean(arrOfStr[5]);
                    boolean hasPaid = Boolean.parseBoolean(arrOfStr[6]);

                    CompetitiveSwimmer compSwimmer = new CompetitiveSwimmer(id,
                            name, phone, dob, membershipActive, hasPaid);

                    int disciplineAmount = arrOfStr.length - 7;
                    for (int i = 0; i < disciplineAmount; i++) {
                        compSwimmer.getSwimmingDisciplineList().add(new SwimmingDiscipline
                                (arrOfStr[7 + i])); // magicNumber is to get the start pos enumSwimDiscipline in array
                    }
                    membersList.add(compSwimmer);
                } else {
                    String name = arrOfStr[2];
                    String phone = arrOfStr[3];
                    String dob = arrOfStr[4];
                    boolean membershipActive = Boolean.parseBoolean(arrOfStr[5]);
                    boolean hasPaid = Boolean.parseBoolean(arrOfStr[6]);
                    LeisureSwimmer leisureSwimmer = new LeisureSwimmer(id,
                            name, phone, dob, membershipActive, hasPaid);
                    membersList.add(leisureSwimmer);
                }
            }   // End of while loop
            return membersList;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } // End of Catch
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
                idArray.add(Integer.parseInt(arrOfStr[1]));
            } // End of while loop
            return idArray.get(idArray.size() - 1) + 1;
        } catch (IndexOutOfBoundsException | FileNotFoundException e) {
            return 1000;
        }
    }
    public void printWelcomeSharks(){
        try {
            readFromFile = new Scanner(sharksPrint);
            while (readFromFile.hasNextLine()){
                System.out.println(readFromFile.nextLine());
            }
        }catch (FileNotFoundException e){
            System.out.println(e);
        }
    }
}