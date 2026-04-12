package com.arrayapp.reader;

import com.arrayapp.exception.ArrayProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ArrayFileReaderTest {

    private ArrayFileReader reader;

    @BeforeEach
    void setUp() {
        reader = new ArrayFileReader();
    }

    @Test
    void testParseLine_ValidNumbersWithCommas_ShouldReturnDoubleArray() throws Exception {
        String line = "1, 2, 3";
        double[] result = reader.parseLine(line);
        assertArrayEquals(new double[]{1, 2, 3}, result, 0.001);
    }

    @Test
    void testParseLine_EmptyString_ShouldReturnEmptyArray() throws Exception {
        String line = "";
        double[] result = reader.parseLine(line);
        assertEquals(0, result.length);
    }

    @Test
    void testParseLine_InvalidNumber_ShouldThrowException() {
        String line = "1, x2, 3";
        assertThrows(ArrayProcessingException.class, () -> reader.parseLine(line));
    }
}