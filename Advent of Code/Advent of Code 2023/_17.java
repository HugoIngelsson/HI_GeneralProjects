import java.util.*;
import java.io.*;

public class _17 {
    public static void main(String[] args) throws IOException {
        //System.out.println(p1());
        System.out.println(p2());
    }

    static long[][][][] A;
    static boolean[][][][] closed;
    static int[][] map;
    public static String p1() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        String in;

        ArrayList<String> input = new ArrayList<>();
        while ((in = br.readLine()) != null) {
            input.add(in);
        }

        map = new int[input.size()][input.get(0).length()];
        for (int i=0; i<map.length; i++) {
            for (int j=0; j<map[0].length; j++) {
                map[i][j] = input.get(i).charAt(j) - '0';
            }
        }
        
        // r * c * dir * seq
        A = new long[map.length][map[0].length][4][3];
        closed = new boolean[map.length][map[0].length][4][3];

        int[] cur = {0,0,0,0};
        int counter = 0;
        while (cur[0] != map.length-1 || cur[1] != map[0].length-1) {
            closed[cur[0]][cur[1]][cur[2]][cur[3]] = true;
            update(cur[0], cur[1], cur[2], cur[3]);
            cur = selectSmallest();
            if (counter++%10000==0) System.out.println(Arrays.toString(cur) +  " " + A[cur[0]][cur[1]][cur[2]][cur[3]] + " " + map[cur[0]][cur[1]]);
        }

