package com.funkygeneral.fahrzeugchecker;

public class Fahrzeug {
	// Die Variable für die Anzahl der Reifen
	public int id;
	public String anzahl_Reifen;

	// Die Funktion welche die Anzahl der Reifen f¸r die Instanz ausgibt
	public void Anzahl_Reifen_ausgeben() throws GrunerException {
		if(Integer.getInteger(anzahl_Reifen) >= 5) { 
			// Wirft die Exception und gibt die Anzahl der Reifen als Message an GrunerException
			throw new GrunerException("Zuviele Reifen - nämlich: ", this);
		} 
	} 
}
