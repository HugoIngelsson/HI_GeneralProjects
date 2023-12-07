import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.*;

class Main {
  public static boolean[][] board;
  public static int max;
  
  public static void main(String[] args) {
    File f = new File("data.txt");
    createBoard(f);

    int i=0;
    while (addSand()) {
      i++;
    }
    System.out.println(i+1);
  }

  public static boolean addSand() {
    int y = 0;
    int x = 500;

    while (y < 199) {
      if (board[x][y+1] == false) {
        y++;
      }
      else {
        if (board[x-1][y+1] == false) {
          x--;
          y++;
        } else if (board[x+1][y+1] == false) {
          x++;
          y++;
        } else {
          board[x][y] = true;
          if (x == 500 && y == 0) return false;
          return true;
        }
      }
    }
  
    return false;
  }

  public static void createBoard(File f) {
    try {
      Scanner in = new Scanner(f);
      board = new boolean[1000][200];
      max = 0;

      while (in.hasNext()) {
        String next = in.nextLine();

        while (next.indexOf("->") >= 0) {
          int x1 = Integer.parseInt(next.substring(0, next.indexOf(",")));
          int y1 = Integer.parseInt(next.substring(next.indexOf(",")+1, next.indexOf(" -> ")));
          next = next.substring(next.indexOf(" -> ") + 4);
          int x2 = Integer.parseInt(next.substring(0, next.indexOf(",")));
          int y2;
          if (next.contains("->")) y2 = Integer.parseInt(next.substring(next.indexOf(",")+1, next.indexOf(" -> ")));
          else y2 = Integer.parseInt(next.substring(next.indexOf(",")+1));
          max = Math.max(Math.max(y1, y2), max);

          if (x1 == x2) {
            if (y1 < y2) {
              for (int i=y1; i<=y2; i++) {
                board[x1][i] = true;
              }
            } else {
              for (int i=y1; i>=y2; i--) {
                board[x1][i] = true;
              }
            }
          } else {
            if (x1 < x2) {
              for (int i=x1; i<=x2; i++) {
                board[i][y1] = true;
              }
            } else {
              for (int i=x1; i>=x2; i--) {
                board[i][y1] = true;
              }
            }
          }
        }
      }

      for (int i=0; i<1000; i++) board[i][max+2] = true;
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
}