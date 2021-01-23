import org.graalvm.compiler.graph.Graph;
import org.graalvm.compiler.graph.Node;

import java.util.LinkedList;

public class TreesAndGraphs {
   public static void main(String[] args) {
      System.out.println("Hello World");
   }

   /*
 Given a directed graph and two nodes, design an algorithm to find out whether there is a route from S to E
  */

   enum State { Unvisited, Visited, Visiting;}
   public static boolean RouteBetweenNodes(Graph g, Node start, Node end) {

      //if my start node and end node are the same
      if (start == end) {
         return true;
      }

      //i will do an iterative implementation of breadth first search
      //we start with one of the two nodes and during traversal, check if the other node is found
      //we should mark any node found in the course of the algorithm as already visited to avoid cycles and repetitions

      LinkedList<Node> q = new LinkedList<Node>();

      //get all the nodes or vertices in the graph
      for (Node u : g.getNodes()) {
         //have not visited any of them yet
         u.state = State.Unvisited;
      }

      start.state = State.Visiting;

      //add to queue
      q.add(start);
      Node u;


      while(!q.isEmpty()) {
         u = q.removeFirst(); //dequeue
         if (u != null) {
            for (Node v : u.getAdjacent()) {
               if (v.state == State.Unvisited) {
                  if (v == end) {
                     return true;
                  } else {
                     v.state = State.Visiting;
                     q.add(v);
                  }
               }
            }
         }
         u.state = State.Visited;
      }
      return false;
   }


}


