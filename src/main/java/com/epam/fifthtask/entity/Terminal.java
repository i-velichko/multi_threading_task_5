package com.epam.fifthtask.entity;

import com.epam.fifthtask.entity.type.TerminalStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Terminal {

    private final static Logger LOGGER = LogManager.getLogger();
    private String name;
    private Semaphore normalQueue;
    private TerminalStatus status = TerminalStatus.READY;
    private Random ran = new Random();

    public Terminal(String name, Semaphore normalQueue) {
        this.name = name;
        this.normalQueue = normalQueue;
    }

    public String getName() {
        return name;
    }

    public TerminalStatus getStatus() {
        return status;
    }

    public void setStatus(TerminalStatus status) {
        this.status = status;
    }

    public void unload(Wagon wagon) {
        LOGGER.info("Wagon unloading " + wagon + " in " + this);

        try {
            TimeUnit.SECONDS.sleep(ran.nextInt(5));
        } catch (Exception e) {
            LOGGER.info("!!!!!!!Error " + e.getMessage());
        }
        LOGGER.info("Unloading finished" + wagon + " in " + this + " Semaphore is free");
        status = TerminalStatus.READY;
        normalQueue.release();
    }

    @Override
    public String toString() {
        return "Terminal{" +
                "name='" + name + '\'' +
                ", status=" + status +
                '}';
    }
}

