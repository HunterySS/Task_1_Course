package com.ilya.arrayapp.service;

import com.ilya.arrayapp.entity.NumericArray;
import com.ilya.arrayapp.exception.LengthMismatchException;
import com.ilya.arrayapp.exception.NullArrayException;
import com.ilya.arrayapp.service.impl.SortService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SortServiceImpl implements SortService {
    private static final Logger LOGGER = LogManager.getLogger(SortServiceImpl.class);

    @Override
    public void bubbleSort(NumericArray array) throws NullArrayException, LengthMismatchException {
        if (array == null) {
            throw new NullArrayException("Cannot sort null array");
        }

        LOGGER.info("Starting bubble sort on array: {}", array);
        double[] values = array.getValues();
        int n = values.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (values[j] > values[j + 1]) {
                    double temp = values[j];
                    values[j] = values[j + 1];
                    values[j + 1] = temp;
                }
            }
        }
        array.setValues(values);
        LOGGER.info("Bubble sort completed: {}", array);
    }

    @Override
    public void quickSort(NumericArray array) throws NullArrayException, LengthMismatchException {
        if (array == null) {
            throw new NullArrayException("Cannot sort null array");
        }

        LOGGER.info("Starting quick sort on array: {}", array);
        double[] values = array.getValues();
        quickSortRecursive(values, 0, values.length - 1);
        array.setValues(values);
        LOGGER.info("Quick sort completed: {}", array);
    }

    private void quickSortRecursive(double[] arr, int left, int right) {
        if (left < right) {
            int pivotIndex = partition(arr, left, right);
            quickSortRecursive(arr, left, pivotIndex - 1);
            quickSortRecursive(arr, pivotIndex + 1, right);
        }
    }

    private int partition(double[] arr, int left, int right) {
        double pivot = arr[right];
        int i = left - 1;
        for (int j = left; j < right; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, right);
        return i + 1;
    }

    private void swap(double[] arr, int i, int j) {
        double temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}