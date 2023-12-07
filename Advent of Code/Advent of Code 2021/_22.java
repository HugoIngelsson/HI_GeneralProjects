import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.*;

class Main {
  static boolean[][][] map = new boolean[101][101][101];
  static int[][] cubes = new int[10000][6];
  static int n = 0;
  static int[] removal = new int[6];

  public static void main(String[] args) {
    File f = new File("data.txt");
    n = 0;
    populate(f);
    System.out.printf("%.0f", countCubes());
  }

  public static double countCubes() {
    double total = 0;

    for (int i=0; i<n; i++) {
      if (cubes[i][0] > Integer.MIN_VALUE) {
        int x = Math.abs(cubes[i][0] - cubes[i][3]) + 1;
        int y = Math.abs(cubes[i][1] - cubes[i][4]) + 1;
        int z = Math.abs(cubes[i][2] - cubes[i][5]) + 1;
        total += 1.0*x*y*z;
      }
    }

    return total;
  }

  public static void removeSpecific(int x1, int x2, int y1, int y2, int z1, int z2) {
    if (x1 == x2 || y1 == y2 || z1 == z2) return;
    if (((removal[0] <= x1 && removal[3] >= x1) || (removal[0] <= x2 && removal[3] >= x2) || (removal[0] >= x1 && removal[3] <= x2)) && ((removal[1] <= y1 && removal[4] >= y1) || (removal[1] <= y2 && removal[4] >= y2) || (removal[1] >= y1 && removal[4] <= y2)) && ((removal[2] <= z1 && removal[5] >= z1) || (removal[2] <= z2 && removal[5] >= z2) || (removal[2] >= z1 && removal[5] <= z2))) {
      if (removal[0] <= x1 && removal[3] >= x1 && removal[3] < x2) removeSpecific(removal[3]+1, x2, y1, y2, z1, z2);
      else if (removal[0] <= x2 && removal[3] >= x2 && removal[0] > x1) removeSpecific(x1, removal[0]-1, y1, y2, z1, z2);
      else if (removal[0] > x1 && removal[3] < x2) {
          removeSpecific(removal[3]+1, x2, y1, y2, z1, z2);
          removeSpecific(x1, removal[0]-1, y1, y2, z1, z2);
        }
      if (removal[1] <= y1 && removal[4] >= y1 && removal[4] < y2) removeSpecific(x1, x2, removal[4]+1, y2, z1, z2);
      else if (removal[1] <= y2 && removal[4] >= y2 && removal[1] > y1) removeSpecific(x1, x2, y1, removal[1]-1, z1, z2);
      else if (removal[1] > y1 && removal[4] < y2) {
          removeSpecific(x1, x2, y1, removal[1]-1, z1, z2);
          removeSpecific(x1, x2, removal[4]+1, y2, z1, z2);
        }
      if (removal[2] <= z1 && removal[5] >= z1 && removal[5] < z2) removeSpecific(x1, x2, y1, y2, removal[5]+1, z2);
      else if (removal[2] <= z2 && removal[5] >= z2 && removal[2] > z1) removeSpecific(x1, x2, y1, y2, z1, removal[2]-1);
      else if (removal[2] > z1 && removal[5] < z2) {
          removeSpecific(x1, x2, y1, y2, z1, removal[2]-1);
          removeSpecific(x1, x2, y1, y2, removal[5]+1, z2);
        }
      return;
    }

    addCube(x1, x2, y1, y2, z1, z2);
  }

  public static void removeCube(int x1, int x2, int y1, int y2, int z1, int z2) {
    removal[0] = x1;
    removal[1] = y1;
    removal[2] = z1;
    removal[3] = x2;
    removal[4] = y2;
    removal[5] = z2;

    for (int i=0; i<n; i++) {
      //Checks whether there's a point within an already existing cube
      if (((cubes[i][0] <= x1 && cubes[i][3] >= x1) || (cubes[i][0] <= x2 && cubes[i][3] >= x2) || (cubes[i][0] >= x1 && cubes[i][3] <= x2)) && ((cubes[i][1] <= y1 && cubes[i][4] >= y1) || (cubes[i][1] <= y2 && cubes[i][4] >= y2) || (cubes[i][1] >= y1 && cubes[i][4] <= y2)) && ((cubes[i][2] <= z1 && cubes[i][5] >= z1) || (cubes[i][2] <= z2 && cubes[i][5] >= z2) || (cubes[i][2] >= z1 && cubes[i][5] <= z2))) {
        int dx1 = cubes[i][0];
        int dy1 = cubes[i][1];
        int dz1 = cubes[i][2];
        int dx2 = cubes[i][3];
        int dy2 = cubes[i][4];
        int dz2 = cubes[i][5];
        cubes[i][0] = Integer.MIN_VALUE;
        cubes[i][1] = Integer.MIN_VALUE;
        cubes[i][2] = Integer.MIN_VALUE;
        cubes[i][3] = Integer.MIN_VALUE;
        cubes[i][4] = Integer.MIN_VALUE;
        cubes[i][5] = Integer.MIN_VALUE;
        removeSpecific(dx1, dx2, dy1, dy2, dz1, dz2);
        System.out.println(n);
      }
    }
  }

