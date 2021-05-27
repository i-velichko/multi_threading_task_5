package com.epam.fifthtask.entity;

import com.epam.fifthtask.warehouse.Warehouse;

import java.util.ArrayList;
import java.util.List;

public class LogisticBase {
    private static LogisticBase logisticBase;
    private List<Terminal> terminals; //ConcurrentList
    private Warehouse warehouse;

    private LogisticBase() {
        terminals.add(new Terminal("Terminal A"));
        terminals.add(new Terminal("Terminal B"));
        terminals.add(new Terminal("Terminal C"));
    }

    public static LogisticBase getInstance(){
        if (logisticBase == null){
            logisticBase = new LogisticBase();
        }
        return logisticBase;
    }


    public Warehouse getWarehouse() { //todo удалить
        return warehouse;
    }

    public void chooseTerminal(Wagon wagon){
        //пошла ебучая многопоточность выбора терминала
    }

}

//  у базы должен быть метод принять вагон и выделить терминал
//передать в этот метод Вагон через this
// соответственно этот метод решает какой терминал занят а какой свободен и дает его

