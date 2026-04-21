package com.ilya.arrayapp.comparator;

import com.ilya.arrayapp.entity.NumericArray;
import java.util.Comparator;

public class ArrayByIdComparator implements Comparator<NumericArray> {
    @Override
    public int compare(NumericArray a1, NumericArray a2) {
        return Integer.compare(a1.getId(), a2.getId());
    }
}