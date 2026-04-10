package com.arrayapp;

import com.arrayapp.entity.NumericArray;
import com.arrayapp.exception.ArrayProcessingException;
import com.arrayapp.factory.ArrayFactory;
import com.arrayapp.factory.ArrayFactoryImpl;
import com.arrayapp.reader.ArrayFileReader;
import com.arrayapp.service.ArrayOperationsService;
import com.arrayapp.service.ArrayOperationsServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.OptionalDouble;

public class Main {

    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        LOGGER.info("Application started");

        ArrayFileReader reader = new ArrayFileReader();
        ArrayFactory factory = new ArrayFactoryImpl();
        ArrayOperationsService service = new ArrayOperationsServiceImpl();

        String filePath = "data/arrays.txt";

        try {
            List<String> lines = reader.readLines(filePath);
            LOGGER.info("Read {} lines from file", lines.size());

            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                LOGGER.info("Processing line {}: '{}'", i + 1, line);

                try {
                    NumericArray array = reader.validateAndCreate(line, factory);

                    if (array.size() == 0) {
                        LOGGER.info("Line {}: empty array created", i + 1);
                        continue;
                    }

                    LOGGER.info("Line {}: array = {}", i + 1, array);

                    OptionalDouble min = service.findMin(array);
                    if (min.isPresent()) {
                        LOGGER.info("  Min value: {}", min.getAsDouble());
                    } else {
                        LOGGER.warn("  Min value: not available");
                    }

                    OptionalDouble max = service.findMax(array);
                    if (max.isPresent()) {
                        LOGGER.info("  Max value: {}", max.getAsDouble());
                    } else {
                        LOGGER.warn("  Max value: not available");
                    }

                    OptionalDouble sum = service.sum(array);
                    LOGGER.info("  Sum: {}", sum.getAsDouble());

                    OptionalDouble average = service.average(array);
                    if (average.isPresent()) {
                        LOGGER.info("  Average: {}", average.getAsDouble());
                    } else {
                        LOGGER.warn("  Average: not available");
                    }

                    NumericArray arrayForBubbleSort = factory.createArray(array.getValues());
                    service.bubbleSort(arrayForBubbleSort);
                    LOGGER.info("  Bubble sort result: {}", arrayForBubbleSort);

                    NumericArray arrayForQuickSort = factory.createArray(array.getValues());
                    service.quickSort(arrayForQuickSort);
                    LOGGER.info("  Quick sort result: {}", arrayForQuickSort);

                } catch (ArrayProcessingException e) {
                    LOGGER.error("Line {}: Failed to parse - {}", i + 1, e.getMessage());
                }
            }

        } catch (IOException e) {
            LOGGER.error("Failed to read file: {}", e.getMessage());
        }

        LOGGER.info("Application finished");
    }
}