import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.*;

class Main {
  static boolean[][] map = new boolean[200][200];
  static String key;
  static String outer = "0";

  public static void main(String[] args) {
    File f = new File("data.txt");

    populate(f);

    for (int i=0; i<50; i++) iterate();

    int count = 0;
    for (int i=0; i<200; i++)
      for (int j=0; j<200; j++)
        if (map[i][j]) count++;

    System.out.println(count);

    for (boolean[] i : map) {
      for (boolean j : i) {
        if (j) System.out.print("#");
        else System.out.print(".");
      }
      System.out.println();
    }
  }

  public static void iterate() {
    boolean[][] temp = new boolean[200][200];

    for (int i=0; i<200; i++) {
      for (int j=0; j<200; j++) {
        temp[j][i] = surround(i,j);
      }
    }

    if (surround(-2,-2)) outer = "1";
    else outer = "0";

    map = temp;
  }

  public static boolean surround(int x, int y) {
    String ret = "";

    for (int i=y-1; i<=y+1; i++) {
      for (int j=x-1; j<=x+1; j++) {
        if (i<0 || i>199 || j<0 || j>199) ret += outer;
        else if (map[i][j] == true)  {
          ret += "1";
        }
        else ret += "0";
      }
    }

    //System.out.println(ret);
    return key.charAt(Integer.parseInt(ret, 2)) == '#';
  }

  public static void populate(File f) {
    try {
      Scanner sc = new Scanner(f);
      key = sc.nextLine();
      sc.nextLine();

      int i = 50;
      while (sc.hasNext()) {
        String s = sc.nextLine();
        for (int j=0; j<100; j++) {
          if (s.charAt(j) == '#') {
            map[i][j+50] = true;
          }
        }
        i++;
      }

    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
}