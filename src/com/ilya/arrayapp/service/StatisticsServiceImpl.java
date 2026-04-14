package com.ilya.arrayapp.service;

import com.ilya.arrayapp.entity.NumericArray;
import com.ilya.arrayapp.service.impl.StatisticsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.OptionalDouble;

public class StatisticsServiceImpl implements StatisticsService {
    private static final Logger LOGGER = LogManager.getLogger(StatisticsServiceImpl.class);

    @Override
    public OptionalDouble findMin(NumericArray array) {
        double[] values = array.getValues();
        if (values.length == 0) {
            LOGGER.warn("findMin called on empty array");
            return OptionalDouble.empty();
        }
        double min = values[0];
        for (int i = 1; i < values.length; i++) {
            if (values[i] < min) min = values[i];
        }
        LOGGER.info("Min value for array {} is {}", array, min);
        return OptionalDouble.of(min);
    }

    @Override
    public OptionalDouble findMax(NumericArray array) {
        double[] values = array.getValues();
        if (values.length == 0) {
            LOGGER.warn("findMax called on empty array");
            return OptionalDouble.empty();
        }
        double max = values[0];
        for (int i = 1; i < values.length; i++) {
            if (values[i] > max) max = values[i];
        }
        LOGGER.info("Max value for array {} is {}", array, max);
        return OptionalDouble.of(max);
    }

    @Override
    public OptionalDouble sum(NumericArray array) {
        double[] values = array.getValues();
        double sum = 0.0;
        for (double v : values) sum += v;
        LOGGER.info("Sum for array {} is {}", array, sum);
        return OptionalDouble.of(sum);
    }

    @Override
    public OptionalDouble average(NumericArray array) {
        double[] values = array.getValues();
        if (values.length == 0) {
            LOGGER.warn("Average called on empty array");
            return OptionalDouble.empty();
        }
        double avg = sum(array).getAsDouble() / values.length;
        LOGGER.info("Average for array {} is {}", array, avg);
        return OptionalDouble.of(avg);
    }
}