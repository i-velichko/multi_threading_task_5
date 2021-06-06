package com.epam.fifthtask.main;

import com.epam.fifthtask.entity.Wagon;
import com.epam.fifthtask.entity.WagonContainer;
import com.epam.fifthtask.exception.LogisticBaseException;
import com.epam.fifthtask.parser.DataParser;
import com.epam.fifthtask.reader.DataFileReader;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    private final static Logger LOGGER = LogManager.getLogger();

    public static void main(String[] args) throws InterruptedException, LogisticBaseException {

        DataFileReader dataFileReader = new DataFileReader();
        DataParser dataParser = new DataParser();
        List<String> strings = dataFileReader.readAllDataFromFile("data/wagonData.txt");
        List<Integer> wagonData = new ArrayList<>();

        for (String string : strings) {
            int data = dataParser.parseStringToWagonData(string);
            wagonData.add(data);
        }

        LOGGER.log(Level.INFO, "Starting work. ");
        ExecutorService service = Executors.newFixedThreadPool(8);

        for (int i = 0; i < wagonData.get(0); i++) {
            Wagon wagon = new Wagon(new WagonContainer(), "Wag " + i);
            service.submit(wagon);
        }

        TimeUnit.MINUTES.sleep(1);
        service.shutdown();

        LOGGER.log(Level.INFO, "End of the working day ");
    }
}

