package com.ilya.arrayapp.service;

import com.ilya.arrayapp.entity.NumericArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.OptionalDouble;
import static org.junit.jupiter.api.Assertions.*;

class StatisticsServiceTest {

    private ArrayOperationsService service;
    private NumericArray array;

    @BeforeEach
    void setUp() {
        service = new ArrayOperationsServiceImpl();
        array = new NumericArray(new double[]{5, 2, 8, 1, 9, 3});
    }

    @Test
    void testFindMin_ShouldReturnSmallestValue() {
        OptionalDouble min = service.findMin(array);
        assertTrue(min.isPresent());
        assertEquals(1.0, min.getAsDouble(), 0.001);
    }

    @Test
    void testFindMin_WhenArrayEmpty_ShouldReturnEmpty() {
        NumericArray emptyArray = new NumericArray(new double[0]);
        OptionalDouble min = service.findMin(emptyArray);
        assertTrue(min.isEmpty());
    }

    @Test
    void testFindMax_ShouldReturnLargestValue() {
        OptionalDouble max = service.findMax(array);
        assertTrue(max.isPresent());
        assertEquals(9.0, max.getAsDouble(), 0.001);
    }

    @Test
    void testFindMax_WhenArrayEmpty_ShouldReturnEmpty() {
        NumericArray emptyArray = new NumericArray(new double[0]);
        OptionalDouble max = service.findMax(emptyArray);
        assertTrue(max.isEmpty());
    }

    @Test
    void testSum_ShouldReturnSumOfAllElements() {
        OptionalDouble sum = service.sum(array);
        assertTrue(sum.isPresent());
        assertEquals(28.0, sum.getAsDouble(), 0.001);
    }

    @Test
    void testSum_WhenArrayEmpty_ShouldReturnZero() {
        NumericArray emptyArray = new NumericArray(new double[0]);
        OptionalDouble sum = service.sum(emptyArray);
        assertTrue(sum.isPresent());
        assertEquals(0.0, sum.getAsDouble(), 0.001);
    }

    @Test
    void testAverage_ShouldReturnCorrectAverage() {
        OptionalDouble avg = service.average(array);
        assertTrue(avg.isPresent());
        assertEquals(28.0 / 6.0, avg.getAsDouble(), 0.001);
    }

    @Test
    void testAverage_WhenArrayEmpty_ShouldReturnEmpty() {
        NumericArray emptyArray = new NumericArray(new double[0]);
        OptionalDouble avg = service.average(emptyArray);
        assertTrue(avg.isEmpty());
    }
}