import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

class Main {
  static boolean[][] marks = new boolean[1500][1500];

  public static void main(String[] args) {
    File file = new File("data.txt");
    populate(file);

    System.out.println(count());

    //Prints out the secret code
    for (int i=0; i<10; i++) {
      for (int j=0; j<100; j++) {
        if (marks[j][i]) System.out.print("O");
        else System.out.print(" ");
      }
      System.out.println();
    }
  }

  //Counts the number of marks left after a fold
  public static int count() {
    int total = 0;

    for (int i=0; i<1500; i++)
      for (int j=0; j<1500; j++)
        if (marks[i][j]) total++;

    return total;
  }

  //Folds the marks across the given line and in the x/y direction based on the input
  public static void fold(boolean xy, int line) {
    int delta;

    for (int x=0; x<1500; x++) {
      for (int y=line; y<1500; y++) {
        delta = y - line;
        if (xy && marks[x][y]) {
          marks[x][line-delta] = true;
          marks[x][y] = false;
        }
        else if (!xy && marks[y][x]) {
          marks[line-delta][x] = true;
          marks[y][x] = false;
        }
      }
    }
  }

  public static void populate(File f) {
    try {
      Scanner sc = new Scanner(f);
      String s;
      int line;
      boolean xy;

      while (sc.hasNext()) {
        s = sc.nextLine();
        if (s.contains("fold along")) {
          if (s.charAt(11) == 'y') xy = true;
          else xy = false;
          line = Integer.parseInt(s.substring(13));
          fold(xy, line);
        }
        else if (!s.contains(",")) continue;
        else {
          int x = Integer.parseInt(s.substring(0,s.indexOf(",")));
          int y = Integer.parseInt(s.substring(s.indexOf(",")+1));

          marks[x][y] = true;
        }
      }
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
}