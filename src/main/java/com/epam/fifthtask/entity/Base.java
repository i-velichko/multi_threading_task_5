package com.epam.fifthtask.entity;

import com.epam.fifthtask.entity.type.TerminalStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Base {

    private final static Logger LOGGER = LogManager.getLogger();
    private static Base instance = new Base();
    private List<Terminal> terminals = new CopyOnWriteArrayList<>();
    private Semaphore normalQueue = new Semaphore(3);
    private Lock lock = new ReentrantLock();

    public static Base getInstance() {
        return instance;
    }

    public Base() {
        terminals.add(new Terminal("Terminal A", normalQueue));
        terminals.add(new Terminal("Terminal B", normalQueue));
        terminals.add(new Terminal("Terminal C", normalQueue));
    }

    public Terminal askForTerminal() {
        try {
            normalQueue.acquire();
            lock.lock();
            Terminal terminal = terminals.stream()
                    .filter(next -> next.getStatus() == TerminalStatus.READY)
                    .findAny()
                    .orElseThrow(() -> new RuntimeException("No Free Terminal"));

            terminal.setStatus(TerminalStatus.BUSY);
            return terminal;

        } catch (Exception e) {
            LOGGER.warn(e.getMessage());
        } finally {
            lock.unlock();
        }

        return null;
    }
}



