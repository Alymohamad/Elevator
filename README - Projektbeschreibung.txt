
Da in der Angabe einpaar Punkte noch offen waren wie sich das System zu verhalten hat erkläre ich hier wie ich das verstanden und implementiert habe.
Es wird alle 3 Sekunden ein Schritt gemacht dieser schritt semuliert die bewegung der Aufzüge 
danach wird eine Tabelle ausgegeben die zeigt wo sich jeder Aufzug befindet und seinen aktuellen status zeigt.

Zum testen habe ich 2 Beispiels test geschrieben die aber jeweils einzeln ausgeführt werden müssen da durch den singelton pattern sie sich gegenseitig beeinflussen und um das zu umgehen mocks verwendet werden müssen
aber da ich haupsächlich durch die Konsole getestet habe waren die Mocks nicht nötig

Das System kann dann über einen Input beeinflusst werden welches den Knopf Druck semulieren sollte.
Da ich aus der Angabe herausgelesen habe das man als mitarbeiter im DC Tower von einem Stockwerk nur ins Ergeschoss und vom Erdgeschoss nur in mein Stockwerk fahren kann
	zb. 4. -> 0. oder 0. -> 4. sind zulässig
	4->3 ist nicht zulässig (aussnahme es ich bin im 4 will ins erdgeschoss und jemand im drritten will mitfahren dann hallte ich an)

es gibt 2 arten von Input:
	Von oben nach unten:
		wenn ich von einem stockwerk zb. 10 runter ins erdgeschoss möchte geb ich 10 ein
		also wenn ich runter will gebe ich meinen aktuelles stockwerk an (knop gedrückt)
	von unten nach oben:
	wenn ich vom erdgeschoss zb. in den 10. rauf will geb ich ein 010
	die 0 vor der 10 siganilisiert dem System ich bin im erdgeschoss und die 10 sagt dem system welches stockwerk ich besuchen möchte
	wenn man ins den ersten rauf will dann 01 aber wenn ich vom ersten ins ergeschoss runter will reicht die 1
	in den 54. rauf dann 054 aber vom 54. runter dann reicht 54 usw.

bei falschem input wird das in der konsole ausgegeben

In der Angabe gabs 2 Lokigoptionen wie sich die Aufzüge verhalten soll ich habe die 2 Option mit zwischenstopps ausgewählt obwohl diese dazu geführt hat das es viele edge cases gibt
und damit einpaar mehr if verschachtelungen.
logik:
	 z. B. 0. -> 16. -> 24. -> 35. oder 48. -> 15. -> 0.

desweiteren stand in der Angabe das man immer den nächsten Freien aufzug nehmen sollte dies habe ich einbischen effezienter adaptiert
Das konzept ist:
	Ich nehme den nächsten Aufzug der mir am nächsten ist wenn er Frei ist oder ich ein Zwischen stopp für ihn wäre
		zb. jemand fährt vom 8 inserdgeschoss runter und im 4. drückt jemand wird der 4. im 4. mitgenommen falls dieser Aufzug der Aufzug am nächsten ist
		
		anderes bespiel falls ein Aufzug mit einem mitarbieter in den 50. rauffährt und momentan im 7. ist und im 8. jemand runter will 
		wird dieser Aufzug ignoriert da er nicht frei ist und die person im 8. kein zwischen stopp sein kann.
	
		in anderen worten werden leute mitgenommen falls der aufzug frei oder bereits in der gleichen richtung unterwegs ist und mich mitnehmen könnte

	und es wurden natührlich die edge cases bedacht
		wie falls ein mitarbeiter mehrmals drückt (mehrmals hintereinander die gleiche eingabe tätigen) wird nur ein aufzug hingeschickt
		oder falls ein lift im erdgeschoss zuerst im 5. soll aber dann noch jemand miteinsteigt und in den 4. will das er zuerst im 4. stoppt und dann im 5ten und nicht umgekehrt
		(reihenfolge der knöpfe also nicht wichitg)

	falls ein Aufzug den mitarbeiter zu seinem Ziel bringt bleibt er wo er ist bis er wieder gebraucht wird
	werden mitarbieter aufgesammelt kommt der status COLLECT wird jemand rausgelassen kommt der Status DROP
	Es gibt auch die Statuse UP und DOWN je nachdem in welche richtung der Aufzug momentan fährt
	
	falls alle Aufzüge beschäftigt und keiner mich mitnehmen kann zb. ich will runter aber alle fahren rauf dann wartet meine Anfrage solange bis jemand mich abholen kann

Die Anfragen gelten für das gesamte System und nicht für die Aufzüge
Der Zustand der Aufzüge wird simmuliert und ausgegeben dies wird erreicht in dem 2 Threads erstellt werden
	Thread 1: zuständig für die Eingabe und die weiterleitung ans System um diese einen Aufzug zuzuteilen
	Thread 2: simmuliert die Aufzug Schriite und zustände und gibt diese in der Konsole aus

Sollte Das Prgramm in einer IDE Ausgeführt werden werden die alten Ausgaben nicht gelöscht sondern nur nach oben verschoben
damit die alten ausgaben gelöscht werden muss das Programm in der Konsole gestartet werden

um das program zu schließen gibt man den buchstaben q ein

Viel Spass beim rauf und runter Fahren.