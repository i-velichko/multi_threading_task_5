package com.epam.fifthtask.entity;

import com.epam.fifthtask.entity.type.TerminalStatus;
import com.epam.fifthtask.exception.LogisticBaseException;
import com.epam.fifthtask.warehouse.Warehouse;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LogisticBase {
    private final static Logger LOGGER = LogManager.getLogger();
    private final List<Terminal> terminals = new CopyOnWriteArrayList<>();
    private Warehouse warehouse;
    private Lock lock = new ReentrantLock();
    private Semaphore normalSemaphore = new Semaphore(1);

    private LogisticBase() {
        terminals.add(new Terminal("Terminal A"));
        terminals.add(new Terminal("Terminal B"));
        terminals.add(new Terminal("Terminal C"));
    }

    private static class LogisticBaseHolder {
        public static final LogisticBase HOLDER_INSTANCE = new LogisticBase();
    }

    public static LogisticBase getInstance() {
        return LogisticBaseHolder.HOLDER_INSTANCE;
    }

    public List<Terminal> getTerminals() {
        return terminals;
    }

    public Warehouse getWarehouse() { //todo удалить
        return warehouse;
    }

    public Terminal chooseTerminal(Wagon wagon) throws LogisticBaseException {

        try {
            normalSemaphore.acquire(3);
            lock.lock();
            for (Terminal terminal : terminals) {
                if (terminal.getTerminalStatus() == TerminalStatus.READY) {
                    LOGGER.log(Level.INFO, "Free terminal found -  " + terminal.getName() + " For wagon: " + wagon.getName());
                    terminal.setTerminalStatus(TerminalStatus.BUSY);
                    return terminal;
                }
            }
            normalSemaphore.release();
        } catch (InterruptedException e) {
            throw new LogisticBaseException("Terminal is not ready ");
        } finally {
            lock.unlock();
        }
        return null;
    }

}


