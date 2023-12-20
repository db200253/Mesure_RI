package Propagation;

import java.io.IOException;

import org.graphstream.algorithm.Toolkit;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.stream.file.FileSource;
import org.graphstream.stream.file.FileSourceEdge;

public class Main {
  
  static Graph g = new DefaultGraph("Reseau");
 
  public static void main(String[] args) {
    
    System.setProperty("org.graphstream.ui", "swing");
    String filePath = "src/main/ressources/com-dblp.ungraph.txt";
    FileSource fs = new FileSourceEdge();
    
    fs.addSink(g);
    
    try {
      
      fs.readAll(filePath);
    } catch(IOException e) {
      
      e.printStackTrace();
    } finally {
      
      System.out.println("Graphe experimental : ");
      printUsefullDatas();
      System.out.println();
      getet();
      System.out.println("------------------------------------------------");
      simulEpid();
      fs.removeSink(g);
    }
  }
  
  /**
   * Question 1
   * Taux de propagation : proba de transmission (1/7) / taux de guérison (2/30) ~ 2
   * Seuil épidémique : k/k² = 6.62.../144.00... = 0.045...
   * Seuil théorique : 1/k+1 => 1/7.62... = 0,1311963
   * Seuil épidémique > taux de propagation donc la maladie progresse
   */
  
  private static void printUsefullDatas() {
    
    int nbNodes = g.getNodeCount();
    int nbEdges = g.getEdgeCount();
    double avgDegree = Toolkit.averageDegree(g);
    double clusterCoef = Toolkit.averageClusteringCoefficient(g);
    
    System.out.println("Nombre de noeuds dans le graphe : " + nbNodes);
    System.out.println("Nombre de liens dans le graphe : " + nbEdges);
    System.out.println("Degré moyen du graphe : " + avgDegree);
    System.out.println("Coefficient de clustering moyen du graphe : " + clusterCoef);
  }
  
  private static void getet() {
    
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
  
  private static void simulEpid() {
    
    Epidemie.go(g, 12, 0);
  }
}