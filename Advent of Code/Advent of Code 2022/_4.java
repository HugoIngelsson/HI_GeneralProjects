import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.*;

class Main {
  public static void main(String[] args) {
    File f = new File("data.txt");
    sol1(f);
    sol2(f);
  }

  public static void sol1(File f) {
    try {
      Scanner in = new Scanner(f);
      int c = 0;
      int n1;
      int n2;
      int n3;
      int n4;

      while (in.hasNext()) {
        String s = in.nextLine();

        n1 = Integer.parseInt(s.substring(0, s.indexOf("-")));
        n2 = Integer.parseInt(s.substring(s.indexOf("-")+1,s.indexOf(",")));
        s = s.substring(s.indexOf(",")+1);
        n3 = Integer.parseInt(s.substring(0, s.indexOf("-")));
        n4 = Integer.parseInt(s.substring(s.indexOf("-")+1));

        if ((n1 <= n3 && n2 >= n4) || (n1 >= n3 && n2 <= n4))
          c++;
      }

      System.out.println(c);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void sol2(File f) {
    try {
      Scanner in = new Scanner(f);
      int c = 0;
      int n1;
      int n2;
      int n3;
      int n4;

      while (in.hasNext()) {
        String s = in.nextLine();

        n1 = Integer.parseInt(s.substring(0, s.indexOf("-")));
        n2 = Integer.parseInt(s.substring(s.indexOf("-")+1,s.indexOf(",")));
        s = s.substring(s.indexOf(",")+1);
        n3 = Integer.parseInt(s.substring(0, s.indexOf("-")));
        n4 = Integer.parseInt(s.substring(s.indexOf("-")+1));

        if (n1 <= n4 && n2 >= n3)
          c++;
      }

      System.out.println(c);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
}

//if (s.charAt(0) >= s.charAt(4) && s.charAt(2) <= s.charAt(6) || 
//    s.charAt(0) <= s.charAt(4) && s.charAt(2) >= s.charAt(6)) {
//      c++;
//}