package com.ilya.arrayapp.repository.impl;

import com.ilya.arrayapp.entity.NumericArray;
import com.ilya.arrayapp.exception.NullArrayException;

import java.util.List;
import java.util.Optional;

public interface ArrayRepository {

    void add(NumericArray array) throws NullArrayException;

    boolean removeById(int id);

    Optional<NumericArray> findById(int id);

    List<NumericArray> findAll();

    int size();

    void clear();
}