package com.ilya.arrayapp;

import com.ilya.arrayapp.entity.NumericArray;
import com.ilya.arrayapp.exception.ArrayProcessingException;
import com.ilya.arrayapp.exception.NullArrayException;
import com.ilya.arrayapp.factory.impl.ArrayFactory;
import com.ilya.arrayapp.factory.ArrayFactoryImpl;
import com.ilya.arrayapp.reader.ArrayFileReader;
import com.ilya.arrayapp.repository.impl.ArrayRepository;
import com.ilya.arrayapp.repository.ArrayRepositoryImpl;
import com.ilya.arrayapp.service.impl.StatisticsService;
import com.ilya.arrayapp.service.StatisticsServiceImpl;
import com.ilya.arrayapp.service.impl.SortService;
import com.ilya.arrayapp.service.SortServiceImpl;
import com.ilya.arrayapp.warehouse.Warehouse;
import com.ilya.arrayapp.repository.ComparisonOperator;
import com.ilya.arrayapp.comparator.ArrayByIdComparator;
import com.ilya.arrayapp.comparator.ArrayByNameComparator;
import com.ilya.arrayapp.comparator.ArrayBySizeComparator;
import com.ilya.arrayapp.comparator.ArrayBySumComparator;
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
        StatisticsService statsService = new StatisticsServiceImpl();
        SortService sortService = new SortServiceImpl();

        ArrayRepository repository = ArrayRepositoryImpl.getInstance();

        String filePath = "data/arrays.txt";

        try {
            List<String> lines = reader.readLines(filePath);

            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);

                try {
                    NumericArray array = reader.validateAndCreate(line, factory);

                    if (array.size() == 0) {
                        continue;
                    }

                    repository.add(array);
                    LOGGER.info("Line {}: array added to repository", i + 1);

                    statsService.findMin(array);
                    statsService.findMax(array);
                    statsService.sum(array);
                    statsService.average(array);

                    NumericArray arrayForBubbleSort = factory.createArray(array.getValues());
                    sortService.bubbleSort(arrayForBubbleSort);

                    NumericArray arrayForQuickSort = factory.createArray(array.getValues());
                    sortService.quickSort(arrayForQuickSort);

                } catch (ArrayProcessingException e) {
                    LOGGER.error("Line {}: Error - {}", i + 1, e.getMessage());
                }
            }

            LOGGER.info("Repository contains {} arrays", repository.size());
            repository.findAll().forEach(array ->
                    LOGGER.info("  Array id {}: {}", array.getId(), array)
            );

            LOGGER.info("Statistics from Warehouse:");
            repository.findAll().forEach(array -> {
                var stats = Warehouse.getInstance().getStatistics(array.getId());
                if (stats != null) {
                    LOGGER.info("  Array id {}: {}", array.getId(), stats);
                } else {
                    LOGGER.warn("  Array id {}: no statistics found", array.getId());
                }
            });

            LOGGER.info("=== Search Demos ===");

            repository.findByName("Array_1").ifPresent(array ->
                    LOGGER.info("Found by name 'Array_1': id {}", array.getId())
            );

            LOGGER.info("Arrays with size > 3:");
            var sizeResult = repository.findBySize(3, ComparisonOperator.GREATER_THAN);
            if (sizeResult.isEmpty()) {
                LOGGER.info("  (no arrays found)");
            } else {
                sizeResult.forEach(array -> LOGGER.info("  id {}: size={}", array.getId(), array.size()));
            }

            LOGGER.info("Arrays with sum > 10:");
            var sumResult = repository.findBySum(10, ComparisonOperator.GREATER_THAN);
            if (sumResult.isEmpty()) {
                LOGGER.info("  (no arrays found)");
            } else {
                sumResult.forEach(array -> LOGGER.info("  id {}: sum={}", array.getId(),
                        Warehouse.getInstance().getStatistics(array.getId()).getSum()));
            }

            LOGGER.info("=== Sorting Demos ===");

            LOGGER.info("Sorted by ID:");
            repository.findAllSorted(new ArrayByIdComparator())
                    .forEach(array -> LOGGER.info("  id {}: {}", array.getId(), array));

            LOGGER.info("Sorted by name:");
            repository.findAllSorted(new ArrayByNameComparator())
                    .forEach(array -> LOGGER.info("  name '{}': {}", array.getName(), array));

            LOGGER.info("Sorted by size:");
            repository.findAllSorted(new ArrayBySizeComparator())
                    .forEach(array -> LOGGER.info("  size {}: {}", array.size(), array));

            LOGGER.info("Sorted by sum:");
            repository.findAllSorted(new ArrayBySumComparator())
                    .forEach(array -> {
                        var stats = Warehouse.getInstance().getStatistics(array.getId());
                        double sum = stats != null ? stats.getSum() : 0.0;
                        LOGGER.info("  sum {}: id {}", sum, array.getId());
                    });

        } catch (IOException e) {
            LOGGER.error("Failed to read file: {}", e.getMessage());
        }

        LOGGER.info("Application finished");
    }
}