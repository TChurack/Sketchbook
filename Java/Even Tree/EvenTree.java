import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;


public class Solution {
    
    public static int[] vertCounts;
    public static int[] parents;
    public static int[] finalVertCount;
    
    public static int sumChildren(int index) {
        int count = 1; //include self
        for (int i = 0; i < parents.length; i++) {
            if (i != index && parents[i] == index) {
                count += sumChildren(i);
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
        
        sumChildren(0);
        
        int evenEdges = 0;
        
        for (int i = 1; i < numVertices; i++) {
            if (finalVertCount[i] != 0 && finalVertCount[i]%2 == 0) { evenEdges++; }
        }
        System.out.println(evenEdges);
    }
}
