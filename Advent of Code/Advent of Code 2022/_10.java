import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.*;

class Main {
  public static void main(String[] args) {
    File f = new File("data.txt");
    sol(f);
  }

  public static void sol(File f) {
    try {
      Scanner in = new Scanner(f);
      int cycle = 1;
      int x = 1;
      int sum = 0;

      System.out.print("#");
      while (in.hasNext()) {
        String next = in.nextLine();

        if (next.equals("noop")) {
          cycle++;
          sum += tick(cycle, x);
          tick2(cycle, x);
        }
        else {
          cycle++;
          sum += tick(cycle, x);
          tick2(cycle, x);
          cycle++;
          x += Integer.parseInt(next.substring(5));
          sum += tick(cycle, x);
          tick2(cycle, x);
        }
      }

      System.out.println(sum);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static int tick(int cycle, int x) {
    if ((cycle+20)%40 == 0)  {
      return x*cycle;
    }
    return 0;
  }

  public static void tick2(int cycle, int x) {
    if (cycle%40 == 0)  {
      if (Math.abs(((cycle-1)%40) - x) <= 1) System.out.println("#");
      else System.out.println(".");
    }
    else {
      if (Math.abs((cycle-1)%40 - x) <= 1) System.out.print("#");
      else System.out.print(".");
    }
  }
}