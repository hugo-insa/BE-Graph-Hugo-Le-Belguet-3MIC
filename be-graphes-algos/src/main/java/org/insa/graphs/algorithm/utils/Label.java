package org.insa.graphs.algorithm.utils;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;

public class Label implements Comparable<Label> {

	private Node SommetCourant;	
	private boolean marque;	
	private double cout;	
	private Arc pere;

	public Label() {
		this.SommetCourant = null;
		this.marque = false;
		this.cout = Double.POSITIVE_INFINITY;
		this.pere = null;	
	}
	
	public Label(Node node) {
		this.SommetCourant = node;
		this.marque = false;
		this.cout = Double.POSITIVE_INFINITY;
		this.pere = null;		
	}

	
	public double getCost() {
		return this.cout;
	}
	public Node getSommetCourant(){
		return this.SommetCourant;
	}
	public boolean getMarque() {
		return this.marque;
	}
	public Arc getPere() {
		return this.pere;
	}
	public double getTotalCost() {
		return this.cout;
	}
	
	
	public void setCost(double cout) {
		this.cout = cout;
	}
	public void setSommetCourant(Node node) {
		this.SommetCourant = node;
	}
	public void setMarque() {
		this.marque = true;
	}
	public void setPere(Arc a) {
		this.pere = a;
	}

	public int compareTo(Label l) {
		if (getTotalCost() < l.getTotalCost()) {
			return -1;
		}else if (getTotalCost() == l.getTotalCost()) {
			return 0;
		}else {
			return 1;
		}
	}

}
