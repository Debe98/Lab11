package it.polito.tdp.rivers.model;

import java.util.*;

import it.polito.tdp.rivers.db.RiversDAO;

public class Model {

	Map <Integer, River> fiumi;
	RiversDAO dao;
	
	public Model() {
		super();
		fiumi = new HashMap<>();
		dao = new RiversDAO();
	}
	
	public List <River> getRivers() {
		List <River> fiumiTutti = dao.getAllRivers();
		for (River r : fiumiTutti) {
			if (!fiumi.containsKey(r.getId())) {
				fiumi.put(r.getId(), r);
			}
		}
		return new LinkedList <> (fiumi.values());
	}

	public DatiFiume getDatiFiume(River river) {
		return dao.getDatiFiume(river);
	}

	public Object[] simulazione(River river, int k) {
		DatiFiume dati = getDatiFiume(river);
		double q = k*dati.getFlussoMedio()*30*(60*60*24);
		
		Simulator simulazione = new Simulator();
		simulazione.setCapienza(q);
		simulazione.setf(dati.getFlussoMedio());
		simulazione.ImportaEventi(dao.getMisurazioni(river));
		simulazione.run();
		Object[] result = {simulazione.getGiorniSenzaIrrigazioneMinima(), simulazione.getOccupazioneMedia()/q};
		return result;
	}
	
	
	
}
