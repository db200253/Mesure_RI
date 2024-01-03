package Propagation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
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
    private static List<Node> immunise;
    private int contaminable = 0;

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
        DecimalFormat df = new DecimalFormat("#.##");
        //System.out.println("Degre moyen pour immune : " + immune + " = " + getDegre(g));
        
        //System.out.println("immune = " + immune);
        //getet();

        for (int step = 0; step < simulationSteps; ++step) {
          
            double ci = countInfected();
            String c = df.format(ci);
            System.out.println("Step " + step + ": Infected Count = " + c + "%");
            
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(of, true))) {
              
              writer.write(step + "," + c + "\n");
            } catch (IOException e) {
              
              e.printStackTrace();
            }
            
            updateEpidemicState();
        }
        
        double ci = countInfected();
        String c = df.format(ci);
        
        System.out.println("Step " + simulationSteps + ": Infected Count = " + c + "%");
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(of, true))) {
          
          writer.write(simulationSteps + "," + c + "\n");
        } catch (IOException e) {
          
          e.printStackTrace();
        }
    }

    private void initializeSimulation(int initialInfectedNodes) {
    	
    	contaminable = g.getNodeCount();
    	
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
        
        contaminable -= immunise.size();
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

    private double countInfected() {
        
      return (double)contamine.size() * 100.0 / (double)contaminable ;
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
    
    /**
     * 
     * @param g
     * @return degre moyen du graphe
     * Pour immune = nothing -> dm = 6.62
     * Pour immune = alea -> dm = 3.31
     * Pour immune = select -> dm = 2.97
     * La différence entre alea et select provient du fait que dans alea -> choix du node immunisé est random donc quasi uniforme
     * dans select -> choix d'un voisin, les nodes ayant un degré superieur ont plus de chance d'être immunisés
     */
    private static double getDegre(Graph g) {
    	
    	double degre = 0.0;
    	
    	for(Node n : g) {
    		
    		List<Node> voisins = getNeighbours(n);
    		
    		for(Node v : voisins) {
    			
    			if(!(immunise.contains(v))) {
    				
    				degre++;
    			}
    		}
    	}
    	
    	degre /= g.getNodeCount();
    	
    	return degre;
    }
    
    private static int[] getDegreDistrib(Graph g) {
    	
    	int[] distrib = new int[350];
    	for(int i = 0; i < distrib.length; ++i) {
    		
    		distrib[i] = 0;
    	}
    	
    	for(Node n : g) {
    		
    		if(!immunise.contains(n)) {
    			
    			int degre = 0;
        		
        		List<Node> voisins = getNeighbours(n);
        		
        		for(Node v : voisins) {
        			
        			if(!(immunise.contains(v))) {
        				
        				degre++;
        			}
        		}
        		
        		distrib[degre]++;
    		}
    	}
    	
    	return distrib;
    }
    
    /**
     * immune = alea -> seuil épidémique = 0.073
     * immune = select -> seuil épidémique = 0.204
     */
    private static void getet() {
        
        int[] distrib = getDegreDistrib(g);
        
        double r = 0.0;
        double d = 0.0;
        
        for(int i = 1; i < distrib.length; ++i) {
          
          r += i * i * distrib[i];
          d += distrib[i];
        }
        
        double avgDegree = getDegre(g);
        double sk = r/d;
        
        System.out.println("<k²> = " + sk);
        System.out.println("Seuil épidémique = " + avgDegree/sk);
    }

    public static void go(Graph g, int step, int immune) {

        Epidemie simulation = new Epidemie(g, immune);
        simulation.runSimulation(1, step);
    }
}
