import java.util.*;
import java.io.*;

public class temp {
    ArrayList<String> words = new ArrayList<String>();

    public static void main(String[] args) throws IOException {
        File f = new File("data/Collins Scrabble Words (2019).txt");
        BufferedReader br = new BufferedReader(new FileReader(f));
        String in;

        while ((in = br.readLine()) != null) {
            if (passes(in)) System.out.println(in);
        }
    }

    public static boolean passes(String in) {
        if (in.length() != 8) return false;

        return in.charAt(2) == in.charAt(5) &&
                in.charAt(3) == in.charAt(6) &&
                in.charAt(4) == in.charAt(7);
    }
}