package com.epam.fifthtask.entity;

import com.epam.fifthtask.entity.type.TerminalStatus;
import com.epam.fifthtask.entity.type.WagonStatus;
import com.epam.fifthtask.warehouse.Warehouse;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Terminal extends Semaphore {
    private final static Logger LOGGER = LogManager.getLogger();
    private final LogisticBase logisticBase = LogisticBase.getInstance();
    private String name;
    private TerminalStatus terminalStatus = TerminalStatus.READY;


    public Terminal(String name) {
        super(3);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TerminalStatus getTerminalStatus() {
        return terminalStatus;
    }

    public void setTerminalStatus(TerminalStatus terminalStatus) {
        this.terminalStatus = terminalStatus;
    }

    public void unloadingWagon(Wagon wagon) throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
        WagonContainer wagonContainer = wagon.getWagonContainer();
        if (Warehouse.getContainers().size() != Warehouse.getMaxWarehouseSize()) {
            Warehouse.getContainers().add(wagonContainer);
            LOGGER.log(Level.INFO, "Container has been added by Termninal {} to Warehouse. from wagon:" + wagon.getName(), name);
            wagon.setStatus(WagonStatus.EMPTY);
            wagon.setWagonContainer(null);
            terminalStatus = TerminalStatus.READY;
        } else {
            LOGGER.log(Level.INFO, "Warehouse is full. ");
        }

    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Terminal{");
        sb.append("name='").append(name).append('\'');
        sb.append(", terminalStatus=").append(terminalStatus);
        sb.append('}');
        return sb.toString();
    }
}
