package com.arrayapp.factory;

import com.arrayapp.entity.NumericArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ArrayFactoryTest {

    private ArrayFactory factory;

    @BeforeEach
    void setUp() {
        factory = new ArrayFactoryImpl();
    }

    @Test
    void testCreateArray_ShouldCreateArrayWithCorrectValues() {
        double[] input = {1, 2, 3, 4, 5};
        NumericArray array = factory.createArray(input);
        assertArrayEquals(input, array.getValues(), 0.001);
    }

    @Test
    void testCreateArray_WithNull_ShouldCreateEmptyArray() {
        NumericArray array = factory.createArray(null);
        assertEquals(0, array.size());
    }
}