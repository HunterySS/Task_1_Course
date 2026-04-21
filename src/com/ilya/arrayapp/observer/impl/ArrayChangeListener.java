package com.ilya.arrayapp.observer.impl;

import com.ilya.arrayapp.entity.NumericArray;

public interface ArrayChangeListener {
    void onArrayChanged(NumericArray array);
}