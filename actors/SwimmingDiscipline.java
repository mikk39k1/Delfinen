package actors;

import utility.UI;

import java.util.ArrayList;


/*
* This class represents what a swimming discipline contains
*  - In essence it contains a type (enum presentation), and an array list of results being its own class
 */
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
    public SwimmingDisciplineTypes getSwimmingDisciplineType() {
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
