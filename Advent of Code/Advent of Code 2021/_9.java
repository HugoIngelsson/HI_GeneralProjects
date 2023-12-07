import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

class Main {
  static int[][] map = new int[100][100];
  static boolean[][] basins = new boolean[100][100];

  public static void readValues(File f) {
    //This reads the numbers in the data file 
    //And stores them in the array "values"
    try { 
        Scanner sc = new Scanner(f);
        int i = 0;
        int j = 0;
        String next;
        
        while (sc.hasNext()) {
          next = sc.nextLine();
          for (String s : next.split("(?!^)")) {
            map[i][j++] = Integer.parseInt(s);
          }

          i++;
          j = 0;
        }

        sc.close(); 
      } catch (IOException e){ 
        e.printStackTrace(); 
      }

  }

  public static void main(String[] args) {
    File file = new File ("data.txt");
    readValues(file);

    System.out.println(problem2());
  }

  public static int problem2() {
    int _1 = 0;
    int _2 = 0;
    int _3 = 0;
    int size = 0;

    populateBasin();
    for (int r = 0; r < 100; r++) {
      for (int c = 0; c < 100; c++) {
        if (basins[r][c]) {
          size = recur(r, c);
          if (size > _1) {
            _3 = _2;
            _2 = _1;
            _1 = size;
          }
          else if (size > _2) {
            _3 = _2;
            _2 = size;
          }
          else if (size > _3) {
            _3 = size;
          }
        }
      }
    }

    return _1 * _2 * _3;
  }

  public static int recur(int r, int c) {
    int total = 0;
    if (basins[r][c]) {
      basins[r][c] = false;
      if (r > 0) total += recur(r-1, c);
      if (r < 99) total += recur(r+1, c);
      if (c > 0) total += recur(r, c-1);
      if (c < 99) total += recur(r, c+1);
      total += 1;
    }

    return total;
  }

  public static void populateBasin() {
    for (int r = 0; r < 100; r++) {
      for (int c = 0; c < 100; c++) {
        if (map[r][c] != 9) basins[r][c] = true;
      }
    }
  }

  //Problem1
  public static int problem1(int[][] map) {
    int tot = 0;
    int val;

    for (int row = 0; row < 100; row++) {
      for (int col = 0; col < 100; col++) {
        val = map[row][col];

        if ((row < 1 || val < map[row-1][col]) && (row > 98 || val < map[row+1][col]) && (col < 1 || val < map[row][col-1]) && (col > 98 || val < map[row][col+1]))
          tot += val + 1;
      }
    }

    return tot;
  }
}