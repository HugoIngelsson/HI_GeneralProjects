import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.*;

class Main {
  public static void main(String[] args) {
    File f = new File("data.txt");
    sol(f);
  }

  static boolean[][][] ext = new boolean[100][100][100];
  static boolean[][][] fill = new boolean[24][24][24];
  public static void sol(File f) {
    try {
      Scanner in = new Scanner(f);
      
      while (in.hasNext()) {
        String[] xyz = in.nextLine().split(",");
        fill[Integer.parseInt(xyz[0])+1][Integer.parseInt(xyz[1])+1][Integer.parseInt(xyz[2])+1] = true;
      }
      fillExterior(0,0,0);

      int SA = 0;
      int sides = 0;
      for (int i=0; i<23; i++) {
        for (int j=0; j<23; j++) {
          for (int k=0; k<23; k++) {
            if (fill[i][j][k]) SA += openSides(fill, i, j, k);
            if (fill[i][j][k]) sides += checkSides(i, j, k);
          }
        }
      }

      System.out.println(SA + " " + sides);
    }
    catch(IOException e) {
      e.printStackTrace();
    }
  }

  public static int openSides(boolean[][][] fill, int x, int y, int z) {
    int ret = 0;

    if (!fill[x+1][y][z]) ret++;
    if (!fill[x-1][y][z]) ret++;
    if (!fill[x][y+1][z]) ret++;
    if (!fill[x][y-1][z]) ret++;
    if (!fill[x][y][z+1]) ret++;
    if (!fill[x][y][z-1]) ret++;

    return ret;
  }

  public static int checkSides(int x, int y, int z) {
    int ret = 0;

    if (ext[x+1][y][z]) ret++;
    if (ext[x-1][y][z]) ret++;
    if (ext[x][y+1][z]) ret++;
    if (ext[x][y-1][z]) ret++;
    if (ext[x][y][z+1]) ret++;
    if (ext[x][y][z-1]) ret++;

    return ret;
  }

  public static void fillExterior(int x, int y, int z) {
    if (x>0 && !ext[x-1][y][z] && !fill[x-1][y][z]) {
      ext[x-1][y][z] = true;
      fillExterior(x-1, y, z);
    }
    if (x<23 && !ext[x+1][y][z] && !fill[x+1][y][z]) {
      ext[x+1][y][z] = true;
      System.out.println(x + " " + y + " " + z);
      fillExterior(x+1, y, z);
    }
    if (y>0 && !ext[x][y-1][z] && !fill[x][y-1][z]) {
      ext[x][y-1][z] = true;
      fillExterior(x, y-1, z);
    }
    if (y<23 && !ext[x][y+1][z] && !fill[x][y+1][z]) {
      ext[x][y+1][z] = true;
      fillExterior(x, y+1, z);
    }
    if (z>0 && !ext[x][y][z-1] && !fill[x][y][z-1]) {
      ext[x][y][z-1] = true;
      fillExterior(x, y, z-1);
    }
    if (z<23 && !ext[x][y][z+1] && !fill[x][y][z+1]) {
      ext[x][y][z+1] = true;
      fillExterior(x, y, z+1);
    }
  }
}