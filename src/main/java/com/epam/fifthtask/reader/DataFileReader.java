package com.epam.fifthtask.reader;

import com.epam.fifthtask.exception.LogisticBaseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataFileReader {
    private final static Logger LOGGER = LogManager.getLogger();

    public List<String> readAllDataFromFile(String resourceName) throws LogisticBaseException {
        List<String> listOfDataForTriangle;

        if (resourceName == null) {
            throw new LogisticBaseException("File path does not exist.");
        }

        try {
            ClassLoader classLoader = getClass().getClassLoader();
            URI uri = classLoader.getResource(resourceName).toURI();
            Stream<String> lineStream = Files.lines(Paths.get(uri));
            LOGGER.info("File " + resourceName + "read successfully.");
            listOfDataForTriangle = lineStream.collect(Collectors.toList());
        } catch (IOException | URISyntaxException e) {
            LOGGER.error("Error in DataFileReader " + e.getMessage());
            throw new LogisticBaseException("Can not open file " + resourceName);
        }
        return listOfDataForTriangle;
    }
}
