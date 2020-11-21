// Kruskals class
//
// CONSTRUCTION: sets up priority queue and hash map to be used later for finding
//               minimum spanning tree/
//
// ******************************PUBLIC OPERATIONS**********************************
// void getVerticesEdgePairs() --> Get all vertices and edges pair up
// int getMinSpanningTree()   --> Print minimum spanning and return minimum distance
// ***********************************ERRORS****************************************
// Error when file path to CSV file is invalid.

import java.util.*;
import java.io.*;

/**
 *  Class that implement Kruskal's minimum spanning tree algorithm using PriorityQueue, DisjSet, and HashMap.
 *
 * @author Sung Hyun Hwang (sxh200013)
 */
public class Kruskals {
    private PriorityQueue<Edge> pqByDistance;
    private HashMap<String, Vertex> vertexMap;
    private int totalDistance;
    private int numVertices = 0;

    /**
     * Construct the object for Kruskal's algorithm.
     */
    public Kruskals() {
        pqByDistance = new PriorityQueue<>();
        vertexMap = new HashMap<>();
    }

    /**
     * Class that is used to construct each edge connecting two vertices.
     */
    private static class Edge implements Comparable<Edge> {
        private Vertex<String> v;
        private Vertex<String> w;
        private Integer distance;

        /**
         * Construct the edge object with two vertices and a distance.
         *
         * @param v first vertex object.
         * @param w second vertex object.
         * @param distance the distance or weight of the edge between the two.
         */
        private Edge(Vertex<String> v, Vertex<String> w, Integer distance) {
            this.v = v;
            this.w = w;
            this.distance = distance;
        }

        /**
         * Public method to get the first vertex v.
         *
         * @return the first vertex v.
         */
        public Vertex<String> getFirstVertex() {
            return this.v;
        }

        /**
         * Public method to get the second vertex w.
         *
         * @return the second vertex w.
         */
        public Vertex<String> getSecondVertex() {
            return this.w;
        }

        /**
         * Public method to get the object's distance value.
         *
         * @return the object's distance value.
         */
        public Integer getDistance() {
            return this.distance;
        }

        /**
         * Overrides the compareTo method to compare Edge object distances.
         *
         * @param e the Edge object to compare to.
         * @return < 0 if this.distance < e.distance; > 0 if this.distance > e.distance; 0 if same.
         */
        @Override
        public int compareTo(Edge e) {
            return this.getDistance().compareTo(e.getDistance());
        }

        /**
         * Overrides the toString method to print out the vertices and the distance between them.
         *
         * @return string with vertex names and the distance between them.
         */
        @Override
        public String toString() {
            return v.getVal() + " to " + w.getVal() + ": " + distance;
        }
    }

    /**
     * Class that is used to create the vertices.
     *
     * @param <AnyType>
     */
    private static class Vertex<AnyType> {
        private AnyType val;
        private int tracker;

        /**
         * Construct the vertex object to be used to create the vertices edge pairings.
         *
         * @param val the "name" of the vertex
         * @param tracker the tracker value to be used when using with disjoint set.
         */
        private Vertex(AnyType val, int tracker) {
            this.val = val;
            this.tracker = tracker;
        }

        /**
         * Public method to get the vertex's val.
         *
         * @return the vertex's val.
         */
        public AnyType getVal() {
            return this.val;
        }

        /**
         * Public method to get the vertex's tracker
         *
         * @return the vertex's tracker.
         */
        public int getTracker() {
            return this.tracker;
        }
    }

    /**
     * Gets the CSV filepath from user. Parses the file for city names and distances between them
     * and adds them to the priority queue.
     *
     * @throws IOException if the file path is invalid.
     */
    public void getVerticesEdgePairs() throws IOException {
        // Read the file path for the CSV file.
        System.out.print("Enter the file path for the CSV file: ");
        Scanner sc = new Scanner(System.in);
        String path = sc.nextLine();
        System.out.println();
        String line;

        // Read each row of the CSV file, connect first column city to all other cities in the row.
        // Create Edge objects using these city pairs as vertices and add the distance val to the object.
        BufferedReader br = new BufferedReader(new FileReader(path));
        while ((line = br.readLine()) != null) {
            // Line has the entire row of the CSV file. Get the row split by comma character.
            String[] csvRowEntries = line.split(",");
            for (int i = 1; i < csvRowEntries.length; i += 2) {
                // Only create a new vertex if there hasn't already been one made by checking for it in a hash map.
                // If new vertex is created, add it to a hash map.
                // Otherwise, get the vertex that has already been made previously from the hash map and reuse.
                Vertex<String> v; Vertex<String> w;
                if (vertexMap.containsKey(csvRowEntries[0])) {   // Check if first city vertex has already been made.
                    v = vertexMap.get(csvRowEntries[0]);
                }
                else {
                    v = new Vertex<>(csvRowEntries[0], numVertices);
                    numVertices++;
                    vertexMap.put(csvRowEntries[0], v);
                }

                if (vertexMap.containsKey(csvRowEntries[i])) {   // Check if second city vertex has already been made.
                    w = vertexMap.get(csvRowEntries[i]);
                }
                else {
                    w = new Vertex<>(csvRowEntries[i], numVertices);
                    numVertices++;
                    vertexMap.put(csvRowEntries[i], v);
                }

                // Get the distance as an Integer object to use its compareTo method when adding to priority queue.
                Integer distance = Integer.parseInt(csvRowEntries[i + 1]);

                // Make the Edge object using the two vertices and the distance. Add to the graph
                Edge e = new Edge(v, w, distance);

                // Add all Edge objects to the priority queue; the compareTo override will take care of priority.
                pqByDistance.add(e);    // Shortest distance to longest distance.
            }
        }
    }

    /**
     * Print out the minimum spanning tree found using Kruskal's algorithm.
     *
     * @return the total distance/weight covered.
     */
    public int getMinSpanningTree() {
        DisjSets dsOfVertices = new DisjSets(numVertices);
        int edges = 1;

        // Poll all of the priority queue's items until all edges have been checked.
        while (pqByDistance.peek() != null) {
            Edge e = pqByDistance.poll();

            // Get the tracker number associated with the Edge to add it to use as the int to search dsOfVertices.
            int vSet = dsOfVertices.find(e.v.tracker);
            int wSet = dsOfVertices.find(e.w.tracker);

            // If the two vertices are not in the same set, connect them, print path, and add distance to totalDistance.
            if (vSet != wSet) {
                dsOfVertices.union(vSet, wSet);
                totalDistance += e.distance;
                System.out.print(edges++ + ".) ");
                System.out.println(e);
            }
        }

        return totalDistance;
    }

    public static void main(String[] args) throws IOException {
        Kruskals minSpanningTree = new Kruskals();
        minSpanningTree.getVerticesEdgePairs();
        int totalDistance = minSpanningTree.getMinSpanningTree();

        System.out.println();
        System.out.println("The total distance is " + totalDistance);
    }

}