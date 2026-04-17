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

                    // Добавляем массив в репозиторий
                    repository.add(array);
                    LOGGER.info("Line {}: array added to repository with id {}", i + 1, array.getId());

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
                }
            });

        } catch (IOException e) {
            LOGGER.error("Failed to read file: {}", e.getMessage());
        }

        LOGGER.info("Application finished");
    }
}