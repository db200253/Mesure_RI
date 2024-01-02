package Propagation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

public class Epidemie {
  
    final int NOTHING = 0;
    final int ALEA = 1;
    final int SELECT = 2;
    final double TRANSMISSION_PROBABILITY = 0.142857143;
    final double RECOVERY_PROBABILITY = 0.071428571;
    final double IMMUNE_PROBABILITY = 0.5;

    private static Graph g;
    private int immune;
    private String of;
    private List<Node> contamine;
    private List<Node> immunise;

    public Epidemie(Graph graph, int immune) {
        
        g = graph;
        
        if(immune == 0) {
          
          this.immune = NOTHING;
          this.of = "src/main/ressources/nothing/cases.csv";
        } else if(immune == 1) {
          
          this.immune = ALEA;
          this.of = "src/main/ressources/alea/cases.csv";
        } else {
          
          this.immune = SELECT;
          this.of = "src/main/ressources/select/cases.csv";
        }
        
        contamine = new ArrayList<>();
        immunise = new ArrayList<>();
    }

    public void runSimulation(int initialInfectedNodes, int simulationSteps) {
        initializeSimulation(initialInfectedNodes);

        for (int step = 0; step < simulationSteps; ++step) {
          
            int ci = countInfected();
            System.out.println("Step " + step + ": Infected Count = " + ci);
            
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(of, true))) {
              
              writer.write(step + "," + ci + "\n");
            } catch (IOException e) {
              
              e.printStackTrace();
            }
            
            updateEpidemicState();
        }
        
        System.out.println("Step " + simulationSteps + ": Infected Count = " + countInfected());
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(of, true))) {
          
          writer.write(simulationSteps + "," + countInfected() + "\n");
        } catch (IOException e) {
          
          e.printStackTrace();
        }
    }

    private void initializeSimulation(int initialInfectedNodes) {
    	
    	for(Node n: g) {
    		
    		n.setAttribute("infected", false);
    	}
    	
        Random random = new Random();

        for (int i = 0; i < initialInfectedNodes; ++i) {
            int randomNode = random.nextInt(g.getNodeCount());
            g.getNode(randomNode).setAttribute("infected", true);
            contamine.add(g.getNode(randomNode));
        }
        
        for (Node node : g) {
          
            if(immune == ALEA) {
              
              if(Math.random() < IMMUNE_PROBABILITY) {
                
                node.setAttribute("immune", true);
                immunise.add(node);
              }
            } else if (immune == SELECT) {
            	
            	if(Math.random() < IMMUNE_PROBABILITY) {
                    
                    List<Node> voisins = new ArrayList<>();
                    List<Edge> le = new ArrayList<>();
                    
                    for(Edge e : node) {
                        
                        le.add(e);
                    }
                    
                    for(Edge e : le) {
                        
                        voisins.add(e.getOpposite(node));
                    }
                    
                    int randomNode = random.nextInt(voisins.size());
                    int id = voisins.get(randomNode).getIndex();
                    g.getNode(id).setAttribute("immune", true);
                    immunise.add(g.getNode(id));
                }
            }
        }
        
        System.out.println(immunise.size());
    }

    private void updateEpidemicState() {
        for (Node node : g) {
            boolean infected = (boolean) node.getAttribute("infected");
            
            if (infected) {
	            if(immune == NOTHING) {
	              
	                for (Node neighbor : getNeighbours(node)) {
	                  
	                    boolean neighborInfected = (boolean) neighbor.getAttribute("infected");
	                    
	                    if (!neighborInfected && Math.random() < TRANSMISSION_PROBABILITY) {
	                      
	                        neighbor.setAttribute("infected", true);
	                        contamine.add(neighbor);
	                    }
	                }
	
	                if (Math.random() < RECOVERY_PROBABILITY) {
	                  
	                    node.setAttribute("infected", false);
	                    contamine.remove(node);
	                }
	            } else {
	             
	                for (Node neighbor : getNeighbours(node)) {
	                  
	                    boolean neighborInfected = (boolean) neighbor.getAttribute("infected");
	                    
	                    if (!neighborInfected && !(immunise.contains(neighbor)) && Math.random() < TRANSMISSION_PROBABILITY) {
	                      
	                        neighbor.setAttribute("infected", true);
	                        contamine.add(neighbor);
	                    }
	                }
	
	                if (Math.random() < RECOVERY_PROBABILITY) {
	                  
	                    node.setAttribute("infected", false);
	                    contamine.remove(node);
	                }
	            }
    		}
        }	
    }

    private int countInfected() {
        
      return contamine.size();
    }
    
    private static List<Node> getNeighbours(Node n) {
      
      List<Edge> le = new ArrayList<>();
      
      for(Edge e : n) {
        
        le.add(e);
      }
      
      List<Node> voisins = new ArrayList<>();
      
      for(Edge e : le) {
        
        voisins.add(e.getOpposite(n));
      }
      
      return voisins;
    }

    public static void go(Graph g, int step, int immune) {

        Epidemie simulation = new Epidemie(g, immune);
        simulation.runSimulation(1, step);
    }
}
