package utility;

import actors.*;
import database.Database;
import utility.member_comparators.SortByMemberAge;
import utility.member_comparators.SortByMemberID;
import utility.member_comparators.SortByMemberName;
import utility.member_comparators.SortByMemberPhoneNumber;
import utility.result_comparators.SortByDistance;
import utility.result_comparators.SortByIsCompetitive;
import utility.result_comparators.SortByRank;
import utility.result_comparators.SortByTime;

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
    SortByDistance sortByDistance = new SortByDistance();
    SortByIsCompetitive sortByIsCompetitive = new SortByIsCompetitive();
    SortByRank sortByRank = new SortByRank();
    SortByTime sortByTime = new SortByTime();

    public ArrayList<SwimmingResult> swimmingResultList(Database database, SwimmingDiscipline.SwimmingDisciplineTypes swimType) {
        ArrayList<SwimmingResult> swimResultList = new ArrayList<>();
        for (Map.Entry<Member, Coach> set : database.getSwimmersCoachAssociationList().entrySet()) {
            for (int i = 0; i < database.getSwimmersCoachAssociationList().size(); i++) {
                if (((CompetitiveSwimmer) set.getKey()).getSwimmingDisciplineList().get(i).getSwimmingDiscipline().equals(swimType)) {
                    swimResultList.addAll(((CompetitiveSwimmer) set.getKey()).getSwimmingDisciplineList().
                            get(i).getSwimmingDisciplineResults());
                } // End of inner if statement
            } // End of for loop
        } // End of outer for loop
        return swimResultList;
    } // End of method


    public void setSortByDistance(Database database, SwimmingDiscipline.SwimmingDisciplineTypes swimType, UI ui) {

        ArrayList<SwimmingResult> localist = swimmingResultList(database, swimType);

        switch(ui.setDistance()) {
            case 100 ->{
                localist.removeIf(swimmingResult -> swimmingResult.getDistance() == 200);
                localist.removeIf(swimmingResult -> swimmingResult.getDistance() == 500);
                localist.sort(sortByTime);
            }
            case 200 ->{
                localist.removeIf(swimmingResult -> swimmingResult.getDistance() == 100);
                localist.removeIf(swimmingResult -> swimmingResult.getDistance() == 500);
                localist.sort(sortByTime);
            }
            case 500 ->{
                localist.removeIf(swimmingResult -> swimmingResult.getDistance() == 100);
                localist.removeIf(swimmingResult -> swimmingResult.getDistance() == 200);
                localist.sort(sortByTime);
            }
        }
        localist.sort(sortByDistance);
        System.out.println(localist);

    }

    /*
    public void setSortByIsCompetitive(Database database, SwimmingDiscipline swimType, UI ui) {
        swimmingResultList(database, swimType).sort(sortByIsCompetitive);
    }

    public void setSortByRank(Database database, SwimmingDiscipline swimType, UI ui) {
        List<Member> localist = database.getMemberList();

    }

    public void setSortByTime(Database database, SwimmingDiscipline swimType, UI ui) {
        List<Member> localist = new ArrayList<>();

    }

     */

}
