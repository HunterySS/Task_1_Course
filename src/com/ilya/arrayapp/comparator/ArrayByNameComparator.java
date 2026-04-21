package com.ilya.arrayapp.comparator;

import com.ilya.arrayapp.entity.NumericArray;
import java.util.Comparator;

public class ArrayByNameComparator implements Comparator<NumericArray> {
    @Override
    public int compare(NumericArray a1, NumericArray a2) {
        return a1.getName().compareTo(a2.getName());
    }
}