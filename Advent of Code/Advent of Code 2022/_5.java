import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.*;

class Main {
  public static void main(String[] args) {
    File f = new File("data.txt");
    //sol1(f);
    sol2(f);
  }

  public static void sol1(File f) {
    try {
      Scanner in = new Scanner(f);
      ArrayList<String>[] stacks = new ArrayList[9];
      for (int i=0; i<9; i++) {
        stacks[i] = new ArrayList<String>();
      }
      
      String ln;

      for (int i=0; i<8; i++) {
        ln = in.nextLine();
        for (int j=0; j<9; j++) {
          if (!ln.substring(1+4*j, 2+4*j).equals(" "));
            stacks[j].add(ln.substring(1+4*j,2+4*j));

          if (stacks[j].get(0).equals(" "))
            stacks[j].remove(0);
        }
      }

      in.nextLine();
      in.nextLine();

      int n = 0, start, end;

      while (in.hasNext()) {
        ln = in.nextLine();
        
        n = Integer.parseInt(ln.substring(5, ln.indexOf(" from ")));
        start = Integer.parseInt(ln.substring(ln.indexOf(" from ") + 6, ln.indexOf(" to ")));
        end = Integer.parseInt(ln.substring(ln.indexOf(" to ") + 4));

        for (int i=0; i<n; i++) {
          stacks[end-1].add(0, stacks[start-1].remove(0));
        }
      }

      System.out.println(Arrays.toString(stacks));
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void sol2(File f) {
    try {
      Scanner in = new Scanner(f);
      ArrayList<String>[] stacks = new ArrayList[9];
      for (int i=0; i<9; i++) {
        stacks[i] = new ArrayList<String>();
      }
      
      String ln;

      for (int i=0; i<8; i++) {
        ln = in.nextLine();
        for (int j=0; j<9; j++) {
          if (!ln.substring(1+4*j, 2+4*j).equals(" "));
            stacks[j].add(ln.substring(1+4*j,2+4*j));

          if (stacks[j].get(0).equals(" "))
            stacks[j].remove(0);
        }
      }

      in.nextLine();
      in.nextLine();

      int n = 0, start, end;

      while (in.hasNext()) {
        ln = in.nextLine();
        
        n = Integer.parseInt(ln.substring(5, ln.indexOf(" from ")));
        start = Integer.parseInt(ln.substring(ln.indexOf(" from ") + 6, ln.indexOf(" to ")));
        end = Integer.parseInt(ln.substring(ln.indexOf(" to ") + 4));

        for (int i=n-1; i>=0; i--) {
          stacks[end-1].add(0, stacks[start-1].remove(i));
        }
      }

      System.out.println(Arrays.toString(stacks));
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
}