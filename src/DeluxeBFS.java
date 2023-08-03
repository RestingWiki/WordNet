import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.SET;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Set;
import java.util.HashSet;

public class DeluxeBFS {
    private final Digraph G;
    private int[] distToV;
    private int[] distToW;
    private HashSet<Integer> markedV;
    private HashSet<Integer> markedW;
    private ArrayDeque<Integer> queueV;
    private ArrayDeque<Integer> queueW;
    private int commonAncestor;

    public DeluxeBFS(Digraph G) {
        this.G = new Digraph(G);
    }



    public int ancestor(int v, int w) {
        bfs(v, w);
        return commonAncestor;
    }

    public int ancestor(Iterable<Integer> vVertices, Iterable<Integer> wVertices){
        bfs(vVertices,wVertices);
        return commonAncestor;
    }

    public int length(int v, int w) {
        return bfs(v, w);
    }

    public int length(Iterable<Integer> vVertices, Iterable<Integer> wVertices) {
        return bfs(vVertices, wVertices);
    }

    public int totalDistance(int v, int w) {
        bfs(v, w);

        if (commonAncestor == -1)
            return  -1;

        return distToV[commonAncestor] + distToW[commonAncestor];
    }

    public int totalDistance(Iterable<Integer> v, Iterable<Integer> w) {
        bfs(v, w);

        if (commonAncestor == -1)
            return  -1;

        return distToV[commonAncestor] + distToW[commonAncestor];
    }



    private int bfs(int v, int w) {
        distToV = new int[G.V()];
        distToW = new int[G.V()];
        markedV = new HashSet<>();
        markedW = new HashSet<>();
        queueV = new ArrayDeque<>();
        queueW = new ArrayDeque<>();

        distToV[v] = 0;
        distToW[w] = 0;
        markedV.add(v);
        markedW.add(w);
        queueV.add(v);
        queueW.add(w);

        int minLength = Integer.MAX_VALUE;
        commonAncestor = -1;

        if (v == w) {
            commonAncestor = v; // Vertex v is its own ancestor
            return 0;
        }

        while (!queueV.isEmpty() || !queueW.isEmpty()) {
            if (!queueV.isEmpty()) {
                int currentV = queueV.poll();
                for (int neighbor : G.adj(currentV)) {
                    if (!markedV.contains(neighbor)) {
                        distToV[neighbor] = distToV[currentV] + 1;
                        markedV.add(neighbor);
                        queueV.add(neighbor);
                        if (markedW.contains(neighbor)) {
                            int length = distToV[neighbor] + distToW[neighbor];
                            if (length < minLength) {
                                minLength = length;
                                commonAncestor = neighbor;
                            }
                        }
                    } else if (currentV == w) { // Check for self-ancestor
                        int length = distToV[currentV];
                        if (length < minLength) {
                            minLength = length;
                            commonAncestor = currentV;
                        }
                    }
                }
            }

            if (!queueW.isEmpty()) {
                int currentW = queueW.poll();
                for (int neighbor : G.adj(currentW)) {
                    if (!markedW.contains(neighbor)) {
                        distToW[neighbor] = distToW[currentW] + 1;
                        markedW.add(neighbor);
                        queueW.add(neighbor);
                        if (markedV.contains(neighbor)) {
                            int length = distToV[neighbor] + distToW[neighbor];
                            if (length < minLength) {
                                minLength = length;
                                commonAncestor = neighbor;
                            }
                        }
                    } else if (currentW == v) { // Check for self-ancestor
                        int length = distToW[currentW];
                        if (length < minLength) {
                            minLength = length;
                            commonAncestor = currentW;
                        }
                    }
                }
            }
        }

        return minLength == Integer.MAX_VALUE ? -1 : minLength;
    }


    private int bfs(Iterable<Integer> vVertices, Iterable<Integer> wVertices) {
        distToV = new int[G.V()];
        distToW = new int[G.V()];
        markedV = new HashSet<>();
        markedW = new HashSet<>();
        queueV = new ArrayDeque<>();
        queueW = new ArrayDeque<>();

        for (int v : vVertices) {
            distToV[v] = 0;
            markedV.add(v);
            queueV.add(v);
        }

        for (int w : wVertices) {
            distToW[w] = 0;
            markedW.add(w);
            queueW.add(w);
        }

        int minLength = Integer.MAX_VALUE;
        commonAncestor = -1;

        // Check if any vertex from vVertices is its own ancestor
        for (int v : vVertices) {
            if (markedW.contains(v)) {
                int length = distToV[v] + distToW[v];
                if (length < minLength) {
                    minLength = length;
                    commonAncestor = v;
                }
            }
        }

        while (!queueV.isEmpty() || !queueW.isEmpty()) {
            if (!queueV.isEmpty()) {
                int currentV = queueV.poll();
                for (int neighbor : G.adj(currentV)) {
                    if (!markedV.contains(neighbor)) {
                        distToV[neighbor] = distToV[currentV] + 1;
                        markedV.add(neighbor);
                        queueV.add(neighbor);
                        if (markedW.contains(neighbor)) {
                            int length = distToV[neighbor] + distToW[neighbor];
                            if (length < minLength) {
                                minLength = length;
                                commonAncestor = neighbor;
                            }
                        }
                    }
                }
            }

            if (!queueW.isEmpty()) {
                int currentW = queueW.poll();
                for (int neighbor : G.adj(currentW)) {
                    if (!markedW.contains(neighbor)) {
                        distToW[neighbor] = distToW[currentW] + 1;
                        markedW.add(neighbor);
                        queueW.add(neighbor);
                        if (markedV.contains(neighbor)) {
                            int length = distToV[neighbor] + distToW[neighbor];
                            if (length < minLength) {
                                minLength = length;
                                commonAncestor = neighbor;
                            }
                        }
                    }
                }
            }
        }

        return minLength == Integer.MAX_VALUE ? -1 : minLength;
    }


    public static void main(String[] args) {
        In in = new In("input.txt");
        Digraph G = new Digraph(in);

        DeluxeBFS bfs = new DeluxeBFS(G);

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


        //System.out.println(bfs.ancestor(set1, set2));
        //System.out.println(bfs.ancestor(9,9));


    }
}
