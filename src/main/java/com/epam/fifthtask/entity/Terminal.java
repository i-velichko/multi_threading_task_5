package com.epam.fifthtask.entity;

import com.epam.fifthtask.entity.type.ProductsPriority;
import com.epam.fifthtask.entity.type.TerminalStatus;
import com.epam.fifthtask.entity.type.WagonStatus;
import com.epam.fifthtask.exception.LogisticBaseException;
import com.epam.fifthtask.warehouse.Warehouse;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Terminal {
    private final static Logger LOGGER = LogManager.getLogger();
    private String name;
    private TerminalStatus terminalStatus = TerminalStatus.READY;
    private final ProductsPriority productsPriority;
    private final Semaphore queue;
    private final Lock lock = new ReentrantLock();

    public Terminal(String name, ProductsPriority priority, Semaphore normalQueue) {
        this.name = name;
        this.queue = normalQueue;
        this.productsPriority = priority;
    }

    public void unloadingWagon(Wagon wagon) throws LogisticBaseException {
        Warehouse warehouse = Warehouse.getInstance();
        lock.lock();
        WagonContainer wagonContainer = wagon.getWagonContainer();
        LOGGER.log(Level.INFO, "" + wagon + " unloading in " + this);
        try {
            TimeUnit.MILLISECONDS.sleep(1);
            if (warehouse.getContainers().size() != warehouse.getMaxWarehouseSize()) {
                warehouse.getContainers().add(wagonContainer);
                LOGGER.log(Level.INFO, "Container has been added by {} to Warehouse. from wagon:" + wagon.getName(), name);
                wagon.setStatus(WagonStatus.EMPTY);
                wagon.setWagonContainer(null);
            } else {
                LOGGER.log(Level.INFO, "Warehouse is full. ");

            }
            terminalStatus = TerminalStatus.READY;
        } catch (InterruptedException e) {
            throw new LogisticBaseException("Something wrong in waiting for unload wagon..." + wagon.getName());
        } finally {
            lock.unlock();
        }
        queue.release();
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

    public ProductsPriority getProductsPriority() {
        return productsPriority;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Terminal ");
        sb.append(name).append('\'');
        return sb.toString();
    }
}
