package it.polito.tdp.gosales.model;

import java.util.Objects;

public class PrdottiUscenti implements Comparable<PrdottiUscenti> {
	private Products p;
	private int uscenti;
	private double ricavo;
	public PrdottiUscenti(Products p, int uscenti, double ricavo) {
		super();
		this.p = p;
		this.uscenti = uscenti;
		this.ricavo = ricavo;
	}
	@Override
	public int hashCode() {
		return Objects.hash(p, ricavo, uscenti);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PrdottiUscenti other = (PrdottiUscenti) obj;
		return Objects.equals(p, other.p) && Double.doubleToLongBits(ricavo) == Double.doubleToLongBits(other.ricavo)
				&& uscenti == other.uscenti;
	}
	public Products getP() {
		return p;
	}
	public void setP(Products p) {
		this.p = p;
	}
	public int getUscenti() {
		return uscenti;
	}
	public void setUscenti(int uscenti) {
		this.uscenti = uscenti;
	}
	public double getRicavo() {
		return ricavo;
	}
	public void setRicavo(double ricavo) {
		this.ricavo = ricavo;
	}
	@Override
	public int compareTo(PrdottiUscenti o) {
		
		return o.uscenti-this.uscenti;
	}
	@Override
	public String toString() {
		return  p + " Entranti(" + uscenti + ")   ricavo=" + ricavo ;
	}
	
	
	
	

}
