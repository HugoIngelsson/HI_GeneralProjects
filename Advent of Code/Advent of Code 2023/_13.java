import java.util.*;
import java.io.*;

public class _13 {
    public static void main(String[] args) throws IOException {
        System.out.println(p1());
        System.out.println(p2());
    }

    public static String p1() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        String in;

        long total = 0;
        while ((in = br.readLine()) != null) {
            ArrayList<String> input = new ArrayList<>();
            input.add(in);
            while ((in = br.readLine()) != null && in.length() != 0) {
                input.add(in);
            }

            boolean[][] rocks = new boolean[input.size()][input.get(0).length()];
            for (int i=0; i<rocks.length; i++) {
                for (int j=0; j<rocks[i].length; j++) {
                    if (input.get(i).charAt(j) == '#') rocks[i][j] = true;
                }
            }

            for (int delta=0; delta<rocks.length-1; delta++) {
                for (int i=0; i<rocks.length; i++) {
                    if (!compareRows(delta-i, delta+i+1, rocks)) break;
                    if (i == rocks.length-1) total += 100*(delta + 1);
                }
            }

            for (int delta=0; delta<rocks[0].length-1; delta++) {
                for (int i=0; i<rocks[0].length; i++) {
                    if (!compareCols(delta-i, delta+i+1, rocks)) break;
                    if (i == rocks[0].length-1) total += (delta + 1);
                }
            }
        }
        

        br.close();
        return "" + total;
    }

    public static boolean compareRows(int r1, int r2, boolean[][] map) {
        if (r1 < 0 || r2 >= map.length) return true;

        for (int c=0; c<map[r1].length; c++) {
            if (map[r1][c] != map[r2][c]) return false;
        }

        return true;
    }

    public static boolean compareCols(int c1, int c2, boolean[][] map) {
        if (c1 < 0 || c2 >= map[0].length) return true;

        for (int r=0; r<map.length; r++) {
            if (map[r][c1] != map[r][c2]) return false;
        }

        return true;
    }

    public static String p2() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        String in;

        long total = 0;
        while ((in = br.readLine()) != null) {
            ArrayList<String> input = new ArrayList<>();
            input.add(in);
            while ((in = br.readLine()) != null && in.length() != 0) {
                input.add(in);
            }

            boolean[][] rocks = new boolean[input.size()][input.get(0).length()];
            for (int i=0; i<rocks.length; i++) {
                for (int j=0; j<rocks[i].length; j++) {
                    if (input.get(i).charAt(j) == '#') rocks[i][j] = true;
                }
            }
            
            int old = -1;
            boolean dir = false;
            for (int delta=0; delta<rocks.length-1; delta++) {
                for (int i=0; i<rocks.length; i++) {
                    if (!compareRows(delta-i, delta+i+1, rocks)) break;
                    if (i == rocks.length-1) {
                        old = delta;
                        dir = true;
                    }
                }
            }

            for (int delta=0; delta<rocks[0].length-1; delta++) {
                for (int i=0; i<rocks[0].length; i++) {
                    if (!compareCols(delta-i, delta+i+1, rocks)) break;
                    if (i == rocks[0].length-1) old = delta;
                }
            }

            loop:
            for (int k=0; k<rocks.length; k++) {
                for (int l=0; l<rocks[k].length; l++) {
                    rocks[k][l] = !rocks[k][l];

                    for (int delta=0; delta<rocks.length-1; delta++) {
                        for (int i=0; i<rocks.length; i++) {
                            if (!compareRows(delta-i, delta+i+1, rocks)) break;
                            if (i == rocks.length-1 && (delta!=old || !dir)) {
                                total += 100*(delta + 1);
                                break loop;
                            }
                        }
                    }

                    for (int delta=0; delta<rocks[0].length-1; delta++) {
                        for (int i=0; i<rocks[0].length; i++) {
                            if (!compareCols(delta-i, delta+i+1, rocks)) break;
                            if (i == rocks[0].length-1 && (delta!=old || dir)) {
                                total += (delta + 1);
                                break loop;
                            }
                        }
                    }

                    rocks[k][l] = !rocks[k][l];
                }
            }
    
        }
        

        br.close();
        return "" + total;
    }
}