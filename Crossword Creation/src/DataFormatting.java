import java.util.*;
import java.io.*;

public class DataFormatting {
    static ArrayList<String> words;

    public static void main(String[] args) throws Exception {
        words = new ArrayList<String>();
        // File scrabbleWords = new File("data/Collins Scrabble Words (2019).txt");
        // addWords(scrabbleWords);

        // File SupplementalWords = new File("data/supplemental_words.txt");
        // addWords(SupplementalWords);

        File NYTWords = new File("data/words.txt");
        addWords(NYTWords);

        // File surnames = new File("data/1000 surnames.txt");
        // addNames(surnames);

        // File firstNames = new File("data/600 first names.txt");
        // addNames(firstNames);

        // File detailedNYT = new File("data/detailedNYT.txt");
        // addDetailedWords(detailedNYT);

        Collections.sort(words);
        removeDupes();

        exportWords("data/allWords.txt");
    }

    public static void removeDupes() {
        for (int i=1; i<words.size(); i++) {
            if (words.get(i).equals(words.get(i-1))) words.remove(i--);
        }
    }

    public static void addDetailedWords(File f) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));

            String s;
            while ((s = br.readLine()) != null) {

                if (allGood(s.substring(0, s.indexOf(" "))))
                    words.add(s);
            }

            br.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void exportWords(String fileName) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
            
            for (String s : words) {
                writer.append(s + "\n");
            }

            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addWords(File f) {
        try {
            Scanner sc = new Scanner(f);

            while (sc.hasNext()) {
                String s = sc.nextLine().toUpperCase();

                if (allGood(s))
                    words.add(s);
            }

            sc.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addNames(File f) {
        try {
            Scanner sc = new Scanner(f);

            while (sc.hasNext()) {
                String s = sc.nextLine();
                StringTokenizer st = new StringTokenizer(s, "\t");
                st.nextToken();

                words.add(st.nextToken());
            }

            sc.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addAbbreviations(File f) {
        try {
            Scanner sc = new Scanner(f);

            while (sc.hasNext()) {
                String s = sc.nextLine();

                if (allGood(s))
                    words.add(s);
            }

            sc.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean allGood(String s) {
        for (int i=0; i<s.length(); i++) {
            if (s.charAt(i) < 65 || s.charAt(i) > 90) return false;
        }

        return true;
    }
}