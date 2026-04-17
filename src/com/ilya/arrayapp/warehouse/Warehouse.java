package com.ilya.arrayapp.warehouse;

import com.ilya.arrayapp.entity.NumericArray;
import com.ilya.arrayapp.observer.ArrayChangeListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ConcurrentHashMap;

public class Warehouse implements ArrayChangeListener {
    private static final Logger LOGGER = LogManager.getLogger(Warehouse.class);

    // Singleton instance
    private static final Warehouse INSTANCE = new Warehouse();

    // Storage for array statistics
    private final ConcurrentHashMap<Integer, ArrayStatistics> statistics = new ConcurrentHashMap<>();

    private Warehouse() {
        LOGGER.info("Warehouse initialized");
    }

    public static Warehouse getInstance() {
        return INSTANCE;
    }

    @Override
    public void onArrayChanged(NumericArray array) {
        LOGGER.info("Array with id {} changed, recalculating statistics", array.getId());
        recalculateStatistics(array);
    }

    private void recalculateStatistics(NumericArray array) {
        double[] values = array.getValues();

        if (values.length == 0) {
            statistics.put(array.getId(), new ArrayStatistics(0, 0.0, 0.0, 0.0, 0.0));
            LOGGER.debug("Statistics for id {}: empty array", array.getId());
            return;
        }

        double min = values[0];
        double max = values[0];
        double sum = 0.0;

        for (double v : values) {
            if (v < min) min = v;
            if (v > max) max = v;
            sum += v;
        }

        double average = sum / values.length;
        int size = values.length;

        ArrayStatistics stats = new ArrayStatistics(size, min, max, sum, average);
        statistics.put(array.getId(), stats);

        LOGGER.debug("Statistics for id {}: size={}, min={}, max={}, sum={}, avg={}",
                array.getId(), size, min, max, sum, average);
    }

    public ArrayStatistics getStatistics(int arrayId) {
        return statistics.get(arrayId);
    }

    public void removeStatistics(int arrayId) {
        statistics.remove(arrayId);
        LOGGER.info("Removed statistics for array id {}", arrayId);
    }

    // Inner class to hold statistics
    public static class ArrayStatistics {
        private final int size;
        private final double min;
        private final double max;
        private final double sum;
        private final double average;

        public ArrayStatistics(int size, double min, double max, double sum, double average) {
            this.size = size;
            this.min = min;
            this.max = max;
            this.sum = sum;
            this.average = average;
        }

        public int getSize() { return size; }
        public double getMin() { return min; }
        public double getMax() { return max; }
        public double getSum() { return sum; }
        public double getAverage() { return average; }

        @Override
        public String toString() {
            return String.format("ArrayStatistics{size=%d, min=%.2f, max=%.2f, sum=%.2f, avg=%.2f}",
                    size, min, max, sum, average);
        }
    }
}