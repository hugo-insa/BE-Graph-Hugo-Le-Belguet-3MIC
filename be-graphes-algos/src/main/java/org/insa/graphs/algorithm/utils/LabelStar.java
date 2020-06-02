package org.insa.graphs.algorithm.utils;

import org.insa.graphs.algorithm.AbstractInputData.Mode;
import org.insa.graphs.algorithm.shortestpath.ShortestPathData;
import org.insa.graphs.model.Node;

public class LabelStar extends Label{
	
private double coutEstime;
	
	public LabelStar() {
		super();
		this.coutEstime = Double.POSITIVE_INFINITY;
	}
	
	public LabelStar(Node node, ShortestPathData data) {
		super(node);
		if (data.getMode() == Mode.TIME) {
			double speed = data.getGraph().getGraphInformation().getMaximumSpeed();
			this.coutEstime = node.getPoint().distanceTo(data.getDestination().getPoint())/(speed*1000.0f/3600.0f);
		}else {
			this.coutEstime = node.getPoint().distanceTo(data.getDestination().getPoint());
		}
	}
	
	public double getTotalCost() {
		return (this.coutEstime + super.getCost());
	}

}
