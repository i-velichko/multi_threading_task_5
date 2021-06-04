package com.epam.fifthtask.entity;

import com.epam.fifthtask.entity.type.ContainerContent;
import com.epam.fifthtask.entity.type.ProductsPriority;
import com.epam.fifthtask.entity.type.TerminalStatus;
import com.epam.fifthtask.exception.LogisticBaseException;
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
    private final Lock lock = new ReentrantLock();
    private final Semaphore normalQueue = new Semaphore(3);
    private final Semaphore priorityQueue = new Semaphore(2);

    private LogisticBase() {
        terminals.add(new Terminal("Terminal A", ProductsPriority.NORMAL_PRIORITY, normalQueue));
        terminals.add(new Terminal("Terminal B", ProductsPriority.NORMAL_PRIORITY, normalQueue));
        terminals.add(new Terminal("Terminal C", ProductsPriority.NORMAL_PRIORITY, normalQueue));
        terminals.add(new Terminal("Terminal D", ProductsPriority.HIGH_PRIORITY, priorityQueue));
        terminals.add(new Terminal("Terminal E", ProductsPriority.HIGH_PRIORITY, priorityQueue));

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

    public Terminal chooseTerminal(Wagon wagon) throws LogisticBaseException {

        if (wagon.getWagonContainer().getContent() == ContainerContent.PERISHABLE_PRODUCTS) {
            return choosePriorityTerminal(ProductsPriority.HIGH_PRIORITY, priorityQueue);
        } else {
            return choosePriorityTerminal(ProductsPriority.NORMAL_PRIORITY, normalQueue);
        }

    }

    private Terminal choosePriorityTerminal(ProductsPriority highPriority, Semaphore queue) throws LogisticBaseException {
        try {
            queue.acquire();
            lock.lock();
            Terminal terminal = terminals.stream()
                    .filter(next -> next.getTerminalStatus() == TerminalStatus.READY)
                    .filter(next -> next.getProductsPriority() == highPriority)
                    .findAny()
                    .orElseThrow(() -> new LogisticBaseException("No free terminal"));
            terminal.setTerminalStatus(TerminalStatus.BUSY);
            return terminal;
        } catch (InterruptedException e) {
            throw new LogisticBaseException("Terminal is not ready ");
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LogisticBase that = (LogisticBase) o;

        return getTerminals().equals(that.getTerminals());
    }

    @Override
    public int hashCode() {
        return getTerminals().hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("LogisticBase{");
        sb.append("terminals=").append(terminals);
        sb.append('}');
        return sb.toString();
    }
}


