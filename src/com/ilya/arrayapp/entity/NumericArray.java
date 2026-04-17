package com.ilya.arrayapp.entity;

import com.ilya.arrayapp.exception.LengthMismatchException;
import com.ilya.arrayapp.exception.NullArrayException;
import com.ilya.arrayapp.observer.ArrayChangeListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class NumericArray extends AbstractArray {
    private static final Logger LOGGER = LogManager.getLogger(NumericArray.class);

    private final int id;
    private double[] values;

    // List of listeners (observers)
    private final List<ArrayChangeListener> listeners = new ArrayList<>();

    // Constructor with id and values
    public NumericArray(int id, double[] values) throws NullArrayException {
        this.id = id;
        if (values == null) {
            throw new NullArrayException("Cannot create array with id " + id + ": values array is null");
        }
        this.values = values.clone();
        LOGGER.info("Created array with id {}: {}", id, this);
    }

    // Constructor without id
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

        // Notify all listeners about the change
        notifyListeners();
    }

    // Observer methods
    public void addListener(ArrayChangeListener listener) {
        if (listener != null && !listeners.contains(listener)) {
            listeners.add(listener);
            LOGGER.debug("Added listener for array id {}", id);
        }
    }

    public void removeListener(ArrayChangeListener listener) {
        listeners.remove(listener);
        LOGGER.debug("Removed listener for array id {}", id);
    }

    private void notifyListeners() {
        for (ArrayChangeListener listener : listeners) {
            listener.onArrayChanged(this);
        }
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