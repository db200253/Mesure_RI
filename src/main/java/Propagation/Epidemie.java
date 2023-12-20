package Propagation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

public class Epidemie {
  
    final int NOTHING = 0;
    final int ALEA = 1;
    final int SELECT = 2;
    final double TRANSMISSION_PROBABILITY = 1/7;
    final double RECOVERY_PROBABILITY = 1/14;

    private static Graph g;
    private int immune;
    private List<Node> contamine;

    public Epidemie(Graph graph, int immune) {
        
        g = graph;
        
        switch(immune) {
          case 0 : this.immune = NOTHING;
          case 1 : this.immune = ALEA;
          case 2 : this.immune = SELECT;
        }
        
        contamine = new ArrayList<>();
    }

    public void runSimulation(int initialInfectedNodes, int simulationSteps) {
        initializeSimulation(initialInfectedNodes);

        for (int step = 0; step < simulationSteps; ++step) {
            updateEpidemicState();
            System.out.println("Step " + step + ": Infected Count = " + countInfected());
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
            
            if(!(node.hasAttribute("infected"))) {
              
              node.setAttribute("infected", false);
            }
        }
    }

    private void updateEpidemicState() {
        for (Node node : g) {
            boolean infected = (boolean) node.getAttribute("infected");

            if (infected) {
                for (Node neighbor : getNeighbours(node)) {
                  
                    boolean neighborInfected = (boolean) neighbor.getAttribute("infected");
                    
                    if (!neighborInfected && getRand() < TRANSMISSION_PROBABILITY) {
                      
                        neighbor.setAttribute("infected", true);
                        contamine.add(neighbor);
                    }
                }

                if (getRand() < RECOVERY_PROBABILITY) {
                  
                    node.setAttribute("infected", false);
                    contamine.remove(node);
                }
            }
        }
    }

    private int countInfected() {
        
      return contamine.size();
    }
    
    private static List<Node> getNeighbours(Node n) {
      
      List<Edge> le = n.leavingEdges().collect(Collectors.toList());
      List<Node> voisins = new ArrayList<>();
      
      for(Edge e : le) {
        
        voisins.add(e.getOpposite(n));
      }
      
      return voisins;
    }
    
    public static double getRand() {
      
      return Math.random();
    }

    public static void go(Graph g, int step, int immune) {

        System.out.println("coucou");
        Epidemie simulation = new Epidemie(g, immune);
        simulation.runSimulation(1, step);
    }
}
