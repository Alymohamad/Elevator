package com.company;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MyTest {
    private ElevatorSystem es;

    @Test
    void getEmployeeFromFloorAndTransportToGround() {
        int srcFloor = 2;
        int dstFloor = 0;

        es.addRequest(srcFloor, dstFloor);
        stepsXTimes(2);

        // Checkt ob der erste Aufzug nach 2 steps im 2. Stockwerk ist
        assertEquals(2, es.getElevators().get(0).getCurrentFloor());

        // Checkt ob der erste Aufzug nach 2 steps im 2. Stockwerk ist
        assertEquals(ElevatorState.COLLECT, es.getElevators().get(0).getElevatorState());

        stepsXTimes(3);

        // Checkt ob der erste Aufzug nach 2 steps im Ergeschoss ist (wieder unten)
        assertEquals(0, es.getElevators().get(0).getCurrentFloor());

        // Checkt ob der erste Aufzug den Mitarbeiter im Erdgeschoss ist und wieder zu haben ist
        assertEquals(ElevatorState.DROP, es.getElevators().get(0).getElevatorState());

    }

    @Test
    void getEmployeeFromGroundAndTransportToFloor() {
        int srcFloor = 0;
        int dstFloor = 2;

        es.addRequest(srcFloor, dstFloor);
        stepsXTimes(2);

        // Checkt ob der erste Aufzug nach 2 steps im 2. Stockwerk ist
        assertEquals(2, es.getElevators().get(0).getCurrentFloor());

        // Checkt ob der erste Aufzug nach 2 steps im 2. Stockwerk ist
        assertEquals(ElevatorState.DROP, es.getElevators().get(0).getElevatorState());

        stepsXTimes(2);

        // Checkt ob der erste Aufzug immernoch im im 2. Stockwerk ist da keine neuen Anfragen reingekommen sind
        assertEquals(2, es.getElevators().get(0).getCurrentFloor());

        // Checkt ob der erste Aufzug den Status Standby hat
        assertEquals(ElevatorState.STANDBY, es.getElevators().get(0).getElevatorState());
    }

    @BeforeEach
    private void initiliseElevatorSystem(){
        this.es = ElevatorSystem.getInstance();
    }

    private void stepsXTimes(int steps){
        while (steps>0) {
            es.step();
            steps--;
        }
    }
}