import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {
    private final Digraph G;
    private int dist;
    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G){
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

        BreadthFirstDirectedPaths BFS_V = new BreadthFirstDirectedPaths(G,v);
        BreadthFirstDirectedPaths BFS_W = new BreadthFirstDirectedPaths(G,w);

        for (int i = 0; i < G.V(); i++) {
            if (BFS_V.hasPathTo(i) && BFS_W.hasPathTo(i)){
                int tempDist = BFS_V.distTo(i) + BFS_W.distTo(i);       // Calculate new dist
                if (tempDist < dist) {
                    sca  = i;
                    dist = tempDist;
                }
            }
        }
        
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


        // Symbol table  for each BFS
        ST<Integer,BreadthFirstDirectedPaths> st = new ST<>();


        for (Integer vertex: v) {
            if (vertex == null)
                throw new IllegalArgumentException("Iterable contains null value!");
            st.put(vertex,new BreadthFirstDirectedPaths(G,vertex));
        }

        for (Integer vertex: w) {
            if (vertex == null)
                throw new IllegalArgumentException("Iterable contains null value!");
            st.put(vertex,new BreadthFirstDirectedPaths(G,vertex));
        }

        int sca = -1;
        dist = Integer.MAX_VALUE;

        for (int i = 0; i < G.V(); i++){
            boolean reachable = false;
            int tempDist_V = 0;
            int tempDist_W = 0;

            // Check the first set of vertices
            for (Integer key: v) {
                 BreadthFirstDirectedPaths bfs = st.get(key);
                 if (bfs.hasPathTo(i))
                 {
                     if (tempDist_V == 0)
                         tempDist_V = bfs.distTo(i);      // Initial path
                     else if (tempDist_V > bfs.distTo(i))
                         tempDist_V = bfs.distTo(i);      // Shorter path


                     reachable = true;
                 }
            }

            // Check the second set of vertices
            if (reachable) {
                for (Integer key : w) {
                    BreadthFirstDirectedPaths bfs = st.get(key);
                    if (bfs.hasPathTo(i))
                    {
                        if (tempDist_W == 0)
                            tempDist_W = bfs.distTo(i);      // Initial path
                        else if (tempDist_W > bfs.distTo(i))
                            tempDist_W = bfs.distTo(i);      // Shorter path


                        // Update sca and dist
                        if (dist < tempDist_W + tempDist_V){
                            dist = tempDist_V + tempDist_W;
                            sca = i;
                        }

                    }
                }
            }


        }


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
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}