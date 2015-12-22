/* Implements Kruskal's Minimum Spanning Tree Algorithm using a
 * Union-Find Data Structure (w/ path compression and union by height)
 * and prints the total weight of edges in the MST to the command line.
 *
 * Reads in the graph for which the Minimum Spanning Tree is to be
 * generated from the command line as follows:
 *	  Number of Nodes in Graph
 *	  Number of Edges
 *	  Then for each edge:
 *		The number representing one of the edge's nodes
 *		The number representing the other of the edge's nodes
 *		The edge's 'weight'
 *
 * NB: Assumes graph vertices are labelled from 1 to N, where N is the number of nodes
 */
 
import java.util.*;

public class Kruskal {
	
	public ArrayList<Edge> mstEdges; // Stores the edges making up the minimum spanning tree
    
	/* Class to represent the vertices in the graph */
    public class Node {
        Node parent; // Not necessarily direct parent but the root of the union-find set containing this node
        int rank; // Only relevant if node is root of a union-find set: is the height of the union-find set's tree
    }
    
	/* Class to represent the edges in the graph */
    public class Edge implements Comparable<Edge> {
        int weight;
        Node n1;
        Node n2;
        
        public int compareTo(Edge e) {
            return (this.weight - e.weight);
        }
    }
    
	/* Implementation of the Union Find data structure w/ path compression and union by height 
	 * Used to store and perform operations on the connected (by the edges chosen thus far) subsets of graph nodes
	 */
    public class UnionFind {
        
		//Constructs a set of a specified single element
        public void makeSet(Node x) {
            x.parent = x;
            x.rank = 0; 
        }
        
		//Returns the root of the set containing the specified node
        public Node find(Node x) {
            if (x.parent != x) { x.parent = find(x.parent); } //path compression
            return x.parent;
        }
        
		//Unifies two sets of nodes
        public void union(Node x, Node y) {
            Node xRoot = find(x);
            Node yRoot = find(y);
            
            if (xRoot == yRoot) {
                return; //already in the same set
            }
            
 
			/* Tree heights equal => new tree will have increased height regardless of root choice
			 * Tree heights differ => root of taller tree becomes new root of shorter tree, total tree height is unchanged
			 */
            if (xRoot.rank == yRoot.rank) {
                yRoot.parent = xRoot;
                xRoot.rank++; //Tree height incremented
            } else if (xRoot.rank < yRoot.rank) {
                xRoot.parent = yRoot;
            } else {
                yRoot.parent = xRoot;
            }
        }
    }
    
	/* Method to read in information from the command line,
	 * to build up the lists of nodes and edges and perform the
	 */
    public void solve() {
        Scanner in = new Scanner(System.in);
        int numNodes = in.nextInt();
        int numEdges = in.nextInt();
        
        ArrayList<Node> nodes = new ArrayList<>(numNodes);
        PriorityQueue<Edge> edges = new PriorityQueue<>(numEdges);
        UnionFind uf = new UnionFind();
        
        int totWeight = 0;
		mstEdges = new ArrayList<>();
		
        //Create nodes
        for (int i = 0; i < numNodes; i++) {
            Node n = new Node();
            nodes.add(n);
            uf.makeSet(n);
        }
        
		//Create edges
        for (int i = 0; i < numEdges; i++) {
            Edge e = new Edge();
            e.n1 = nodes.get(in.nextInt()-1);
            e.n2 = nodes.get(in.nextInt()-1);
            e.weight = in.nextInt();
            
            edges.add(e);
        }
        
		//Choose edges that make up MST
        while (!edges.isEmpty()) {
            Edge next = edges.poll();
            if (uf.find(next.n1) != uf.find(next.n2)) {
                totWeight += next.weight;
                uf.union(next.n1, next.n2);
				mstEdges.add(next);
            }
        }
        
        System.out.println(totWeight);
    }

    public static void main(String[] args) {
        Kruskal sol = new Kruskal();
        sol.solve();
    }
}