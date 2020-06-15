package com.company;

/**
 * Beschreibung: Consolen Client verantwortlich für den Input und der weiterleitung der Requests
 * @version 1.0
 * @author  Mohamad Aly
 */
public class ConsoleClient extends Thread {

    private ElevatorSystem es;
    public ConsoleClient(){this.es = ElevatorSystem.getInstance();}

    /**
     * startet und überprüft den Input und leitet den korrekten input als Request dem Elevatorsystem weiter
     * @since 1.0
     */
    public void run() {

        // Create Menu
        Menu menu = new Menu("DC Tower Elevator Challenge");
        while (true) {
            try {
                String input = menu.inputMenu().trim();
                if(input.equals("q"))
                    quit();
                else if (input.equals("Wrong Input!"))
                    StyledOutput.printBlock("Wrong Input!");
                else{
                    //Habe korrekten Input (zahl von 0-55 oder 00 -- 055)
                    //falls führende null dann Anfrage vom Ergeschoss weiterleiten
                    if(Integer.parseInt(input.substring(0,1)) == 0) {
                        es.addRequest(0, Integer.parseInt(input));
                        StyledOutput.printBlock("Ihre Anfrage vom Erdgeschoss ins Stockwerk: " + input.substring(1) + " ist Erfolgreich, Aufzug ist auf dem Weg!");
                    }
                    else{
                        //anderfalls Anfrage vom angegeben Stockwerk weiterleiten
                        es.addRequest(Integer.parseInt(input), 0);
                        StyledOutput.printBlock("Ihre Anfrage vom Stockwerk: " + input + " ins Erdgeschoss ist Erfolgreich, Aufzug ist auf dem Weg!");
                    }
                }
            } catch (ClassCastException e) { StyledOutput.printBlock("Wrong input"); }
        }
    }

    private void quit(){
        StyledOutput.printBlock("Program finished!");
        System.exit(0);
    }
}
