package it.polito.tdp.poweroutages.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class PowerOutages implements Comparable <PowerOutages> {
 private int id;
 private Nerc nerc;
 private LocalDateTime avvio_interruzione,fine_interruzione;
 private int interessato;
 private long durata_interruzione;
 private int anno;
public PowerOutages(int id, Nerc nerc, LocalDateTime avvio_interruzione, LocalDateTime fine_interruzione,
		int interessato) {
	
	this.id = id;
	this.nerc = nerc;
	this.avvio_interruzione = avvio_interruzione;
	this.fine_interruzione = fine_interruzione;
	this.interessato = interessato;
	LocalDateTime tempoData=LocalDateTime.from(avvio_interruzione);
	this.durata_interruzione=tempoData.until(fine_interruzione, ChronoUnit.HOURS);
	this.anno=avvio_interruzione.getYear();
	
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public Nerc getNerc() {
	return nerc;
}
public void setNerc(Nerc nerc) {
	this.nerc = nerc;
}
public LocalDateTime getAvvio_interruzione() {
	return avvio_interruzione;
}
public void setAvvio_interruzione(LocalDateTime avvio_interruzione) {
	this.avvio_interruzione = avvio_interruzione;
}
public LocalDateTime getFine_interruzione() {
	return fine_interruzione;
}
public void setFine_interruzione(LocalDateTime fine_interruzione) {
	this.fine_interruzione = fine_interruzione;
}
public int getInteressato() {
	return interessato;
}
public void setInteressato(int interessato) {
	this.interessato = interessato;
}
public long getDurata_interruzione() {
	return durata_interruzione;
}
public void setDurata_interruzione(long durata_interruzione) {
	this.durata_interruzione = durata_interruzione;
}
public int getAnno() {
	return anno;
}
public void setAnno(int anno) {
	this.anno = anno;
}
@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + id;
	return result;
}
@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	PowerOutages other = (PowerOutages) obj;
	if (id != other.id)
		return false;
	return true;
}
public  int  compareTo ( PowerOutages  o ) {
	return this.getAvvio_interruzione().compareTo (o.getAvvio_interruzione());
}
@Override
public String toString() {
	return "PowerOutages [id=" + id + "]";
}

 
}
