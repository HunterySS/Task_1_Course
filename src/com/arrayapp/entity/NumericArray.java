package com.arrayapp.entity;

import java.util.Arrays;

public class NumericArray extends AbstractArray {
    private double[] values;

    public NumericArray(double[] values) {
        if (values == null) {
            this.values = new double[0];
        } else {
            this.values = values.clone();
        }
    }

    @Override
    public double[] getValues() {
        return values.clone();
    }

    @Override
    public int size() {
        return values.length;
    }

    public void setValues(double[] newValues) {
        if (newValues == null) {
            throw new IllegalArgumentException("newValues cannot be null");
        }
        if (newValues.length != this.values.length) {
            throw new IllegalArgumentException("Length mismatch: expected " + this.values.length + " but got " + newValues.length);
        }
        System.arraycopy(newValues, 0, this.values, 0, newValues.length);
    }

    @Override
    public String toString() {
        if (values == null || values.length == 0) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < values.length; i++) {
            sb.append(values[i]);
            if (i < values.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}