import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.*;

class Main {
  public static void main(String[] args) {
    File f = new File("data.txt");
    sol2(f);
  }

  public static void sol1(File f) {
    try {
      Scanner in = new Scanner(f);
      String sack;
      int score = 0;

      while (in.hasNext()) {
        sack = in.nextLine();
        String p1 = sack.substring(0, sack.length()/2);
        String p2 = sack.substring(sack.length()/2, sack.length());

        for (int i=0; i<p1.length(); i++) {
          if (p2.contains(p1.substring(i,i+1))) {
            if (p1.charAt(i) < 91) score += p1.charAt(i) - 38;
            else score += p1.charAt(i) - 96;
            break;
          }
        }
      }

      System.out.println(score);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void sol2(File f) {
    try {
      Scanner in = new Scanner(f);
      String s1, s2, s3;
      int score = 0;

      while (in.hasNext()) {
        s1 = in.nextLine();
        s2 = in.nextLine();
        s3 = in.nextLine();

        for (int i=0; i<s1.length(); i++) {
          if (s2.contains(s1.substring(i,i+1)) && s3.contains(s1.substring(i,i+1))) {
            if (s1.charAt(i) < 91) score += s1.charAt(i) - 38;
            else score += s1.charAt(i) - 96;
            break;
          }
        }
      }

      System.out.println(score);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
}