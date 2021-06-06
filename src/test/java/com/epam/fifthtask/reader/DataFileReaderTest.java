package com.epam.fifthtask.reader;

import com.epam.fifthtask.exception.LogisticBaseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DataFileReaderTest {
    private static final String RESOURCE_NAME = "wagonData.txt";
    private static DataFileReader dataFileReader;

    @BeforeEach
    void setUp() {
        dataFileReader = new DataFileReader();
    }

    @Test
    void testReadWagonDataSuccess() throws LogisticBaseException {
        int actualLinesSize = dataFileReader.readAllDataFromFile(RESOURCE_NAME).size();
        int expectedLinesSize = 3;
        assertEquals(expectedLinesSize, actualLinesSize);
    }


    @Test()
    void testReadDataWhenPathToFileIsNull() {
        assertThrows(LogisticBaseException.class, () ->
                dataFileReader.readAllDataFromFile(null));
    }
}