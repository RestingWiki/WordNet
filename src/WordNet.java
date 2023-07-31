import edu.princeton.cs.algs4.SET;
import  edu.princeton.cs.algs4.ST;
import  edu.princeton.cs.algs4.In;


import java.util.ArrayList;
import java.util.List;

public class WordNet {
    private ST<Integer,String> st;
    private SET<String> nounSET;

    private Digraph G;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms){
        int synsetsSize = 0;


        // Check invalid argument
        if (synsets == null || hypernyms == null){
            throw  new IllegalArgumentException("");
        }


        // Create a symbol table
        st      = new ST<>();

        // Create a set to check the nouns
        nounSET = new SET<>();

        // Read the synsets.txt file
        In in = new In(synsets);
        while(in.hasNextLine()){
            // Split the CSV line
            String[] arr = in.readLine().split(",");

            // Create a key-value pair
            int id = Integer.parseInt(arr[0]);
            st.put(id,arr[1]);
            nounSET.add(arr[1]);

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
                G.addEdge(id,hyper);
            }

        }

    }

    // returns all WordNet nouns
    public Iterable<String> nouns(){
        List<String> nouns = new ArrayList<>();
        for (Integer key: st) {
            nouns.add(st.get(key));
        }
        return nouns;
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word){
        // Check invalid argument
        if (word == null){
            throw  new IllegalArgumentException("");
        }


        return nounSET.contains(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB){
        // Check invalid argument
        if (nounA == null || nounB == null){
            throw  new IllegalArgumentException("");
        }

        if (!isNoun(nounA) || !isNoun(nounB))
            throw new IllegalArgumentException("");

        return  0;
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB){
        return null;
    }




    // do unit testing of this class
    public static void main(String[] args){}
}