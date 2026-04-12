package com.arrayapp.service;

import com.arrayapp.entity.NumericArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SortServiceTest {

    private ArrayOperationsService service;
    private NumericArray array;
    private double[] expectedSorted;

    @BeforeEach
    void setUp() {
        service = new ArrayOperationsServiceImpl();
        array = new NumericArray(new double[]{5, 2, 8, 1, 9, 3});
        expectedSorted = new double[]{1, 2, 3, 5, 8, 9};
    }

    @Test
    void testBubbleSort_ShouldSortArrayInAscendingOrder() {
        service.bubbleSort(array);
        assertArrayEquals(expectedSorted, array.getValues(), 0.001);
    }

    @Test
    void testQuickSort_ShouldSortArrayInAscendingOrder() {
        NumericArray testArray = new NumericArray(new double[]{5, 2, 8, 1, 9, 3});
        service.quickSort(testArray);
        assertArrayEquals(expectedSorted, testArray.getValues(), 0.001);
    }
}