package it.polito.tdp.rivers.model;

import java.time.LocalDate;
import java.util.Collection;
import java.util.PriorityQueue;
import java.util.concurrent.ForkJoinPool.ManagedBlocker;

public class Simulator {
	// CODA DEGLI EVENTI
	private PriorityQueue<Flow> queue;

	// PARAMETRI DI SIMULAZIONE

	private LocalDate day;
	private double fOutMin;
	private double capienzaTotale;

	// MODELLO DEL MONDO
	private double ratioFoFin = 0.8;
	private double ratioCiCmax = 0.5;
	private double capienzaAttuale;
	private double probFluxExtra = 0.05;
	private double ratioFluxExtra = 10;
	private double fluxExtra;

	// VALORI DA CALCOLARE
	private int giorniSenzaIrrigazioneMinima ;
	private double occupazioneMedia;
	private int numeroEventi;
	private double sommaOccupazioni;

	// METODI PER IMPOSTARE I PARAMETRI
	
	public void setCapienza(double capienzaTotale) {
		this.capienzaTotale = capienzaTotale;
		capienzaAttuale = capienzaTotale*ratioCiCmax;
	}

	public void setf(double fmed) {
		this.fOutMin = fmed*ratioFoFin;
		fluxExtra = fOutMin*ratioFluxExtra;
	}
	
	public void ImportaEventi(Collection <Flow> eventi) {
		queue = new PriorityQueue<>(eventi);
	}
	
	// METODI PER RESTITUIRE I RISULTATI
	public int getGiorniSenzaIrrigazioneMinima() {
		return giorniSenzaIrrigazioneMinima;
	}

	public double getOccupazioneMedia() {
		return occupazioneMedia;
	}
	

	// SIMULAZIONE VERA E PROPRIA

	public void run() {
		giorniSenzaIrrigazioneMinima = numeroEventi = 0;
		occupazioneMedia = sommaOccupazioni = 0.0;
		while(!this.queue.isEmpty()) {
			Flow e = this.queue.poll();
			day = e.getDay();
			//				System.out.println(e);
			processEvent(e);
		}
		occupazioneMedia = sommaOccupazioni / numeroEventi;
	}

		private void processEvent(Flow e) {
			double fOut = fOutMin;
			if (Math.random() < probFluxExtra) {
				fOut = fluxExtra;
				System.out.println(e.getDay()+" - Flusso extra!");
			}
			
			capienzaAttuale += (e.getFlow()-fOut)*(60*60*24);
			System.out.println(String.format("capienza-pre: %.0f", capienzaAttuale));
			if (capienzaAttuale > capienzaTotale) {
				double tracimazione = capienzaAttuale - capienzaTotale;
				capienzaAttuale = capienzaTotale;
				System.out.println(e.getDay()+" - Tracimazione di "+String.format("%.0f", tracimazione));
			}
			
			if (capienzaAttuale < 0) {
				double debito = 0 - capienzaAttuale;
				capienzaAttuale = 0;
				System.out.println(e.getDay()+" - Mancanza per "+String.format("%.0f", debito));
				giorniSenzaIrrigazioneMinima++;
			}
			System.out.println(String.format("capienza-post: %.0f", capienzaAttuale));

			numeroEventi++;
			sommaOccupazioni += capienzaAttuale;
		}

	}
