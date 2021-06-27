package it.polito.tdp.imdb.model;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.imdb.db.ImdbDAO;

public class Model {
	
	private Graph<Actor, DefaultWeightedEdge> grafo;
	private ImdbDAO dao;
	private Map<Integer, Actor> idMap;
	
	public Model() {
		this.dao = new ImdbDAO();
	}
	
	public void creaGrafo(String genere) {
		
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		this.idMap = new HashMap<>();
		this.dao.getAttoriPerGenere(genere, idMap);
		Graphs.addAllVertices(this.grafo, this.idMap.values());
		
		for (Adiacenza a : this.dao.getArchi(idMap, genere) ) {
			Graphs.addEdge(this.grafo, a.getA1(), a.getA2(), a.getPeso());
		}
		
	}
	
	public int getNumVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int getNumArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public Collection<Actor> getVertici() {
		List<Actor> risultato = new LinkedList<>(this.idMap.values());
		Collections.sort(risultato);
		return risultato;
	}
	
	public List<Actor> getConnessi(Actor a) {
		ConnectivityInspector<Actor, DefaultWeightedEdge> ispettore = new ConnectivityInspector<>(grafo);
		List<Actor> risultato = new LinkedList<>();
		for (Actor attore : ispettore.connectedSetOf(a)) {
			if (!attore.equals(a))
				risultato.add(attore);
		}
		Collections.sort(risultato);
		return risultato;
	}
	
	public List<String> getGeneri() {
		return this.dao.getGeneri();
	}

}
