package com.ilya.arrayapp.service.impl;

import com.ilya.arrayapp.entity.NumericArray;

public interface SortService {
    void bubbleSort(NumericArray array);
    void quickSort(NumericArray array);
}