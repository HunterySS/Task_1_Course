package com.ilya.arrayapp.reader;

import com.ilya.arrayapp.entity.NumericArray;
import com.ilya.arrayapp.exception.ArrayProcessingException;
import com.ilya.arrayapp.factory.impl.ArrayFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ArrayFileReader {
    private static final Logger LOGGER = LogManager.getLogger(ArrayFileReader.class);
    private static final Pattern DELIMITER_PATTERN = Pattern.compile("[\\s,;\\-]+");

    public List<String> readLines(String filePath) throws IOException {
        LOGGER.info("Reading file: {}", filePath);
        Path path = Paths.get(filePath);
        List<String> lines = Files.readAllLines(path);
        LOGGER.info("Read {} lines from file", lines.size());
        return lines;
    }

    public double[] parseLine(String line) throws ArrayProcessingException {
        if (line == null || line.trim().isBlank()) {
            LOGGER.warn("Empty or null line, returning empty array");
            return new double[0];
        }

        String[] tokens = DELIMITER_PATTERN.split(line.strip());
        List<Double> numbers = new ArrayList<>();

        for (String token : tokens) {
            if (token.isEmpty()) continue;
            try {
                double value = Double.parseDouble(token);
                numbers.add(value);
            } catch (NumberFormatException e) {
                LOGGER.error("Invalid number format: '{}' in line: {}", token, line);
                throw new ArrayProcessingException("Invalid number format: " + token);
            }
        }

        double[] result = new double[numbers.size()];
        for (int i = 0; i < numbers.size(); i++) {
            result[i] = numbers.get(i);
        }
        LOGGER.debug("Parsed line '{}' into array of {} numbers", line, result.length);
        return result;
    }

    public NumericArray validateAndCreate(String line, ArrayFactory factory) throws ArrayProcessingException {
        LOGGER.debug("Validating and creating array from line: {}", line);
        double[] values = parseLine(line);
        NumericArray array = factory.createArray(values);
        LOGGER.info("Created array: {}", array);
        return array;
    }
}