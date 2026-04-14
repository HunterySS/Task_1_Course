package com.ilya.arrayapp.service.impl;

import com.ilya.arrayapp.entity.NumericArray;
import com.ilya.arrayapp.exception.LengthMismatchException;
import com.ilya.arrayapp.exception.NullArrayException;

public interface SortService {
    void bubbleSort(NumericArray array) throws NullArrayException, LengthMismatchException;
    void quickSort(NumericArray array) throws NullArrayException, LengthMismatchException;
}