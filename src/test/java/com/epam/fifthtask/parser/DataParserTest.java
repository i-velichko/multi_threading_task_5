package com.epam.fifthtask.parser;

import com.epam.fifthtask.exception.LogisticBaseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

    class DataParserTest {
        private final static String LINE_BEFORE_PARSING = "1000";
        private final static String WRONG_LINE_FOR_PARSING = "Help me 1000";
        private final static int EXPECTED_VALUE_FROM_LINE = 1000;
        private DataParser dataParser;


        @BeforeEach
        void setUp() {
            dataParser = new DataParser();
        }

        @Test
        void parseStringToWagonData() throws LogisticBaseException {
            int actualValueFromLine = dataParser.parseStringToWagonData(LINE_BEFORE_PARSING);
            assertEquals(EXPECTED_VALUE_FROM_LINE, actualValueFromLine);
        }

        @Test
        void testWhenLineForParsingIsNull() {
            Assertions.assertThrows(LogisticBaseException.class, () ->
                    dataParser.parseStringToWagonData(null));
        }

        @Test
        void testWhenUncorrectedCharactersInLine() {
            Assertions.assertThrows(LogisticBaseException.class, () ->
                    dataParser.parseStringToWagonData(WRONG_LINE_FOR_PARSING));
        }
    }