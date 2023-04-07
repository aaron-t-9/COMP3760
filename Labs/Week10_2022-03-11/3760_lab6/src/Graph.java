/*

COMP 3760 - Lab 6
Tom Magliery
March 20/2022

Aaron

The Graph class contains methods to traverse both undirected, and directed graphs, using DFS. It also contains a method
to topologically sort direct graphs.

*/

import java.util.ArrayList;

public class Graph {

    public static void main(String[] args) {
        /*
        Main function containing test graphs that are both unidrected and directed, for testing the DFS and topological
        sort methods.
         */

        boolean graph1_directed = false;
        ArrayList<String> graph1Vertices = new ArrayList<>();
        graph1Vertices.add("a0");
        graph1Vertices.add("a1");
        graph1Vertices.add("a2");
        graph1Vertices.add("a3");
        graph1Vertices.add("a4");
        Graph graph1 = new Graph(graph1Vertices, graph1_directed);

        graph1.adjMatrix = new int[graph1Vertices.size()][graph1Vertices.size()];
        graph1.AddEdge("a0", "a1");
        graph1.AddEdge("a1", "a2");
        graph1.AddEdge("a2", "a3");
        graph1.AddEdge("a3", "a4");
        graph1.AddEdge("a4", "a0");
        graph1.AddEdge("a4", "a1");
        graph1.AddEdge("a3", "a0");

        graph1.getDFSOrder("a0");
        System.out.println(graph1);

        graph1.getDFSOrder("a3");
        System.out.println(graph1);


        boolean graph2_directed = true;
        ArrayList<String> graph2Vertices = new ArrayList<>();
        graph2Vertices.add("d0");
        graph2Vertices.add("d1");
        graph2Vertices.add("d2");
        graph2Vertices.add("d3");
        graph2Vertices.add("d4");
        graph2Vertices.add("d5");
        Graph graph2 = new Graph(graph2Vertices, graph2_directed);

        graph2.adjMatrix = new int[graph2Vertices.size()][graph2Vertices.size()];
        graph2.AddEdge("d0", "d1"); // ab
        graph2.AddEdge("d0", "d4"); // ae
        graph2.AddEdge("d0", "d5"); // af
        graph2.AddEdge("d1", "d2"); // bc
        graph2.AddEdge("d3", "d1"); // db
        graph2.AddEdge("d3", "d2"); // dc
        graph2.AddEdge("d4", "d3"); // ed
        graph2.AddEdge("d5", "d2"); // fc
        graph2.AddEdge("d5", "d4"); // fe

        graph2.getDFSOrder("d0");
        graph2.getTopologicalOrder("d0");
        System.out.println(graph2);

        graph2.getDFSOrder("d4");
        graph2.getTopologicalOrder("d4");
        System.out.println(graph2);

        graph2.getDFSOrder("d5");
        graph2.getTopologicalOrder("d5");
        System.out.println(graph2);


        boolean graph3_directed = true;
        ArrayList<String> graph3Vertices = new ArrayList<>();
        graph3Vertices.add("b0");
        graph3Vertices.add("b1");
        graph3Vertices.add("b2");
        graph3Vertices.add("b3");
        graph3Vertices.add("b4");
        graph3Vertices.add("b5");
        graph3Vertices.add("b6");
        Graph graph3 = new Graph(graph3Vertices, graph3_directed);

        graph3.adjMatrix = new int[graph3Vertices.size()][graph3Vertices.size()];
        graph3.AddEdge("b0", "b1"); // ab
        graph3.AddEdge("b0", "b2"); // ac
        graph3.AddEdge("b1", "b3"); // bd
        graph3.AddEdge("b2", "b3"); // cd
        graph3.AddEdge("b2", "b4"); // ce
        graph3.AddEdge("b3", "b5"); // df
        graph3.AddEdge("b5", "b6"); // fg

        graph3.getDFSOrder("b0");
        graph3.getTopologicalOrder("b0");
        System.out.println(graph3);
    }

    ArrayList<String> vertices;
    boolean directed_flag;
    int[][] adjMatrix;
    ArrayList<String> DFSOrder;
    ArrayList<String> TopoOrder;

    public Graph(ArrayList<String> vertices, boolean directed_flag) {
        /*
        Constructor for the graph class.
         */

        this.vertices = vertices;
        this.directed_flag = directed_flag;
    }

    public void AddEdge(String vertexOne, String vertexTwo) {
        /*
        Adds the edge between the two specified vertices. The specified vertices MUST be in the format of a single
        letter and a single integer (e.g. 'a0', f9').

        The adjacency matrix is read by the x-axis, or by the row, for directed graphs.
         */
        if (isDirected()) {

            // gets the row and column of the vertex passed in
            int rowIndex = Character.getNumericValue(vertexOne.charAt(1));
            int colIndex = Character.getNumericValue(vertexTwo.charAt(1));

            // adds the edge to the adjacency matrix one way for directed graphs
            adjMatrix()[rowIndex][colIndex] = 1;
        }
        else {
            // gets the row and column of the vertex passed in
            int rowIndex = Character.getNumericValue(vertexOne.charAt(1));
            int colIndex = Character.getNumericValue(vertexTwo.charAt(1));

            // adds the edge to the adjacency matrix for both ways as the graph is undirected
            adjMatrix()[rowIndex][colIndex] = 1;
            adjMatrix()[colIndex][rowIndex] = 1;
        }
    }

    public int size() {
        /*
        Returns N, the number of vertices within the graph.
        */

        return this.vertices.size();
    }

