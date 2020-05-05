package it.polito.tdp.poweroutages.model;

import java.util.HashMap;
import java.util.Map;

public class NercIdMap {
	private Map<Integer,Nerc> mappa;
	public NercIdMap() {
		mappa=new HashMap<>();
		
	}
	public Nerc get(int id) {
		return mappa.get(id);
	}
	public Nerc get(Nerc nerc) {
		Nerc vecchio=mappa.get(nerc.getId());
		if(vecchio==null) {
			mappa.put(nerc.getId(),nerc);
			return nerc;
		}else
			return vecchio;
	}
	public void put(int id,Nerc nerc) {
		mappa.put(id, nerc);
	}

}
