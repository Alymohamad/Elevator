package com.company;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Beschreibung: Fahrstuhlsystem welches die Anfragen annimmt und verarbeitet und an die entsprechenden Fahrstühle weiterleitet
 * @version 1.0
 * @author  Mohamad Aly
 */

// Singelton angwendet da nur ein System exestieren sollte
public class ElevatorSystem {
    private static final int MAXNUMBEROFELEVATORS = 7;
    private static ElevatorSystem ourInstance = new ElevatorSystem(MAXNUMBEROFELEVATORS);
    private List<Elevator> elevatorList;

    //Hier kommen die Requests rein die abgearbeitet werden
    private Queue<Request> requests;

    public static ElevatorSystem getInstance() { return ourInstance; }

    private ElevatorSystem(int numberOfElevators) {
        this.requests = new LinkedList<Request>();
        this.elevatorList = new ArrayList<Elevator>();
        initializeElevators(numberOfElevators);
    }

    /**
     * fügt eine request zur reqquest Liste queue hinzu
     * @param currentFloor wo der Fahrer wartet
     * @param destinationFloor wohin er hin möchte
     * @since 1.0
     */
    public void addRequest(int currentFloor, int destinationFloor){
        if (currentFloor < destinationFloor)
            requests.add(new Request(currentFloor, destinationFloor, ElevatorState.UP));
        else if (currentFloor > destinationFloor)
            requests.add(new Request(currentFloor, destinationFloor, ElevatorState.DOWN));
    }

    /**
     * Initialisiert die Fahrstühle
     * @param numberOfElevators bestimmt wie viele Fahrstühle erstellt werden
     * @since 1.0
     */
    public void initializeElevators(int numberOfElevators){
        for(int elevatorId = 1;elevatorId<=numberOfElevators;elevatorId++){
            elevatorList.add(new Elevator(elevatorId));
        }
    }

    public List<Elevator> getElevators(){
        return elevatorList;
    }

    /**
     * verschickt einen Fahrstuhl zu einem bestimmten stockwerk
     * @param elevatorId Aufzug identifier
     * @param destinationFloor Ziel stockwerk
     * @param type bestimmt ob die fahrt ein abholung oder ein transport ist
     * @since 1.0
     */
    public void sendElevatorToDestinatoin(Integer elevatorId, Integer destinationFloor, DestinationType type) {
        elevatorList.get(elevatorId).addNewDestinatoin(destinationFloor, type);
    }

    /**
     * diese Funktion ist der Kern des Algorythmus da diese Funktion festellt welcher Aufzug die Anfrage zugewiesen bekommt
     * je nachdem welche frei bzw. am nähsten dran sind
     * @since 1.0
     */
    public void distributeWaitingRequests() {

        int requestsSize = requests.size();
        for (int i = 0;i<requestsSize;i++){
            //Nachdem löschen einer Request aus der requests queue rückt die nächste an ihrer stelle bzw. Stelle 0
            Request req = (Request) requests.toArray()[0];

            Integer nearestElevatorId = 0;
            Integer nearestElevatorDiffrence = Integer.MAX_VALUE;
            Integer elevatorDiff = 0;

            for (Elevator elevator : elevatorList) {
                if (elevator.isBusy()) {
                    if(elevator.nextDestionation().getDestinationFloor() == req.getSource() && elevator.nextDestionation().getType() == DestinationType.PICKING_UP){
                        nearestElevatorId = elevator.getElevatorId();
                        break;
                    }
                    else if(elevator.nextDestionation().getDestinationFloor() == req.getDestination() && elevator.nextDestionation().getType() == DestinationType.TRANSPORTING && req.getSource() == 0 && elevator.currentFloor() == 0){
                        nearestElevatorId = elevator.getElevatorId();
                        requests.remove(req);
                        break;
                    }
                    if (elevator.getElevatorState() == ElevatorState.DOWN && req.getSource() <= elevator.currentFloor()) {
                        if (req.getState() == ElevatorState.DOWN) {
                            elevatorDiff = Math.abs(req.getSource() - elevator.getCurrentFloor());
                            if (elevatorDiff < nearestElevatorDiffrence) {
                                nearestElevatorId = elevator.getElevatorId();
                                nearestElevatorDiffrence = elevatorDiff;
                            }
                        }
                    } else if (elevator.getElevatorState() == ElevatorState.UP && req.getSource() >= elevator.currentFloor()) {
                        if (req.getState() == ElevatorState.UP) {
                            elevatorDiff = Math.abs(req.getSource() - elevator.getCurrentFloor());
                            if (elevatorDiff < nearestElevatorDiffrence) {
                                nearestElevatorId = elevator.getElevatorId();
                                nearestElevatorDiffrence = elevatorDiff;
                            }
                        }
                    }
                } else if (!elevator.isBusy()) {
                    elevatorDiff = Math.abs(req.getSource() - elevator.getCurrentFloor());
                    if (elevatorDiff < nearestElevatorDiffrence) {
                        nearestElevatorId = elevator.getElevatorId();
                        nearestElevatorDiffrence = elevatorDiff;
                    }
                }
            }
            //assign all requests to the elevators and when no more elevators available break and try the next step to assisgn the remaining requests
            if(assignRequestToElevator(nearestElevatorId, req))
                break;
        }
    }

