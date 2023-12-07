import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.*;

class Main {
  public static void main(String[] args) {
    File f = new File("data.txt");
    solution(f);
  }

  public static void solution(File f) {
    int x = 99;
    try {
      Scanner in = new Scanner(f);
      int[][] grid = new int[x][x];
      int k = 0;
      
      while (in.hasNext()) {
        String s = in.nextLine();

        for (int i=0; i<x; i++) {
          grid[k][i] = Integer.parseInt(s.substring(i,i+1));
        }

        k++;
      }

      int n = 0;
      int max = 0;
      for (int i=0; i<x; i++) {
        for (int j=0; j<x; j++) {
          if (isVisible(grid, i, j)) n++;
          max = Math.max(getScenicScore(grid, i, j), max);
        }
      }

      System.out.println("Number of Clear Trees: " + n);
      System.out.println("Max Scenic Score: " + max);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static boolean isVisible(int[][] grid, int x, int y) {
    boolean a = true, b = true, c = true, d = true;
    
    for (int i=x+1; i<99; i++) if (grid[i][y] >= grid[x][y]) a = false;
    for (int i=x-1; i>=0; i--) if (grid[i][y] >= grid[x][y]) b = false;
    for (int i=y+1; i<99; i++) if (grid[x][i] >= grid[x][y]) c = false;
    for (int i=y-1; i>=0; i--) if (grid[x][i] >= grid[x][y]) d = false;
    
    return a | b | c | d;
  }

  public static int getScenicScore(int[][] grid, int x, int y) {
    int ret = 1;

    for (int i=x+1; i<99; i++) 
      if (grid[i][y] >= grid[x][y]) {
        ret *= (i - x);
        break;
      } else if (i == 98) {
        ret *= (98 - x);
      }
    for (int i=x-1; i>=0; i--) 
      if (grid[i][y] >= grid[x][y]) {
        ret *= (x - i);
        break;
      } else if (i == 0) {
        ret *= x;
      }
    for (int i=y+1; i<99; i++) 
      if (grid[x][i] >= grid[x][y]) {
        ret *= (i - y);
        break;
      } else if (i == 98) {
        ret *= (98 - y);
      }
    for (int i=y-1; i>=0; i--) 
      if (grid[x][i] >= grid[x][y]) {
        ret *= (y - i);
        break;
      } else if (i == 0) {
        ret *= y;
      }
    
    return ret;
  }
}