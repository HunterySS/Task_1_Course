package com.ilya.arrayapp.repository.impl;

import com.ilya.arrayapp.entity.NumericArray;
import com.ilya.arrayapp.exception.NullArrayException;
import com.ilya.arrayapp.repository.ComparisonOperator;
import java.util.Comparator;

import java.util.List;
import java.util.Optional;

public interface ArrayRepository {

    // Basic CRUD
    void add(NumericArray array) throws NullArrayException;
    boolean removeById(int id);
    Optional<NumericArray> findById(int id);
    List<NumericArray> findAll();
    int size();
    void clear();

    // Search by name
    Optional<NumericArray> findByName(String name);
    List<NumericArray> findByNameContains(String substring);

    // Search by criteria using Warehouse statistics
    List<NumericArray> findBySize(int size, ComparisonOperator operator);
    List<NumericArray> findByMin(double min, ComparisonOperator operator);
    List<NumericArray> findByMax(double max, ComparisonOperator operator);
    List<NumericArray> findBySum(double sum, ComparisonOperator operator);
    List<NumericArray> findByAverage(double average, ComparisonOperator operator);

    // Sorting
    List<NumericArray> findAllSorted(Comparator<NumericArray> comparator);
}