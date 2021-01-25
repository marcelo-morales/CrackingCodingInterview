import apple.laf.JRSUIUtils;
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

   /*
   Given a sorted (increasing order) array with unique integer elements, write an algorithm to create
   a binary search tree with minimal height.
    */

   /*
   TO create a tree of minimal height, we need to match the number of nodes in the left subtree to the
   number of nodes in the right subtree as much as possible -> we want the root to be the middle of the array -> since that would mean that half the elements
   would be less than the root and hald would be greater than it.
    */

   public static TreeNode minimalTree(int [] array) {
      return minimalTree(array, 0, array.length - 1);

   }
   /*
   the method is passed a subsection of the array and tetruns the root of a minal tree of that array

   algorithm is as follows:

   1. insert into the tree the middle element of the array
   2. insert (int toe left subtree) the left subarray elements.
   3. Insert (into the right subtree) the right subarray elements.
   4, Recurse
    */

   public static TreeNode minimalTree(int [] array, int start, int end) {
      if (end < start) {
         return null;
      }

      int mid = (start + end) / 2;
      TreeNode n = new TreeNode(arr[mid]);
      n.left = createMinimalTree(arr, start, mid-1);
      n.right = createMinimalTree(arr, mid + 1, end);
      return n;
   }


}


