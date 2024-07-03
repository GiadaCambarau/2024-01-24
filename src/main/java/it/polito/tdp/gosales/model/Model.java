package it.polito.tdp.gosales.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

import it.polito.tdp.gosales.dao.GOsalesDAO;

public class Model {
	private GOsalesDAO dao;
	private List<Methods> metodi;
	private Map<Integer,Products> mappa;
	private Graph<Products, DefaultEdge> grafo;
	private List<Ricavo> ricavi;
	private List<PrdottiUscenti> best;
	private int max=0;
	public Model() {
		super();
		this.dao = new GOsalesDAO();
		this.metodi = dao.getMetodi();
		this.mappa = new HashMap<>();
		this.grafo = new SimpleDirectedGraph<>(DefaultEdge.class);
		this.ricavi = new ArrayList<>();
	}
	
	public List<Methods> getMetodi(){
		return this.metodi;
	}
	
	public void creaGrafo(int anno, Methods m, double s) {
		for (Products p: dao.getAllProducts()) {
			mappa.put(p.getNumber(), p);
		}
		Graphs.addAllVertices(this.grafo, dao.getProdotti(m, anno, mappa));
		this.ricavi = dao.getRicavo(anno, m, mappa);
		for (Ricavo r1 : ricavi) {
			for (Ricavo r2 : ricavi) {
				if (this.grafo.vertexSet().contains(r1.getP1()) && this.grafo.vertexSet().contains(r2.getP1())) {
					if (!r1.equals(r2)) {
						if (r1.getRicavo() > (r2.getRicavo()*(1+s))) {
							Graphs.addEdgeWithVertices(this.grafo, r2.getP1(), r1.getP1());
						}
						
					}
				}
			}
		}
		
	}
	public int getV() {
		return this.grafo.vertexSet().size();
		
	}
	public int getA() {
		return this.grafo.edgeSet().size();
	}
	
	public List<PrdottiUscenti> getTop(){
		List<PrdottiUscenti>result = new ArrayList<>();
		for (Products p: this.grafo.vertexSet()) {
			int uscenti = this.grafo.outDegreeOf(p);
			int entranti = this.grafo.inDegreeOf(p);
			if (uscenti ==0) {
				result.add(new PrdottiUscenti(p, entranti, getRicavo(p)));
			}
					
		}
		Collections.sort(result);
		return result;
	}

	private double getRicavo(Products p) {
		for (Ricavo r: this.ricavi) {
			if( r.getP1().equals(p)) {
				return r.getRicavo();
			}
		}
		return 0;
	}
	
	public List<PrdottiUscenti> trovaPercorso(){
		this.best = new ArrayList<>();
		this.max =0;
		Set<DefaultEdge> archi = this.grafo.edgeSet();
		
		for (Products p: this.grafo.vertexSet()) {
			if (this.grafo.inDegreeOf(p)==0) {
				List<PrdottiUscenti> parziale = new ArrayList<>();
				parziale.add(new PrdottiUscenti(p, this.grafo.outDegreeOf(p), getRicavo(p)));
				ricorsione(parziale, archi);
				parziale.remove(parziale.size()-1);
			}
		}
		return this.best;
	}

	private void ricorsione( List<PrdottiUscenti> parziale, Set<DefaultEdge> archi) {
		PrdottiUscenti p = parziale.get(parziale.size()-1);
		if (this.grafo.outDegreeOf(p.getP())==0) {
			if (parziale.size()>=max){
				this.best = new ArrayList<>(parziale);
				this.max = parziale.size();
			}
		}
		//normale
		Set<DefaultEdge> successori = this.grafo.outgoingEdgesOf(p.getP());
		for (DefaultEdge e : successori) {
			if (!archi.contains(e)) {
				Products nuovo = this.grafo.getEdgeTarget(e);
				parziale.add(new PrdottiUscenti(nuovo, this.grafo.outDegreeOf(nuovo), getRicavo(nuovo)));
				parziale.remove(parziale.size()-1);
			}
			
		}
		
	}
	
	
	
	
	
	
	
}
	

