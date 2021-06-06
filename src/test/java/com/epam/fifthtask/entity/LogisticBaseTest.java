package com.epam.fifthtask.entity;

import com.epam.fifthtask.entity.type.ContainerContent;
import com.epam.fifthtask.entity.type.ProductsPriority;
import com.epam.fifthtask.entity.type.TerminalStatus;
import com.epam.fifthtask.exception.LogisticBaseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.epam.fifthtask.entity.type.ContainerContent.NORMAL_PRODUCTS;
import static com.epam.fifthtask.entity.type.ContainerContent.PERISHABLE_PRODUCTS;
import static com.epam.fifthtask.entity.type.TerminalStatus.READY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LogisticBaseTest {
    private LogisticBase logisticBase;
    private Wagon wagon1;
    private Wagon wagon2;

    @BeforeEach
    void setUp() {
        logisticBase = LogisticBase.getInstance();
        wagon1 = new Wagon(new WagonContainer(PERISHABLE_PRODUCTS),"Wagon 1");
        wagon2 = new Wagon(new WagonContainer(NORMAL_PRODUCTS),"Wagon 1");
        List<Terminal> terminals = logisticBase.getTerminals();
        terminals.forEach(terminal -> terminal.setTerminalStatus(READY));
    }



    @Test
    void chooseTerminalWhenPerishableProducts() throws LogisticBaseException {
        Terminal terminal = logisticBase.chooseTerminal(wagon1);
        ProductsPriority actualTerminalProductsPriority = terminal.getProductsPriority();
        ProductsPriority expectedTerminalProductsPriority = ProductsPriority.HIGH_PRIORITY;
        assertEquals(expectedTerminalProductsPriority, actualTerminalProductsPriority);
    }

    @Test
    void chooseTerminalWhenNormalProducts() throws LogisticBaseException {
        Terminal terminal = logisticBase.chooseTerminal(wagon2);
        ProductsPriority actualTerminalProductsPriority = terminal.getProductsPriority();
        ProductsPriority expectedTerminalProductsPriority = ProductsPriority.NORMAL_PRIORITY;
        assertEquals(expectedTerminalProductsPriority, actualTerminalProductsPriority);
    }

    @Test
    void expectedExceptionWhenAllTerminalsIsBusy() {
        List<Terminal> terminals = logisticBase.getTerminals();
        for (Terminal nextTerminal : terminals) {
            nextTerminal.setTerminalStatus(TerminalStatus.BUSY);
        }
        assertThrows(LogisticBaseException.class, ()->
                logisticBase.chooseTerminal(wagon2));
    }
}