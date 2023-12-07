import java.util.*;
import java.io.*;

public class OutputCross {
    static int rows, cols, day;
    static char[][] board;
    static String[][] formatBoard;
    static ArrayList<String> across, vertical;
    static double totalDifficulty;
    static int clueDelta;

    public static void main(String[] args) throws IOException {
        File input = new File("outputBin.txt");
        readFile(input);

        File wordData = new File("data/detailed.txt");
        getWords(wordData);

        getClues();

        System.out.println("Average Difficulty: " + (totalDifficulty / (across.size() + vertical.size() - clueDelta)));
        //printCross("output/outputCross.txt");
    }

    public static void printCross(String fileName) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
            
        for (String[] ss : formatBoard) {
            for (String s : ss) {
                if (s.length() > 1) {
                    writer.append(s + ".. ");
                }
                else {
                    if (s.equals("#")) {
                        writer.append("#### ");
                    }
                    else {
                        writer.append(s + "... ");
                    }
                
                }
            }

            writer.append("\n");
            for (int i=0; i<2; i++) {
                for (String s : ss) {
                    if (s.equals("#")) {
                        writer.append("#### ");
                    }
                    else {
                        writer.append(".... ");
                    }
                }
                writer.append("\n");
            }
        }

        writer.append("Average Difficulty: " + (totalDifficulty / (across.size() + vertical.size() - clueDelta)));

        writer.append("\n\nACROSS\n");
        for (String s : across) {
            writer.append(s + "\n");
        }

        writer.append("\nVERTICAL\n");
        for (String s : vertical) {
            writer.append(s + "\n");
        }

        writer.append("\n");
        writer.close();
    }

    public static String findClue(String s) {
        String ret = "???";

        int len = 1;
        int dif = -100;
        double dist = (totalDifficulty + day) / (across.size() + vertical.size() - clueDelta + 1);
        for (int i=0; i<answers.size(); i++) {
            if (s.equals(answers.get(i))) {
                if (identities.get(i) == dif) {
                    if (Math.random() < 1.0 / ++len) ret = clues.get(i);
                }
                else if (identities.get(i) == day || dif == -100) {
                    ret = clues.get(i);
                    len = 1;
                    dif = identities.get(i);
                }
                else if ((identities.get(i) < day && dif <= day) || (identities.get(i) > day && dif >= day)) {
                    if (Math.abs(identities.get(i)-day) < Math.abs(dif-day)) {
                        ret = clues.get(i);
                        len = 1;
                        dif = identities.get(i);
                    }
                }
                else if (identities.get(i) < day && day-dist < 0 || identities.get(i) > day && day-dist > 0) {
                    ret = clues.get(i);
                    len = 1;
                    dif = identities.get(i);
                }
            }
        }

        if (dif >= 0) totalDifficulty += dif;
        else clueDelta += 1;

        return ret;
    }

    public static void getClues() {
        across = new ArrayList<String>();
        vertical = new ArrayList<String>();
        totalDifficulty = 0;
        clueDelta = 0;

        int clueNum = 1;
        for (int r=0; r<rows; r++) {
            for (int c=0; c<cols; c++) {
                if (board[r][c] == ' ') {
                    formatBoard[r][c] = "#";
                }
                else if (r == 0 || c == 0 || board[r-1][c] == ' ' || board[r][c-1] == ' ') {
                    formatBoard[r][c] = "" + clueNum;

                    if (r == 0 || board[r-1][c] == ' ') {
                        getVertical(r, c, clueNum);
                    }

                    if (c == 0 || board[r][c-1] == ' ') {
                        getAcross(r, c, clueNum);
                    }

                    clueNum++;
                }
                else formatBoard[r][c] = ".";

                System.out.print(formatBoard[r][c] + " \t");
            }
            System.out.println();
        }
    }

    public static void getAcross(int r, int c, int clueNum) {
        String finl = clueNum + ". ";

        String word = "";
        while (c < cols && board[r][c] != ' ') {
            word = word + board[r][c++];
        }

        finl = finl + findClue(word);
        across.add(finl);
    }

    public static void getVertical(int r, int c, int clueNum) {
        String finl = clueNum + ". ";

        String word = "";
        while (r < rows && board[r][c] != ' ') {
            word = word + board[r++][c];
        }

        finl = finl + findClue(word);
        vertical.add(finl);
    }

    static ArrayList<String> answers, clues;
    static ArrayList<Integer> identities;
    public static void getWords(File f) throws IOException { 
        BufferedReader br = new BufferedReader(new FileReader(f));
        answers = new ArrayList<String>();
        clues = new ArrayList<String>();
        identities = new ArrayList<Integer>();

        String in;
        while ((in = br.readLine()) != null) {
            answers.add(in.substring(0, in.indexOf(" ")));
            in = in.substring(in.indexOf(" ") + 1);
            identities.add(Integer.parseInt(in.substring(0, 1)));
            clues.add(in.substring(2));
        }

        br.close();
    }

    public static void readFile(File f) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(f));
        StringTokenizer st = new StringTokenizer(br.readLine());
        rows = Integer.parseInt(st.nextToken());
        cols = Integer.parseInt(st.nextToken());
        day = Integer.parseInt(st.nextToken());
        board = new char[rows][cols];
        formatBoard = new String[rows][cols];

        for (int r=0; r<rows; r++) {
            String in = br.readLine();

            for (int c=0; c<cols; c++) {
                board[r][c] = in.charAt(c);
            }
        }

        br.close();
    }
}
