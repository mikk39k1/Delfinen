package actors;

import utility.UI;

import java.util.ArrayList;

public class SwimmingDiscipline {

    public enum SwimmingDisciplineTypes {
        CRAWL,
        BREASTSTROKE,
        FREESTYLE,
        BACKCRAWL,
        BUTTERFLY
    }

    // Attributes --------------------------------
    private final SwimmingDisciplineTypes swimmingDiscipline;
    private final ArrayList<SwimmingResult> swimmingDisciplineResults = new ArrayList<>();


    // Constructor manual add method-------------------------------
    public SwimmingDiscipline(UI ui) {
        this.swimmingDiscipline = ui.setSwimmingDisciplineType();
        ui.printLn("Swimming Discipline added");
    }

    // Constructor for loading method-------------------------------
    public SwimmingDiscipline(String swimDisciplineType) {
        this.swimmingDiscipline = SwimmingDiscipline.SwimmingDisciplineTypes.valueOf
                (swimDisciplineType.toUpperCase());
    }

    // Getter ------------------------------------
    public SwimmingDisciplineTypes getSwimmingDiscipline() {
        return swimmingDiscipline;
    }

    public ArrayList<SwimmingResult> getSwimmingDisciplineResults() {
        return swimmingDisciplineResults;
    }


    @Override
    public String toString() {
        return "" + swimmingDiscipline;
    }
}
