package com.arrayapp.factory;

import com.arrayapp.entity.NumericArray;

public interface ArrayFactory {
    NumericArray createArray(double[] values);
}