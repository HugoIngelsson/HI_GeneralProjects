import java.util.*;
import java.io.*;

//71 x 71
class Main {
  static boolean[][] elves;
  static boolean[][] nextR;
  static int[][] props;
  static int nElves = 0;
  static int round = 0;
  
  public static void main(String[] args) {
    File f = new File("data.txt");
    sol(f);
  }

  public static void sol(File f) {
    int n = 200;
    try {
      Scanner in = new Scanner(f);
      elves = new boolean[n][n];

      int i = 0;
      while (in.hasNext()) {
        String next = in.nextLine();
        for (int j=0; j<71; j++) {
          if (next.charAt(j) == '#') {
            elves[i+65][j+65] = true;
            nElves++;
          }
        }
        i++;
      }
      // for (boolean[] b : elves) {
      //   for (boolean a : b) if (a) System.out.print("#"); else System.out.print(" ");
      //   System.out.println();
      // }

      for (int r=0; r<1; r++) {
        int cnt = 0;
        props = new int[n][n];
        for (int y=0; y<n; y++) {
          for (int x=0; x<n; x++) {
            if (elves[y][x]) {
              if (!clear(y, x)) {
                for (int j=0; j<4; j++) {
                  if (order((round+j)%4, x, y)) {
                    incProp((round+j)%4, x, y);
                    j = 4;
                  }
                  else if (j == 3) props[y][x] = 1;
                }
              }
              else props[y][x] = 1;
            }
          }
        }

        nextR = new boolean[n][n];
        for (int y=0; y<n; y++) {
          for (int x=0; x<n; x++) {
            if (props[y][x] == 1) {
              nextR[y][x] = true;
              if (!elves[y][x]) cnt++;
            } else if (props[y][x] == 2) {
              if (elves[y-1][x]) {
                nextR[y-1][x] = true;
                nextR[y+1][x] = true;
              }
              else {
                nextR[y][x-1] = true;
                nextR[y][x+1] = true;
              }
            }
          }
        }

        if (cnt != 0) r--;
        elves = nextR;
        // int k = 0;
        // for (boolean[] b : elves) {
        //   System.out.print(k++);
        //   for (boolean a : b) if (a) System.out.print("#"); else System.out.print(" ");
        // System.out.println();
        // }
        round++;
        System.out.println(round);
      }

      int minX = n, maxX = 0, minY = n, maxY = 0;
      for (int y=0; y<n; y++) {
        for (int x=0; x<n; x++) {
          if (elves[y][x]) {
            minX = Math.min(minX, x);
            maxX = Math.max(maxX, x);
            minY = Math.min(minY, y);
            maxY = Math.max(maxY, y);
          }
        }
      }

      // for (boolean[] b : elves) {
      //   for (boolean a : b) if (a) System.out.print("#"); else System.out.print(" ");
      // System.out.println();
      // }
      
      System.out.println(minX + " " + maxX + " " + minY + " " + maxY + " " + round);

      System.out.println((maxX - minX + 1) * (maxY - minY + 1) - nElves);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void incProp(int i, int x, int y) {
    if (i == 0) {
      props[y-1][x]++;
    } else if (i == 1) {
      props[y+1][x]++;
    } else if (i == 2) {
      props[y][x-1]++;
    } else if (i == 3) {
      props[y][x+1]++;
    }
  }

  public static boolean order(int i, int x, int y) {
    if (i == 0) {
      return checkUP(x, y);
    } else if (i == 1) {
      return checkDOWN(x, y);
    } else if (i == 2) {
      return checkLEFT(x, y);
    } else if (i == 3) {
      return checkRIGHT(x, y);
    }

    return false;
  }

  //OOPS flipped the order hehe
  public static boolean clear(int x, int y) {
    return !(elves[x-1][y-1] || elves[x-1][y] || elves[x-1][y+1] || elves[x][y+1] || elves[x][y-1] || elves[x+1][y-1] || elves[x+1][y] || elves[x+1][y+1]);
  }

  public static boolean checkUP(int x, int y) {
    return !(elves[y-1][x-1] || elves[y-1][x] || elves[y-1][x+1]);
  }

  public static boolean checkDOWN(int x, int y) {
    return !(elves[y+1][x-1] || elves[y+1][x] || elves[y+1][x+1]);
  }

  public static boolean checkRIGHT(int x, int y) {
    return !(elves[y-1][x+1] || elves[y][x+1] || elves[y+1][x+1]);
  }

  public static boolean checkLEFT(int x, int y) {
    return !(elves[y-1][x-1] || elves[y][x-1] || elves[y+1][x-1]);
  }
}