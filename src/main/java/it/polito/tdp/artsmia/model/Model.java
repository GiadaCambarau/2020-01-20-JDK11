package it.polito.tdp.artsmia.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {
	private ArtsmiaDAO dao;
	private Map<Integer, Artists> mappa;
	private Graph<Artists, DefaultWeightedEdge> grafo;
	private List<String> ruoli;
	private List<Arco> archi;
	private List<Artists> best;
	private int max;
	private int peso;
	
	public Model() {
		this.dao = new ArtsmiaDAO();
		this.mappa = new HashMap<>();
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		this.ruoli = dao.getRoles();
		this.archi = new ArrayList<>();
	}
	
	public List<String> getRuoli(){
		return this.ruoli;
	}
	public void creaGrafo(String role) {
		List<Artists> vertici = dao.getVertici(role);
		Graphs.addAllVertices(this.grafo, vertici);
		for (Artists a : this.grafo.vertexSet()) {
			mappa.put(a.getId(), a);
		}
		this.archi = dao.getArchi(role, mappa);
		for (Arco a : archi) {
			if (this.grafo.containsVertex(a.getA1())&&this.grafo.containsVertex(a.getA2()) ) {
				Graphs.addEdge(this.grafo, a.getA1(), a.getA2(), a.getPeso());
			}
		}
		
	}
	public int getV() {
		return this.grafo.vertexSet().size();
		
	}
	public int getA() {
		return this.grafo.edgeSet().size();
	}
	
	public List<Arco> getConnessi(){
		
		Collections.sort(this.archi);
		return this.archi;
	}
	
	public List<Artists> trovaPercorso(int  artistID){
		boolean trovato = false;
		Artists partenza = null;
		this.peso =0;
		for (Artists a : this.grafo.vertexSet()) {
			if (a.getId() ==  artistID) {
				trovato = true;
				partenza = mappa.get(artistID);
			}
		}
		if (trovato== true) {
			this.best = new ArrayList<>();
			this.max =0;
			List<Artists> parziale = new ArrayList<>();
			List<Arco> archiDisponibili = new ArrayList<>(archi);
			parziale.add(partenza);
			ricosione(partenza,parziale,archiDisponibili);
			
			return this.best;
			
		}else {
			return null; 
		}
	}

	private void ricosione(Artists partenza, List<Artists> parziale, List<Arco> archiDisponibili) {
		//condizione di uscita 
		if (parziale.size()>= max) {
			this.best = new ArrayList<>(parziale);
			this.max = parziale.size();
			
		}
		Artists corrente = partenza;
		//caso normale
		for (Arco a : archiDisponibili) {
			if (a.getA1().equals(corrente)) {
				DefaultWeightedEdge e = this.grafo.getEdge(corrente, a.getA2());
				this.peso = (int) this.grafo.getEdgeWeight(e);
				if (parziale.contains(a.getA2())) {
					parziale.add(a.getA2());
					List<Arco> nuoviDisponibili = trovaArchiConPeso(peso);
					ricosione(a.getA2(),parziale, nuoviDisponibili);
					parziale.remove(parziale.size()-1);
				}
					
			}
		}
		
		
	}

	private List<Arco> trovaArchiConPeso(int peso) {
		List<Arco> nuovi = new ArrayList<>();
		for (Arco a : archi) {
			if (a.getPeso() == peso) {
				nuovi.add(a);
			}
		}
		return nuovi;
	}
	public int getPeso() {
		return this.peso;
	}
	
	
	

}
