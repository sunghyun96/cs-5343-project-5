import java.util.*;
import java.io.*;

public class Kruskals {
    public Kruskals() {

    }

    private class Edge {
        /**
         *
         * @param v
         * @param w
         */
        private Edge(Vertex v, Vertex w, ) {

        }
    }

    private class Vertex<AnyType> {
        AnyType val;

        private Vertex(AnyType val) {
            this.val = val;
        }
    }

    public void getVeticesEdgePairs() {
        // Read the file path for the csv file.
        Scanner sc = new Scanner(System.in);
        String path = sc.nextLine();
        try {
            // Read each row of the csv file, connect first column city to all other cities in the row.
            // Create Edge objects using these city pairs as vertices and add the distance val to the object.
            BufferedReader br = new BufferedReader(new FileReader(path));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }




    }

    public static void main(String[] args) {

    }

}