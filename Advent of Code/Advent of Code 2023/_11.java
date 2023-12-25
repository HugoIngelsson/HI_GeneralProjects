import java.util.*;
import java.io.*;

public class _11 {
    public static void main(String[] args) throws IOException {
        System.out.println(p1());
        System.out.println(p2());
    }

    public static String p1() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        String in;
        int total = 0;

        ArrayList<String> input = new ArrayList<>();
        while ((in = br.readLine()) != null) {
            input.add(in);
        }

        boolean[][] map = new boolean[input.size()][input.get(0).length()];
        ArrayList<Integer> rows = new ArrayList<>();
        ArrayList<Integer> cols = new ArrayList<>();
        for (int i=0; i<map.length; i++) {
            boolean empty = true;
            for (int j=0; j<map[i].length; j++) {
                if (input.get(i).charAt(j) == '#') {
                    map[i][j] = true;
                    empty = false;
                }
                if (j == map[i].length-1 && empty) {
                    rows.add(i);
                }
            }
        }
        for (int j=0; j<map[0].length; j++) {
            boolean empty = true;
            for (int i=0; i<map.length; i++) {
                if (map[i][j]) {
                    empty = false;
                }
                if (i == map.length-1 && empty) {
                    cols.add(j);
                }
            }
        }

        ArrayList<Point> points = new ArrayList<>();
        for (int i=0; i<map.length; i++) {
            for (int j=0; j<map[i].length; j++) {
                if (map[i][j]) {
                    int r=i, c=j;
                    for (int k=0; k<rows.size(); k++) {
                        if (rows.get(k) < i) r++;
                        else break;
                    }
                    for (int k=0; k<cols.size(); k++) {
                        if (cols.get(k) < j) c++;
                        else break;
                    }

                    points.add(new Point(r, c));
                }
            }
        }
        for (int i=0; i<points.size(); i++) {
            for (int j=i+1; j<points.size(); j++) {
                total += manDist(points.get(i), points.get(j));
            }
        }

        br.close();
        return "" + total;
    }

    public static String p2() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        String in;
        long total = 0;

        ArrayList<String> input = new ArrayList<>();
        while ((in = br.readLine()) != null) {
            input.add(in);
        }

        boolean[][] map = new boolean[input.size()][input.get(0).length()];
        ArrayList<Integer> rows = new ArrayList<>();
        ArrayList<Integer> cols = new ArrayList<>();
        for (int i=0; i<map.length; i++) {
            boolean empty = true;
            for (int j=0; j<map[i].length; j++) {
                if (input.get(i).charAt(j) == '#') {
                    map[i][j] = true;
                    empty = false;
                }
                if (j == map[i].length-1 && empty) {
                    rows.add(i);
                }
            }
        }
        for (int j=0; j<map[0].length; j++) {
            boolean empty = true;
            for (int i=0; i<map.length; i++) {
                if (map[i][j]) {
                    empty = false;
                }
                if (i == map.length-1 && empty) {
                    cols.add(j);
                }
            }
        }

        ArrayList<Point> points = new ArrayList<>();
        for (int i=0; i<map.length; i++) {
            for (int j=0; j<map[i].length; j++) {
                if (map[i][j]) {
                    int r=i, c=j;
                    for (int k=0; k<rows.size(); k++) {
                        if (rows.get(k) < i) r+=999999;
                        else break;
                    }
                    for (int k=0; k<cols.size(); k++) {
                        if (cols.get(k) < j) c+=999999;
                        else break;
                    }

                    points.add(new Point(r, c));
                }
            }
        }
        for (int i=0; i<points.size(); i++) {
            for (int j=i+1; j<points.size(); j++) {
                total += manDist(points.get(i), points.get(j));
            }
        }
        
        br.close();
        return "" + total;
    }

    public static int manDist(Point p1, Point p2) {
        return Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y);
    }
}

class Point {
    int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}