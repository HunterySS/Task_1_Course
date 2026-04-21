package com.ilya.arrayapp.comparator;

import com.ilya.arrayapp.entity.NumericArray;
import com.ilya.arrayapp.warehouse.Warehouse;
import java.util.Comparator;

public class ArrayByMaxComparator implements Comparator<NumericArray> {
    @Override
    public int compare(NumericArray a1, NumericArray a2) {
        var stats1 = Warehouse.getInstance().getStatistics(a1.getId());
        var stats2 = Warehouse.getInstance().getStatistics(a2.getId());

        double max1 = stats1 != null ? stats1.getMax() : 0.0;
        double max2 = stats2 != null ? stats2.getMax() : 0.0;

        return Double.compare(max1, max2);
    }
}