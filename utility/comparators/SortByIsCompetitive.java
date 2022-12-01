package utility.comparators;

import actors.SwimmingResult;

public class SortByIsCompetitive extends Comparator{
    @Override
    public int compare(SwimmingResult swimResult, SwimmingResult otherSwimResult) {
        return Boolean.compare(swimResult.isCompetitive(), otherSwimResult.isCompetitive());
    }
}
