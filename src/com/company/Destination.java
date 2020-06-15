package com.company;

import java.util.ArrayList;
import java.util.List;

/**
 * Beschreibung: reprässentiert Ein Ziel eines Aufzugs
 * @version 1.0
 * @author  Mohamad Aly
 */
public class Destination {
    private Integer destinationFloor;
    private DestinationType type;
    private List<PickupStop> pickupStops;

    public Destination(Integer destinationFloor, DestinationType type){
        this.destinationFloor = destinationFloor;
        this.type = type;
        this.pickupStops = new ArrayList<>();
    }

    /**
     * fügt einem Ziel ein Zwischenstopp hinzu damit jemand ein/austeigen kann
     * @param floor Stockwerk des Zwischenstopps
     * @param state Richtung der Anfrage (UP oder DOWN)
     * @since 1.0
     */

    public void addPickupStops(Integer floor, ElevatorState state){
        PickupType type;
        if (state == ElevatorState.UP)
            type = PickupType.DROP;
        else if (state == ElevatorState.DOWN)
            type = PickupType.COLLECT;
        //Wird nie auftreten das default
        else
            type = PickupType.COLLECT;
        this.pickupStops.add(new PickupStop(floor, type));
    }

    public boolean isFloorInPickupStops(Integer floor){
        for (PickupStop ps: pickupStops) {
            if (ps.getFloor() == floor)
                return true;
        }
        return false;
    }

    public PickupStop getPickupStop(Integer floor) {
        for (PickupStop ps: pickupStops) {
            if (floor == ps.getFloor())
                return ps;
        }
        //Wird nicht auftreten da nur aufgerufen wenn in elevator jemanden ab pickt
        return null;
    }

    public void deletePickupStop(Integer floor){
        for (int i = 0;i<pickupStops.size();i++){
            if(pickupStops.get(i).getFloor() == floor){
                pickupStops.remove(pickupStops.get(i));
            }
        }
    }

    public Integer getDestinationFloor() {
        return destinationFloor;
    }

    public DestinationType getType() {
        return type;
    }
}
