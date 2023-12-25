import java.util.*;
import java.io.*;

public class _18 {
    public static void main(String[] args) throws IOException {
        System.out.println(p1());
        System.out.println(p2());
    }

    static boolean[][] map;
    static boolean[][] toSearch;
    public static String p1() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        StringTokenizer st;
        String in;

        int r = 0,c = 0;
        int maxR = -10000, minR = 10000, maxC = -10000, minC = 10000;
        ArrayList<String> input = new ArrayList<>();
        while ((in = br.readLine()) != null) {
            st = new StringTokenizer(in);
            String comm = st.nextToken();
            int len = Integer.parseInt(st.nextToken());

            if (comm.equals("U")) {
                r -= len;
            }
            else if (comm.equals("D")) {
                r += len;
            }
            else if (comm.equals("R")) {
                c += len;
            }
            else {
                c -= len;
            }
            input.add(in);
            maxR = Math.max(maxR, r);
            minR = Math.min(minR, r);
            maxC = Math.max(maxC, c);
            minC = Math.min(minC, c);
        }

        map = new boolean[maxR-minR+1][maxC-minC+1];
        toSearch = new boolean[maxR-minR+1][maxC-minC+1];
        r = -minR;
        c = -minC;
        long total = 0;
        for (String s : input) {
            st = new StringTokenizer(s);
            String comm = st.nextToken();
            int len = Integer.parseInt(st.nextToken());
            int deltaR, deltaC;

            switch(comm) {
                case("U"): deltaR=-1; deltaC=0; break;
                case("D"): deltaR=1; deltaC=0; break;
                case("R"): deltaR=0; deltaC=1; break;
                default: deltaR=0; deltaC=-1; break;
            }

            while (len --> 0) {
                map[r][c] = true;
                toSearch[r+deltaC][c-deltaR] = true;
                total++;
                r += deltaR;
                c += deltaC;
            }
        }

        for (int i=0; i<map.length; i++) {
            for (int j=0; j<map[0].length; j++) {
                if (toSearch[i][j] && !map[i][j])
                    total += dfs(i,j, 0);
            }
        }

        br.close();
        return "" + total;
    }

    public static long dfs(int r, int c, int i) {
        if (i > 1000) {
            toSearch[r][c] = true;
            return 0;
        }
        if (map[r][c]) return 0;
        map[r][c] = true;

        return 1 + dfs(r-1,c, i+1) + dfs(r+1,c, i+1) + dfs(r,c-1, i+1) + dfs(r,c+1, i+1);
    }

    public static String p2() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        StringTokenizer st;
        String in;

        long r = 0, c = 0;
        long minR = 0, maxR = 0, minC = 0, maxC = 0;
        ArrayList<Point18> points = new ArrayList<>();
        while ((in = br.readLine()) != null) {
            st = new StringTokenizer(in);
            st.nextToken(); st.nextToken();
            String comm = st.nextToken();

            long delta = Long.parseLong(comm.substring(2,7), 16);
            switch (comm.charAt(7)) {
                case('0'): c += delta; break;
                case('1'): r += delta; break;
                case('2'): c -= delta; break;
                default: r -= delta; break;
            }

            minR = Math.min(minR, r);
            maxR = Math.max(maxR, r);
            minC = Math.min(minC, c);
            maxC = Math.max(maxC, c);
            points.add(new Point18(r, c));
        }
        points.add(points.get(0));

        long total = 0;
        long otherTotal = 2;
        for (int i=0; i<points.size()-1; i++) {
            total += points.get(i).x*points.get(i+1).y - points.get(i).y*points.get(i+1).x;
            otherTotal += Math.abs(points.get(i).x-points.get(i+1).x);
            otherTotal += Math.abs(points.get(i).y-points.get(i+1).y);
        }
        total = Math.abs(total);
        total += otherTotal;

        br.close();
        return "" + Math.abs(total)/2;
    }
}

class Point18 {
    long x;
    long y;

    public Point18(long x, long y) {
        this.x = x;
        this.y = y;
    }
}