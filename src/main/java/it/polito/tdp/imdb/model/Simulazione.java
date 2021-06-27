package it.polito.tdp.imdb.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

public class Simulazione {
	
	private int giorni;
	private List<Actor> attori;
	private Graph<Actor, DefaultWeightedEdge> grafo;
	
	private List<Actor> intervistati;
	private int giorniPausa;
	
	public void init(int giorni, List<Actor> attori, Graph<Actor, DefaultWeightedEdge> grafo) {
		this.giorni = giorni;
		this.attori = new LinkedList<>(attori);
		this.grafo = grafo;
		this.giorniPausa = 0;
		this.intervistati = new LinkedList<>();
	}
	
	public void run() {
		Random caso = new Random();
		Actor corrente = this.attori.get(caso.nextInt(attori.size()));
		this.attori.remove(corrente);
		this.intervistati.add(corrente);
		boolean stessoGenere = false;
		for (int i = 1; i<giorni; i++) {
			float numero = caso.nextFloat();
			if (stessoGenere) {
				if (caso.nextFloat()<0.9) {
					this.giorniPausa++;
					stessoGenere = false;
					continue;
				}
				stessoGenere = false;
			}
			if (numero<0.6) {
				corrente = this.attori.get(caso.nextInt(attori.size()));
				if (corrente.getGender().equals(this.intervistati.get(intervistati.size()-1).getGender())) {
					stessoGenere = true;
				}
				this.attori.remove(corrente);
				this.intervistati.add(corrente);
			} else {
				corrente = getMigliore(corrente);
				if (corrente.getGender().equals(this.intervistati.get(intervistati.size()-1).getGender())) {
					stessoGenere = true;
				}
				this.attori.remove(corrente);
				this.intervistati.add(corrente);
			}
		}
		
	}
	
	public Actor getMigliore(Actor a) {
		List<Actor> attori = Graphs.neighborListOf(grafo, a);
		int pesoMax = 0;
		Actor migliore = null;
		for (Actor ad : attori) {
			if (this.grafo.getEdgeWeight(this.grafo.getEdge(ad, a))>pesoMax) {
				migliore = ad;
				pesoMax = (int) this.grafo.getEdgeWeight(this.grafo.getEdge(ad, a));
			}
		}
		return migliore;
	}

	public List<Actor> getIntervistati() {
		return intervistati;
	}

	public int getGiorniPausa() {
		return giorniPausa;
	}

}
