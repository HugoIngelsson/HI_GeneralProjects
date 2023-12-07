import java.util.*;
import java.io.*;

public class App {
    static int rows, cols, clues, max, good = 0, count;
    static char[][] board;
    static Entry[][] entriesA, entriesV;
    static List<String>[] words = new ArrayList[25];
    static boolean[][] frozen;

    public static void main(String[] args) throws Exception {
        File wordData = new File("data/allWords.txt");
        getWords(wordData);

        File boardData = new File("data/tofill.txt");
        System.out.println();

        for (int i=0; i<1000; i++) {
            clues = 0;
            boolean esc = true;
            getBoard(boardData);

            createEntries();
            crossEntries();

            printBoard();
            System.out.println(numGood() + "\n");

            max = 0;
            count = 0;
            while (count++ < max*10+5 && max != clues) {
                replaceEntry();

                // printBoard();
                good = numGood();
                System.out.println(good + " / " + clues + "\n");

                if (numGood() > max) {
                    max = numGood();
                    count = 0;

                    if (esc && max == clues - 1) {
                        esc = false;
                        printToFile("output/almost.txt");
                        count -= 500;
                    }
                }
            }

            if (max == clues) {
                System.out.println("YAY! ... " + i);
                printToFile("output/success.txt");
                break;
            }
        }
    }

