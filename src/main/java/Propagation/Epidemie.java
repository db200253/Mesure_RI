package Propagation;

<<<<<<< HEAD
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
=======
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

>>>>>>> master
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

public class Epidemie {
  
    final int NOTHING = 0;
    final int ALEA = 1;
    final int SELECT = 2;
<<<<<<< HEAD
    final double TRANSMISSION_PROBABILITY = 0.142857143;
    final double RECOVERY_PROBABILITY = 0.071428571;
    final double IMMUNE_PROBABILITY = 0.5;

    private static Graph g;
    private int immune;
    private String of;
    private List<Node> contamine;
    private List<Node> immunise;
=======
    final double TRANSMISSION_PROBABILITY = 1/7;
    final double RECOVERY_PROBABILITY = 1/14;

    private static Graph g;
    @SuppressWarnings("unused")
    private int immune;
    private List<Node> contamine;
>>>>>>> master

    public Epidemie(Graph graph, int immune) {
        
        g = graph;
        
<<<<<<< HEAD
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
=======
        switch(immune) {
          case 0 : this.immune = NOTHING;
          case 1 : this.immune = ALEA;
          case 2 : this.immune = SELECT;
        }
        
        contamine = new ArrayList<>();
>>>>>>> master
    }

    public void runSimulation(int initialInfectedNodes, int simulationSteps) {
        initializeSimulation(initialInfectedNodes);

        for (int step = 0; step < simulationSteps; ++step) {
<<<<<<< HEAD
          
            int ci = countInfected();
            System.out.println("Step " + step + ": Infected Count = " + ci);
            
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(of))) {
              
              writer.write(step + "," + ci + "\n");
            } catch (IOException e) {
              
              e.printStackTrace();
            }
            
            updateEpidemicState();
        }
        
        System.out.println("Step " + simulationSteps + ": Infected Count = " + countInfected());
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(of))) {
          
          writer.write(simulationSteps + "," + countInfected() + "\n");
        } catch (IOException e) {
          
          e.printStackTrace();
=======
            updateEpidemicState();
            System.out.println("Step " + step + ": Infected Count = " + countInfected());
>>>>>>> master
        }
    }

    private void initializeSimulation(int initialInfectedNodes) {
        Random random = new Random();

        for (int i = 0; i < initialInfectedNodes; ++i) {
            int randomNode = random.nextInt(g.getNodeCount());
            g.getNode(randomNode).setAttribute("infected", true);
            contamine.add(g.getNode(randomNode));
        }
        
        for (Node node : g) {
<<<<<<< HEAD
          
            if(immune == ALEA) {
              
              if(!(node.hasAttribute("infected")) && Math.random() < IMMUNE_PROBABILITY) {
                
                node.setAttribute("immune", true);
                immunise.add(node);
              }
            }
            
            if(!(node.hasAttribute("infected")) && !(immunise.contains(node))) {
=======
            
            if(!(node.hasAttribute("infected"))) {
>>>>>>> master
              
              node.setAttribute("infected", false);
            }
        }
    }

    private void updateEpidemicState() {
        for (Node node : g) {
            boolean infected = (boolean) node.getAttribute("infected");

<<<<<<< HEAD
            if(immune == NOTHING) {
              
              if (infected) {
=======
            if (infected) {
>>>>>>> master
                for (Node neighbor : getNeighbours(node)) {
                  
                    boolean neighborInfected = (boolean) neighbor.getAttribute("infected");
                    
<<<<<<< HEAD
                    if (!neighborInfected && Math.random() < TRANSMISSION_PROBABILITY) {
=======
                    if (!neighborInfected && getRand() < TRANSMISSION_PROBABILITY) {
>>>>>>> master
                      
                        neighbor.setAttribute("infected", true);
                        contamine.add(neighbor);
                    }
                }

<<<<<<< HEAD
                if (Math.random() < RECOVERY_PROBABILITY) {
=======
                if (getRand() < RECOVERY_PROBABILITY) {
>>>>>>> master
                  
                    node.setAttribute("infected", false);
                    contamine.remove(node);
                }
<<<<<<< HEAD
              }
            } else if (immune == ALEA) {
              
              if (infected) {
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
=======
>>>>>>> master
            }
        }
    }

    private int countInfected() {
        
      return contamine.size();
    }
    
    private static List<Node> getNeighbours(Node n) {
      
<<<<<<< HEAD
      List<Edge> le = new ArrayList<>();
      
      for(Edge e : n) {
        
        le.add(e);
      }
      
=======
      List<Edge> le = n.leavingEdges().collect(Collectors.toList());
>>>>>>> master
      List<Node> voisins = new ArrayList<>();
      
      for(Edge e : le) {
        
        voisins.add(e.getOpposite(n));
      }
      
      return voisins;
    }
<<<<<<< HEAD

    public static void go(Graph g, int step, int immune) {

=======
    
    public static double getRand() {
      
      return Math.random();
    }

    public static void go(Graph g, int step, int immune) {

        System.out.println("coucou");
>>>>>>> master
        Epidemie simulation = new Epidemie(g, immune);
        simulation.runSimulation(1, step);
    }
}
