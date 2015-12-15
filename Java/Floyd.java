/* Implementation of Floyd's Algorithm to find the shortest path between
 * any (and every) two nodes in a weighted Graph. Prints the requested 
 * shortest path between pairs of nodes to the command line (or 'NO PATH'
 * if no such path exists).
 *
 * Reads in the graph for which the paths are to be calculated from
 * the command line as follows:
 * 		The number of vertices in the graph
 * 		The number of edges in the graph
 * 		Then for each edge:
 *			The vertex which the edge is directed away from
 *			The vertex which the edge is directed toward
 *			The weight (cost) of traversing the edge
 *		The number of shortest path queries that will follows
 * 		Then for each query:
 *			The node from which the shortest path is desired
 *			The node to which the shortest path is desired
 * NB: The algorithm assumes a directed graph, if operation over an undirected graph is required
 * simply input each undirected edge twice (once for each direction).
 */

import java.util.*;
import java.math.*;

public class Floyd {

    public void solve() {
        Scanner in = new Scanner(System.in);
        int numV = in.nextInt();
        int numE = in.nextInt();
        
        int[][] dists = new int[numV][numV]; //Multidimensional array to store minimum distances between pairs of nodes
        
		//Fill min-distance array with extremely large values
        for (int[] row : dists) {
            Arrays.fill(row, Integer.MAX_VALUE/2); //Using half max val to prevent integer overflow when calculating path via detour
        }
        
		//Distance between a vertex and itself is always zero
        for (int i = 0; i < numV; i++) {
            dists[i][i] = 0;
        }
        
		//Read in provided edges and edge weights
        for (int j = 0; j < numE; j++) {
            int x = in.nextInt();
            int y = in.nextInt();
            int w = in.nextInt();
            
			/*Array uses Zero-based indexing but vertices are labelled from 1
			 so vertex num -1 is the relevant array index. */
			dists[x-1][y-1] = w; 
        }
        
		//Iterate through all potential detours and set minimal distances appropriately
        for (int k = 0; k < numV; k++) {
            for (int i = 0; i < numV; i++) {
                for (int j = 0; j < numV; j++) {
                    dists[i][j] = Math.min(dists[i][j], dists[i][k] + dists[k][j]);
                }
            }
        }
        
        int numQueries = in.nextInt();
        
        for (int i = 0; i < numQueries; i++) {
            int ind1 = in.nextInt();
            int ind2 = in.nextInt();
            
            int res = dists[ind1-1][ind2-1];
			
            if (res >= Integer.MAX_VALUE/2) {
				System.out.println("NO PATH");
			} else {
				System.out.println(res);
			}
        }
    }
    
    public static void main(String[] args) {
        Floyd sol = new Floyd();
        sol.solve();
    }
}