package com.ilya.arrayapp.repository;

import com.ilya.arrayapp.entity.NumericArray;
import com.ilya.arrayapp.exception.NullArrayException;
import com.ilya.arrayapp.repository.impl.ArrayRepository;
import com.ilya.arrayapp.warehouse.Warehouse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Comparator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class ArrayRepositoryImpl implements ArrayRepository {
    private static final Logger LOGGER = LogManager.getLogger(ArrayRepositoryImpl.class);

    private static final ArrayRepositoryImpl INSTANCE = new ArrayRepositoryImpl();
    private final ConcurrentHashMap<Integer, NumericArray> storage = new ConcurrentHashMap<>();
    private int nextId = 1;

    private ArrayRepositoryImpl() {
        LOGGER.info("ArrayRepository initialized");
    }

    public static ArrayRepositoryImpl getInstance() {
        return INSTANCE;
    }

    // ========== Basic CRUD ==========

    @Override
    public void add(NumericArray array) throws NullArrayException {
        if (array == null) throw new NullArrayException("Cannot add null array");

        if (array.getId() == -1) {
            int newId = generateId();
            String newName = "Array_" + newId;
            NumericArray newArray = new NumericArray(newId, newName, array.getValues());
            storage.put(newId, newArray);
            newArray.attach(Warehouse.getObserver());
            Warehouse.getInstance().recalculateStatistics(newArray);
            LOGGER.info("Added array with generated id {}: {}", newId, newArray);
        } else {
            storage.put(array.getId(), array);
            array.attach(Warehouse.getObserver());
            Warehouse.getInstance().recalculateStatistics(array);
            LOGGER.info("Added array with id {}: {}", array.getId(), array);
        }
    }

    @Override
    public boolean removeById(int id) {
        NumericArray array = storage.get(id);
        if (array != null) {
            array.detach(Warehouse.getObserver());
            Warehouse.getInstance().removeStatistics(id);
            storage.remove(id);
            LOGGER.info("Removed array id {}", id);
            return true;
        }
        return false;
    }



    @Override
    public Optional<NumericArray> findById(int id) {
        return Optional.ofNullable(storage.get(id));
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
        for (NumericArray array : storage.values()) {
            array.detach(Warehouse.getObserver());
            Warehouse.getInstance().removeStatistics(array.getId());
        }
        storage.clear();
        LOGGER.info("Repository cleared");
    }


    @Override
    public Optional<NumericArray> findByName(String name) {
        if (name == null || name.isBlank()) {
            return Optional.empty();
        }
        for (NumericArray array : storage.values()) {
            if (array.getName().equalsIgnoreCase(name)) {
                return Optional.of(array);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<NumericArray> findByNameContains(String substring) {
        if (substring == null || substring.isBlank()) {
            return new ArrayList<>();
        }
        List<NumericArray> result = new ArrayList<>();
        for (NumericArray array : storage.values()) {
            if (array.getName().toLowerCase().contains(substring.toLowerCase())) {
                result.add(array);
            }
        }
        return result;
    }


    @Override
    public List<NumericArray> findBySize(int size, ComparisonOperator operator) {
        List<NumericArray> result = new ArrayList<>();
        for (NumericArray array : storage.values()) {
            var stats = Warehouse.getInstance().getStatistics(array.getId());
            if (stats != null && compare(stats.getSize(), size, operator)) {
                result.add(array);
            }
        }
        return result;
    }

    @Override
    public List<NumericArray> findByMin(double min, ComparisonOperator operator) {
        List<NumericArray> result = new ArrayList<>();
        for (NumericArray array : storage.values()) {
            var stats = Warehouse.getInstance().getStatistics(array.getId());
            if (stats != null && compare(stats.getMin(), min, operator)) {
                result.add(array);
            }
        }
        return result;
    }

    @Override
    public List<NumericArray> findByMax(double max, ComparisonOperator operator) {
        List<NumericArray> result = new ArrayList<>();
        for (NumericArray array : storage.values()) {
            var stats = Warehouse.getInstance().getStatistics(array.getId());
            if (stats != null && compare(stats.getMax(), max, operator)) {
                result.add(array);
            }
        }
        return result;
    }

    @Override
    public List<NumericArray> findBySum(double sum, ComparisonOperator operator) {
        List<NumericArray> result = new ArrayList<>();
        for (NumericArray array : storage.values()) {
            var stats = Warehouse.getInstance().getStatistics(array.getId());
            if (stats != null && compare(stats.getSum(), sum, operator)) {
                result.add(array);
            }
        }
        return result;
    }

    @Override
    public List<NumericArray> findByAverage(double average, ComparisonOperator operator) {
        List<NumericArray> result = new ArrayList<>();
        for (NumericArray array : storage.values()) {
            var stats = Warehouse.getInstance().getStatistics(array.getId());
            if (stats != null && compare(stats.getAverage(), average, operator)) {
                result.add(array);
            }
        }
        return result;
    }

    @Override
    public List<NumericArray> findAllSorted(Comparator<NumericArray> comparator) {
        List<NumericArray> sortedList = new ArrayList<>(storage.values());
        sortedList.sort(comparator);
        LOGGER.info("Returning {} arrays sorted", sortedList.size());
        return sortedList;
    }


    private boolean compare(double value1, double value2, ComparisonOperator operator) {
        switch (operator) {
            case EQUALS: return Math.abs(value1 - value2) < 0.0001;
            case GREATER_THAN: return value1 > value2;
            case LESS_THAN: return value1 < value2;
            case GREATER_THAN_OR_EQUALS: return value1 >= value2;
            case LESS_THAN_OR_EQUALS: return value1 <= value2;
            default: return false;
        }
    }

    private synchronized int generateId() {
        return nextId++;
    }
}