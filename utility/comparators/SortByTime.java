package utility.comparators;

import actors.SwimmingResult;

public class SortByTime extends Comparator{

    @Override
    public int compare(SwimmingResult swimResult, SwimmingResult otherSwimResult) {
        return swimResult.getSwimTime().compareTo(otherSwimResult.getSwimTime());
    }
}
