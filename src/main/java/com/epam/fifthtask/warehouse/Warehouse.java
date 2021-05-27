package com.epam.fifthtask.warehouse;

import java.util.ArrayList;
import java.util.List;

public class Warehouse {

    private final static int MAX_WAREHOUSE_SIZE = 1000;
    private List<WagonContainer> containers = new ArrayList<>();

    public List<WagonContainer> getContainers() {
        return containers;
    }

}
