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
    private static final Logger LOGGER = LogManager.getLogger();
    private final String name;
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Terminal terminal = (Terminal) o;

        if (name != null ? !name.equals(terminal.name) : terminal.name != null) {
            return false;
        }
        if (getTerminalStatus() != terminal.getTerminalStatus()) {
            return false;
        }
        return getProductsPriority() == terminal.getProductsPriority();
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (getTerminalStatus() != null ? getTerminalStatus().hashCode() : 0);
        result = 31 * result + (getProductsPriority() != null ? getProductsPriority().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Terminal ");
        sb.append(name).append('\'');
        return sb.toString();
    }
}
