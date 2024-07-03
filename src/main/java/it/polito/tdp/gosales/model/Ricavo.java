package it.polito.tdp.gosales.model;

import java.util.Objects;

public class Ricavo {
	private Products p1;
	private double ricavo;
	public Ricavo(Products p1, double ricavo) {
		super();
		this.p1 = p1;
		this.ricavo = ricavo;
	}
	public Products getP1() {
		return p1;
	}
	public void setP1(Products p1) {
		this.p1 = p1;
	}
	public double getRicavo() {
		return ricavo;
	}
	public void setRicavo(double ricavo) {
		this.ricavo = ricavo;
	}
	@Override
	public int hashCode() {
		return Objects.hash(p1, ricavo);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ricavo other = (Ricavo) obj;
		return Objects.equals(p1, other.p1) && Double.doubleToLongBits(ricavo) == Double.doubleToLongBits(other.ricavo);
	}
	

}
