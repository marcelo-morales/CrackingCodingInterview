import apple.laf.JRSUIUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import org.graalvm.compiler.graph.Graph;
import org.graalvm.compiler.graph.Node;

import javax.swing.tree.TreeNode;
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
   
   /*
   Given a binary tree, design an algorithm that creates a linked list of all the nodes at each depth
   (e.g., if you have a tree with depth D, you'll have D linked lists)
   
   we can also implement a modficiation of breadth-first search, with implementation, we can
   iterate through the root first, then level 2, then level 3, and so on.
    */
   
      ArrayList<LinkedList<TreeNode>> createLevelLinkedList(Tree root) {
         ArrayList<LinkedList<TreeNode>> result = new ArrayList<LinkedList<TreeNode>>();
         
         //visit the root
         LinkedList<TreeNode> current = new LinkedList<TreeNode>();
         if (root != null) {
            result.add(root);
         }
         
         while (current.size() > 0) {
            result.add(current); //add previous level
            LinkedList<TreeNode> parents = current;
            for (TreeNode parent : parents) {
               if (parent.left != null) {
                  current.add(parent.left);
               }

               if (parent.right != null) {
                  current.add(parent.right);
               }
            }

         }

         return result;
      }


      /*
      Implement a function to check if a binary tree is balanced. A balanced tree is defined to be a tree
      such that the heights of the two subtrees of any node never differ by more than one -> for the purpose
      of this question.

      this algorithm is O(n log n) since each node is touched once per node above it.
       */

      int getHeight(TreeNode root) {
         if (root==null) {
            return -1; //base case
         }
         return Math.max(getHeights(root.left), getHeights(root.right)) + 1;
      }

      boolean isBalanced(TreeNode root) {
         if (root == null) {
            return true; //base case
         }

         int heightDiff = getHeight(root.left) - getHeight(root.right);

         if (Math.abs(heightDiff) > 1) {
            return false;
         } else {
            return isBalanced(root.right) && isBalanced(root.left);
         }


      }

      /*
      Implement a function to check if a binary tree is a binary search tree.
       */

      //the recursive code is as follows:

      boolean checkBST(TreeNode n) {
         return checkBST(n, null, null);
      }

      boolean checkBST(TreeNode n, Integer min, Integer max) {
         if (n == null) {
            return true;
         }

         if ((min != null && n.data <= min) || (max != null && n.data > max)) {
            return false;
         }

         if (!checkBST(n.left, min, n.data) || !checkBST(n.right, n.data, max)) {
            return false;
         }

         return true;

      }

      //remember that in recursive algorithms, you should always make sure that your base cases, as well
      //as your null cases, are well handled


      /*
      Write an algorithm to find the "next" node of a fiven node in a binary search tree

      In in-order traverses the left subtree, then the current node, then the right subtrees
       */

      TreeNode inorderSucc(TreeNode n) {
         if (n == null) {
            return null;
         }

         //if found right children -> return leftmose node of righ subtree

         if (n.right != null) {
            return leftMostChild(n.right);
         } else {
            TreeNode q = n;
            TreeNode x = q.parent;

            //go up until we're on left insteaf of right
            while (x != null && x.left != q) {
               q = x;
               x = x.parent;
            }
            return x;
         }
      }

      TreeNode leftMostChild (TreeNode n) {
         if (n == null) {
            return null;
         }
         while (n.left != null) {
            n = n.left;
         }
         return n;
      }


      /*
      Design an algorithm and write code to find the first common ancestor of two nodes in a binary tree.
       */

      TreeNode commonAncdstor(TreeNode p, TreeNode q) {
         int delta = depth(p) - depth(q);  //get differnece in depths
         TreeNode first = delta > 0 ? q : p; //get shallower node
         TreeNode second = delta > 0 ? p : q; //get deeper node
         second = goUpBy(second, Math.abs(delta)); //move deeper node up

         //find where paths intersect
         while(first != second && first != null && second != null) {
            first = first.parent;
            second = second.parent;
         }
         return first == null || second == null ? null : first;

      }

   private TreeNode goUpBy(TreeNode node, int delta) {
      while (delta > 0 && node != null) {
         node = node.parent;
         delta--;
      }
      return node;
   }

   private int depth(TreeNode node) {
      int depth = 0;
      while (node != null) {
         ++depth;
         node = node.parent;
      }
      return depth;
   }

   /*
   Check subtree - T1 and T2 are two very large binary trees, with T1 much bigger than T2. Create an algorithm to
   determine if T2 is a subtree of T1.
    */

   //observe that as long as we represent the NULL nodes, the pre-order traversal of a tree is unique

   //a preorder traversal always starts at the root, and from there, the path we take is entirely defined by the
   //traversal. Therefore, two trees are identifical if they have the same pre-order traversal.

   boolean containsTree(TreeNode t1, TreeNode t2) {
      StringBuilder string1 = new StringBuilder();
      StringBuilder string2 = new StringBuilder();

      getOrderString(t1, string1);
      getOrderString(t2, string2);

      return string1.indexOf(string2.toString()) != -1;



   }

   private void getOrderString(TreeNode node, StringBuilder sb) {
      if (node == null) {
         sb.append("X"); //adding a null indicator
         return;
      }

      sb.append(node.data + " "); //adding root
      getOrderString(node.left, sb); //add left
      getOrderString(node.right, sb); //add right
   }


   /*
   You are given a binary tree in which each node contains an integer value. Design an algorithm to
   count the number of paths that sum to a given value
    */

   //brute force solution

   //look at all possivle paths
   //to do this, we traverse to each node. At each node, we recursively try all paths downwards
   //tracking the sum as we go, as soon as we hit our target sum, we increment the total

   int countPathsWithSum (TreeNode root, int targetSum) {
      if (root == null) {
         return 0;
      }

      //count paths with sum starting from root
      int pathsFromRoot = countPathsWithSumFromNode(root, targetSum, 0);

      //try nodes on the left and the right
      int pathsOnLeft = countPathsWithSumFromNode(root.left, targetSum, 0);
      int pathsOnRight = countPathsWithSumFromNode(root.right, targetSum, 0);

      return pathsFromRoot + pathsOnLeft + pathsOnRight;


   }

   private int countPathsWithSumFromNode(TreeNode node, int targetSum, int currentSum) {
      if (node == null) {
         return 0;
      }

      currentSum += node.data;
      int totalPaths = 0;
      if (currentSum == targetSum) {
         ++totalPaths;
      }

      totalPaths += countPathsWithSum(node.left, targetSum, currentSum);
      totalPaths += countPathsWithSum(node.right, targetSum, currentSum);
      return totalPaths;


   }


}


