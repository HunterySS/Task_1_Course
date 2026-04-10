package com.arrayapp.reader;

import com.arrayapp.entity.NumericArray;
import com.arrayapp.exception.ArrayProcessingException;
import com.arrayapp.factory.ArrayFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ArrayFileReader {

    private static final Pattern DELIMITER_PATTERN = Pattern.compile("[\\s,;\\-]+");

    public List<String> readLines(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return Files.readAllLines(path);
    }

    public double[] parseLine(String line) throws ArrayProcessingException {
        if (line == null || line.trim().isEmpty()) {
            return new double[0];
        }

        String[] tokens = DELIMITER_PATTERN.split(line.trim());
        List<Double> numbers = new ArrayList<>();

        for (String token : tokens) {
            if (token.isEmpty()) {
                continue;
            }
            try {
                double value = Double.parseDouble(token);
                numbers.add(value);
            } catch (NumberFormatException e) {
                throw new ArrayProcessingException("Invalid number format: " + token);
            }
        }

        double[] result = new double[numbers.size()];
        for (int i = 0; i < numbers.size(); i++) {
            result[i] = numbers.get(i);
        }
        return result;
    }

    public NumericArray validateAndCreate(String line, ArrayFactory factory) throws ArrayProcessingException {
        double[] values = parseLine(line);
        return factory.createArray(values);
    }
}