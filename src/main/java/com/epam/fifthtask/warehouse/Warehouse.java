package com.epam.fifthtask.warehouse;

import com.epam.fifthtask.entity.WagonContainer;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Warehouse {
    private static final int MAX_WAREHOUSE_SIZE = 1000;
    private static final List<WagonContainer> containers = new CopyOnWriteArrayList<>();

    private static class WarehouseHolder {
        public static final Warehouse HOLDER_INSTANCE = new Warehouse();
    }

    public static Warehouse getInstance() {
        return Warehouse.WarehouseHolder.HOLDER_INSTANCE;
    }

    public List<WagonContainer> getContainers() {
        return containers;
    }

    public int getMaxWarehouseSize() {
        return MAX_WAREHOUSE_SIZE;
    }
}
