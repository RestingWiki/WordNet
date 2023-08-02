import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.In;

import java.util.HashMap;

public class SAP {
    private final Digraph G;
    private int dist;
    //private HashMap<Integer,BreadthFirstDirectedPaths> st;
    // constructor takes a digraph (not necessarily a DAG)
    public SAP(edu.princeton.cs.algs4.Digraph G){
        if (G == null)
            throw  new IllegalArgumentException("");

        this.G  = new Digraph(G);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w){
        validateVertex(v);
        validateVertex(w);


        // CHeck for common ancestor
        int sca = ancestor(v,w);
        if (sca == -1)
            return -1;


        return  dist;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w){
        // Check for null
        validateVertex(v);
        validateVertex(w);

        int sca = -1;
        dist = Integer.MAX_VALUE;

        DeluxeBFS bfs = new DeluxeBFS(G);
        sca =bfs.ancestor_BFS(v,w);

        return  sca;        // Return the shortest common ancestor
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w){
        // Check for null
        if (v == null || w == null)
            throw new IllegalArgumentException("");


        for (Integer vertex: v) {
            if (vertex == null)
                throw new IllegalArgumentException("Iterable contains null value!");
        }

        for (Integer vertex: w) {
            if (vertex == null)
                throw new IllegalArgumentException("Iterable contains null value!");
        }

        int sca = -1;
        sca = ancestor(v,w);

        if (sca == -1)
            return  sca;


        return dist;

    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w){
        // Check for null
        if (v == null || w == null)
            throw new IllegalArgumentException("");


        for (Integer vertex: v) {
            if (vertex == null)
                throw new IllegalArgumentException("Iterable contains null value!");
        }

        for (Integer vertex: w) {
            if (vertex == null)
                throw new IllegalArgumentException("Iterable contains null value!");
        }

        int sca = -1;




        return  sca;
    }




    private void validateVertex(int v){
        if (v < 0 || v >= G.V())
            throw new IllegalArgumentException("Vertex out of range!");
    }


    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In("input.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        /*
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }

         */
        SET<Integer> set1 = new SET<>();
        set1.add(9);
        set1.add(1);
        SET<Integer> set2 = new SET<>();
        set2.add(3);


        System.out.println(sap.ancestor(set1,set2));
    }
}