    /**
     * diese Funktion weist dem ausgewählten aufzug die Request hinzu und entfernt diese dann aus der requests queue da Anfrage abgearbeitet
     * @since 1.0
     */
    public boolean assignRequestToElevator(Integer nearestElevatorId, Request req){
        // Wenn nichts frei ist und nichts auf dem weg zu mir ist dann wird bis zum nächsten step gewartet und nochmal probiert
        if (nearestElevatorId != 0) {
            // Wenn ich eine request vom erdgeschoss bekomme und der lift eh unten ist
            if(elevatorList.get(nearestElevatorId-1).currentFloor() == req.getSource() && req.getSource() == 0 ){
                if (!(destinationFloorsContainsDestination(nearestElevatorId, req.getDestination()))) {
                    if (elevatorList.get(nearestElevatorId - 1).getDestinationFloors().size() == 0) {
                        sendElevatorToDestinatoin(nearestElevatorId - 1, req.getDestination(), DestinationType.TRANSPORTING);
                        requests.remove(req);
                    } else {
                        if (req.getDestination() < elevatorList.get(nearestElevatorId - 1).getDestinationFloors().peek().getDestinationFloor()) {
                            elevatorList.get(nearestElevatorId - 1).nextDestionation().addPickupStops(req.getDestination(), req.getState());
                            requests.remove(req);
                        } else if (req.getDestination() > elevatorList.get(nearestElevatorId - 1).getDestinationFloors().peek().getDestinationFloor()) {
                            sendElevatorToDestinatoin(nearestElevatorId - 1, req.getDestination(), DestinationType.TRANSPORTING);
                            requests.remove(req);
                        }
                    }
                }
            }
            else if (!(destinationFloorsContainsDestination(nearestElevatorId, req.getDestination()))) {
                sendElevatorToDestinatoin(nearestElevatorId - 1, req.getSource(), DestinationType.PICKING_UP);
                sendElevatorToDestinatoin(nearestElevatorId - 1, req.getDestination(), DestinationType.TRANSPORTING);
                requests.remove(req);
            } else if (destinationFloorsContainsDestination(nearestElevatorId, req.getDestination())) {
                elevatorList.get(nearestElevatorId-1).nextDestionation().addPickupStops(req.getSource(), req.getState());
                requests.remove(req);
            } else {
                //  wenn nearestElevatorId == 0 und alle beschäftigt sind und nichts zugewiesen wurde dann break
                return true;
            }
        }
        return false;
    }

    /**
     * schaut ob ein Ziel in der Ziel liste eines Aufzugs drinnen ist (ob er schon dieses Stockwerk zugewiesen bekommen hat)
     * @param elevatorId Aufzug identifier
     * @param destination Ziel stockwerk
     * @since 1.0
     */
    public boolean destinationFloorsContainsDestination(Integer elevatorId, Integer destination){
        for (Destination dest: elevatorList.get(elevatorId-1).getDestinationFloors()) {
            if(dest.getDestinationFloor() == destination)
                return true;
        }
        return false;
    }

    /**
     * bewegt die beschäftigten Aufzüge und verteilt die neuen Anfragen bei an die entsprächenden Aufzüge
     * @since 1.0
     */
    public void step() {
        distributeWaitingRequests();

        for (Elevator currentElevator : elevatorList){
            if(currentElevator.isBusy()) {
                switch(currentElevator.getElevatorState()) {
                    case UP:
                        currentElevator.moveUp();
                        break;
                    case DOWN:
                        currentElevator.moveDown();
                        break;
                    case COLLECT:
                        // Angekommen und angehalten
                        currentElevator.deleteDestinationFromQueue();
                        break;
                    case DROP:
                        currentElevator.deleteDestinationFromQueue();
                        break;
                    case PICKUP:
                        currentElevator.getDestinationFloors().peek().deletePickupStop(currentElevator.currentFloor());
                        break;
                }
            }
        }
    }
}
