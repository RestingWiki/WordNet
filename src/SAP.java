import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.In;

import java.util.HashMap;
import java.util.Iterator;

public class SAP {
    private final Digraph G;
    private final HashMap<String, Integer> cacheLength;
    private final HashMap<String,Integer> cacheAncestor;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null)
            throw new IllegalArgumentException("");

        this.G = new Digraph(G);
        this.cacheLength = new HashMap<>();
        this.cacheAncestor = new HashMap<>();
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        validateVertex(v);
        validateVertex(w);

        String key1 = v + "-" + w;
        String key2 = w + "-" + v;
        if (cacheLength.containsKey(key1)) {
            return cacheLength.get(key1);
        }

        if (cacheLength.containsKey(key2)){
            return  cacheLength.get(key2);
        }

        DeluxeBFS bfs = new DeluxeBFS(G);
        int result = bfs.length(v, w);
        cacheLength.put(key1, result);
        cacheLength.put(key2,result);
        return result;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        validateVertex(v);
        validateVertex(w);

        String key1 = v + "-" + w;
        String key2 = w + "-" + v;
        if (cacheAncestor.containsKey(key1)) {
            return cacheAncestor.get(key1);
        }

        if (cacheAncestor.containsKey(key2)){
            return  cacheAncestor.get(key2);
        }

        DeluxeBFS bfs = new DeluxeBFS(G);
        int result = bfs.ancestor(v, w);
        cacheAncestor.put(key1, result);
        cacheAncestor.put(key2, result);
        return result;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null)
            throw new IllegalArgumentException("");

        if (!v.iterator().hasNext() || !w.iterator().hasNext())
            return -1;

        for (Integer vertex : v) {
            if (vertex == null)
                throw new IllegalArgumentException("Iterable contains null value!");
            validateVertex(vertex);
        }

        for (Integer vertex : w) {
            if (vertex == null)
                throw new IllegalArgumentException("Iterable contains null value!");
            validateVertex(vertex);
        }

        String token1 = createKey(v);
        String token2 = createKey(w);
        String key1 = token1.concat(token2);
        String key2 = token2.concat(token1);


        if (cacheLength.containsKey(key1)) {
            return cacheLength.get(key1);
        }
        if (cacheLength.containsKey(key2)) {
            return cacheLength.get(key2);
        }

        DeluxeBFS bfs = new DeluxeBFS(G);
        int result = bfs.length(v, w);
        cacheLength.put(key1, result);
        cacheLength.put(key2, result);
        return result;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null)
            throw new IllegalArgumentException("");

        if (!v.iterator().hasNext() || !w.iterator().hasNext())
            return -1;

        for (Integer vertex : v) {
            if (vertex == null)
                throw new IllegalArgumentException("Iterable contains null value!");
            validateVertex(vertex);
        }

        for (Integer vertex : w) {
            if (vertex == null)
                throw new IllegalArgumentException("Iterable contains null value!");
            validateVertex(vertex);
        }

        String token1 = createKey(v);
        String token2 = createKey(w);
        String key1 = token1.concat(token2);
        String key2 = token2.concat(token1);


        if (cacheAncestor.containsKey(key1)) {
            return cacheAncestor.get(key1);
        }
        if (cacheAncestor.containsKey(key2)) {
            return cacheAncestor.get(key2);
        }

        DeluxeBFS bfs = new DeluxeBFS(G);
        int result = bfs.ancestor(v, w);
        cacheAncestor.put(key1, result);
        cacheAncestor.put(key2, result);
        return result;
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= G.V())
            throw new IllegalArgumentException("Vertex out of range!");
    }
    private String createKey(Iterable<Integer> iterable) {
        StringBuilder sb = new StringBuilder();
        Iterator<Integer> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            Integer value = iterator.next();
            sb.append(value);
            if (iterator.hasNext()) {
                sb.append(",");
            }
        }
        return sb.toString();
    }
    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In("input.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);

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

        System.out.println(sap.ancestor(set1, set2));
    }
}
