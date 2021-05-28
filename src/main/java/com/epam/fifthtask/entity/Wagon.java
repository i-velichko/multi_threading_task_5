package com.epam.fifthtask.entity;

import com.epam.fifthtask.entity.type.WagonStatus;
import com.epam.fifthtask.exception.LogisticBaseException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Callable;

public class Wagon implements Runnable {
    private final static Logger LOGGER = LogManager.getLogger();
    private WagonStatus status;
    private WagonContainer wagonContainer;
    private String name;

    public Wagon(WagonContainer wagonContainer, String name) {
        this.name = name;
        this.status = WagonStatus.FULL;
        this.wagonContainer = wagonContainer;
    }

    public WagonContainer getWagonContainer() {
        return wagonContainer;
    }

    public void setWagonContainer(WagonContainer wagonContainer) {
        this.wagonContainer = wagonContainer;
    }

    public WagonStatus getStatus() {
        return status;
    }

    public void setStatus(WagonStatus status) {
        this.status = status;
    }

    @Override
    public void run()  {
        LOGGER.log(Level.INFO, "This wagon ID = " + name + " Thread ID:" +Thread.currentThread().getId());
        LogisticBase logisticBase = LogisticBase.getInstance();
        try {
            Terminal terminal = logisticBase.chooseTerminal(this);
            terminal.unloadingWagon(this);
            LOGGER.log(Level.INFO, "Unloading container finished wagon:" + name);
        } catch (Exception e) {
            LOGGER.error("Wagon.error " + e.getMessage());
        }
    }

    public String getName() {
        return name;
    }
}
