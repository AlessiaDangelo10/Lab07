package it.polito.tdp.poweroutages.model;



import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

import java.util.List;
import java.util.Set;

import it.polito.tdp.poweroutages.DAO.PowerOutageDAO;

public class Model {
	
	private NercIdMap nercIdMap;
	
	PowerOutageDAO podao;

	private	List<Nerc> listan;

private List<PowerOutages> listaeventi;

private List<PowerOutages> listaeventi_filtri;

private List<PowerOutages> soluzione;

private int numPersoneCoinvolte;

public Model() {

	podao = new PowerOutageDAO();
	
	nercIdMap=new NercIdMap();
	
	listan=podao.getNercList(nercIdMap);
	
	System.out.println(listan);
	
	listaeventi=podao.getPowerOutageList(nercIdMap);
		
		System.out.println(listaeventi);
	}
	

//aggiungi il parametro nerc
	
public List<PowerOutages> getWorstCase(int numMaxYear,int numMaxOre,Nerc nerc){

	//inizializzazione
		soluzione=new ArrayList<>();
		numPersoneCoinvolte=0;
	
		//creo nuovo eventolista_filtri
		listaeventi_filtri=new ArrayList<>();
		
		for(PowerOutages evento:listaeventi) {
			if(evento.getNerc().equals(nerc)) {
			listaeventi_filtri.add(evento);
		}
	}
		
	Collections.sort(listaeventi_filtri);
	
	System.out.println("dimensione filtro elenco :"+ listaeventi_filtri.size());
	
	cerca(new ArrayList<PowerOutages>(), numMaxYear,numMaxOre);
	
	return soluzione;
	
	}
	public int sommaPersoneCoinvolte(List<PowerOutages> parziale) {
		int sum=0;
		for(PowerOutages evento:parziale) {
			sum+=evento.getInteressato();
			
		}
		return sum;
	}
	//anno piu recente-anno piu vecchio <= X
	private boolean checkMaxYear(List<PowerOutages> parziale,int numMaxYear) {
		
		if(parziale.size()>=2) {
			int y1=parziale.get(0).getAnno();
			int y2=parziale.get(parziale.size()-1).getAnno();
				
			if((y2-y1+1)>numMaxYear) {
						return false;
					}
		}
		return true;
	}
	//
	public int sommaOreDisservizio(List<PowerOutages> parziale ) {
		int sum=0;
		
		for(PowerOutages evento:parziale) {
			sum+=evento.getDurata_interruzione();
		}
		return sum;
	}
	//controllo che il num totale delle ore di disservizio sia minore del valore che inserisco di ore
	 private boolean checkMaxOreDisservizio(List<PowerOutages> parziale ,int numMaxOre) {
		
		 int sum=sommaOreDisservizio(parziale);
		 
		 if(sum>numMaxOre) {
			 return false;
			 
		 }
		 return true;
	 }
	 private void cerca(List<PowerOutages> parziale ,int numMaxYear,int numMaxOre) {
		 //Aggiorno la soluzione se necessario
		 if(sommaPersoneCoinvolte(parziale)>numPersoneCoinvolte) {
			 numPersoneCoinvolte=sommaPersoneCoinvolte(parziale);
			 soluzione=new ArrayList<PowerOutages>(parziale);
		
		 }
		 
		 for(PowerOutages evento:listaeventi_filtri) {
		
			 if(!parziale.contains(evento)) {
				 parziale.add(evento);
			//costruisco solo soluzione esatta
				 if(checkMaxYear(parziale,numMaxYear) && checkMaxOreDisservizio(parziale,numMaxOre)) {
					 cerca(parziale,numMaxYear,numMaxOre);
				 }
				 
				 parziale.remove(evento);
			 }
		 }
	 }

	 public List<Nerc> getNercList(){
		 return this.listan;
	 }
	public List<Integer> listaAnni(){
		Set<Integer> yearSet= new HashSet<Integer>();
		for(PowerOutages evento:listaeventi) {
			yearSet.add(evento.getAnno());
		}
		List<Integer> listaYear=new ArrayList<Integer>(yearSet);
		listaYear.sort(new Comparator<Integer>(){
		public int compare(Integer o1,Integer o2) {
			return o2.compareTo(o1);
		}
		});
		return listaYear;
	}
}
