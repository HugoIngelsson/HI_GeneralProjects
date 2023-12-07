import java.util.*;
import java.io.*;


public class App {
    static String[] words;
    static char[][] board;
    static ArrayList<String> found = new ArrayList<String>();
    static ArrayList<String> traces = new ArrayList<String>();
    static int operations;
    static char[] letters = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
    static int dubSquare, dubLetter, tripLetter;

    public static void main(String[] args) throws Exception {
        File wordData = new File("data/Collins Scrabble Words (2019).txt");
        File boardData = new File("data/boggle board.txt");
        getWords(wordData);
        // getBoard(boardData);
        createBoard(10,10);
        
        boolean[][] state = new boolean[board.length][board[0].length];
        int[] range = {0,279495};

        for (char[] c : board) System.out.println(Arrays.toString(c));
        //System.out.println(dubSquare + " " + dubLetter + " " + tripLetter);

        operations = 0;
        for (int r=1; r<board.length+2; r+=3) {
            for (int c=1; c<board[0].length+2; c+=3) {
                recursiveSolve(r,c,state,range, "", 0, "");
            }
        }

        // System.out.println("\n(" + found.size() + ")");
        // System.out.println("0 SWAPS: ");
        for (WordPath w : getBestWords()) System.out.println(w);

        // operations = 0;
        // found = new ArrayList<String>();
        // traces = new ArrayList<String>();
        // for (int r=1; r<board.length+2; r+=3) {
        //     for (int c=1; c<board[0].length+2; c+=3) {
        //         recursiveSolve(r,c,state,range, "", 1, "");
        //     }
        // }

        // System.out.println("\n(" + found.size() + ")");
        // System.out.println("1 SWAP: ");
        // for (WordPath w : getBestWords()) System.out.println(w);

        // operations = 0;
        // found = new ArrayList<String>();
        // traces = new ArrayList<String>();
        // for (int r=1; r<board.length+2; r+=3) {
        //     for (int c=1; c<board[0].length+2; c+=3) {
        //         recursiveSolve(r,c,state,range, "", 2, "");
        //     }
        // }

        // System.out.println("\n(" + found.size() + ")");
        // System.out.println("2 SWAPS: ");
        // for (WordPath w : getBestWords()) System.out.println(w);

        // operations = 0;
        // found = new ArrayList<String>();
        // traces = new ArrayList<String>();
        // for (int r=1; r<board.length+2; r+=3) {
        //     for (int c=1; c<board[0].length+2; c+=3) {
        //         recursiveSolve(r,c,state,range, "", 3, "");
        //     }
        // }

        // System.out.println("\n(" + found.size() + ")");
        // System.out.println("3 SWAPS: ");
        // for (WordPath w : getBestWords()) System.out.println(w);
    }

    public static WordPath[] getBestWords() {
        WordPath[] bestWords = new WordPath[20];

        for (int i=0; i<found.size(); i++) {
            WordPath checkWord = new WordPath(found.get(i), traces.get(i).substring(1), getPoints(found.get(i), traces.get(i).substring(1)));

            for (int j=0; j<20; j++) {
                if (bestWords[j] == null) {
                    bestWords[j] = checkWord;
                    j = 20;
                }
                else if (bestWords[j].value < checkWord.value) {
                    WordPath temp = bestWords[j];
                    bestWords[j] = checkWord;
                    checkWord = temp;
                }
                else if (bestWords[j].word.equals(checkWord.word)) j = 20;
            }
        }

        return bestWords;
    }

    public static int getPoints(String word, String path) {
        StringTokenizer st = new StringTokenizer(path, "-");
        int[] values = {1,4,5,3,1,5,3,4,1,7,6,3,4,2,1,4,8,2,2,2,4,5,5,7,4,8};
        int ret = 0;
        boolean dubWord = false;

        int i=0;
        while (st.hasMoreTokens()) {
            int next = Integer.parseInt(st.nextToken());

            if (next == dubSquare) dubWord = true;
            if (next == dubLetter) {
                ret += 2*values[word.charAt(i++)-65];
            }
            else if (next == tripLetter) {
                ret += 3*values[word.charAt(i++)-65];
            }
            else {
                ret += values[word.charAt(i++)-65];
            }
        }

        if (dubWord) ret *= 2;
        if (word.length() >= 6) ret += 10;
        return ret;
    }

