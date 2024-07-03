package it.polito.tdp.gosales.model;

import java.util.Objects;

public class Arco {
	private Products p1;
	private Products p2;
	private double s1;
	private double s2;
	public Arco(Products p1, Products p2, double s1, double s2) {
		super();
		this.p1 = p1;
		this.p2 = p2;
		this.s1 = s1;
		this.s2 = s2;
	}
	@Override
	public int hashCode() {
		return Objects.hash(p1, p2, s1, s2);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Arco other = (Arco) obj;
		return Objects.equals(p1, other.p1) && Objects.equals(p2, other.p2)
				&& Double.doubleToLongBits(s1) == Double.doubleToLongBits(other.s1)
				&& Double.doubleToLongBits(s2) == Double.doubleToLongBits(other.s2);
	}
	public Products getP1() {
		return p1;
	}
	public void setP1(Products p1) {
		this.p1 = p1;
	}
	public Products getP2() {
		return p2;
	}
	public void setP2(Products p2) {
		this.p2 = p2;
	}
	public double getS1() {
		return s1;
	}
	public void setS1(double s1) {
		this.s1 = s1;
	}
	public double getS2() {
		return s2;
	}
	public void setS2(double s2) {
		this.s2 = s2;
	}
	
	
	

}
