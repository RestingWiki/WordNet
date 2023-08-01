
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;

import  java.util.Hashtable;
import  java.util.HashSet;
import  java.util.Arrays;
import  java.util.List;
import  java.util.ArrayList;

public class WordNet {
    private Hashtable<String ,Integer> SynonymID;
    private Hashtable<Integer,HashSet<String>>  keyValue;

    private Digraph G;


    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms){
        int synsetsSize = 0;


        // Check invalid argument
        if (synsets == null || hypernyms == null){
            throw  new IllegalArgumentException("");
        }


        // Create a symbol table
        SynonymID     = new Hashtable<>();

        // Create a set to check the nouns
        keyValue      = new Hashtable<>();

        // Read the synsets.txt file
        In in = new In(synsets);
        while(in.hasNextLine()){
            // Split the CSV line
            String[] arr = in.readLine().split(",");

            // Create a key-value pair
            int id = Integer.parseInt(arr[0]);

            // Put the noun in a set
            String[] nouns = arr[1].split(" ");
            HashSet<String> set = new HashSet<>(Arrays.asList(nouns));

            keyValue.put(id, set);
            for (String noun: set){
                SynonymID.put(noun,id);
            }
            synsetsSize++;
        }


        // Read the hypernyms file
        in = new In(hypernyms);

        // Create a Digraph object
        G = new Digraph(synsetsSize);





        while (in.hasNextLine()){
            String[] arr = in.readLine().split(",");

            int id = Integer.parseInt(arr[0]);

            for (int i = 1; i < arr.length; i++) {
                int hyper = Integer.parseInt(arr[i]);
                G.addEdge(id, hyper);
            }

        }


        // Check if the Digraph is a DAG
        DirectedCycle cycleDetector = new DirectedCycle(G);
        if (cycleDetector.hasCycle()) {
            throw new IllegalArgumentException("Input graph is not a Directed Acyclic Graph (DAG).");
        }

    }

    // returns all WordNet nouns
    public Iterable<String> nouns(){


        return SynonymID.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word){

        // Check invalid argument
        if (word == null){
            throw  new IllegalArgumentException("This is not a noun in the WordNet!");
        }


        return SynonymID.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB){
        // Check invalid argument
        if (nounA == null || nounB == null){
            throw  new IllegalArgumentException("noun is Null");
        }


        if (!isNoun(nounA) || !isNoun(nounB))
            throw new IllegalArgumentException("Noun is not in synset");




        //  Retrieve the vertex (id) for the corresponding noun
        //System.out.println(SynonymID.size());
        int v = SynonymID.get(nounA);
        int w = SynonymID.get(nounB);
        SAP sap = new SAP(G);

        return  sap.length(v,w);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB){
        // Check invalid argument
        if (nounA == null || nounB == null){
            throw  new IllegalArgumentException("");
        }


        if (!isNoun(nounA) || !isNoun(nounB))
            throw new IllegalArgumentException("");


        //  Retrieve the vertex (id) for the corresponding noun
        int v = SynonymID.get(nounA);
        int w = SynonymID.get(nounB);

        // Find the sca
        SAP sap = new SAP(G);
        int sca = sap.ancestor(v,w);

        if (sca == -1)
            return null;

        StringBuilder strb = new StringBuilder();

        // Create 2 BFS object for each nouns
        BreadthFirstDirectedPaths BFS_V = new BreadthFirstDirectedPaths(G,v);
        BreadthFirstDirectedPaths BFS_W = new BreadthFirstDirectedPaths(G,w);


        Iterable<Integer> pathV  =  BFS_V.pathTo(sca);
        Iterable<Integer> pathW = BFS_W.pathTo(sca);
        Stack<Integer> stack = new Stack<>();



        for (Integer vertex: pathW) {
            stack.push(vertex);
        }
        stack.pop();    // Pop the duplicate sca


        // Create a list to store the path
        List<Integer> path = new ArrayList<>();
        for (Integer vertex: pathV) {
            path.add(vertex);
        }

        while (!stack.isEmpty()){
            path.add(stack.pop());
        }

        for (Integer vertex: path){
            if (strb.length() == 0)
                strb.append(keyValue.get(vertex));
            else
                strb.append("-").append(keyValue.get(vertex));
        }


        return strb.toString();
    }




    // do unit testing of this class
    public static void main(String[] args){

    }
}