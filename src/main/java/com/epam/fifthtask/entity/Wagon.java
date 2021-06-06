package com.epam.fifthtask.entity;

import com.epam.fifthtask.entity.type.WagonStatus;
import com.epam.fifthtask.exception.LogisticBaseException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Wagon implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger();
    private WagonStatus status;
    private WagonContainer wagonContainer;
    private final String name;

    public Wagon(WagonContainer wagonContainer, String name) {
        this.name = name;
        this.status = WagonStatus.FULL;
        this.wagonContainer = wagonContainer;
    }

    @Override
    public void run() {
        LogisticBase logisticBase = LogisticBase.getInstance();
        try {
            Terminal terminal = logisticBase.chooseTerminal(this);
            LOGGER.log(Level.INFO, "" + this + " got " + terminal);
            terminal.unloadingWagon(this);
        } catch (LogisticBaseException e) {
            LOGGER.error("Wagon.error " + e.getMessage());
        }
    }

    public String getName() {
        return name;
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Wagon wagon = (Wagon) o;

        if (getStatus() != wagon.getStatus()) {
            return false;
        }
        if (getWagonContainer() != null ? !getWagonContainer().equals(wagon.getWagonContainer()) : wagon.getWagonContainer() != null) {
            return false;
        }
        return getName() != null ? getName().equals(wagon.getName()) : wagon.getName() == null;
    }

    @Override
    public int hashCode() {
        int result = getStatus() != null ? getStatus().hashCode() : 0;
        result = 31 * result + (getWagonContainer() != null ? getWagonContainer().hashCode() : 0);
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Wagon: ");
        sb.append("status=").append(status);
        sb.append(", wagonContainer=").append(wagonContainer);
        sb.append(", name= '").append(name).append('\'');
        sb.append('.');
        return sb.toString();
    }
}
