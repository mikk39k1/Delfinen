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


    // Constructor -------------------------------
    public SwimmingDiscipline(UI ui) {
        this.swimmingDiscipline = ui.setSwimmingDisciplineType();
    }

    // Getter ------------------------------------
    public SwimmingDisciplineTypes getSwimmingDiscipline() {
        return swimmingDiscipline;
    }

    public ArrayList<SwimmingResult> getSwimmingDisciplineResults() {
        return swimmingDisciplineResults;
    }
}
