package com.company;

/**
 * Beschreibung: repräsentiert einen zwischenstopp der eingelegt werden muss auf dem weg zum Ziel
 * @version 1.0
 * @author  Mohamad Aly
 */
public class PickupStop {
    private Integer floor;
    private PickupType type;

    public PickupStop(Integer floor, PickupType type){
        this.floor = floor;
        this.type = type;
    }

    public Integer getFloor() {
        return floor;
    }

    public PickupType getType() {
        return type;
    }
}
