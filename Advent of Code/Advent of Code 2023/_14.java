import java.util.*;
import java.io.*;

public class _14 {
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

        boolean[][] cubes = new boolean[input.size()][input.get(0).length()];
        boolean[][] spheres = new boolean[input.size()][input.get(0).length()];
        for (int i=0; i<cubes.length; i++) {
            for (int j=0; j<cubes[0].length; j++) {
                if (input.get(i).charAt(j) == 'O') {
                    spheres[i][j] = true;
                }
                else if (input.get(i).charAt(j) == '#') {
                    cubes[i][j] = true;
                }
            }
        }

        for (int i=0; i<cubes.length; i++) {
            for (int j=0; j<cubes[0].length; j++) {
                if (spheres[i][j]) rollUp(i, j, cubes, spheres);
            }
        }

        long total = 0;
        for (int i=0; i<cubes.length; i++) {
            for (int j=0; j<cubes[0].length; j++) {
                if (spheres[i][j]) {
                    total += cubes[0].length - i;
                }
            }
        }        

        br.close();
        return "" + total;
    }

    public static void cycle(boolean[][] cubes, boolean[][] spheres) {
        for (int i=0; i<cubes.length; i++) {
            for (int j=0; j<cubes[0].length; j++) {
                if (spheres[i][j]) rollUp(i, j, cubes, spheres);
            }
        }
        for (int i=0; i<cubes.length; i++) {
            for (int j=0; j<cubes[0].length; j++) {
                if (spheres[i][j]) rollLeft(i, j, cubes, spheres);
            }
        }
        for (int i=cubes.length-1; i>=0; i--) {
            for (int j=0; j<cubes[0].length; j++) {
                if (spheres[i][j]) rollDown(i, j, cubes, spheres);
            }
        }
        for (int i=0; i<cubes.length; i++) {
            for (int j=cubes[0].length-1; j>=0; j--) {
                if (spheres[i][j]) rollRight(i, j, cubes, spheres);
            }
        }
    }

    public static void rollUp(int r, int c, boolean[][] cubes, boolean[][] spheres) {
        spheres[r][c] = false;

        for (int row=r-1; row>=0; row--) {
            if (cubes[row][c] || spheres[row][c]) {
                spheres[row+1][c] = true;
                return;
            }
        }

        spheres[0][c] = true;
    }

    public static void rollDown(int r, int c, boolean[][] cubes, boolean[][] spheres) {
        spheres[r][c] = false;

        for (int row=r+1; row<cubes.length; row++) {
            if (cubes[row][c] || spheres[row][c]) {
                spheres[row-1][c] = true;
                return;
            }
        }

        spheres[cubes.length-1][c] = true;
    }

    public static void rollLeft(int r, int c, boolean[][] cubes, boolean[][] spheres) {
        spheres[r][c] = false;

        for (int col=c-1; col>=0; col--) {
            if (cubes[r][col] || spheres[r][col]) {
                spheres[r][col+1] = true;
                return;
            }
        }

        spheres[r][0] = true;
    }

    public static void rollRight(int r, int c, boolean[][] cubes, boolean[][] spheres) {
        spheres[r][c] = false;

        for (int col=c+1; col<cubes[0].length; col++) {
            if (cubes[r][col] || spheres[r][col]) {
                spheres[r][col-1] = true;
                return;
            }
        }

        spheres[r][cubes[0].length-1] = true;
    }

    public static String p2() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        String in;

        ArrayList<String> input = new ArrayList<>();
        while ((in = br.readLine()) != null) {
            input.add(in);
        }

        boolean[][] cubes = new boolean[input.size()][input.get(0).length()];
        boolean[][] spheres = new boolean[input.size()][input.get(0).length()];
        for (int i=0; i<cubes.length; i++) {
            for (int j=0; j<cubes[0].length; j++) {
                if (input.get(i).charAt(j) == 'O') {
                    spheres[i][j] = true;
                }
                else if (input.get(i).charAt(j) == '#') {
                    cubes[i][j] = true;
                }
            }
        }

        for (int i=0; i<1000; i++) cycle(cubes, spheres);
        boolean[][] copy = new boolean[cubes.length][cubes[0].length];
        for (int i=0; i<copy.length; i++) {
            for (int j=0; j<copy[0].length; j++) {
                copy[i][j] = spheres[i][j];
            }
        }

        long cycle = 0;
        do {
            cycle(cubes, spheres);
            cycle++;
        }
        while (!equals(copy, spheres));
        long cyclesLeft = (1000000000-1000)%cycle;

        for (int i=0; i<cyclesLeft; i++) cycle(cubes, spheres);

        long total = 0;
        for (int i=0; i<cubes.length; i++) {
            for (int j=0; j<cubes[0].length; j++) {
                if (spheres[i][j]) {
                    total += cubes[0].length - i;
                }
            }
        }        

        br.close();
        return "" + total;
    }

    public static boolean equals(boolean[][] spheres, boolean[][] copy) {
        for (int i=0; i<copy.length; i++) {
            for (int j=0; j<copy[0].length; j++) {
                if (copy[i][j] != spheres[i][j]) return false;
            }
        }

        return true;
    }
}