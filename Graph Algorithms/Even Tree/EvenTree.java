/**
 * Breaks a tree into a forest by removing as many edges as possible while
 * satisfying the constraint that each tree in the forest must contain an 
 * even number of vertices.
 *
 * Input from STDIN:
 * 		Number of vertices
 * 		Number of edges
 *		For each edge:
 *			A pair of integers representing the two vertices connected to the edge
 *
 * Output to STDOUT:
 *		Prints the maximum number of edges that can be removed from the input tree
 *		leaving a forest of trees all with an even number of vertices.
 */
import java.io.*;

public class Solution {
    
    public static int[] vertCounts; //Stores the number of vertices that are direct children
    public static int[] parents; //Stores the parents of each vertex
    public static int[] finalVertCount; //Stores the number of descendant vertices (including itself)
    
	//Sums the number of children of a vertex (including itself)
    public static int sumChildren(int index) {
        int count = 1; //include self
        for (int i = 0; i < parents.length; i++) {
            if (i != index && parents[i] == index) {
                count += sumChildren(i); //Recursively sum the number of children
            }
        }
        finalVertCount[index] = count;
        return count;
    }
    

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int numVertices = in.nextInt();
        int numEdges = in.nextInt();
        
        vertCounts = new int[numVertices];
        parents = new int[numVertices];
        
        for (int i = 0; i < numEdges; i++) {
            int child = in.nextInt() - 1;
            int parent = in.nextInt() - 1;
            
            parents[child] = parent;
            vertCounts[parent]++;
        }
        
        finalVertCount = new int[numVertices];
        
        sumChildren(0); //Recursively determine children starting from the root
        
        int evenEdges = 0;
        
        for (int i = 1; i < numVertices; i++) { //Ignore vertex 0 (root)
			//Sever the edge between the vertex with an even number of descendants (including itself) and its parent, creating an even tree
            if (finalVertCount[i] != 0 && finalVertCount[i]%2 == 0) { evenEdges++; } 
        }
		
        System.out.println(evenEdges);
    }
}
