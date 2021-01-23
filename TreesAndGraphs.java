import org.graalvm.compiler.graph.Graph;
import org.graalvm.compiler.graph.Node;

public class TreesAndGraphs {
   public static void main(String[] args) {
      System.out.println("Hello World");
   }

   /*
 Given a directed graph and two nodes, design an algorithm to find out whether there is a route from S to E
  */

   enum State { Unvisited, Visited, Visiting;}
   public static boolean RouteBetweenNodes(Graph g, Node start, Node end) {
      if (start == end) {
         return true;
      }

      //i will do an iterative implementation of breadth first search


   }

}


