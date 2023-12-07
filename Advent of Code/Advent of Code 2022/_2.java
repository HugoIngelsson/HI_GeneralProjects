import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.*;

class Main {
  public static void main(String[] args) {
    File file = new File("data.txt");
    readValues(file);
  }

  public static void readValues(File f) {
    try {
      Scanner in = new Scanner(f);
      int score = 0;

      while (in.hasNext()) {
        String s = in.nextLine();
        int n = 0;

        if (s.charAt(0) == 'A') {
          n = 1;
        }
        else if (s.charAt(0) == 'B') {
          n = 2;
        }
        else if (s.charAt(0) == 'C') {
          n = 3;
        }

        if (s.charAt(2) == 'X') {
          score += (n+1)%3+1;
        }
        if (s.charAt(2) == 'Y') {
          score += (3 + n);
        }
        if (s.charAt(2) == 'Z') {
          score += (6 + n%3+1);
        }
        

        // if (s.equals("A X")) score += 4;
        // else if (s.equals("A Y")) score += 8;
        // else if (s.equals("A Z")) score += 3;
        // else if (s.equals("B X")) score += 1;
        // else if (s.equals("B Y")) score += 5;
        // else if (s.equals("B Z")) score += 9;
        // else if (s.equals("C X")) score += 7;
        // else if (s.equals("C Y")) score += 2;
        // else if (s.equals("C Z")) score += 6;
      }

      System.out.println(score);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
}