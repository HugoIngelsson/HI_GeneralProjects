import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.*;

class Main {
  public static void main(String[] args) {
    File f = new File("data.txt");
    sol2(f);
  }

  public static void sol1(File f) {
    try {
      Scanner in = new Scanner(f);
      boolean[][] grid = new boolean[1000][1000];
      grid[500][500] = true;
      int tx = 500, ty = 500, rx = 500, ry = 500;

      while (in.hasNext()) {
        String next = in.nextLine();
        int n = Integer.parseInt(next.substring(2));

        for (int i=0; i<n; i++) {
          if (next.charAt(0) == 'R') {
            tx++;
            if (tx - rx > 1) {
              rx++;
              if (ty > ry) ry++;
              if (ty < ry) ry--;
              grid[rx][ry] = true;
            }
          } else if (next.charAt(0) == 'L') {
            tx--;
            if (rx - tx > 1) {
              rx--;
              if (ty > ry) ry++;
              if (ty < ry) ry--;
              grid[rx][ry] = true;
            }
          } else if (next.charAt(0) == 'U') {
            ty++;
            if (ty - ry > 1) {
              ry++;
              if (tx > rx) rx++;
              if (tx < rx) rx--;
              grid[rx][ry] = true;
            }
          } else if (next.charAt(0) == 'D') {
            ty--;
            if (ry - ty > 1) {
              ry--;
              if (tx > rx) rx++;
              if (tx < rx) rx--;
              grid[rx][ry] = true;
            }
          }
        }
      }

      int n = 0;
      for (int i=0; i<1000; i++) {
        for (int j=0; j<1000; j++) {
          if (grid[i][j]) n++;
        }
      }

      System.out.println(n);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  static int[] x = new int[10];
  static int[] y = new int[10];
  public static void sol2(File f) {
    try {
      Scanner in = new Scanner(f);
      boolean[][] grid = new boolean[1000][1000];
      grid[500][500] = true;
      for (int i=0; i<10; i++) x[i] = 500;
      for (int i=0; i<10; i++) y[i] = 500;

      while (in.hasNext()) {
        String next = in.nextLine();
        for (int n=0; n<Integer.parseInt(next.substring(2)); n++) {
          simulateMove(0, next);
          for (int i=0; i<9; i++) {
            chain(i);
          }
          grid[x[9]][y[9]] = true;
        }
      }

      int n = 0;
      for (int i=0; i<1000; i++) {
        for (int j=0; j<1000; j++) {
          if (grid[i][j]) n++;
        }
      }
      System.out.println(n);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void simulateMove(int id, String next) {
    if (next.charAt(0) == 'R') {
      x[id]++;
    } else if (next.charAt(0) == 'L') {
      x[id]--;
    } else if (next.charAt(0) == 'U') {
      y[id]++;
    } else if (next.charAt(0) == 'D') {
      y[id]--;
    }
  }

  public static void chain(int id) {
    if (x[id] - x[id+1] > 1) {
      x[id+1]++;
      if (y[id] > y[id+1]) y[id+1]++;
      if (y[id] < y[id+1]) y[id+1]--;
    } else if (x[id] - x[id+1] < -1) {
      x[id+1]--;
      if (y[id] > y[id+1]) y[id+1]++;
      if (y[id] < y[id+1]) y[id+1]--;
    } else if (y[id] - y[id+1] > 1) {
      y[id+1]++;
      if (x[id] > x[id+1]) x[id+1]++;
      if (x[id] < x[id+1]) x[id+1]--;
    } else if (y[id] - y[id+1] < -1) {
      y[id+1]--;
      if (x[id] > x[id+1]) x[id+1]++;
      if (x[id] < x[id+1]) x[id+1]--;
    }
  }
}