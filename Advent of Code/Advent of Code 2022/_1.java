import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.*;

class Main {
  public static void main(String[] args) {
    File file = new File("data2.txt");
    readValues(file);
  }
  
  public static void readValues(File f) {
    try {
      Scanner in = new Scanner(f);

      String s;
      int max1 = 0;
      int max2 = 0;
      int max3 = 0;
      int c = 0;
      boolean t = false;
      
      while (in.hasNext()) {
        s = in.nextLine();
        if (!s.equals("")) {
          c += Integer.parseInt(s);
          t = false;
        }
        else {
          t = true;
        }
        
        if (t & c > max1) {
          max3 = max2;
          max2 = max1;
          max1 = c;
        }
        else if (t & c > max2) {
          max3 = max2;
          max2 = c;
        }
        else if (t & c > max3) {
          max3 = c;
        }

        if (t) c = 0;
      }

      System.out.println(max1 + max2 + max3);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
}