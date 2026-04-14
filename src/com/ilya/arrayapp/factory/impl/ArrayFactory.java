package com.ilya.arrayapp.factory.impl;

import com.ilya.arrayapp.entity.NumericArray;
import com.ilya.arrayapp.exception.NullArrayException;

public interface ArrayFactory {
    NumericArray createArray(double[] values) throws NullArrayException;
}