package com.epam.fifthtask.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Wagon implements Runnable {
    private final static Logger LOGGER = LogManager.getLogger();
    private String name;

    public Wagon(int i) {
        this.name = "Wagon-" + i;
    }

    public String getName() {
        return name;
    }

    @Override
    public void run() {
        Base base = Base.getInstance();
        Terminal terminal = base.askForTerminal();
        LOGGER.info("" + this + " Got " + terminal);
        terminal.unload(this);

    }

    @Override
    public String toString() {
        return "Wagon{" +
                "name='" + name + '\'' +
                '}';
    }
}