  public static void addCube(int x1, int x2, int y1, int y2, int z1, int z2) {
    if (x1 == x2 || y1 == y2 || z1 == z2) return;
    for (int i=0; i<n; i++) {
      if (((cubes[i][0] <= x1 && cubes[i][3] >= x1) || (cubes[i][0] <= x2 && cubes[i][3] >= x2) || (cubes[i][0] >= x1 && cubes[i][3] <= x2)) && ((cubes[i][1] <= y1 && cubes[i][4] >= y1) || (cubes[i][1] <= y2 && cubes[i][4] >= y2) || (cubes[i][1] >= y1 && cubes[i][4] <= y2)) && ((cubes[i][2] <= z1 && cubes[i][5] >= z1) || (cubes[i][2] <= z2 && cubes[i][5] >= z2) || (cubes[i][2] >= z1 && cubes[i][5] <= z2))) {
        if (cubes[i][0] <= x1 && cubes[i][3] >= x1 && cubes[i][3] < x2) addCube(cubes[i][3]+1, x2, y1, y2, z1, z2);
        else if (cubes[i][0] <= x2 && cubes[i][3] >= x2 && cubes[i][0] > x1) addCube(x1, cubes[i][0]-1, y1, y2, z1, z2);
        else if (cubes[i][0] > x1 && cubes[i][3] < x2) {
          addCube(cubes[i][3]+1, x2, y1, y2, z1, z2);
          addCube(x1, cubes[i][0]-1, y1, y2, z1, z2);
        }
        if (cubes[i][1] <= y1 && cubes[i][4] >= y1 && cubes[i][4] < y2) addCube(x1, x2, cubes[i][4]+1, y2, z1, z2);
        else if (cubes[i][1] <= y2 && cubes[i][4] >= y2 && cubes[i][1] > y1) addCube(x1, x2, y1, cubes[i][1]-1, z1, z2);
        else if (cubes[i][1] > y1 && cubes[i][4] < y2) {
          addCube(x1, x2, y1, cubes[i][1]-1, z1, z2);
          addCube(x1, x2, cubes[i][4]+1, y2, z1, z2);
        }
        if (cubes[i][2] <= z1 && cubes[i][5] >= z1 && cubes[i][5] < z2) addCube(x1, x2, y1, y2, cubes[i][5]+1, z2);
        else if (cubes[i][2] <= z2 && cubes[i][5] >= z2 && cubes[i][2] > z1) addCube(x1, x2, y1, y2, z1, cubes[i][2]-1);
        else if (cubes[i][2] > z1 && cubes[i][5] < z2) {
          addCube(x1, x2, y1, y2, z1, cubes[i][2]-1);
          addCube(x1, x2, y1, y2, cubes[i][5]+1, z2);
        }
        //We know that this cube intersects something, so we need to get rid of it
        return;
      }
    }

    //If we reach this point, we know the cube we're checking doesn't intersect with any other cubes
    cubes[n][0] = x1;
    cubes[n][1] = y1;
    cubes[n][2] = z1;
    cubes[n][3] = x2;
    cubes[n][4] = y2;
    cubes[n][5] = z2;
    n++;
  }

  public static void populate(File f) {
    try {
      boolean assign = false;
      String s;
      Scanner sc = new Scanner(f);

      while (sc.hasNext()) {
        s = sc.nextLine();
        if (s.charAt(1) == 'n') {
          assign = true;
          s = s.substring(5);
        }
        else {
          assign = false;
          s = s.substring(6);
        }

        int x1 = Integer.parseInt(s.substring(0,s.indexOf("..")));
        s = s.substring(s.indexOf("..")+2);
        int x2 = Integer.parseInt(s.substring(0,s.indexOf(",")));
        s = s.substring(s.indexOf(",")+3);
        int y1 = Integer.parseInt(s.substring(0,s.indexOf("..")));
        s = s.substring(s.indexOf("..")+2);
        int y2 = Integer.parseInt(s.substring(0,s.indexOf(",")));
        s = s.substring(s.indexOf(",")+3);
        int z1 = Integer.parseInt(s.substring(0,s.indexOf("..")));
        s = s.substring(s.indexOf("..")+2);
        int z2 = Integer.parseInt(s);

        //if (x1 >= -50 && x2 <= 50 && y1 >= -50 && y2 <= 50 && z1 >= -50 && z2 <= 50) {
          System.out.println(x1 + " " + x2 + " " + y1 + " " + y2 + " " + z1 + " " + z2);
          if (assign) addCube(x1, x2, y1, y2, z1, z2);
          else removeCube(x1, x2, y1, y2, z1, z2);
        //}
      }
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
}
//4055