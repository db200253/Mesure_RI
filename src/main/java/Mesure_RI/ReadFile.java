package Mesure_RI;

import java.io.IOException;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.stream.file.FileSource;
import org.graphstream.stream.file.FileSourceEdge;

public class ReadFile {
  
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
      
      fs.removeSink(g);
    }
  }
}
