package com.ilya.arrayapp.factory.impl;

import com.ilya.arrayapp.entity.NumericArray;

public interface ArrayFactory {
    NumericArray createArray(double[] values);
}