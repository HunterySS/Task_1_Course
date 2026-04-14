package com.ilya.arrayapp.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NumericArray extends AbstractArray {
    private static final Logger LOGGER = LogManager.getLogger(NumericArray.class);
    private double[] values;

    public NumericArray(double[] values) {
        if (values == null) {
            this.values = new double[0];
            LOGGER.info("Created empty array (input was null)");
        } else {
            this.values = values.clone();
            LOGGER.info("Created array with {} elements: {}", this.values.length, this);
        }
    }

    @Override
    public double[] getValues() {
        LOGGER.debug("getValues() called, returning copy of array");
        return values.clone();
    }

    @Override
    public int size() {
        return values.length;
    }

    public void setValues(double[] newValues) {
        if (newValues == null) {
            LOGGER.error("Attempt to set null array");
            throw new IllegalArgumentException("newValues cannot be null");
        }
        if (newValues.length != this.values.length) {
            LOGGER.error("Length mismatch: expected {}, got {}", this.values.length, newValues.length);
            throw new IllegalArgumentException("Length mismatch: expected " + this.values.length + " but got " + newValues.length);
        }
        System.arraycopy(newValues, 0, this.values, 0, newValues.length);
        LOGGER.info("Array values updated: {}", this);
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