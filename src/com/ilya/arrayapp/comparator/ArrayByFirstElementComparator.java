package com.ilya.arrayapp.comparator;

import com.ilya.arrayapp.entity.NumericArray;
import java.util.Comparator;

public class ArrayByFirstElementComparator implements Comparator<NumericArray> {
    @Override
    public int compare(NumericArray a1, NumericArray a2) {
        double first1 = a1.getValues().length > 0 ? a1.getValues()[0] : Double.NEGATIVE_INFINITY;
        double first2 = a2.getValues().length > 0 ? a2.getValues()[0] : Double.NEGATIVE_INFINITY;
        return Double.compare(first1, first2);
    }
}