package com.company;

/**
 * Beschreibung: Die Fahrstuhl Tabelle wird hier ausgegeben und die einzelnen Schritte der Fahrstühle werden hier ausgeführt
 * @version 1.0
 * @author  Mohamad Aly
 */
public class ElevatorTable extends Thread{
    private ElevatorSystem es;
    public ElevatorTable(){this.es = ElevatorSystem.getInstance();}

    //Sekunden die vergehen nach jedem Aufzugschritt
    private final double SECONDS_PER_STEP = 3.5;

    public void run() {

        // Print Software Banner
        StyledOutput.printBanner();

        // Start Software
        ElevatorSystem es = ElevatorSystem.getInstance();
        while (true) {
            // Ein Step ist eine Zeiteinheit bzw. eine Bewegung alles Aufzüge
            es.step();
            startMenu(es);

            try {
                Thread.sleep((int)(SECONDS_PER_STEP * 1000.0));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Startet die Ausgabe der Aufzugtabelleu nd fürhrt alle SECONDS_PER_STEP Sekunden einen Schritt aus
     * @param es ElevatorSystem aus wem die Daten geholt werden die ausgegeben werden
     * @since 1.0
     */
    public void startMenu(ElevatorSystem es){
        // Create Menu
        Menu menu = new Menu("DC Tower Elevator Challenge");
        // Insert entries
        for (Elevator elevator: es.getElevators()) {
            if (elevator.getElevatorState() == ElevatorState.PICKUP)
                menu.insert("Elevator " + elevator.getElevatorId().toString(), "Floor: " + elevator.getCurrentFloor().toString() +
                        "\t State: " + elevator.getDestinationFloors().peek().getPickupStop(elevator.currentFloor()).getType());
            else
                menu.insert("Elevator " + elevator.getElevatorId().toString(), "Floor: " + elevator.getCurrentFloor().toString() +
                    "\t State: " + elevator.getElevatorState().toString());
        }
        menu.insert("q", "Quit");

        //Funktioniert nur wenn das programm über der Konsole gestartet wurde und nicht über der IDE
        StyledOutput.clearScreen();
        menu.outputMenu();
    }
}
