package Propagation;

import java.io.IOException;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Queue;
import org.graphstream.algorithm.Toolkit;
import org.graphstream.algorithm.generator.BarabasiAlbertGenerator;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.RandomGenerator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.file.FileSource;
import org.graphstream.stream.file.FileSourceEdge;

public class Main {
  
  static Graph g = new DefaultGraph("Reseau");
  static Graph g2 = new SingleGraph("Random");
  static Graph g3 = new SingleGraph("Barabasi-Albert");
 
  public static void main(String[] args) {
    
    System.setProperty("org.graphstream.ui", "swing");
    String filePath = "src/main/ressources/com-dblp.ungraph.txt";
    FileSource fs = new FileSourceEdge();
    
    fs.addSink(g);
    
    int numNodes = 317080;
    Generator gen = new RandomGenerator(6.62208890914917);
    
    gen.addSink(g2);
    gen.begin();
    
    for (int i = 0; i < numNodes; i++) {
        
        gen.nextEvents();
    }
    gen.end();
    
    int m = 6;
    
    Generator gen2 = new BarabasiAlbertGenerator(m);
    
    gen2.addSink(g3);
    gen2.begin();
    
    for (int i = 0; i < numNodes; i++) {
      
        gen2.nextEvents();
    }
    gen2.end();
    
    /*On rend g2 connexe*/
    connect(g2);
    
    try {
      
      fs.readAll(filePath);
    } catch(IOException e) {
      
      e.printStackTrace();
    } finally {
      
      System.out.println("Graphe experimental : ");
      printUsefullDatas(g);
      getet(g);
      System.out.println("------------------------------------------------");
      simulEpid(g, 0);
      System.out.println("------------------------------------------------");
      System.out.println("Graphe aléatoire : ");
      printUsefullDatas(g2);
      getet(g2);
      System.out.println("------------------------------------------------");
      simulEpid(g2, 1);
      System.out.println("------------------------------------------------");
      System.out.println("Graphe de Barabasi-Albert : ");
      printUsefullDatas(g3);
      getet(g3);
      System.out.println("------------------------------------------------");
      simulEpid(g3, 2);
      System.out.println("------------------------------------------------");
      
      fs.removeSink(g);
      gen.removeSink(g2);
      gen2.removeSink(g3);
    }
  }
  
  /**
   * Question 1
   * Taux de propagation : proba de transmission (1/7) / taux de guérison (2/30) ~ 2
   * Seuil épidémique : k/k² = 6.62.../144.00... = 0.045...
   * Seuil théorique : 1/k+1 => 1/7.62... = 0,1311963
   * Seuil épidémique < taux de propagation donc la maladie progresse
   */
  
  private static void printUsefullDatas(Graph g) {
    
    int nbNodes = g.getNodeCount();
    int nbEdges = g.getEdgeCount();
    double avgDegree = Toolkit.averageDegree(g);
    double clusterCoef = Toolkit.averageClusteringCoefficient(g);
    
    System.out.println("Nombre de noeuds dans le graphe : " + nbNodes);
    System.out.println("Nombre de liens dans le graphe : " + nbEdges);
    System.out.println("Degré moyen du graphe : " + avgDegree);
    System.out.println("Coefficient de clustering moyen du graphe : " + clusterCoef);
  }
  
  private static void getet(Graph g) {
    
    int[] distrib = Toolkit.degreeDistribution(g);
    
    double r = 0.0;
    double d = 0.0;
    
    for(int i = 1; i < distrib.length; ++i) {
      
      r += i * i * distrib[i];
      d += distrib[i];
    }
    
    double avgDegree = Toolkit.averageDegree(g);
    double sk = r/d;
    
    System.out.println("<k²> = " + sk);
    System.out.println("Seuil épidémique = " + avgDegree/sk);
  }
  
  private static void simulEpid(Graph g, int modele) {
    
	Epidemie.go(g, 90, modele, 0);
    clearGraph(g);
    System.out.println();
    Epidemie.go(g, 90, modele, 1);
    clearGraph(g);
    System.out.println();
    Epidemie.go(g, 90, modele, 2);
  }
  
  private static void clearGraph(Graph g) {
	  
	  for(Node n : g) {
		  
		  n.setAttribute("infected", false);
		  n.setAttribute("immune", false);
	  }
  }
  
  private static void connect(Graph g) {
	    Set<Node> visitedNodes = new HashSet<>();
	    List<Node> allNodes = new ArrayList<>();
	    
	    for(Node n: g) allNodes.add(n);

	    // Identifier les composantes connexes avec un bfs
	    List<List<Node>> connectedComponents = new ArrayList<>();
	    for (Node node : allNodes) {
	        if (!visitedNodes.contains(node)) {
	            List<Node> component = bfs(node, visitedNodes);
	            connectedComponents.add(component);
	        }
	    }

	    // Connecter les composantes connexes
	    if (connectedComponents.size() > 1) {
	        for (int i = 1; i < connectedComponents.size(); i++) {
	            Node node1 = connectedComponents.get(i - 1).get(0);
	            Node node2 = connectedComponents.get(i).get(0);
	            g.addEdge(node1.getId() + "_" + node2.getId(), node1, node2);
	        }
	    }
	}
  
  	private static List<Node> bfs(Node startNode, Set<Node> visitedNodes) {
	    List<Node> component = new ArrayList<>();
	    Queue<Node> queue = new LinkedList<>();
	    queue.offer(startNode);
	    visitedNodes.add(startNode);

	    while (!queue.isEmpty()) {
	        Node currentNode = queue.poll();
	        component.add(currentNode);

	        for (Node neighbor : Epidemie.getNeighbours(currentNode)) {
	            if (!visitedNodes.contains(neighbor)) {
	                queue.offer(neighbor);
	                visitedNodes.add(neighbor);
	            }
	        }
	    }

	    return component;
	}
}