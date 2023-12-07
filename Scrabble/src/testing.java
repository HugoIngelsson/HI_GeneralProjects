import java.util.*;
import java.io.*;

public class testing {
    static int[] mostOfLetter;
    public static void main(String[] args) throws Exception {
        mostOfLetter = new int[26];

        makeWordsIntoCharCounts("data/Collins Scrabble Words (2019).txt", "data/identityData.txt");

        // fileReader("data/Collins Scrabble Words (2019).txt");

        // for (int i=0; i<26; i++) {
        //     System.out.println(((char)(i+65)) + ": " + mostOfLetter[i]);
        // }
    }

    public static void fileReader(String fileName) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String in;

        while ((in = br.readLine()) != null) {
            for (int i=0; i<26; i++) {
                mostOfLetter[i] = Math.max(mostOfLetter[i], countMatches(in, (char)(i+65)));
            }
        }

        br.close();
    }

    public static int countMatches(String in, char c) {
        if (in.length() == 0) return 0;

        return (in.charAt(0) == c ? 1 : 0) + countMatches(in.substring(1), c);
    }

    public static void makeWordsIntoCharCounts(String inputFile, String fileName) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
        ArrayList<Identity> wordIdentities = new ArrayList<Identity>();
        String in;

        while ((in = br.readLine()) != null) {
            String identity = "";
            for (int i=0; i<26; i++) {
                identity = identity + countMatches(in, (char)(i+65));
            }
            wordIdentities.add(new Identity(identity, in));
        }

        Collections.sort(wordIdentities);
        for (int i=1; i<wordIdentities.size(); i++) {
            if (wordIdentities.get(i).getIdentity().equals(wordIdentities.get(i-1).getIdentity())) {
                wordIdentities.get(i-1).addWord(wordIdentities.get(i).getWords().get(0));
                wordIdentities.remove(i--);
            }
        }
        for (int i=0; i<wordIdentities.size(); i++) {
            writer.append(wordIdentities.get(i).getIdentity() + " ");
            for (int j=0; j<wordIdentities.get(i).getWords().size(); j++) {
                writer.append(wordIdentities.get(i).getWords().get(j));
                if (j != wordIdentities.get(i).getWords().size()-1) writer.append(" ");
                else writer.append("\n");
            }
        }

        writer.close();
        br.close();
    }
}