import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    private WordNet wordNet;

    public Outcast(WordNet wordnet){
        this.wordNet = wordnet;
    }         // constructor takes a WordNet object
    public String outcast(String[] nouns){
        int totalNouns = nouns.length;
        int[] distance = new int[totalNouns];

        // distnace
        int maxDist = Integer.MIN_VALUE;
        int outcastID = 0;
        for (int i = 0; i <totalNouns ; i++) {
            String curNoun = nouns[i];
            distance[i] = 0;

            for (int j = 0; j <  totalNouns; j++) {
                distance[i] += wordNet.distance(curNoun,nouns[j]);
            }

            if (maxDist < distance[i]){
                maxDist = distance[i];
                outcastID = i;
            }
        }

        return nouns[outcastID];
    }   // given an array of WordNet nouns, return an outcast
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}