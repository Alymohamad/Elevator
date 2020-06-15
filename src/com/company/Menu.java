// Packages
package com.company;

// Imports

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

// Interne Hilfsklasse
class MenuEntry {
	String key;
    String text;
	MenuEntry(String k, String t){key=k; text=t;}
}

/**
 *
 * Beschreibung: Diese Klasse uerbernimmt die Verwaltung/Darstellung der Menues (Eingabe und Ausgabe) vor.
 **/
public class Menu {

    // Attributes
    private String titel; // Ueberschrift
    private Vector vec; // Menueeintraege

	//Constructor
    /**
     * Konstruktor Menu
     * Parameter ----
     *
     * Initialisierung der internen Datenstrukturen
     **/
	public Menu(String title){
		this.titel = title;
		this.vec = new Vector();
	}

    /**
     * Methode insert
     * Parameter: String key
     *            String text
     *
     * Beschreibung: Diese Methode fuegt einen neuen Menueeintrag hinzu. Der Parameter key
     * gibt das einzugebende Zeichen an. text ist die anzuzeigende Beschreibung
     **/
	@SuppressWarnings("unchecked")
    public void insert(String key, String text)
	{
	    vec.addElement(new MenuEntry(key,text));
	}

	/**
	 * Methode outputMenu
	 * Parameter ----
	 *
	 * Beschreibung: Diese Funktion zeigt durchgehend das definierte Menue an
	 **/
	public void outputMenu() {
		printMenu();
		StyledOutput.printSeparator();
		StyledOutput.printPrompt("> ");
	}

	/**
	 * Methode inputMenu     //CASE INSENSITIVE CHARACTER HANDLING
	 * Parameter ----
	 * Rueckgabwert String
	 *
	 * Beschreibung: Diese Funktion zeigt die defenierte Tabelle der Aufzüge an und wartet bis ein korrekte die Eingabe erfolgt
	 * @return Der Rueckgabe wert entspricht ist entweder ein Wrong Input welches ausgegeben wird falls ein falsche eingabe erfolgte
	 * oder der korrekten Eingabe iner Zahl zwischen 0-55 und 00-055 (führende nuller repräsentieren eine Anfrage aus dem Erdgeschoss)
	 * es Menueeintrages
	 **/
	public String inputMenu() {
		// Eingabeschleife
		BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));

		do {

			String value="\0";

			// Einlesen einer Zeile und eventuelles abfangen eienr IO Exception
			try {
				value=inReader.readLine();
				StyledOutput.printFoot();
				StyledOutput.printBlock("Ihre Eingabe: " + value);
			}
			catch ( IOException e) {
			}

			if (value.equals("q")){
				return value;}
			if (value.length()>0 && isNumeric(value)) { // Ueberpruefung ob die Eingabe ein Leerstring ist und ob die Eingabe eine Zahl ist
				if(0 <= Integer.parseInt(value) && Integer.parseInt(value) <= 55){
					return value;
				}
			}
			// Benutzer benachrichtigen, dass sein Eingabe ungueltig war
			return "Wrong Input!";
		} while (true);
	}

	/**
	 * schaut ob der input nur aus zahlen besteht oder nicht
	 * @param str input des users
	 * @return boolean der sagt ob der input eine Zahl ist
	 * @since 1.0
	 */
	public static boolean isNumeric(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch(NumberFormatException e){
			return false;
		}
	}

	/**
	 * gibt das Menü aus und damit die Aufzug Tabelle
	 * @since 1.0
	 */
	private void printMenu() {
		StyledOutput.printHeading(titel);
		// Alle Menueeingraege nacheinander ausgeben (Key + Beschreibung)
		for (int i=0;i<vec.size();i++) {
			MenuEntry ent=(MenuEntry)vec.elementAt(i);
			try {
				if (ent.key.equals("p") || ent.key.equals("l") || Integer.parseInt(ent.key) % 10 == 0) //Improve. Too impl dependant
					StyledOutput.printSeparator();
			}catch (NumberFormatException e){}
			StyledOutput.printText(String.format("%3s │ %s", ent.key, ent.text));
		}
	}
}