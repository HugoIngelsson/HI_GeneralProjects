import java.util.*;
import java.io.*;

class Main {
  static int maxY = 35, maxX = 100;
  static boolean[][] up = new boolean[maxY][maxX];
  static boolean[][] down = new boolean[maxY][maxX];
  static boolean[][] left = new boolean[maxY][maxX];
  static boolean[][] right = new boolean[maxY][maxX];
  
  public static void main(String[] args) {
    File f = new File("data.txt");
    sol(f);
  }

  public static void sol(File f) {
    try {
      Scanner in = new Scanner(f);

      int _y = 0;
      in.nextLine();
      while (in.hasNext() && _y < maxY) {
        String next = in.nextLine();
        for (int x=0; x<maxX; x++) {
          if (next.charAt(x+1) == '^') up[_y][x] = true;
          else if (next.charAt(x+1) == 'v') down[_y][x] = true;
          else if (next.charAt(x+1) == '<') left[_y][x] = true;
          else if (next.charAt(x+1) == '>') right[_y][x] = true;
        }
        _y++;
      }

      int r = recur(0, 0, -1);
      System.out.println(r);
      thresh = 600;
      take2 = r;
      visited = new HashMap<String, Boolean>();
      r = recur2(r, maxX-1, maxY);
      System.out.println(r);
      thresh = 900;
      take2 = r;
      visited = new HashMap<String, Boolean>();
      r = recur(r, 0, -1);
      System.out.println(r);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  static HashMap<String, Boolean> visited = new HashMap<String, Boolean>();
  static int thresh = 300;
  static int take2 = 0;
  public static int recur(int round, int x, int y) {
    if (y != -1 && (down[(y-round+maxY*1000)%maxY][x] || up[(y+round)%maxY][x] || right[y][(x-round+maxX*1000)%maxX] || left[y][(x+round)%maxX])) return Integer.MAX_VALUE;
    if (visited.containsKey(round + " " + x + " " + y)) return Integer.MAX_VALUE;
    else visited.put(round + " " + x + " " + y, true);
    if (x == maxX - 1 && y == maxY - 1) return round+1;
    if (round > thresh) return Integer.MAX_VALUE;
    
    int min = Integer.MAX_VALUE;
    if (y != -1 || round < 10 + take2) min = Math.min(recur(round+1, x, y), min);
    if (x > 0) min = Math.min(recur(round+1, x-1, y), min);
    if (x < maxX - 1 && y != -1) min = Math.min(recur(round+1, x+1, y), min);
    if (y > 0) min = Math.min(recur(round+1, x, y-1), min);
    if (y < maxY - 1) min = Math.min(recur(round+1, x, y+1), min);
    
    return min;
  }
  
  public static int recur2(int round, int x, int y) {
    if (y != maxY && (down[(y-round+maxY*1000)%maxY][x] || up[(y+round)%maxY][x] || right[y][(x-round+maxX*1000)%maxX] || left[y][(x+round)%maxX])) return Integer.MAX_VALUE;
    if (visited.containsKey(round + " " + x + " " + y)) return Integer.MAX_VALUE;
    else visited.put(round + " " + x + " " + y, true);
    if (x == 0 && y == 0) return round+1;
    if (round > thresh) return Integer.MAX_VALUE;
    
    int min = Integer.MAX_VALUE;
    if (y != 35 || round < 10 + take2) min = Math.min(recur2(round+1, x, y), min);
    if (x > 0 && y != maxY) min = Math.min(recur2(round+1, x-1, y), min);
    if (x < maxX - 1) min = Math.min(recur2(round+1, x+1, y), min);
    if (y > 0) min = Math.min(recur2(round+1, x, y-1), min);
    if (y < maxY - 1) min = Math.min(recur2(round+1, x, y+1), min);
    
    return min;
  }
}