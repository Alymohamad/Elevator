package com.company;

/**
 * Beschreibung: Fahrstuhl der die Anfragen ausführt
 * @version 1.0
 * @author  Mohamad Aly
 */
import java.util.LinkedList;
import java.util.Queue;

public class Elevator {
    private Integer elevatorId;
    private Integer currentFloor;
    private Queue<Destination> destinationFloors;
    private boolean empty;

    public Elevator(Integer elevatorId){
        this.elevatorId = elevatorId;
        this.currentFloor = 0;
        this.destinationFloors = new LinkedList<Destination>();
        this.empty = true;
    }

    public Destination nextDestionation(){
        return this.destinationFloors.peek();
    }

    public void deleteDestinationFromQueue(){
        this.destinationFloors.remove();
    }

    public int currentFloor(){
        return this.currentFloor;
    }

    //Den Aufzug rauf bewegen
    public void moveUp() {
        currentFloor++;
    }

    //Den Aufzug runter bewegen
    public void moveDown() {
        currentFloor--;
    }

    //neues Ziel dem Aufzug übergenem
    public void addNewDestinatoin(Integer destination, DestinationType type) {
        this.destinationFloors.add(new Destination(destination, type));
    }

    public Queue<Destination> getDestinationFloors() {
        return destinationFloors;
    }

    public boolean isBusy() {
        return destinationFloors.size() != 0;
    }

    /**
     * bestimmt den Status des Aufzugs und gibt ihn zurück
     * @return ElevatorState den Status des Aufzugs
     * @since 1.0
     */
    public ElevatorState getElevatorState() {
        if (isBusy()){
            if (destinationFloors.peek().isFloorInPickupStops(currentFloor)){
                return ElevatorState.PICKUP;
            } else if (currentFloor < destinationFloors.peek().getDestinationFloor()){
                return ElevatorState.UP;
            } else if (currentFloor > destinationFloors.peek().getDestinationFloor()) {
                return ElevatorState.DOWN;
            } else if(!destinationFloors.isEmpty()){
                if (destinationFloors.peek().getType() == DestinationType.PICKING_UP)
                    return ElevatorState.COLLECT;
                else if (destinationFloors.peek().getType() == DestinationType.TRANSPORTING)
                    return ElevatorState.DROP;
            }
        }
        return ElevatorState.STANDBY;
    }

    public Integer getElevatorId() {
        return elevatorId;
    }

    public Integer getCurrentFloor() {
        return currentFloor;
    }
}