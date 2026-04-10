package com.arrayapp.service;

import com.arrayapp.entity.AbstractArray;
import com.arrayapp.entity.NumericArray;
import java.util.OptionalDouble;

public class ArrayOperationsServiceImpl implements ArrayOperationsService {

    @Override
    public OptionalDouble findMin(AbstractArray array) {
        double[] values = array.getValues();
        if (values.length == 0) {
            return OptionalDouble.empty();
        }
        double min = values[0];
        for (int i = 1; i < values.length; i++) {
            if (values[i] < min) {
                min = values[i];
            }
        }
        return OptionalDouble.of(min);
    }

    @Override
    public OptionalDouble findMax(AbstractArray array) {
        double[] values = array.getValues();
        if (values.length == 0) {
            return OptionalDouble.empty();
        }
        double max = values[0];
        for (int i = 1; i < values.length; i++) {
            if (values[i] > max) {
                max = values[i];
            }
        }
        return OptionalDouble.of(max);
    }

    @Override
    public OptionalDouble sum(AbstractArray array) {
        double[] values = array.getValues();
        double sum = 0.0;
        for (double v : values) {
            sum += v;
        }
        return OptionalDouble.of(sum);
    }

    @Override
    public OptionalDouble average(AbstractArray array) {
        double[] values = array.getValues();
        if (values.length == 0) {
            return OptionalDouble.empty();
        }
        double sum = sum(array).getAsDouble();
        return OptionalDouble.of(sum / values.length);
    }

    @Override
    public void bubbleSort(AbstractArray array) {
        if (!(array instanceof NumericArray)) {
            throw new IllegalArgumentException("Only NumericArray is supported");
        }

        NumericArray numArray = (NumericArray) array;
        double[] values = numArray.getValues();

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

        numArray.setValues(values);
    }

    @Override
    public void quickSort(AbstractArray array) {
        if (!(array instanceof NumericArray)) {
            throw new IllegalArgumentException("Only NumericArray is supported");
        }

        NumericArray numArray = (NumericArray) array;
        double[] values = numArray.getValues();

        quickSortRecursive(values, 0, values.length - 1);

        numArray.setValues(values);
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