package com.ilya.arrayapp.comparator;

import com.ilya.arrayapp.entity.NumericArray;
import com.ilya.arrayapp.warehouse.Warehouse;
import java.util.Comparator;

public class ArrayByMinComparator implements Comparator<NumericArray> {
    @Override
    public int compare(NumericArray a1, NumericArray a2) {
        var stats1 = Warehouse.getInstance().getStatistics(a1.getId());
        var stats2 = Warehouse.getInstance().getStatistics(a2.getId());

        double min1 = stats1 != null ? stats1.getMin() : 0.0;
        double min2 = stats2 != null ? stats2.getMin() : 0.0;

        return Double.compare(min1, min2);
    }
}