package com.ilya.arrayapp;

import com.ilya.arrayapp.entity.NumericArray;
import com.ilya.arrayapp.exception.ArrayProcessingException;
import com.ilya.arrayapp.factory.impl.ArrayFactory;
import com.ilya.arrayapp.factory.ArrayFactoryImpl;
import com.ilya.arrayapp.reader.ArrayFileReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.ilya.arrayapp.service.impl.StatisticsService;
import com.ilya.arrayapp.service.StatisticsServiceImpl;
import com.ilya.arrayapp.service.impl.SortService;
import com.ilya.arrayapp.service.SortServiceImpl;

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

                    statsService.findMin(array);
                    statsService.findMax(array);
                    statsService.sum(array);
                    statsService.average(array);

                    NumericArray arrayForBubbleSort = factory.createArray(array.getValues());
                    sortService.bubbleSort(arrayForBubbleSort);

                    NumericArray arrayForQuickSort = factory.createArray(array.getValues());
                    sortService.quickSort(arrayForQuickSort);

                } catch (ArrayProcessingException e) {
                    // error logged inside reader
                }
            }

        } catch (IOException e) {
        }

        LOGGER.info("Application finished");
    }
}