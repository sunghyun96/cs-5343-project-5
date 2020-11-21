import java.util.*;
import java.io.*;
import static java.lang.Integer.parseInt;

public class Kruskals {
    private PriorityQueue<Edge> pqByDistance;
    private int totalDistance;
    private int numEdges = 0;

    public Kruskals() {
        pqByDistance = new PriorityQueue<>();
    }

    private static class Edge implements Comparable<Edge> {
        private Vertex<String> v;
        private Vertex<String> w;
        private Integer distance;

        /**
         *
         * @param v
         * @param w
         * @param distance
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
            return v.getVal() + "to" + w.getVal() + ": " + distance;
        }
    }

    private static class Vertex<AnyType> {
        private AnyType val;
        private int num;

        private Vertex(AnyType val) {
            this.val = val;
            this.num =
        }

        public AnyType getVal() {
            return this.val;
        }
    }

    /**
     * Gets the CSV filepath from user. Parses the file for city names and distances between them
     * and adds them to the priority queue.
     *
     * @throws IOException if the file path is invalid.
     */
    public void getVerticesEdgePairs() throws IOException {
        // Read the file path for the csv file.
        Scanner sc = new Scanner(System.in);
        String path = sc.nextLine();
        String line = "";

        // Read each row of the csv file, connect first column city to all other cities in the row.
        // Create Edge objects using these city pairs as vertices and add the distance val to the object.
        BufferedReader br = new BufferedReader(new FileReader(path));
        while ((line = br.readLine()) != null) {
            // Line has the entire row of the CSV file.
            String[] csvRowEntries = line.split(",");
            for (int i = 1; i < csvRowEntries.length; i += 2) {
                // TODO: Use a hash map to store vertices created to avoid duplicate cities.
                Vertex<String> v = new Vertex<>(csvRowEntries[0]);    // The first city.
                Vertex<String> w = new Vertex<>(csvRowEntries[i]);    // The second city to connect the first to.
                Integer distance = Integer.parseInt(csvRowEntries[i + 1]);  // Get the distance as an Integer object

                // Make the Edge object using the two vertices and the distance. Add to the graph
                Edge e = new Edge(v, w, distance);

                // Add all Edge objects to the priority queue; the compareTo override will take care of priority.
                pqByDistance.add(e);    // Shortest distance to longest distance.

                // Track total number of edges added to priority queue to use to make the disjoint set later.
                numEdges++;
            }
        }
    }

    public int findMinSpanningTree() {
        DisjSets dsOfEdges = new DisjSets(numEdges);

        // Poll all of the priority queue's items until all edges have been checked.
        while (pqByDistance.peek() != null) {
            Edge e = pqByDistance.poll();

        }

        return totalDistance;
    }

    public static void main(String[] args) {

    }

}