    public static double[] runSimulations(int r, int c, int sims) {
        double[] ret = new double[4];

        ret[0] = Integer.MAX_VALUE;
        for (int s=0; s<sims; s++) {
            createBoard(r,c);
            operations = 0;

            boolean[][] state = new boolean[board.length][board[0].length];
            found = new ArrayList<String>();

            for (int i=0; i<board.length; i++) {
                for (int j=0; j<board[i].length; j++) {
                    state[i][j] = true;
                    recursiveSolve(i, j, state, getRange(board[i][j] + "", 0, 279495), board[i][j] + "", 0, board[i][j]+"");
                    state[i][j] = false;
                }
            }

            ret[0] = Math.min(ret[0], found.size());
            ret[1] += found.size();
            ret[2] = Math.max(ret[2], found.size());
            ret[3] += operations;
        }
        ret[1] /= sims;
        ret[3] /= sims;

        return ret;
    }

    public static void recursiveSolve(int r, int c, boolean[][] state, int[] range, String current, int swaps, String trace) {
        operations++;

        if (range[0] == -1) return;
        if (words[range[0]].equals(current) && current.length() > 2) {
            found.add(current);
            traces.add(trace);
        }

        for (int i=-1; i<=1; i++) {
            for (int j=-1; j<=1; j++) {
                if (r+i>=0 && r+i<state.length && c+j>=0 && c+j<state[0].length && !state[r+i][c+j]) {
                    state[r+i][c+j] = true;

                    if (swaps > 0) {
                        for (char ch : letters)
                            recursiveSolve(r+i, c+j, state, getRange(current+ch, range[0], range[1]), current+ch, swaps-1, trace+"-"+((r+i)*board.length+c+j));
                    }

                    recursiveSolve(r+i, c+j, state, getRange(current+board[r+i][c+j], range[0], range[1]), current+board[r+i][c+j], swaps, trace+"-"+((r+i)*board.length+c+j));
                    state[r+i][c+j] = false;
                }
            }
        }
    }

    public static void getWords(File f) {
        try {
            Scanner sc = new Scanner(f);
            sc.nextLine(); sc.nextLine();
            words = new String[279496];

            int i = 0;
            while (sc.hasNext()) {
                String in = sc.nextLine();
                words[i++] = in;
            }

            sc.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createBoard(int r, int c) {
        board = new char[r][c];
        // dubSquare = (int)(Math.random()*r*c);
        // dubLetter = (int)(Math.random()*r*c);
        // tripLetter = (int)(Math.random()*r*c);
        dubSquare = -1;
        dubLetter = -1;
        tripLetter = -1;

        for (int i=0; i<r; i++) {
            for (int j=0; j<c; j++) {
                board[i][j] = letters[(int)(Math.random()*26)];
            }
        }
    }

    public static void getBoard(File f) {
        try {
            Scanner sc = new Scanner(f);
            StringTokenizer st = new StringTokenizer(sc.nextLine());
            board = new char[Integer.parseInt(st.nextToken())][Integer.parseInt(st.nextToken())];
            dubSquare = Integer.parseInt(st.nextToken());
            dubLetter = Integer.parseInt(st.nextToken());
            tripLetter = Integer.parseInt(st.nextToken());

            int i = 0;
            while (sc.hasNext()) {
                String in = sc.nextLine();

                for (int j=0; j<board[0].length; j++) board[i][j] = in.charAt(j);
                i++;
            }

            sc.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int[] getRange(String key, int start, int end) {
        int[] ret = {-1, -1};
        if (words[start].startsWith(key)) ret[0] = start;
        if (words[end].startsWith(key)) ret[1] = end;

        while (start != end) {
            int mid = (start + end) / 2;

            if (!words[mid].startsWith(key)) {
                if (words[mid].compareTo(key) > 0) {
                    end = mid;
                }
                else {
                    start = mid;
                }
            }
            else {
                ret[0] = getLowerBound(key, start, mid);
                ret[1] = getUpperBound(key, mid, end);
                return ret;
            }

            if (start == end-1 && (!words[start].startsWith(key) || !words[end].startsWith(key))) {
                if (ret[0] == -1) ret[0] = ret[1];
                else if (ret[1] == -1) ret[1] = ret[0];
                return ret;
            }
        }

        return ret;
    }

    public static int getLowerBound(String key, int start, int end) {
        if (words[start].startsWith(key)) return start;

        while (start != end-1) {
            int mid = (start + end) / 2;

            if (!words[mid].startsWith(key)) {
                start = mid;
            }
            else {
                end = mid;
            }
        }

        return end;
    }

    public static int getUpperBound(String key, int start, int end) {
        if (words[end].startsWith(key)) return end;

        while (start != end-1) {
            int mid = (start + end) / 2;

            if (!words[mid].startsWith(key)) {
                end = mid;
            }
            else {
                start = mid;
            }
        }

        return start;
    }
}
