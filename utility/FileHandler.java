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

    private PrintStream printToFile;
    private PrintStream appendPrintToFile;
    private Scanner readFromFile;

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
            }

        } catch (Exception e) {
            System.out.println("File doesn't exist\n");
        }
        System.out.println("Username doesn't exist\n");
        return "0";
    }

    public String checkPassword(String password) {//reads from passwd file and gets password
        try {
            Scanner readLoginCredentials = new Scanner(new File("files/passwd.txt"));

            while (readLoginCredentials.hasNextLine()) {
                String loadedPassword = readLoginCredentials.next();
                if (loadedPassword.equals(password)) {
                    readLoginCredentials.close();
                    return loadedPassword;
                }
            }
        } catch (Exception e) {
            System.out.println("File doesn't exist\n");
        }
        System.out.println("Password is incorrect  ");
        return "0";
    }

    public void writeToFullMembersList(ArrayList<Member> swimmers) {
        try {
            //printToFile = new PrintStream(memberArrayListFile);
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

                if (swimmer instanceof CompetitiveSwimmer)
                    for (SwimmingDiscipline type : ((CompetitiveSwimmer) swimmer).getSwimmingDisciplineList()) {
                        printToFile.print((";") + (type));
                    }
                printToFile.println("");
            }
        } catch (FileNotFoundException e) {
            System.out.println("Noget gik galt");
        }
    }
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


    public ArrayList<Member> loadMemberList(ArrayList<Member> membersList) {
        try {
            readFromFile = new Scanner(memberArrayListFile);
            while (readFromFile.hasNextLine()) {
                String s = readFromFile.nextLine();
                String[] arrOfStr = s.split(";");

                if (arrOfStr[0].equalsIgnoreCase("true")) {
                    int id = Integer.parseInt(arrOfStr[1]);
                    String name = arrOfStr[2];
                    String phone = arrOfStr[3];
                    String dob = arrOfStr[4];
                    boolean membershipActive = Boolean.parseBoolean(arrOfStr[5]);

                    CompetitiveSwimmer compSwimmer = new CompetitiveSwimmer(id, name, phone, dob, membershipActive);

                    int disciplineAmount = arrOfStr.length - 6;
                    for (int i = 0; i < disciplineAmount; i++) {
                        compSwimmer.getSwimmingDisciplineList().add(new SwimmingDiscipline
                                (arrOfStr[6 + i])); //magicnumber is to get the start pos enumSwimDisciplin in array
                    }
                    membersList.add(compSwimmer);
                } else {
                    int id = Integer.parseInt(arrOfStr[1]);
                    String name = arrOfStr[2];
                    String phone = arrOfStr[3];
                    String dob = arrOfStr[4];
                    boolean membershipActive = Boolean.parseBoolean(arrOfStr[5]);
                    LeisureSwimmer leisureSwimmer = new LeisureSwimmer(id, name, phone, dob, membershipActive);
                    membersList.add(leisureSwimmer);
                }
            }
            Member.setID(loadID());
            return membersList;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return membersList;
    }

    public int loadID() {
        ArrayList<Integer> idArray = new ArrayList<>();
        try {
            readFromFile = new Scanner(memberArrayListFile);
            while (readFromFile.hasNextLine()) {
                String s = readFromFile.nextLine();
                String[] arrOfStr = s.split(";");
                idArray.add(Integer.parseInt(arrOfStr[1]));
            }
            return idArray.get(idArray.size()-1)+1;
        } catch (IndexOutOfBoundsException | FileNotFoundException e) {
            return 1000;
        }
    }
}