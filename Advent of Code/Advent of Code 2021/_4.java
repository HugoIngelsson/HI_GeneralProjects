import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;

class Main {
  static int[] key = {13,79,74,35,76,12,43,71,87,72,23,91,31,67,58,61,96,16,81,92,41,6,32,86,77,42,0,55,68,14,53,26,25,11,45,94,75,1,93,83,52,7,4,22,34,64,69,88,65,66,39,97,27,29,78,5,49,82,54,46,51,28,98,36,48,15,2,50,38,24,89,59,8,3,18,47,10,90,21,80,73,33,85,62,19,37,57,95,60,20,99,17,63,56,84,44,40,70,9,30};
  static int[][] board = new int[5][5];

  public static void main(String[] args) {
    File file = new File("data.txt");
    readValues(file);
  }

  public static void readValues(File f) {
    try {
      Scanner in = new Scanner(f);
      int id = 0;
      int best = 0;
      int num = 0;

      for (int i=0; i<99; i++) {
        in.nextLine();
        populate(in);

        while (!BINGO()) {
          remove(id++);
        }

        System.out.println(--id + " " + countRemaining() + " " + key[id]);
        if (id > best) {
          num = countRemaining() * key[id];
          best = id;
        }
        id = 0;
      }

      System.out.println(num);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void populate(Scanner s) {
    for (int row = 0; row < 5; row++) {
      for (int col = 0; col < 5; col ++) {
        board[row][col] = s.nextInt();
      }
      s.nextLine();
    }
  }

  public static void remove(int id) {
    for (int row = 0; row < 5; row++) {
      for (int col = 0; col < 5; col ++) {
        if (board[row][col] == key[id])
          board[row][col] = -1;
      }
    }
  }

  public static boolean BINGO() {
    for (int i = 0; i < 5; i++) {
      if (board[i][0] == -1 && board[i][1] == -1 && board[i][2] == -1 && board[i][3] == -1 && board[i][4] == -1)
        return true;
      if (board[0][i] == -1 && board[1][i] == -1 && board[2][i] == -1 && board[3][i] == -1 && board[4][i] == -1)
        return true;
    }
    
    return false;
  }

  public static int countRemaining() {
    int count = 0;

    for (int row = 0; row < 5; row++) {
      for (int col = 0; col < 5; col ++) {
        if (board[row][col] != -1)
          count += board[row][col];
      }
    }

    return count;
  }
}