    public static void printToFile(String fileName) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));

            for (int r=0; r<rows; r++) {
                int count = 0;
                for (int c=0; c<cols; c++) {
                    if (entriesA[r][c] != null) {
                        writer.append(entriesA[r][c].getWord());
                        count = entriesA[r][c].getWord().length();
                    }
                    else if (count <= 0) {
                        writer.append(" ");
                    }
                    count--;
                }
                writer.append("\n");
            }

            writer.append("\n");
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int numGood() {
        int ret=0;

        for (int r=0; r<rows; r++) {
            for (int c=0; c<cols; c++) {
                if (entriesA[r][c] != null && (entriesA[r][c].getIsWord() || entriesA[r][c].getIsFrozen())) ret++;
                if (entriesV[r][c] != null && (entriesV[r][c].getIsWord() || entriesV[r][c].getIsFrozen())) ret++;
            }
        }

        return ret;
    }

    public static void replaceEntry() {
        minReplaceValue = Double.MAX_VALUE;
        for (int r=0; r<rows; r++) {
            for (int c=0; c<cols; c++) {
                if (entriesA[r][c] != null && !entriesA[r][c].getIsFrozen() && entriesA[r][c].shouldChange()) {
                    considerWords(entriesA[r][c]);
                }
                if (entriesV[r][c] != null && !entriesV[r][c].getIsFrozen() && entriesV[r][c].shouldChange()) {
                    considerWords(entriesV[r][c]);
                }
            }
        }
        
        minReplaceEntry.setWord(bestWord);
        minReplaceEntry.setLastMove(count);

        Entry[] cross = minReplaceEntry.getCrosses();
        int[] overlaps = minReplaceEntry.getOverlaps();
        for (int i=0; i<bestWord.length(); i++) {
            cross[i].setLetter(bestWord.charAt(i), overlaps[i]);
            cross[i].setIsWord(isWord(cross[i].getWord()));
        }
    }

    public static void printBoard() {
        for (int r=0; r<rows; r++) {
            int count = 0;
            for (int c=0; c<cols; c++) {
                if (entriesA[r][c] != null) {
                    System.out.print(entriesA[r][c].getWord());
                    count = entriesA[r][c].getWord().length();
                }
                else if (count <= 0) {
                    System.out.print(" ");
                }
                count--;
            }
            System.out.println();
        }
    }

    static Entry minReplaceEntry;
    static double minReplaceValue;
    static ArrayList<String> bestWords;
    static String bestWord;
    static boolean whichList;
    public static void considerWords(Entry e) {
        String[][] fragments = e.splitWords();
        boolean[] trueWords = e.crossesAreWords();

        int l = e.getWord().length();
        for (String s : words[l]) {
            if (s != e.getWord() && s != e.getLastWord() && !badCross(s, e.getCrosses(), e.getOverlaps())) {
                double adj = 0;
                if (!e.getIsWord()) {
                    adj -= 2;
                    if (clues - good <= 3) {
                        adj -= 2;
                        if (clues - good <= 2) adj -= 0.3;
                        if (clues - good <= 1) adj -= 0.3;
                    }
                }

                double val = replaceValue(s, fragments, trueWords, adj); 

                if (val > 0) val *= Math.log(e.getChanges());
                else if (e.getLastMove() - count <= 2) val += 5*Math.random();

                if (val < minReplaceValue) {
                    minReplaceValue = val;
                    minReplaceEntry = e;
                    bestWord = s;
                    whichList = true;
                }
            }
        }
    }

    public static boolean badCross(String s, Entry[] crosses, int[] overlaps) {
        for (int i=0; i<s.length(); i++) {
            if (crosses[i].getIsFrozen() && s.charAt(i) != crosses[i].getWord().charAt(overlaps[i])) return true;
        }

        return false;
    }

    static double[] vals = {1, 2.4, 1, 1.7, 1, 2.4, 1.7, 1.7, 1, 3.1, 2.4, 1, 1.7, 1, 1, 1.7, 3.1, 1, 1, 1, 1.7, 3.1, 2.4, 3.1, 3.1, 2.4};
    public static double replaceValue(String s, String[][] words, boolean[] areWords, double adj) {
        double ret = adj;

        for (int i=0; i<s.length(); i++) {
            boolean newWord = isWord(words[i][0] + s.charAt(i) + words[i][1]);

            if (areWords[i] && !newWord) {
                ret += 3*vals[s.charAt(i)-65];
            }
            else if (!areWords[i] && newWord) {
                ret -= 1.3*vals[s.charAt(i)-65];
            }
            else {
                ret += vals[s.charAt(i)-65];
            }
        }

        if (ret < 0) return ret;
        return ret / Math.pow(s.length(), 2) * (0.4+Math.random());
    }

    public static void crossEntries() {
        for (int r=0; r<rows; r++) {
            for (int c=0; c<cols; c++) {
                if (entriesA[r][c] != null) {
                    fillEntry(r,c,entriesA[r][c]);
                }
            }
        }
    }

    public static void fillEntry(int r, int c, Entry e) {
        for (int col=c; col<c+e.getWord().length(); col++) {
            int row = r;
            while (entriesV[row][col] == null) row--;
            e.insertCross(entriesV[row][col], col-c, r-row);
            entriesV[row][col].insertCross(e, r-row, col-c);
        }
    }

    public static void createEntries() {
        entriesA = new Entry[rows][cols];
        entriesV = new Entry[rows][cols];

        for (int r=0; r<rows; r++) {
            boolean turn = true;
            Entry e = new Entry("", false);
            String word = "";

            for (int c=0; c<cols; c++) {
                if (turn && board[r][c] != ' ') {
                    e = new Entry("", false);
                    entriesA[r][c] = e;
                    if (frozen[r][c]) e.makeFrozen();
                }
                
                if (board[r][c] == ' ') {
                    if (!turn) {
                        e.setWord(word);
                        e.setIsWord(isWord(word));
                        clues++;
                    }
                    word = "";
                    turn = true;
                }
                else {
                    turn = false;
                    word = word + board[r][c];

                    if (c == cols-1) {
                        e.setWord(word);
                        e.setIsWord(isWord(word));
                        clues++;
                    }
                }
            }
        }

        for (int c=0; c<cols; c++) {
            boolean turn = true;
            Entry e = new Entry("", false);
            String word = "";

            for (int r=0; r<rows; r++) {
                if (turn && board[r][c] != ' ') {
                    e = new Entry("", false);
                    entriesV[r][c] = e;
                }
                
                if (board[r][c] == ' ') {
                    if (!turn) {
                        e.setWord(word);
                        e.setIsWord(isWord(word));
                        clues++;
                    }
                    word = "";
                    turn = true;
                }
                else {
                    turn = false;
                    word = word + board[r][c];

                    if (r == rows-1) {
                        e.setWord(word);
                        e.setIsWord(isWord(word));
                        clues++;
                    }
                }
            }
        }
    }

    public static boolean isWord(String s) {
        int start = 0, end = words[s.length()].size()-1;
        ArrayList<String> comparison = (ArrayList<String>)words[s.length()];

        while (start != end && start != end-1) {
            int mid = (start + end)/2;
            int compare = s.compareTo(comparison.get(mid));

            if (compare == 0) return true;
            else if (compare < 0) {
                end = mid;
            }
            else {
                start = mid;
            }
        }

        return s.equals(comparison.get(start)) || s.equals(comparison.get(end));
    }

    public static void getWords(File f) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            for (int i=0; i<25; i++) {
                words[i] = new ArrayList<String>();
            }

            String in;
            while ((in = br.readLine()) != null) {
                //@SuppressWarnings("unchecked");
                if (in.length() < 25) {
                    words[in.length()].add(in);
                }
            }

            br.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getBoard(File f) {
        try {
            Scanner sc = new Scanner(f);
            StringTokenizer st = new StringTokenizer(sc.nextLine());
            double[] freqs = {0.084966, 0.020720, 0.045388, 0.033844, 0.111607, //ABCDE
                              0.018121, 0.024705, 0.030034, 0.075448, 0.001965, //FGHIJ
                              0.011016, 0.054893, 0.030129, 0.066544, 0.071635, //KLMNO
                              0.031671, 0.001962, 0.075809, 0.057351, 0.069509, //PQRST UVWXYZ
                              0.036308, 0.010074, 0.012899, 0.002902, 0.017779, 0.002722};

            rows = Integer.parseInt(st.nextToken());
            cols = Integer.parseInt(st.nextToken());
            board = new char[rows][cols];
            frozen = new boolean[rows][cols];

            int r=0;
            while (sc.hasNext()) {
                String in = sc.nextLine();

                for (int c=0; c<cols; c++) {
                    if (in.charAt(c) == '.') {
                        double rand = Math.random();
                        for (int i=0; i<26; i++) {
                            rand -= freqs[i];
                            if (rand < 0 || i==25) {
                                board[r][c] = (char)(65+i);
                                i=26;
                            }
                        }
                    }
                    else if (in.charAt(c) == '#') {
                        board[r][c] = ' ';
                    }
                    else {
                        board[r][c] = in.charAt(c);
                        frozen[r][c] = true;
                    }
                }

                r++;
            }

            sc.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    } 
}
