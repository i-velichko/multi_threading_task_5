package com.epam.fifthtask.entity;

import com.epam.fifthtask.entity.type.WagonStatus;
import com.epam.fifthtask.warehouse.WagonContainer;

import java.util.List;
import java.util.concurrent.Callable;

public class Wagon implements Callable<Boolean> {
    private WagonStatus status;
    private WagonContainer wagonContainer;

    public Wagon(WagonContainer wagonContainer) {
        this.status = WagonStatus.FULL;
        this.wagonContainer = wagonContainer;
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
    public Boolean call() throws Exception {
        LogisticBase logisticBase = LogisticBase.getInstance();
        System.out.println("Выгружаю контейнер");
        logisticBase.testTerminal(this);



//        TimeUnit.SECONDS.sleep(5);
        return false;
    }
}
