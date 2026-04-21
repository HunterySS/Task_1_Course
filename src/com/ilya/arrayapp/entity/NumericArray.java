package com.ilya.arrayapp.entity;

import com.ilya.arrayapp.exception.LengthMismatchException;
import com.ilya.arrayapp.exception.NullArrayException;
import com.ilya.arrayapp.observer.impl.ArrayChangeListener;
import com.ilya.arrayapp.observer.impl.Observable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class NumericArray extends AbstractArray implements Observable {
    private static final Logger LOGGER = LogManager.getLogger(NumericArray.class);

    private final int id;
    private final String name;
    private double[] values;
    private final List<ArrayChangeListener> observers = new ArrayList<>();

    public NumericArray(int id, String name, double[] values) throws NullArrayException {
        this.id = id;
        this.name = (name == null || name.isBlank()) ? "Array_" + id : name;
        if (values == null) {
            throw new NullArrayException("Cannot create array with id " + id);
        }
        this.values = values.clone();
        LOGGER.info("Created array id {} name '{}': {}", id, this.name, this);
    }

    public NumericArray(int id, double[] values) throws NullArrayException {
        this(id, "Array_" + id, values);
    }

    public NumericArray(double[] values) throws NullArrayException {
        this(-1, values);
    }

    public int getId() { return id; }
    public String getName() { return name; }

    @Override
    public double[] getValues() { return values.clone(); }

    @Override
    public int size() { return values.length; }

    public void setValues(double[] newValues) throws NullArrayException, LengthMismatchException {
        if (newValues == null) throw new NullArrayException("newValues is null for id " + id);
        if (newValues.length != values.length)
            throw new LengthMismatchException("Length mismatch for id " + id);
        System.arraycopy(newValues, 0, this.values, 0, newValues.length);
        LOGGER.info("Array id {} updated: {}", id, this);
        notifyObservers();
    }

    @Override
    public void attach(ArrayChangeListener listener) {
        if (listener != null && !observers.contains(listener)) {
            observers.add(listener);
            LOGGER.debug("Attached listener to array id {}", id);
        }
    }

    @Override
    public void detach(ArrayChangeListener listener) {
        if (observers.remove(listener)) {
            LOGGER.debug("Detached listener from array id {}", id);
        }
    }

    @Override
    public void notifyObservers() {
        for (ArrayChangeListener listener : observers) {
            listener.onArrayChanged(this);
        }
    }

    @Override
    public String toString() {
        if (values.length == 0) return "[]";
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < values.length; i++) {
            sb.append(values[i]);
            if (i < values.length - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}