package com.ilya.arrayapp.repository;

import com.ilya.arrayapp.entity.NumericArray;
import com.ilya.arrayapp.exception.NullArrayException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.ilya.arrayapp.repository.impl.ArrayRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class ArrayRepositoryImpl implements ArrayRepository {
    private static final Logger LOGGER = LogManager.getLogger(ArrayRepositoryImpl.class);

    // Singleton instance
    private static final ArrayRepositoryImpl INSTANCE = new ArrayRepositoryImpl();

    // Storage: id -> NumericArray
    private final ConcurrentHashMap<Integer, NumericArray> storage = new ConcurrentHashMap<>();
    private int nextId = 1;

    // Private constructor for Singleton
    private ArrayRepositoryImpl() {
        LOGGER.info("ArrayRepository initialized");
    }

    public static ArrayRepositoryImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public void add(NumericArray array) throws NullArrayException {
        if (array == null) {
            throw new NullArrayException("Cannot add null array to repository");
        }

        if (array.getId() == -1) {
            int id = generateId();
            storage.put(id, array);
            LOGGER.info("Added array with generated id {}: {}", id, array);
        } else {
            storage.put(array.getId(), array);
            LOGGER.info("Added array with id {}: {}", array.getId(), array);
        }
    }

    @Override
    public boolean removeById(int id) {
        NumericArray removed = storage.remove(id);
        if (removed != null) {
            LOGGER.info("Removed array with id {}: {}", id, removed);
            return true;
        }
        LOGGER.warn("Array with id {} not found for removal", id);
        return false;
    }

    @Override
    public Optional<NumericArray> findById(int id) {
        NumericArray array = storage.get(id);
        return Optional.ofNullable(array);
    }

    @Override
    public List<NumericArray> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public void clear() {
        storage.clear();
        LOGGER.info("Repository cleared");
    }

    private synchronized int generateId() {
        return nextId++;
    }
}