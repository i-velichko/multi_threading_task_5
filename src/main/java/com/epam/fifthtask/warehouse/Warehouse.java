package com.epam.fifthtask.warehouse;

import com.epam.fifthtask.entity.WagonContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Warehouse {

    private static final int MAX_WAREHOUSE_SIZE = 1000;
    private static final List<WagonContainer> containers = new CopyOnWriteArrayList<>();

    public static List<WagonContainer> getContainers() {
        return containers;
    }

    public static int getMaxWarehouseSize() {
        return MAX_WAREHOUSE_SIZE;
    }
}
