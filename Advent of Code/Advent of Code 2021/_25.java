import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.*;

class Main {
  static int _1 = 137;
  static int _2 = 139;
  static char[][] board = new char[_1][_2];
  static int cnt = 0;
  static boolean b = true;

  public static void main(String[] args) {
    File f = new File("data.txt");
    readValues(f);

    while (b) iterate();
    visualize();

    System.out.println(cnt);
  }

  public static int countV() {
    int ret = 0;

    for (int i=0; i<_1; i++) {
      for (int j=0; j<_2; j++) {
        if (board[i][j] == '>') ret++;
      }
    }

    return ret;
  }

  public static void visualize() {
    for (int i=0; i<_1; i++) {
      for (int j=0; j<_2; j++) System.out.print(board[i][j]);
      System.out.println();
    }
  }

  public static void iterate() {
    char[][] temp = new char[_1][_2];

    for (int i=0; i<_1; i++) {
      for (int j=0; j<_2; j++) {
        temp[i][j] = '.';
      }
    }

    for (int i=0; i<_1; i++) {
      for (int j=0; j<_2; j++) {
        if (board[i][j] == '>' && board[i][(j+1)%_2] == '.') {
          temp[i][(j+1)%_2] = '>';
        }
        else if (board[i][j] == '>') {
          temp[i][j] = '>';
        }
      }
    }

    for (int i=0; i<_1; i++) {
      for (int j=0; j<_2; j++) {
        if (board[i][j] == 'v' && temp[(i+1)%_1][j] != '>' && board[(i+1)%_1][j] != 'v') {
          temp[(i+1)%_1][j] = 'v';
        }
        else if (board[i][j] == 'v') {
          temp[i][j] = 'v';
        }
      }
    }

    if (areEqual(temp)) b = false;
    board = temp;
    System.out.println(cnt++);
  }

  public static boolean areEqual(char[][] check) {
    for (int i=0; i<_1; i++) {
      for (int j=0; j<_2; j++) {
        if (board[i][j] != check[i][j]) return false;
      }
    }

    return true;
  }

  public static void readValues(File f) {
    try {
      Scanner sc = new Scanner(f);

      int i = 0;
      while (sc.hasNext()) {
        String s = sc.nextLine();

        for (int j=0; j<s.length(); j++) {
          board[i][j] = s.charAt(j);
        }

        i++;
      }
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
}