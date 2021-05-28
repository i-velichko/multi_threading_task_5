package com.epam.fifthtask.main;

import com.epam.fifthtask.entity.LogisticBase;
import com.epam.fifthtask.entity.Terminal;
import com.epam.fifthtask.entity.Wagon;
import com.epam.fifthtask.entity.WagonContainer;
import com.epam.fifthtask.exception.LogisticBaseException;
import com.epam.fifthtask.warehouse.Warehouse;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    private final static Logger LOGGER = LogManager.getLogger();
    public static void main(String[] args) throws ExecutionException, InterruptedException, LogisticBaseException {
        LOGGER.log(Level.INFO, "Starting work. ");

        ExecutorService service = Executors.newFixedThreadPool(8);

        for (int i = 0; i < 1000; i++) {
            Wagon wagon = new Wagon(new WagonContainer(), "Wag" + i);
            service.submit(wagon);
        }

        TimeUnit.MINUTES.sleep(1);
        LOGGER.log(Level.INFO, "Containers count in warehouse: - " + Warehouse.getContainers().size());
        service.shutdown();
    }
}

