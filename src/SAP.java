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
        DeluxeBFS bfs = new DeluxeBFS(G);


        return  bfs.length(v,w);
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w){
        // Check for null
        validateVertex(v);
        validateVertex(w);

        //int sca = -1;
        dist = Integer.MAX_VALUE;

        DeluxeBFS bfs = new DeluxeBFS(G);


        return  bfs.ancestor(v,w);      // Return the shortest common ancestor
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w){
        // Check for null
        if (v == null || w == null)
            throw new IllegalArgumentException("");

        // Check for empty inputs
        if (!v.iterator().hasNext() || !w.iterator().hasNext())
            return -1;

        for (Integer vertex: v) {
            if (vertex == null)
                throw new IllegalArgumentException("Iterable contains null value!");
            validateVertex(vertex);
        }

        for (Integer vertex: w) {
            if (vertex == null)
                throw new IllegalArgumentException("Iterable contains null value!");
            validateVertex(vertex);
        }

        DeluxeBFS bfs = new DeluxeBFS(G);

        return bfs.length(v,w);

    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w){
        // Check for null
        if (v == null || w == null)
            throw new IllegalArgumentException("");

        if (!v.iterator().hasNext() || !w.iterator().hasNext())
            return -1;

        for (Integer vertex: v) {
            if (vertex == null)
                throw new IllegalArgumentException("Iterable contains null value!");
            validateVertex(vertex);
        }

        for (Integer vertex: w) {
            if (vertex == null)
                throw new IllegalArgumentException("Iterable contains null value!");
            validateVertex(vertex);
        }

        DeluxeBFS bfs = new DeluxeBFS(G);



        return  bfs.ancestor(v,w);
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
        set1.add(10);
        set1.add(11);
        set1.add(5);
        set1.add(3);
        set1.add(1);

        set1.add(0);
        SET<Integer> set2 = new SET<>();
        set2.add(6);
        set2.add(1);
        set2.add(0);

        //System.out.println(sap.ancestor(10,7));
       // System.out.println(sap.length(10,7));
        System.out.println(sap.ancestor(set1,set2));
    }
}