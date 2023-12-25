import java.util.*;
import java.io.*;

public class _16 {
    public static void main(String[] args) throws IOException {
        System.out.println(p1());
        System.out.println(p2());
    }

    static char[][] map;
    static boolean[][][] mapspace;
    public static String p1() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        String in;

        ArrayList<String> input = new ArrayList<>();
        while ((in = br.readLine()) != null) {
            input.add(in);
        }

        map = new char[input.size()][input.get(0).length()];
        mapspace = new boolean[input.size()][input.get(0).length()][4];
        for (int i=0; i<map.length; i++) {
            for (int j=0; j<map[0].length; j++) {
                map[i][j] = input.get(i).charAt(j);
            }
        }

        beam(0,0,1);

        long total = 0;
        for (int i=0; i<map.length; i++) {
            for (int j=0; j<map[0].length; j++) {
                if (mapspace[i][j][0] || mapspace[i][j][1]
                    || mapspace[i][j][2] || mapspace[i][j][3]) {
                        total++;
                    }
                else {
                }
            }
        }

        br.close();
        return "" + total;
    }

    public static String p2() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        String in;

        ArrayList<String> input = new ArrayList<>();
        while ((in = br.readLine()) != null) {
            input.add(in);
        }

        map = new char[input.size()][input.get(0).length()];
        for (int i=0; i<map.length; i++) {
            for (int j=0; j<map[0].length; j++) {
                map[i][j] = input.get(i).charAt(j);
            }
        }

        long max = 0;
        for (int i=0; i<map.length; i++) {
            mapspace = new boolean[input.size()][input.get(0).length()][4];
            beam(i,0,1);
            max = Math.max(max, countFilled());

            mapspace = new boolean[input.size()][input.get(0).length()][4];
            beam(i,map[0].length-1,3);
            max = Math.max(max, countFilled());
        }

        for (int i=0; i<map[0].length; i++) {
            mapspace = new boolean[input.size()][input.get(0).length()][4];
            beam(0,i,2);
            max = Math.max(max, countFilled());

            mapspace = new boolean[input.size()][input.get(0).length()][4];
            beam(map.length-1,i,0);
            max = Math.max(max, countFilled());
        }

        br.close();
        return "" + max;
    }

    public static long countFilled() {
        long total = 0;
        for (int i=0; i<map.length; i++) {
            for (int j=0; j<map[0].length; j++) {
                if (mapspace[i][j][0] || mapspace[i][j][1]
                    || mapspace[i][j][2] || mapspace[i][j][3]) {
                        total++;
                    }
                else {
                }
            }
        }

        return total;
    }

    public static void beam(int r, int c, int dir) {
        if (r<0 || r>=map.length || c<0 || c>=map[0].length) return;

        if (mapspace[r][c][dir]) return;
        else mapspace[r][c][dir] = true;

        if (map[r][c] == '\\') {
            if (dir == 0) beam(r, c-1, 3);
            else if (dir == 1) beam(r+1, c, 2);
            else if (dir == 2) beam(r, c+1, 1);
            else if (dir == 3) beam(r-1, c, 0);
        }
        else if (map[r][c] == '/') {
            if (dir == 0) beam(r, c+1, 1);
            else if (dir == 1) beam(r-1, c, 0);
            else if (dir == 2) beam(r, c-1, 3);
            else if (dir == 3) beam(r+1, c, 2);
        }
        else if (map[r][c] == '|' && dir%2==1) {
            beam(r-1, c, 0);
            beam(r+1, c, 2);
        }
        else if (map[r][c] == '-' && dir%2==0) {
            beam(r, c-1, 3);
            beam(r, c+1, 1);
        }
        else {
            if (dir == 0) beam(r-1, c, dir);
            else if (dir == 1) beam(r, c+1, dir);
            else if (dir == 2) beam(r+1, c, dir);
            else if (dir == 3) beam(r, c-1, dir);
        }
    }
}