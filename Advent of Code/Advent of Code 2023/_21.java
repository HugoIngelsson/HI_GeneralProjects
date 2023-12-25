import java.util.*;
import java.io.*;

public class _21 {
    public static void main(String[] args) throws IOException {
        System.out.println(p1());
        System.out.println(p2());
    }

    public static String p1() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        String in;

        ArrayList<String> input = new ArrayList<>();
        while ((in = br.readLine()) != null) {
            input.add(in);
        }

        boolean[][] blocked = new boolean[input.size()][input.get(0).length()];
        int[][] moves = new int[input.size()][input.get(0).length()];
        for (int i=0; i<blocked.length; i++) {
            for (int j=0; j<blocked[0].length; j++) {
                if (input.get(i).charAt(j) == '#') blocked[i][j] = true;
                else if (input.get(i).charAt(j) == 'S') {
                    continue;
                }
                else {
                    moves[i][j] = 65;
                }
            }
        }

        for (int m=0; m<=64; m++) {
            for (int i=0; i<moves.length; i++) {
                for (int j=0; j<moves[0].length; j++) {
                    if (!blocked[i][j] && moves[i][j] == m) {
                        propogate(moves, blocked, i, j);
                    }
                }
            }
        }

        long total = 0;
        for (int i=0; i<moves.length; i++) {
            for (int j=0; j<moves[0].length; j++) {
                if (!blocked[i][j] && moves[i][j]%2 == 0) total++;
            }
        }

        br.close();
        return "" + total;
    }

    public static void propogate(int[][] moves, boolean[][] blocked, int r, int c) {
        if (r > 0 && moves[r-1][c] > moves[r][c] && !blocked[r-1][c]) moves[r-1][c] = moves[r][c]+1;
        if (r < moves.length-1 && moves[r+1][c] > moves[r][c] && !blocked[r+1][c]) moves[r+1][c] = moves[r][c]+1;
        if (c > 0 && moves[r][c-1] > moves[r][c] && !blocked[r][c-1]) moves[r][c-1] = moves[r][c]+1;
        if (c < moves[0].length-1 && moves[r][c+1] > moves[r][c] && !blocked[r][c+1]) moves[r][c+1] = moves[r][c]+1;
    }

    public static String p2() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        String in;

        ArrayList<String> input = new ArrayList<>();
        while ((in = br.readLine()) != null) {
            input.add(in);
        }

        boolean[][] blocked = new boolean[input.size()][input.get(0).length()];
        int[][] moves = new int[input.size()][input.get(0).length()];
        int sr=0, sc=0;
        int n = 5;
        for (int i=0; i<blocked.length; i++) {
            for (int j=0; j<blocked[0].length; j++) {
                if (input.get(i).charAt(j) == '#') blocked[i][j] = true;
                else if (input.get(i).charAt(j) == 'S') {
                    moves[i][j] = Integer.MAX_VALUE-1;
                    sr = moves.length*(n/2)+i;
                    sc = moves[0].length*(n/2)+j;
                }
                else {
                    moves[i][j] = Integer.MAX_VALUE-1;
                }
            }
        }
        moves = nAppend(moves, n);
        blocked = nAppendB(blocked, n);
        moves[sr][sc] = 0;

        long[] coeffs = {0,0,0};
        for (int m=0; m<=sr; m++) {
            if (m%(moves.length/n)==65) {
                int count = 0;
                for (int i=0; i<moves.length; i++) {
                    for (int j=0; j<moves.length; j++) {
                        if (!blocked[i][j] && moves[i][j]%2==m%2 && moves[i][j] < 100000) count++;
                    }
                }

                if (m%(moves.length/n)==65) {
                    if (coeffs[0] == 0) coeffs[0] = count;
                    else if (coeffs[1] == 0) coeffs[1] = count;
                    else if (coeffs[2] == 0) coeffs[2] = count;
                }
            }
            for (int i=0; i<moves.length; i++) {
                for (int j=0; j<moves[0].length; j++) {
                    if (!blocked[i][j] && moves[i][j] == m) {
                        propogate(moves, blocked, i, j);
                    }
                }
            }
        }

        long x = 202300;
        long c = coeffs[0];
        long a = (coeffs[2]+c-2*coeffs[1])/2;
        long b = coeffs[1]-c-a;

        long total = a*x*x+b*x+c;
        
        br.close();
        return "" + total;
    }

    public static int[][] nAppend(int[][] toApp, int n) {
        int[][] ret = new int[toApp.length*n][toApp[0].length*n];

        for (int i=0; i<toApp.length*n; i++) {
            for (int j=0; j<toApp[0].length*n; j++) {
                ret[i][j] = toApp[i%toApp.length][j%toApp[0].length];
            }
        }

        return ret;
    }

    public static boolean[][] nAppendB(boolean[][] toApp, int n) {
        boolean[][] ret = new boolean[toApp.length*n][toApp[0].length*n];

        for (int i=0; i<toApp.length*n; i++) {
            for (int j=0; j<toApp[0].length*n; j++) {
                ret[i][j] = toApp[i%toApp.length][j%toApp[0].length];
            }
        }

        return ret;
    }
}