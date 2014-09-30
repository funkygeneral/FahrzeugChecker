package com.funkygeneral.fahrzeugchecker;

public class GrunerException extends Exception {
	public Fahrzeug fahrzeug;
	public String message;
	public int code = 0;
	public int anzahl_Reifen;
	
	public GrunerException(String message, Fahrzeug fahrzeug) {
		super(message);
		this.message = message;
		this.fahrzeug = fahrzeug;
		
	 }
	
	public String errorMessage() {
		String errorMsg = this.message + this.fahrzeug.anzahl_Reifen;
		return errorMsg;
	}
}
