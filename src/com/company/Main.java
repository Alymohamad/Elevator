package com.company;

/**
 * Beschreibung: Main Initialisiert das Fahrstuhlsystem und führt die simulation mittels threads aus
 * @version 1.0
 * @author  Mohamad Aly
 */
public class Main {

    public static void main(String[] args) {
	// write your code here

        // Thread 1: Input und verarbeitung der Anfragen
        ConsoleClient cc = new ConsoleClient();
        cc.start();

        // Thread 1: Output und Elevator Schritte Simulieren
        ElevatorTable et = new ElevatorTable();
        et.start();
    }
}
