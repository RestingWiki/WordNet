import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.SET;

import java.util.HashSet;

public class DeluxeBFS {
    private static final int INFINITY = Integer.MAX_VALUE;
    private static final boolean VISITED = true;

    private boolean[] markedV;
    private boolean[] markedW;
    private int[] edgeToV;
    private int[] edgeToW;
    private int[] distToV;
    private int[] distToW;

    private Digraph G;

    // Sets to keep track of visited vertices during BFS
    private HashSet<Integer> visitedFromV;
    private HashSet<Integer> visitedFromW;

    public DeluxeBFS(Digraph G) {
        this.G = new Digraph(G);
    }

    public int ancestor_BFS(int v, int w) {
        //validateVertex(v);
        //validateVertex(w);
        initialize();

        Queue<Integer> q_V = new Queue<>();
        Queue<Integer> q_W = new Queue<>();

        markedV[v] = true;
        distToV[v] = 0;
        q_V.enqueue(v);

        markedW[w] = true;
        distToW[w] = 0;
        q_W.enqueue(w);

        visitedFromV.add(v);
        visitedFromW.add(w);

        if (v == w)
            return v;

        // Lock step BFS
        while (!q_V.isEmpty() || !q_W.isEmpty()) {
            if (!q_V.isEmpty()) {
                int vertex_V = q_V.dequeue();
                for (int adj_V : G.adj(vertex_V)) {
                    if (!markedV[adj_V]) {
                        edgeToV[adj_V] = vertex_V;
                        distToV[adj_V] = distToV[vertex_V] + 1;
                        markedV[adj_V] = VISITED;
                        visitedFromV.add(adj_V);
                        q_V.enqueue(adj_V);
                        if (checkSCA(visitedFromW, adj_V))
                            return adj_V;
                    }
                }
            }

            if (!q_W.isEmpty()) {
                int vertex_W = q_W.dequeue();
                for (int adj_W : G.adj(vertex_W)) {
                    if (!markedW[adj_W]) {
                        edgeToW[adj_W] = vertex_W;
                        distToW[adj_W] = distToW[vertex_W] + 1;
                        markedW[adj_W] = VISITED;
                        visitedFromW.add(adj_W);
                        q_W.enqueue(adj_W);
                        // Check if the vertex is a common ancestor
                        if (checkSCA(visitedFromV, adj_W))
                            return adj_W;
                    }
                }
            }
        }

        return -1; // No common ancestor found
    }
    public int ancestor_BFS(Iterable<Integer> v, Iterable<Integer> w) {
        validateVertices(v);
        validateVertices(w);

        initialize();

        Queue<Integer> q_V = new Queue<>();
        Queue<Integer> q_W = new Queue<>();

        // Initialize the BFS from v vertices
        for (int vertex : v) {
            markedV[vertex] = true;
            distToV[vertex] = 0;
            q_V.enqueue(vertex);
            visitedFromV.add(vertex);
        }

        // Initialize the BFS from w vertices
        for (int vertex : w) {
            markedW[vertex] = true;
            distToW[vertex] = 0;
            q_W.enqueue(vertex);
            visitedFromW.add(vertex);
        }

        int shortestCommonAncestor = -1;

        // Lock step BFS
        while (!q_V.isEmpty() || !q_W.isEmpty()) {
            if (!q_V.isEmpty()) {
                int vertex_V = q_V.dequeue();
                for (int adj_V : G.adj(vertex_V)) {
                    if (!markedV[adj_V]) {
                        edgeToV[adj_V] = vertex_V;
                        distToV[adj_V] = distToV[vertex_V] + 1;
                        markedV[adj_V] = VISITED;
                        visitedFromV.add(adj_V);
                        q_V.enqueue(adj_V);

                        // Check if the vertex is a common ancestor
                        if (visitedFromW.contains(adj_V)) {
                            int candidate = calculateSCA(adj_V);
                            if (shortestCommonAncestor == -1 || distToVertex(candidate, true) + distToVertex(candidate, false) < distToVertex(shortestCommonAncestor, true) + distToVertex(shortestCommonAncestor, false))
                                shortestCommonAncestor = candidate;
                        }
                    }
                }
            }

            if (!q_W.isEmpty()) {
                int vertex_W = q_W.dequeue();
                for (int adj_W : G.adj(vertex_W)) {
                    if (!markedW[adj_W]) {
                        edgeToW[adj_W] = vertex_W;
                        distToW[adj_W] = distToW[vertex_W] + 1;
                        markedW[adj_W] = VISITED;
                        visitedFromW.add(adj_W);
                        q_W.enqueue(adj_W);

                        // Check if the vertex is a common ancestor
                        if (visitedFromV.contains(adj_W)) {
                            int candidate = calculateSCA(adj_W);
                            if (shortestCommonAncestor == -1 || distToVertex(candidate, true) + distToVertex(candidate, false) < distToVertex(shortestCommonAncestor, true) + distToVertex(shortestCommonAncestor, false))
                                shortestCommonAncestor = candidate;
                        }
                    }
                }
            }
        }

        return shortestCommonAncestor;
    }

    public int distToVertex(int vertex, boolean fromV) {
        validateVertex(vertex);
        if (fromV && visitedFromV.contains(vertex))
            return distToV[vertex];
        else if (!fromV && visitedFromW.contains(vertex))
            return distToW[vertex];
        return INFINITY; // The vertex is not in the visited set
    }

    public int distToVertex(int sca) {
        validateVertex(sca);
        return distToV[sca] + distToW[sca];
    }

    private boolean checkSCA(HashSet<Integer> visitedVertices, int vertex) {
        return visitedVertices.contains(vertex);
    }

    private void validateVertex(int v) {
        int V = G.V();
        if (v < 0 || v >= V) {
            throw new IllegalArgumentException("Vertex " + v + " is not between 0 and " + (V - 1));
        }
    }

    private void validateVertices(Iterable<Integer> vertices) {
        if (vertices == null)
            throw new IllegalArgumentException("Iterable is null!");

        for (Integer vertex : vertices) {
            if (vertex == null)
                throw new IllegalArgumentException("Iterable contains null value!");
        }
    }

    private void initialize(){
        int V = G.V();
        markedV = new boolean[V];
        markedW = new boolean[V];
        edgeToV = new int[V];
        edgeToW = new int[V];
        distToV = new int[V];
        distToW = new int[V];
        for (int i = 0; i < V; i++) {
            distToV[i] = INFINITY;
            distToW[i] = INFINITY;
        }
        visitedFromV = new HashSet<>();
        visitedFromW = new HashSet<>();
    }
    private int calculateSCA(int vertex) {
        if (visitedFromV.contains(vertex) && visitedFromW.contains(vertex))
            return vertex;
        return -1; // Not a common ancestor
    }
    public static void main(String[] args) {
        In in = new In("input.txt");
        Digraph G = new Digraph(in);

        DeluxeBFS bfs = new DeluxeBFS(G);

        SET<Integer> set1 = new SET<>();
        set1.add(9);
        set1.add(1);
        SET<Integer> set2 = new SET<>();
        set2.add(3);

        int sca = bfs.ancestor_BFS(set1,set2);
        System.out.println(sca);
    }
}
