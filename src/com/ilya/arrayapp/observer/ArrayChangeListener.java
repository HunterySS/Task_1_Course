package com.ilya.arrayapp.observer;

import com.ilya.arrayapp.entity.NumericArray;

public interface ArrayChangeListener {
    void onArrayChanged(NumericArray array);
}