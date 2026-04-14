package com.ilya.arrayapp.service;

import com.ilya.arrayapp.entity.NumericArray;
import com.ilya.arrayapp.exception.LengthMismatchException;
import com.ilya.arrayapp.exception.NullArrayException;
import com.ilya.arrayapp.service.impl.SortService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SortServiceTest {

    private SortService service;
    private NumericArray array;
    private double[] expectedSorted;

    @BeforeEach
    void setUp() throws NullArrayException {
        service = new SortServiceImpl();
        array = new NumericArray(new double[]{5, 2, 8, 1, 9, 3});
        expectedSorted = new double[]{1, 2, 3, 5, 8, 9};
    }

    @Test
    void testBubbleSort_ShouldSortArrayInAscendingOrder() throws NullArrayException, LengthMismatchException {
        service.bubbleSort(array);
        assertArrayEquals(expectedSorted, array.getValues(), 0.001);
    }

    @Test
    void testQuickSort_ShouldSortArrayInAscendingOrder() throws NullArrayException, LengthMismatchException {
        NumericArray testArray = new NumericArray(new double[]{5, 2, 8, 1, 9, 3});
        service.quickSort(testArray);
        assertArrayEquals(expectedSorted, testArray.getValues(), 0.001);
    }
}