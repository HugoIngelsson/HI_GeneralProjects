import java.util.*;
import java.io.*;

public class VariableCodeCompression {
    static ArrayList<String> input;
    static HashMap<Character, Integer> frequencyCounts;
    static ArrayList<Frequency> frequenciesSorted;
    static HashMap<Character, String> mappings;
    static HashMap<String, Character> inverseMappings;

    public static void main(String[] args) throws IOException {
        input = new ArrayList<String>();
        frequencyCounts = new HashMap<Character, Integer>();
        frequenciesSorted = new ArrayList<Frequency>();
        
        readInput("input2.txt");
        // countFrequencies(input);
        countExplicitFrequencies(input);
        sortFrequencies(frequencyCounts);

        FindVariableCompression fvp = new FindVariableCompression(frequenciesSorted);
        fvp.developDP();
        fvp.assignMappings("", fvp.frequencies.size()-1);

        mappings = new HashMap<Character, String>();
        for (int i=0; i<fvp.frequencies.size(); i++) {
            mappings.put(fvp.frequencies.get(i).c, fvp.assignments[i]);
        }

        Huffman huff = new Huffman(frequenciesSorted);
        huff.developTree();

        System.out.println(mappings);
        System.out.println("Static ASCII: " + 8*fvp.sumTil[fvp.sumTil.length-1]);
        System.out.println("Fixed-length bits: " + (int)(Math.ceil(Math.log(frequenciesSorted.size())/Math.log(2))*fvp.sumTil[fvp.sumTil.length-1]));
        System.out.println("Variable-length bits: " + fvp.bestCounts[fvp.bestCounts.length-1]);
        System.out.println("Huffman: " + (huff.getNodes().get(0).sumSubtree() - huff.getNodes().get(0).getValue()));

        // printBits("output.txt", input, mappings);
        //inverseMappings = inverseMap(mappings);
        // decodeBits("output.txt");
        // decodeFixedLength("output.txt", 8);
    }

    public static void decodeBits(String fileName) throws IOException {
        HashMap<String, Character> inverseMappings = new HashMap<String, Character>();
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String in = br.readLine();
        br.close();

        int numKeys = Integer.parseInt(in.substring(0, 16), 2);
        int i = 16;
        while (numKeys --> 0) {
            char c = (char)Integer.parseInt(in.substring(i, i+16), 2);
            int keyLength = Integer.parseInt(in.substring(i+16, i+24), 2);
            i += 24;

            inverseMappings.put(in.substring(i, i+keyLength), c);
            i += keyLength;
        }

        System.out.println(inverseMappings);

        String runningInput = "";
        while (i < in.length()) {
            runningInput += in.charAt(i);

            if (inverseMappings.containsKey(runningInput)) {
                System.out.print(inverseMappings.get(runningInput));
                runningInput = "";
            }

            i++;
        }
    }

    public static void decodeFixedLength(String fileName, int length) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String in = br.readLine();
        PrintWriter pw = new PrintWriter(System.out);

        for (int i=0; i<in.length(); i+=length) {
            if (i + length - 1 < in.length()) {
                pw.print((char)(Integer.parseInt(in.substring(i, i+length), 2)));
            }
            else {
                pw.print((char)(Integer.parseInt(in.substring(i), 2)));
            }
        }

        br.close();
        pw.close();
    }

    public static void printBits(String fileName, ArrayList<String> input, HashMap<Character, String> mappings) throws IOException {
        PrintWriter pw = new PrintWriter(new File(fileName));

        pw.print(FindVariableCompression.intToBinary(mappings.keySet().size(), 15));
        for (Map.Entry<Character, String> e : mappings.entrySet()) {
            pw.print(FindVariableCompression.intToBinary(e.getKey(), 15));
            pw.print(FindVariableCompression.intToBinary(e.getValue().length(), 7));
            pw.print(e.getValue());
        }

        for (String s : input) {
            for (char c : s.toCharArray()) {
                pw.print(mappings.get(c));
            }

            pw.print(mappings.get('\n'));
        }

        pw.close();
    }

    public static HashMap<String, Character> inverseMap(HashMap<Character, String> map) {
        HashMap<String, Character> out = new HashMap<String, Character>();

        for (Map.Entry<Character, String> e : map.entrySet()) out.put(e.getValue(), e.getKey());

        return out;
    }

    public static void sortFrequencies(HashMap<Character, Integer> frequencyCounts) {
        for (Map.Entry<Character, Integer> freq : frequencyCounts.entrySet()) {
            frequenciesSorted.add(new Frequency(freq.getKey(), freq.getValue()));
        }

        Collections.sort(frequenciesSorted);
    }

    public static void countFrequencies(ArrayList<String> input) {
        for (String s : input) {
            for (char c : s.toCharArray()) {
                if (frequencyCounts.containsKey(c)) frequencyCounts.put(c, frequencyCounts.get(c)+1);
                else frequencyCounts.put(c, 1);
            }
        }

        if (input.size() > 1) frequencyCounts.put('\n', input.size()-1);
    }

    public static void countExplicitFrequencies(ArrayList<String> input) {
        for (String s : input) {
            frequencyCounts.put(s.charAt(0), Integer.parseInt(s.substring(2)));
        }
    }

    public static void readInput(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));

        String in;
        while ((in = br.readLine()) != null) {
            input.add(in);
        }

        br.close();
    }
}
