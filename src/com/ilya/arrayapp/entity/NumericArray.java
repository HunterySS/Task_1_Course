package com.ilya.arrayapp.entity;

import com.ilya.arrayapp.exception.LengthMismatchException;
import com.ilya.arrayapp.exception.NullArrayException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NumericArray extends AbstractArray {
    private static final Logger LOGGER = LogManager.getLogger(NumericArray.class);

    private final int id;
    private double[] values;

    // Constructor with id and values
    public NumericArray(int id, double[] values) throws NullArrayException {
        this.id = id;
        if (values == null) {
            throw new NullArrayException("Cannot create array with id " + id + ": values array is null");
        }
        this.values = values.clone();
        LOGGER.info("Created array with id {}: {}", id, this);
    }

    // Constructor without id (for backward compatibility)
    public NumericArray(double[] values) throws NullArrayException {
        this(-1, values);
    }

    public int getId() {
        return id;
    }

    @Override
    public double[] getValues() {
        return values.clone();
    }

    @Override
    public int size() {
        return values.length;
    }

    public void setValues(double[] newValues) throws NullArrayException, LengthMismatchException {
        if (newValues == null) {
            LOGGER.error("Attempt to set null array for id {}", id);
            throw new NullArrayException("Cannot set null array for id " + id);
        }
        if (newValues.length != this.values.length) {
            LOGGER.error("Length mismatch for id {}: expected {}, got {}", id, this.values.length, newValues.length);
            throw new LengthMismatchException("Length mismatch for id " + id + ": expected " + this.values.length + " but got " + newValues.length);
        }
        System.arraycopy(newValues, 0, this.values, 0, newValues.length);
        LOGGER.info("Array with id {} updated: {}", id, this);
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