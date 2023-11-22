package Mesure_RI;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

import org.graphstream.algorithm.Toolkit;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.stream.file.FileSource;
import org.graphstream.stream.file.FileSourceEdge;

public class Main {
  
  static Graph g = new DefaultGraph("Graphe");
  
  public static void main( String[] args ) {
    
    System.setProperty("org.graphstream.ui", "swing");
    String filePath = "src/main/ressources/com-dblp.ungraph.txt";
    FileSource fs = new FileSourceEdge();
    
    fs.addSink(g);
    
    try {
      
      fs.readAll(filePath);
    } catch(IOException e) {
      
      e.printStackTrace();
    } finally {
      
      printUsefullDatas();
      System.out.println();
      printIfConnnected();
      getDegreeDistribution();
      fs.removeSink(g);
    }
  }
  
  /**
   * Question 2 sur les données
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
  
  /**
   * Question 3 sur la connexité
   */
  private static void printIfConnnected() {
    
    boolean a = Toolkit.isConnected(g);
    
    if(a) 
      System.out.println("Le réseau est connexe");
    else 
      System.out.println("Le réseau n'est pas connexe");
  }
  
  private static void getDegreeDistribution() {
    
    String outputFile = "src/main/ressources/degreeDistribution.csv";
    
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {

      int[] degreeDistribution = Toolkit.degreeDistribution(g);

      for (int i = 0; i < degreeDistribution.length; i++) {
        
        double normalizedValue = (double) degreeDistribution[i] / g.getNodeCount();
        writer.write(i + "," + String.format(Locale.US, "%.8f", normalizedValue) + "\n");
      }

    } catch (IOException e) {
      
        e.printStackTrace();
    }
  }
}