        br.close();
        return "" + A[cur[0]][cur[1]][cur[2]][cur[3]];
    }

    public static int[] selectSmallest() {
        int[] min = {-1,-1,-1,-1};
        long minExp = Long.MAX_VALUE;

        for (int r=0; r<map.length; r++) {
            for (int c=0; c<map[0].length; c++) {
                for (int dir=0; dir<A[0][0].length; dir++) {
                    for (int seq=0; seq<A[0][0][0].length; seq++) {
                        if (A[r][c][dir][seq] != 0 && 
                            A[r][c][dir][seq] < minExp &&
                            !closed[r][c][dir][seq]) {
                            minExp = A[r][c][dir][seq];
                            min[0] = r;
                            min[1] = c;
                            min[2] = dir;
                            min[3] = seq;
                        }
                    }
                }
            }
        }

        return min;
    }

    public static void update(int r, int c, int dir, int seq) {
        if (dir == 0) {
            if (c>0 && (A[r][c-1][3][0] == 0 || 
                A[r][c-1][3][0] > A[r][c][dir][seq] + map[r][c-1])) {
                A[r][c-1][3][0] = A[r][c][dir][seq] + map[r][c-1];
            }

            if (seq < 2 && r>0 && (A[r-1][c][0][seq+1] == 0 || 
                A[r-1][c][0][seq+1] > A[r][c][dir][seq] + map[r-1][c])) {
                A[r-1][c][0][seq+1] = A[r][c][dir][seq] + map[r-1][c];
            }

            if (c<map[0].length-1 && (A[r][c+1][1][0] == 0 || 
                A[r][c+1][1][0] > A[r][c][dir][seq] + map[r][c+1])) {
                A[r][c+1][1][0] = A[r][c][dir][seq] + map[r][c+1];
            }
        }
        else if (dir == 1) {
            if (r>0 && (A[r-1][c][0][0] == 0 || 
                A[r-1][c][0][0] > A[r][c][dir][seq] + map[r-1][c])) {
                A[r-1][c][0][0] = A[r][c][dir][seq] + map[r-1][c];
            }

            if (seq < 2 && c<map[0].length-1 && (A[r][c+1][0][seq+1] == 0 || 
                A[r][c+1][1][seq+1] > A[r][c][dir][seq] + map[r][c+1])) {
                A[r][c+1][1][seq+1] = A[r][c][dir][seq] + map[r][c+1];
            }

            if (r<map.length-1 && (A[r+1][c][2][0] == 0 || 
                A[r+1][c][2][0] > A[r][c][dir][seq] + map[r+1][c])) {
                A[r+1][c][2][0] = A[r][c][dir][seq] + map[r+1][c];
            }
        }
        else if (dir == 2) {
            if (c>0 && (A[r][c-1][3][0] == 0 || 
                A[r][c-1][3][0] > A[r][c][dir][seq] + map[r][c-1])) {
                A[r][c-1][3][0] = A[r][c][dir][seq] + map[r][c-1];
            }

            if (seq < 2 && r<map.length-1 && (A[r+1][c][0][seq+1] == 0 || 
                A[r+1][c][2][seq+1] > A[r][c][dir][seq] + map[r+1][c])) {
                A[r+1][c][2][seq+1] = A[r][c][dir][seq] + map[r+1][c];
            }

            if (c<map[0].length-1 && (A[r][c+1][1][0] == 0 || 
                A[r][c+1][1][0] > A[r][c][dir][seq] + map[r][c+1])) {
                A[r][c+1][1][0] = A[r][c][dir][seq] + map[r][c+1];
            }
        }
        else if (dir == 3) {
            if (r>0 && (A[r-1][c][0][0] == 0 || 
                A[r-1][c][0][0] > A[r][c][dir][seq] + map[r-1][c])) {
                A[r-1][c][0][0] = A[r][c][dir][seq] + map[r-1][c];
            }

            if (seq < 2 && c>0 && (A[r][c-1][0][seq+1] == 0 || 
                A[r][c-1][3][seq+1] > A[r][c][dir][seq] + map[r][c-1])) {
                A[r][c-1][3][seq+1] = A[r][c][dir][seq] + map[r][c-1];
            }

            if (r<map.length-1 && (A[r+1][c][2][0] == 0 || 
                A[r+1][c][2][0] > A[r][c][dir][seq] + map[r+1][c])) {
                A[r+1][c][2][0] = A[r][c][dir][seq] + map[r+1][c];
            }
        }
    }

    // min = 1092, max = 1105
    static int[][][] closed2;
    static HashSet<Node17> open;
    public static String p2() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        String in;

        ArrayList<String> input = new ArrayList<>();
        while ((in = br.readLine()) != null) {
            input.add(in);
        }

        map = new int[input.size()][input.get(0).length()];
        for (int i=0; i<map.length; i++) {
            for (int j=0; j<map[0].length; j++) {
                map[i][j] = input.get(i).charAt(j) - '0';
            }
        }
        
        // r * c * dir * seq
        A = new long[map.length][map[0].length][4][10];
        closed2 = new int[map.length][map[0].length][4];
        for (int i=0; i<map.length; i++) for (int j=0; j<map[0].length; j++) for (int k=0; k<4; k++) {
            closed2[i][j][k] = 10;
        }
        open = new HashSet<>();

        A[0][4][1][3] = map[0][1]+map[0][2]+map[0][3]+map[0][4];
        A[4][0][2][3] = map[1][0]+map[2][0]+map[3][0]+map[4][0];
        updateUltra(0,4,1,3);
        updateUltra(4,0,2,3);
        Node17 cur = selectSmallest3();
        int counter = 0;
        while (cur.r != map.length-1 || cur.c != map[0].length-1) {
            closed2[cur.r][cur.c][cur.dir] = cur.seq;
            updateUltra(cur.r, cur.c, cur.dir, cur.seq);

            open.remove(cur);
            cur = selectSmallest3();
            if (counter++%10000==0) System.out.println(cur +  " " + A[cur.r][cur.c][cur.dir][cur.seq]);
        }

        br.close();
        return "" + A[cur.r][cur.c][cur.dir][cur.seq];
    }

    public static void updateUltra(int r, int c, int dir, int seq) {
        if (dir == 0) {
            if (c>=4 && (A[r][c-4][3][3] == 0 || 
                A[r][c-4][3][3] > A[r][c][dir][seq] + map[r][c-1] + map[r][c-2] + map[r][c-3] + map[r][c-4])) {
                if (A[r][c-4][3][3] == 0) open.add(new Node17(r,c-4,3,3));
                A[r][c-4][3][3] = A[r][c][dir][seq] + map[r][c-1] + map[r][c-2] + map[r][c-3] + map[r][c-4];
            }

            if (seq < 9 && r>0 && (A[r-1][c][0][seq+1] == 0 || 
                A[r-1][c][0][seq+1] > A[r][c][dir][seq] + map[r-1][c])) {
                if (A[r-1][c][0][seq+1] == 0) open.add(new Node17(r-1,c,0,seq+1));
                A[r-1][c][0][seq+1] = A[r][c][dir][seq] + map[r-1][c];
            }

            if (c<map[0].length-4 && (A[r][c+4][1][3] == 0 || 
                A[r][c+4][1][3] > A[r][c][dir][seq] + map[r][c+1] + map[r][c+2] + map[r][c+3] + map[r][c+4])) {
                if (A[r][c+4][1][3] == 0) open.add(new Node17(r,c+4,1,3));
                A[r][c+4][1][3] = A[r][c][dir][seq] + map[r][c+1] + map[r][c+2] + map[r][c+3] + map[r][c+4];
            }
        }
        else if (dir == 1) {
            if (r>=4 && (A[r-4][c][0][3] == 0 || 
                A[r-4][c][0][3] > A[r][c][dir][seq] + map[r-1][c] + map[r-2][c] + map[r-3][c] + map[r-4][c])) {
                if (A[r-4][c][0][3] == 0) open.add(new Node17(r-4,c,0,3));
                A[r-4][c][0][3] = A[r][c][dir][seq] + map[r-1][c] + map[r-2][c] + map[r-3][c] + map[r-4][c];
            }

            if (seq < 9 && c<map[0].length-1 && (A[r][c+1][0][seq+1] == 0 || 
                A[r][c+1][1][seq+1] > A[r][c][dir][seq] + map[r][c+1])) {
                if (A[r][c+1][1][seq+1] == 0) open.add(new Node17(r,c+1,1,seq+1));
                A[r][c+1][1][seq+1] = A[r][c][dir][seq] + map[r][c+1];
            }

            if (r<map.length-4 && (A[r+4][c][2][3] == 0 || 
                A[r+4][c][2][3] > A[r][c][dir][seq] + map[r+1][c] + map[r+2][c] + map[r+3][c] + map[r+4][c])) {
                if (A[r+4][c][2][3] == 0) open.add(new Node17(r+4,c,2,3));
                A[r+4][c][2][3] = A[r][c][dir][seq] + map[r+1][c] + map[r+2][c] + map[r+3][c] + map[r+4][c];
            }
        }
        else if (dir == 2) {
            if (c>=4 && (A[r][c-4][3][3] == 0 || 
                A[r][c-4][3][3] > A[r][c][dir][seq] + map[r][c-1] + map[r][c-2] + map[r][c-3] + map[r][c-4])) {
                if (A[r][c-4][3][3] == 0) open.add(new Node17(r,c-4,3,3));
                A[r][c-4][3][3] = A[r][c][dir][seq] + map[r][c-1] + map[r][c-2] + map[r][c-3] + map[r][c-4];
            }

            if (seq < 9 && r<map.length-1 && (A[r+1][c][0][seq+1] == 0 || 
                A[r+1][c][2][seq+1] > A[r][c][dir][seq] + map[r+1][c])) {
                if (A[r+1][c][2][seq+1] == 0) open.add(new Node17(r+1,c,2,seq+1));
                A[r+1][c][2][seq+1] = A[r][c][dir][seq] + map[r+1][c];
            }

            if (c<map[0].length-4 && (A[r][c+4][1][3] == 0 || 
                A[r][c+4][1][3] > A[r][c][dir][seq] + map[r][c+1] + map[r][c+2] + map[r][c+3] + map[r][c+4])) {
                if (A[r][c+4][1][3] == 0) open.add(new Node17(r,c+4,1,3));
                A[r][c+4][1][3] = A[r][c][dir][seq] + map[r][c+1] + map[r][c+2] + map[r][c+3] + map[r][c+4];
            }
        }
        else if (dir == 3) {
            if (r>=4 && (A[r-4][c][0][3] == 0 || 
                A[r-4][c][0][3] > A[r][c][dir][seq] + map[r-1][c] + map[r-2][c] + map[r-3][c] + map[r-4][c])) {
                if (A[r-4][c][0][3] == 0) open.add(new Node17(r-4,c,0,3));
                A[r-4][c][0][3] = A[r][c][dir][seq] + map[r-1][c] + map[r-2][c] + map[r-3][c] + map[r-4][c];
            }

            if (seq < 9 && c>0 && (A[r][c-1][0][seq+1] == 0 || 
                A[r][c-1][3][seq+1] > A[r][c][dir][seq] + map[r][c-1])) {
                if (A[r][c-1][3][seq+1] == 0) open.add(new Node17(r,c-1,3,seq+1));
                A[r][c-1][3][seq+1] = A[r][c][dir][seq] + map[r][c-1];
            }

            if (r<map.length-4 && (A[r+4][c][2][3] == 0 || 
                A[r+4][c][2][3] > A[r][c][dir][seq] + map[r+1][c] + map[r+2][c] + map[r+3][c] + map[r+4][c])) {
                if (A[r+4][c][2][3] == 0) open.add(new Node17(r+4,c,2,3));
                A[r+4][c][2][3] = A[r][c][dir][seq] + map[r+1][c] + map[r+2][c] + map[r+3][c] + map[r+4][c];
            }
        }
    }

    public static int[] selectSmallest2() {
        int[] min = {-1,-1,-1,-1};
        long minExp = Long.MAX_VALUE;

        for (int r=0; r<map.length; r++) {
            for (int c=0; c<map[0].length; c++) {
                for (int dir=0; dir<A[0][0].length; dir++) {
                    for (int seq=0; seq<A[0][0][0].length; seq++) {
                        if (A[r][c][dir][seq] != 0 && 
                            A[r][c][dir][seq] < minExp &&
                            closed2[r][c][dir] > seq) {
                            minExp = A[r][c][dir][seq];
                            min[0] = r;
                            min[1] = c;
                            min[2] = dir;
                            min[3] = seq;
                        }
                    }
                }
            }
        }

        return min;
    }

    public static Node17 selectSmallest3() {
        Node17 min = null;
        long minExp = Long.MAX_VALUE;

        for (Node17 n : open) {
            if (A[n.r][n.c][n.dir][n.seq] != 0 && 
                A[n.r][n.c][n.dir][n.seq] < minExp &&
                closed2[n.r][n.c][n.dir] > n.seq) {
                minExp = A[n.r][n.c][n.dir][n.seq];
                min = n;
            }
        }

        return min;
    }
}

class Node17 {
    int r,c,dir,seq;

    public Node17(int r, int c, int dir, int seq) {
        this.r = r;
        this.c = c;
        this.dir = dir;
        this.seq = seq;
    }

    public String toString() {
        return r + " " + c + " " + dir + " " + seq;
    }
}