package Mesure_RI;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import org.graphstream.algorithm.Toolkit;
import org.graphstream.graph.BreadthFirstIterator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
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
      getDistances();
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
  
  /**
   * Question 4 sur la distribution des degrés
   */
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
  
  /**
   * Question 5 sur la distribution des distances
   */
  private static void getDistances() {
    
    float[] distances = new float[1000];
    String outputFile = "src/main/ressources/distanceDistribution.csv";
    float distMoy = 0;
    
    ArrayList<Node> echantillon = new ArrayList<Node>();
    
    while (echantillon.size() != 1000) {
      
        Node noeud = Toolkit.randomNode(g);
        
        if(!(echantillon.contains(noeud))) echantillon.add(noeud);
    }
    
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {

      for(int i = 0; i < 1000; ++i) {
        
        BreadthFirstIterator bfs = new BreadthFirstIterator(echantillon.get(i));
        
        float tmp = 0;
        
        for(int j = 0; j < 1000; ++j) {
          
          bfs.next();
          tmp += bfs.getDepthMax();
        }
        
        tmp /= 1000.0;
        distances[i] = tmp;
        distMoy += tmp;
      }
      
      distMoy /= 1000;
      System.out.println("Distance moyenne du réseau : " + distMoy);
      
      for (int i = 0; i < distances.length; i++) {
        
        double normalizedValue = (double) distances[i];
        writer.write(String.format(Locale.US, "%.1f", normalizedValue) + "\n");
      }
    } catch (IOException e) {
      
      e.printStackTrace();
    }
  }
}