    public String getVertex(int vertex) {
        /*
        Returns the name of the specified vertex within the list of vertices.
         */

        return this.vertices.get(vertex);
    }

    public boolean isDirected() {
        /*
        Returns true if and only if the graph is directed.
         */

        return this.directed_flag;
    }

    public int[][] adjMatrix() {
        /*
        Returns an 2D array of ints representing the adjacency matrix of the graph.
         */

        return this.adjMatrix;
     }

    public ArrayList<String> getDFSOrder(String vertex) {
        /*
        Returns a list of all the vertices in the graph in a Depth-First-Search (DFS) order. Iterates through each
        vertex and calls a helper method to recursively find the neighbours for each vertex.
         */
        ArrayList<String> visitedVertices = new ArrayList<>();
        visitedVertices.add(vertex);
        int startingVertex = Character.getNumericValue(vertex.charAt(1));


        int numOfVertices = size();
        for (int row = startingVertex; row < numOfVertices; row++) {

            visitedVertices = visitNeighbours(visitedVertices, row, 0);
        }

        // for directed graphs, checks for missing vertices that may not have been checked due to the starting node
        // and its directed connections with its neighbouring vertices
        if (visitedVertices.size() != numOfVertices) {

            for (int row = 0; row < startingVertex; row++) {

                boolean hasBeenVisited = visited(visitedVertices, row);
                if (!hasBeenVisited) {   // checks if the vertex has already been visited

                    String visitedVertex = getVertex(row);
                    visitedVertices.add(visitedVertex);     // adds the unvisited vertex to the list of visited vertices

                    visitedVertices = visitNeighbours(visitedVertices, row, 0);
                }
            }
        }

        this.DFSOrder = visitedVertices;
        return visitedVertices;
    }

     public ArrayList<String> getTopologicalOrder(String vertex) {
        /*
        Returns an ArrayList containing the valid Topological Sort Order of the vertices of a direct graph. if the graph
        is undirected, then null is returned.
         */

         if (isDirected()) {
             ArrayList<String> deadEndStack = new ArrayList<>();
             int startingVertex = Character.getNumericValue(vertex.charAt(1));

             int numOfVertices = size();
             for (int row = startingVertex; row < numOfVertices; row++) {

                 deadEndStack = doTopoSearch(deadEndStack, row, 0);
             }

             // for directed graphs, checks for missing vertices that may not have been checked due to the starting node
             // and its directed connections with its neighbouring vertices
             if (deadEndStack.size() != numOfVertices) {

                 for (int row = 0; row < startingVertex; row++) {

                     boolean hasBeenVisited = visited(deadEndStack, row);
                     if (!hasBeenVisited) {   // checks if the vertex has already been visited

                         String visitedVertex = getVertex(row);
                         deadEndStack.add(0, visitedVertex);     // adds the unvisited vertex to the list of visited vertices

                         deadEndStack = doTopoSearch(deadEndStack, row, 0);
                     }
                 }
             }

             this.TopoOrder = deadEndStack;
             return deadEndStack;
         }
         else {
             return null;
         }
     }

     public ArrayList<String> doTopoSearch(ArrayList<String> deadEndStack, int row, int col) {
        /*
        Iterates through the neighbours of the provided vertex and performs recursive method calls of itself to find
        its neighbouring vertices to perform a topological search.
         */

         for (; col < size(); col++) {

             if (adjMatrix()[row][col] == 1) {    // checks if the current vertex has a connected neighbouring vertex

                 boolean hasBeenVisited = visited(deadEndStack, col);
                 if (!hasBeenVisited) {   // checks if the vertex has already been visited

                     // recursive method call to search for neighbouring vertices
                     deadEndStack = doTopoSearch(deadEndStack, col, 0);
                 }
             }
         }

         // adds the dead end vertex to the deadEndStack for the topological sort
         boolean hasBeenVisited = visited(deadEndStack, row);
         if (!hasBeenVisited) {   // checks if the vertex has already been visited
             String visitedVertex = getVertex(row);
             deadEndStack.add(0, visitedVertex);     // adds the unvisited vertex to the list of visited vertices
         }

         return deadEndStack;
     }

    public ArrayList<String> visitNeighbours(ArrayList<String> visitedVertices, int row, int col) {
        /*
        Iterates through the neighbours of the provided vertex and performs recursive method calls of itself to find
        its neighbouring vertices to perform a DFS.
         */

        for (; col < size(); col++) {

            if (adjMatrix()[row][col] == 1) {    // checks if the current vertex has a connected neighbouring vertex

                boolean hasBeenVisited = visited(visitedVertices, col);
                if (!hasBeenVisited) {   // checks if the vertex has already been visited

                    String visitedVertex = getVertex(col);
                    visitedVertices.add(visitedVertex);     // adds the unvisited vertex to the list of visited vertices

                    // recursive method call to search for neighbouring vertices
                    visitedVertices = visitNeighbours(visitedVertices, col, 0);
                }
            }
        }

        return visitedVertices;
    }

    private boolean visited(ArrayList<String> visited, int vertexInQuestion) {
        /*
        Iterates through the visited vertices to check if the specified vertex has already been visited or not. Returns
        true if the vertex has been visited, or else false is return.
         */

        String currentVertex = getVertex(vertexInQuestion);

        for (String visitedVertex: visited) {
            if (visitedVertex.equals(currentVertex)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        return "Directed Graph: " + isDirected() + "  -->  DFS Vertices order: " + this.DFSOrder +
                "  -->  Topological order: " + this.TopoOrder;
    }
}
