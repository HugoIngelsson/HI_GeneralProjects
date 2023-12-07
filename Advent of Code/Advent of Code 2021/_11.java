import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

class Main {
  static int[][] oct = new int[10][10];
  static boolean[][] flashed = new boolean[10][10];
  static int total = 0;

  public static void main(String[] args) {
    File file = new File("data.txt");
    populate(file);

    for (int n=0; n<300; n++) {
      increaseAll();

      for (int i=0; i<10; i++) {
        for (int j=0; j<10; j++) {
          if (oct[i][j] > 9 && !flashed[i][j]) {
            flash(i,j);
          }
        }
      }

      reduce();
      if (checkSync()) System.out.println(n);
      flashed = new boolean[10][10];
    }

    System.out.println(total);
  }

  public static boolean checkSync() {
    for (int i=0; i<10; i++)
      for (int j=0; j<10; j++)
        if (!flashed[i][j]) return false;

    return true;
  }

  public static void reduce() {
    for (int i=0; i<10; i++)
      for (int j=0; j<10; j++)
        if (flashed[i][j]) oct[i][j] = 0;
  }

  public static void flash(int x0, int y0) {
    int[][] checks = {{-1,-1},{0,-1},{1,-1},{-1,0},{1,0},{-1,1},{0,1},{1,1}};
    flashed[x0][y0] = true;
    total++;

    for (int[] pos : checks) {
      int x = x0 + pos[0];
      int y = y0 + pos[1];

      if (x >= 0 && x < 10 && y >= 0 && y < 10) {
        oct[x][y]++;
        if (!flashed[x][y] && oct[x][y] > 9) {
          flash(x,y);
        }
      }
    }
  }

  public static void increaseAll() {
    for (int i=0; i<10; i++)
      for (int j=0; j<10; j++)
        oct[i][j]++;
  }

  public static void populate(File f) {
    try {
      Scanner sc = new Scanner(f);
      String s;

      int i = 0;
      while (sc.hasNext()) {
        s = sc.nextLine();

        int j = 0;
        for (String w : s.split("(?!^)")) {
          oct[i][j++] = Integer.parseInt(w);
        }

        i++;
      }
    }
    catch (IOException e) {
      e.printStackTrace(); 
    }
  }
}