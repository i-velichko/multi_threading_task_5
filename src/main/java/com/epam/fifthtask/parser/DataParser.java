package com.epam.fifthtask.parser;

import com.epam.fifthtask.exception.LogisticBaseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DataParser {
    private static final Logger LOGGER = LogManager.getLogger();

    public int parseStringToWagonData(String dataLine) throws LogisticBaseException {
        if (dataLine == null) {
            throw new LogisticBaseException("Line for parsing is null.");
        }
        int wagonData;
        try {
            wagonData = Integer.parseInt(dataLine);
        } catch (NumberFormatException e) {
            LOGGER.error("This data can not be a parse." + e.getMessage());
            throw new LogisticBaseException();
        }
        return wagonData;
    }

}
