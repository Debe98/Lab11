package it.polito.tdp.rivers.db;

import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.rivers.model.DatiFiume;
import it.polito.tdp.rivers.model.Flow;
import it.polito.tdp.rivers.model.River;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RiversDAO {

	public List<River> getAllRivers() {
		
		final String sql = "SELECT id, name FROM river";

		List<River> rivers = new LinkedList<River>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				rivers.add(new River(res.getInt("id"), res.getString("name")));
			}

			conn.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return rivers;
	}

	public DatiFiume getDatiFiume(River river) {
		final String sql = "SELECT COUNT(flow.id) AS tot, AVG(flow.flow) AS average\r\n" + 
				"FROM flow\r\n" + 
				"WHERE flow.river = ?;";
		DatiFiume dati = new DatiFiume();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, river.getId());
			ResultSet res = st.executeQuery();

			while (res.next()) {
				dati.setFlussoMedio(res.getDouble("average"));
				dati.setTotmisurazioni(res.getInt("tot"));
			}
			
			List <Flow> misurazioni= getMisurazioni(river);
			dati.setPrimaMisurazione(misurazioni.get(0).getDay());
			dati.setUltimaMisurazione(misurazioni.get(misurazioni.size()-1).getDay());

			conn.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return dati;
	}

	public List<Flow> getMisurazioni(River river) {

		final String sql = "SELECT *\r\n" + 
				"FROM flow\r\n" + 
				"WHERE flow.river = ?\r\n" + 
				"ORDER BY flow.day ASC;";

		List<Flow> misurazioni = new LinkedList<>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, river.getId());
			ResultSet res = st.executeQuery();

			while (res.next()) {
				misurazioni.add(new Flow(res.getDate("day").toLocalDate(), res.getDouble("flow"),  river));
			}

			conn.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return misurazioni;
	}
}
