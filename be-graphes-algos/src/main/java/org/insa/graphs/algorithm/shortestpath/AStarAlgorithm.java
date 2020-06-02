package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.algorithm.utils.Label;
import org.insa.graphs.algorithm.utils.LabelStar;
import org.insa.graphs.model.Node;

public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }
    
    @Override
    protected Label newLabel(Node node, ShortestPathData data) {
		return new LabelStar(node,data);
	}
    
    @Override
    protected Label newLabel() {
		return new LabelStar();
	}

}
