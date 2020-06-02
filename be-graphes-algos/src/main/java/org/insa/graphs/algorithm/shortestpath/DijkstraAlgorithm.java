package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;
import java.util.Collections;

import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.algorithm.utils.Label;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

	private int SommetExplores = 0;	
	private int SommetMarques = 0;
    
    @Override
    protected ShortestPathSolution doRun() {
    	
    	//int maxTas = 0;
    	
        final ShortestPathData data = getInputData();
        
        ShortestPathSolution solution = null;
        
        BinaryHeap<Label> labelHeap = new BinaryHeap<Label>();
        ArrayList<Label> tabLabel = new ArrayList<Label>();
        
        Label labelCourant = newLabel();
        Label labelFils = newLabel();
        
        /* On initialise tabLabel avec tous les label du graph */
        for (int i = 0; i < data.getGraph().size(); i++) {
        	tabLabel.add(newLabel(data.getGraph().get(i),data));
        }
        
        /* On récupère l'id de l'origine et on fixe son cout à 0 dans tabLabel */
        tabLabel.get(data.getOrigin().getId()).setCost(0);
        
        /* on initialise le labelCourant au sommet d'origine */
        labelCourant = tabLabel.get(data.getOrigin().getId());
        
        /* puis on met ce sommet dans le tas */
        labelHeap.insert(labelCourant);
        
        // Notify observers about the first event (origin processed).
        notifyOriginProcessed(data.getOrigin());
        
        
        /* Tant que le tas n'est pas vide et tant que la destination n'a pas été marquée */
        while(!labelHeap.isEmpty() && !labelCourant.getSommetCourant().equals(data.getDestination())) {
        	
        	labelCourant = labelHeap.deleteMin();
        	this.SommetExplores++;
        	
        	/* récupère le label courant dans tabLabel puis le marque comme étant visité */
        	tabLabel.get(labelCourant.getSommetCourant().getId()).setMarque();
        	
        	/* print le cout du label marqué actuel */
        	System.out.println(labelCourant.getCost());
        	
        	this.SommetMarques++;
        	
        	/* print le nombre de sommets marqués */
        	System.out.println(SommetMarques);
        	
        	/* Permet de passer en revu tous les arcs qui sont successeurs de labelCourant */
        	for (Arc a : labelCourant.getSommetCourant().getSuccessors()) {
        		
        		/* on récupère la destination du labelCourant dans tabLabel et on initialise labelFils avec */
        		labelFils = tabLabel.get(a.getDestination().getId());
        		
            	this.SommetExplores++;
            	
            	/* print le nombre de sommets explorés */
            	System.out.println(SommetExplores);

                // Small test to check allowed roads...
                if (!data.isAllowed(a)) {
                    continue;
                }
                
        		/* On vérifie que le successeurs potentiel de labelCourant (donc labelFils) n'a pas déjà été marqué */
        		if (!labelFils.getMarque()) {
        			
        			/* Mets en couleurs les noeuds atteints lors de l'execution de l'algorithme sur la carte */
        			notifyNodeReached(a.getDestination());
        			
        			/* Mets en couleurs les noeuds marqués lors de l'execution de l'algorithme sur la carte */
        			notifyNodeMarked(a.getDestination());
        			
        			/* on vérifie que le cout du labelFils est bien supérieur ce qui signifie qu'on avance vers la destination */
        			if (labelFils.getCost() > (labelCourant.getCost() + data.getCost(a))) {
        				
        				/* Si c'est le cas on fixe le coup du fils au cout du label actuel */
        				labelFils.setCost(labelCourant.getCost() + data.getCost(a));
        				
        				/* Puis on le mets dans le tas */
        				labelHeap.insert(labelFils);
        				
        				/* on fixe le père du labelFils comme étant le successeur de labelCourant */
        				labelFils.setPere(a);
        				
        				/* Puis on le met dans le tableau de label */
        				tabLabel.set(a.getDestination().getId(), labelFils);
        				
        				/* On se sert de la méthode remove de binaryHeap parcequ'on ne l'a pas codé pour rien */
        				try {
        					labelHeap.remove(labelCourant);
        				}catch(Exception e) {
                            
                        }
        			}
        		}
        	}
        }
        
        
        // Destination has no predecessor, the solution is infeasible...
        if (tabLabel.get(data.getDestination().getId()).getPere() == null) {
            solution = new ShortestPathSolution(data, Status.INFEASIBLE);
        }
        else {
            // The destination has been found, notify the observers.
            notifyDestinationReached(data.getDestination());
            
            // Create the path from the array of predecessors...
            ArrayList<Arc> arcs = new ArrayList<>();
            Arc arc = tabLabel.get(data.getDestination().getId()).getPere();
            while (arc != null) {
                arcs.add(arc);
                arc = tabLabel.get(arc.getOrigin().getId()).getPere();
            }

            // Reverse the path...
            Collections.reverse(arcs);

            // Create the final solution
            solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(data.getGraph(), arcs));
            
        }
        
        return solution;
    }

    protected Label newLabel(Node node, ShortestPathData data) {
		return new Label(node);
	}
    
    protected Label newLabel() {
		return new Label();
	}
    
    public int getSommetExplores() {
    	return SommetExplores;
    }
    
    public int getSommetMarques() {
    	return SommetMarques;
    }
    
}
