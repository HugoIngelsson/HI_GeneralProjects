package Minesweeper;

import java.io.*;
import java.util.*;

class Main {
  static int r, c, m, e;
  static int[][] board;
  static int[][] key;
  static int count;
  static boolean[][] clear;
  
  public static void main(String[] args) {
    File data = new File("Minesweeper/board.txt");

    testBoard(data);

    System.out.println();
    for (int[] b : board) {
      for (int i : b) System.out.print(i + "\t");
      System.out.println();
    }
    int score = 0, total = 0;

    for (int i=0; i<1000; i++) {
      if (i%10 == 0) System.out.println(i);
      randomBoard(16, 16, 40);
      
      PrintWriter pw = new PrintWriter(System.out);
      count = 1;
      while (count > 0) {
        Player playTest = new Player(board, m);
        ArrayList<Click> clicks = playTest.determineClick();
  
        if (clicks.size() == 0) {
          break;
        }
        else {
          for (Click c : clicks) {
            clickSpot(c);
          }
        }

        count++;
      }

      if (i%100 == 0) {
        printBoard("Minesweeper/boardout.txt", board);
      }
      
      
      if (count > 0) {
        score++;
      }
      total++;
    }

    System.out.println((double)score / total);
  }

  public static void clickSpot(Click cl) {
    if (cl.left) {
      if (key[cl.x][cl.y] == -99) {
        if (count == 1) {
          int rx = (int)(Math.random()*r);
          int ry = (int)(Math.random()*c);

          while (key[rx][ry] == -99) {
            rx = (int)(Math.random()*r);
            ry = (int)(Math.random()*c);
          }

          clear = new boolean[r][c];
          key[rx][ry] = -99;
          key[cl.x][cl.y] = adjacentMines(cl.x, cl.y);

          updateSpot(rx, ry);
          updateSpot(cl.x, cl.y);

          clickSpot(cl);
        }
        else {
          count = -1;
          board[cl.x][cl.y] = -77;
        }
      }
      else if (key[cl.x][cl.y] == 0) {
        board[cl.x][cl.y] = 0;
        clearSurround(cl.x, cl.y);
      }
      else {
        board[cl.x][cl.y] = key[cl.x][cl.y];
      }
    }
    else {
      board[cl.x][cl.y] = -99;
    }
  }

  public static void clearSurround(int x, int y) {
    for (int i=-1; i<=1; i++) {
      for (int j=-1; j<=1; j++) {
        if (x+i>=0 && x+i<r && y+j>=0 && y+j<c) {
          if (key[x+i][y+j] == 0 && board[x+i][y+j] != 0) {
            board[x+i][y+j] = 0;
            clearSurround(x+i, y+j);
          }
          else if (board[x+i][y+j] != 0) {
            board[x+i][y+j] = key[x+i][y+j];
          }
        }
      }
    }
  }

  public static void randomBoard(int rows, int cols, int mines) {
    r = rows; c = cols; m = mines;
    
    key = new int[r][c];
    board = new int[r][c];

    while (mines > 0) {
      int rx = (int)(Math.random()*rows);
      int ry = (int)(Math.random()*cols);

      if (key[rx][ry] != -99) {
        key[rx][ry] = -99;
        mines--;
      }
    }

    for (int i=0; i<rows; i++) {
      for (int j=0; j<cols; j++) {
        board[i][j] = -1;
        if (key[i][j] != -99) key[i][j] = adjacentMines(i, j);
      }
    }
  }

  public static void updateSpot(int x, int y) {
    for (int i=-1; i<=1; i++) {
      for (int j=-1; j<=1; j++) {
        if (x+i>=0 && x+i<r && y+j>=0 && y+j<c) {
          if (key[x+i][y+j] != -99 && !clear[x+i][y+j]) {
            clear[x+i][y+j] = true;
            key[x+i][y+j] = adjacentMines(x+i, y+j);
            if (key[x+i][y+j] == 0) updateSpot(x+i, y+j);
          }
        }
      }
    }
  }
  
  public static int adjacentMines(int x, int y) {
    int ret = 0;

    for (int i=-1; i<=1; i++) {
      for (int j=-1; j<=1; j++) {
        if (x+i>=0 && x+i<r && y+j>=0 && y+j<c) {
          if (key[x+i][y+j] == -99) ret++;
        }
      }
    }

    return ret;
  }

  public static void readBoard(File f) {
    try {
      Scanner sc = new Scanner(f);
      StringTokenizer st = new StringTokenizer(sc.nextLine());

      r = Integer.parseInt(st.nextToken());
      c = Integer.parseInt(st.nextToken());
      m = Integer.parseInt(st.nextToken());
      e = 0;
      board = new int[r][c];

      for (int i=0; i<r; i++) {
        String in = sc.nextLine();

        for (int j=0; j<c; j++) {
          if (in.charAt(j) == '.') {
            board[i][j] = -1;
            e++;
          } else if (in.charAt(j) == 'X') {
            board[i][j] = -99;
          } else if (in.charAt(j) == ' ') {
            board[i][j] = 0;
          } else {
            board[i][j] = in.charAt(j) - 48;
          }
        }
      }
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void testBoard(File f) {
    try {
      Scanner sc = new Scanner(f);
      StringTokenizer st = new StringTokenizer(sc.nextLine());

      r = Integer.parseInt(st.nextToken());
      c = Integer.parseInt(st.nextToken());
      m = Integer.parseInt(st.nextToken());
      e = 0;
      key = new int[r][c];
      board = new int[r][c];

      for (int i=0; i<r; i++) {
        String in = sc.nextLine();

        for (int j=0; j<c; j++) {
          if (in.charAt(j) == '.') {
            key[i][j] = 0;
            e++;
          }
          else if (in.charAt(j) == 'X') {
            key[i][j] = -99;
          } 
          else {
            key[i][j] = in.charAt(j) - 48;
          }
          board[i][j] = -1;
        }
      }
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void printBoard(String f, int[][] board) {
    try {
        PrintWriter pw = new PrintWriter(f);

        for (int[] row : board) {
            for (int i : row) {
                switch(i) {
                    case(-99): pw.print("X"); break;
                    case(-77): pw.print("#"); break;
                    case(-1): pw.print("."); break;
                    case(0): pw.print(" "); break;
                    default: pw.print(i); break;
                }
            }
            pw.println();
            pw.flush();
        }
        pw.flush();
        pw.close();
    }
    catch (IOException e) {
        e.printStackTrace();
    }
  }
}