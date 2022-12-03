package utility;

import actors.*;
import database.Database;
import utility.member_comparators.SortByMemberAge;
import utility.member_comparators.SortByMemberID;
import utility.member_comparators.SortByMemberName;
import utility.member_comparators.SortByMemberPhoneNumber;
import utility.result_comparators.SortByTime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SuperSorterThreeThousand {
    // Member SORT SECTION ----------------------------------------------------------
    SortByMemberAge sortByMemberAge = new SortByMemberAge();
    SortByMemberID sortByMemberID = new SortByMemberID();
    SortByMemberName sortByMemberName = new SortByMemberName();
    SortByMemberPhoneNumber sortByMemberPhoneNumber = new SortByMemberPhoneNumber();

    public List<Member> setSortByMemberAge(Database database) {
        List<Member> locaList = database.getMemberList();
        locaList.sort(sortByMemberAge);
        return locaList;
    }

    public List<Member> setSortByMemberID(Database database) {
        List<Member> locaList = database.getMemberList();
        locaList.sort(sortByMemberID);
        return locaList;
    }

    public List<Member> setSortByMemberName(Database database) {
        List<Member> locaList = database.getMemberList();
        locaList.sort(sortByMemberName);
        return locaList;
    }

    public List<Member> setSortByMemberPhoneNumber(Database database) {
        List<Member> locaList = database.getMemberList();
        locaList.sort(sortByMemberPhoneNumber);
        return locaList;
    }


    // Result SORT SECTION ----------------------------------------------------------
    SortByTime sortByTime = new SortByTime();

    public ArrayList<SwimmingResult> swimmingResultList(Database database, SwimmingDiscipline.SwimmingDisciplineTypes swimType) {
        ArrayList<SwimmingResult> swimResultList = new ArrayList<>();
        for (Map.Entry<Member, Coach> set : database.getSwimmersCoachAssociationList().entrySet()) {
            for (int i = 0; i < ((CompetitiveSwimmer)set.getKey()).getSwimmingDisciplineList().size(); i++) {
                if (((CompetitiveSwimmer) set.getKey()).getSwimmingDisciplineList().get(i).getSwimmingDiscipline().equals(swimType)) {
                    swimResultList.addAll(((CompetitiveSwimmer) set.getKey()).getSwimmingDisciplineList().
                            get(i).getSwimmingDisciplineResults());
                } // End of inner if statement
            } // End of for loop
        } // End of outer HashMap iteration
        return swimResultList;
    } // End of method

    public ArrayList<SwimmingResult> oneSwimmersResultList(CompetitiveSwimmer swimmer, Database database, SwimmingDiscipline.SwimmingDisciplineTypes swimType) {
        ArrayList<SwimmingResult> swimResultList = new ArrayList<>();
        for (Map.Entry<Member, Coach> set : database.getSwimmersCoachAssociationList().entrySet()) {
            if (set.getKey().getName().equals(swimmer.getName())) {
                for (int i = 0; i < swimmer.getSwimmingDisciplineList().size(); i++) {
                    if (((CompetitiveSwimmer) set.getKey()).getSwimmingDisciplineList().get(i).getSwimmingDiscipline().equals(swimType)) {
                        swimResultList.addAll(((CompetitiveSwimmer) set.getKey()).getSwimmingDisciplineList().
                                get(i).getSwimmingDisciplineResults());
                    } // End of inner if statement
                } // End of inner for loop
            } //End of out if statement
        } // End of outer for HashMap iteration
        return swimResultList;
    } // End of method



    public void setSortByDistance(UI ui, ArrayList<SwimmingResult> swimmingResults) {
        switch(ui.setDistance()) {
            case 100 ->{
                swimmingResults.removeIf(swimmingResult -> swimmingResult.getDistance() != 100);
                swimmingResults.sort(sortByTime);
            } // End of case 100
            case 200 ->{
                swimmingResults.removeIf(swimmingResult -> swimmingResult.getDistance() != 200);
                swimmingResults.sort(sortByTime);
            } // End of case 200
            case 500 ->{
                swimmingResults.removeIf(swimmingResult -> swimmingResult.getDistance() != 500);
                swimmingResults.sort(sortByTime);
            } // End of case 500
        } // End of switch case
        System.out.println(swimmingResults);
    } // End of method





    public void setSortByIsCompetitive(UI ui, ArrayList<SwimmingResult> swimmingResults) {

        if (!ui.setCompetitiveness()) {
            swimmingResults.removeIf(SwimmingResult::isCompetitive);
            swimmingResults.sort(sortByTime);
        } else {
            swimmingResults.removeIf(swimmingResult -> !swimmingResult.isCompetitive());
            swimmingResults.sort(sortByTime);
        }
        System.out.println(swimmingResults);
    }

    public void setSortByRank(UI ui, ArrayList<SwimmingResult> swimmingResults) {

        switch(ui.setRank()) {
            case 3 ->{
                swimmingResults.removeIf(swimmingResult -> swimmingResult.getRank() > 3);
                swimmingResults.sort(sortByTime);
            } // End of case 100
            case 5 ->{
                swimmingResults.removeIf(swimmingResult -> swimmingResult.getRank() > 5);
                swimmingResults.sort(sortByTime);
            } // End of case 200
            case 10 ->{
                swimmingResults.removeIf(swimmingResult -> swimmingResult.getRank() > 10);
                swimmingResults.sort(sortByTime);
            } // End of case 500
        } // End of switch case
        System.out.println(swimmingResults);
    }

    public void setSortByDate(UI ui, ArrayList<SwimmingResult> swimmingResults) {

        switch (ui.chooseTimeFrame()) {
            case 1 -> {
                swimmingResults.removeIf(swimmingResult -> swimmingResult.getDate().isBefore(LocalDate.now().minusMonths(3)));
                swimmingResults.sort(sortByTime);
            }
            case 2 -> {
                swimmingResults.removeIf(swimmingResult -> swimmingResult.getDate().isBefore(LocalDate.now().minusMonths(6)));;
                swimmingResults.sort(sortByTime);
            }
            case 3 -> {
                swimmingResults.removeIf(swimmingResult -> !(swimmingResult.getDate().getYear() == LocalDate.now().getYear()));
                swimmingResults.sort(sortByTime);
            }
            case 4 -> {
                int specificYear = LocalDate.now().getYear() - ui.readYear();
                swimmingResults.removeIf(swimmingResult -> swimmingResult.getDate().isBefore(LocalDate.now().minusYears(specificYear)));
                swimmingResults.sort(sortByTime);
            }
            case 5 -> {
                int specificYear = LocalDate.now().getYear() - ui.readYear();
                swimmingResults.removeIf(swimmingResult -> !swimmingResult.getDate().isEqual(LocalDate.now().minusYears(specificYear)));
                swimmingResults.sort(sortByTime);
            }
            case 6 -> {
                LocalDate date = ui.setDate();
                swimmingResults.removeIf(swimmingResult -> !swimmingResult.getDate().isEqual(date));
                swimmingResults.sort(sortByTime);
            }
        }
        System.out.println(swimmingResults);

    }
}
