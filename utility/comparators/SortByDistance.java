package utility.comparators;

import actors.SwimmingResult;

public class SortByDistance extends Comparator{
    @Override
    public int compare(SwimmingResult swimResult, SwimmingResult otherSwimResult) {
        return Integer.compare(swimResult.getDistance(), otherSwimResult.getDistance());
    }
}
