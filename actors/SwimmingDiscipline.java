package actors;

import utility.UI;

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


    // Constructor -------------------------------
    public SwimmingDiscipline(UI ui) {
        this.swimmingDiscipline = ui.setSwimmingDisciplineType();
    }

    // Getter ------------------------------------
    public SwimmingDisciplineTypes getSwimmingDiscipline() {
        return swimmingDiscipline;
    }
}
