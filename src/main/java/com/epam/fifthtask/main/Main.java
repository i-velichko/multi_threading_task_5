package com.epam.fifthtask.main;

import com.epam.fifthtask.entity.Wagon;
import com.epam.fifthtask.entity.WagonContainer;
import com.epam.fifthtask.exception.LogisticBaseException;
import com.epam.fifthtask.parser.DataParser;
import com.epam.fifthtask.reader.DataFileReader;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    private final static Logger LOGGER = LogManager.getLogger();
    public static void main(String[] args) throws ExecutionException, InterruptedException, LogisticBaseException {

        DataFileReader dataFileReader = new DataFileReader();
        DataParser dataParser = new DataParser();
        List<String> strings = dataFileReader.readAllDataFromFile("data/wagonData.txt");
        int[] wagonData = new int[2];
        for (int i = 0, stringsSize = strings.size(); i < stringsSize; i++) {
            int data = dataParser.parseStringToWagonData(strings.get(i));
            wagonData[i] = data;
        }

        System.out.println(Arrays.toString(wagonData));


        LOGGER.log(Level.INFO, "Starting work. ");
        ExecutorService service = Executors.newFixedThreadPool(8);

        for (int i = 0; i < wagonData[0]; i++) {
            Wagon wagon = new Wagon(new WagonContainer(), "Wag " + i); // криейтер грузовика из файла

            service.submit(wagon);
        }

        TimeUnit.MINUTES.sleep(5);
        service.shutdown();

        LOGGER.log(Level.INFO, "End of the working day ");
    }
}

