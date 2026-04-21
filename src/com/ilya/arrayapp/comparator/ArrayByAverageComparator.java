package com.ilya.arrayapp.comparator;

import com.ilya.arrayapp.entity.NumericArray;
import com.ilya.arrayapp.warehouse.Warehouse;
import java.util.Comparator;

public class ArrayByAverageComparator implements Comparator<NumericArray> {
    @Override
    public int compare(NumericArray a1, NumericArray a2) {
        var stats1 = Warehouse.getInstance().getStatistics(a1.getId());
        var stats2 = Warehouse.getInstance().getStatistics(a2.getId());

        double avg1 = stats1 != null ? stats1.getAverage() : 0.0;
        double avg2 = stats2 != null ? stats2.getAverage() : 0.0;

        return Double.compare(avg1, avg2);
    }
}