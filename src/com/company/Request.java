package com.company;

/**
 * Beschreibung: repräsentiert eine Anfrage an das Fahrstuhlsystem
 * @version 1.0
 * @author  Mohamad Aly
 */
public class Request {
    private Integer source;
    private Integer destination;
    private ElevatorState state;

    public Request(int source, int destination, ElevatorState state){
        this.source = source;
        this.destination = destination;
        this.state = state;
    }

    public Integer getSource() {
        return source;
    }

    public Integer getDestination() {
        return destination;
    }

    public ElevatorState getState() {
        return state;
    }

}
