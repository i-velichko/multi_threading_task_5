package com.epam.fifthtask.main;

import com.epam.fifthtask.entity.Wagon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.*;

public class Main {

    private final static Logger LOGGER = LogManager.getLogger();

    public static void main(String[] args) throws InterruptedException {
        LOGGER.info("Start program!!!");
        ExecutorService pool = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 500; i++) {
            Wagon wagon = new Wagon(i);
            pool.submit(wagon);
        }

        TimeUnit.MINUTES.sleep(1);
        pool.shutdown();
        LOGGER.info("End program!!!");
    }
}


