package com.epam.fifthtask.entity;

import com.epam.fifthtask.entity.type.TerminalStatus;
import com.epam.fifthtask.entity.type.WagonStatus;
import com.epam.fifthtask.warehouse.WagonContainer;
import com.epam.fifthtask.warehouse.Warehouse;

public class Terminal {
    private String name;
    private TerminalStatus terminalStatus = TerminalStatus.READY;
    private LogisticBase logisticBase = LogisticBase.getInstance();

    public Terminal(String name) {
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

    public void unloadingWagon(Wagon wagon){
        WagonContainer wagonContainer = wagon.getWagonContainer();
        Warehouse warehouse = logisticBase.getWarehouse();
        warehouse.getContainers().add(wagonContainer);
        wagon.setStatus(WagonStatus.EMPTY);
        wagon.setWagonContainer(null);
    }

}
