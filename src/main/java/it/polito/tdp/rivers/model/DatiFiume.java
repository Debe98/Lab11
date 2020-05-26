package it.polito.tdp.rivers.model;

import java.time.LocalDate;

public class DatiFiume {
	private LocalDate primaMisurazione;
	private LocalDate ultimaMisurazione;
	private int Totmisurazioni;
	private double flussoMedio;
	
	public LocalDate getPrimaMisurazione() {
		return primaMisurazione;
	}
	
	public void setPrimaMisurazione(LocalDate primaMisurazione) {
		this.primaMisurazione = primaMisurazione;
	}
	
	public LocalDate getUltimaMisurazione() {
		return ultimaMisurazione;
	}
	
	public void setUltimaMisurazione(LocalDate ultimaMisurazione) {
		this.ultimaMisurazione = ultimaMisurazione;
	}
	
	public int getTotmisurazioni() {
		return Totmisurazioni;
	}
	
	public void setTotmisurazioni(int totmisurazioni) {
		Totmisurazioni = totmisurazioni;
	}
	
	public double getFlussoMedio() {
		return flussoMedio;
	}
	
	public void setFlussoMedio(double flussoMedio) {
		this.flussoMedio = flussoMedio;
	}
	
	

}
