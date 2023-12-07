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
      String data = in.nextLine();
      boolean[] check = new boolean[100];

      for (int i=0; i<data.length(); i++) {
        if (!(data.charAt(i) == data.charAt(i+1) || data.charAt(i) == data.charAt(i+2) || data.charAt(i) == data.charAt(i+3) || data.charAt(i+1) == data.charAt(i+2) || data.charAt(i+1) == data.charAt(i+3) || data.charAt(i+2) == data.charAt(i+3))) {
          System.out.println(i+4);
          break;
        }
      }
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void sol2(File f) {
    try {
      Scanner in = new Scanner(f);
      String data = in.nextLine();
      boolean[] check = new boolean[200];
      boolean exit = false;

      for (int i=0; i<data.length()-13; i++) {
        check = new boolean[200];
        exit = true;
        for (int j=i; j<i+14; j++) {
          if (!check[Character.getNumericValue(data.charAt(j))]) {
            check[Character.getNumericValue(data.charAt(j))] = true;
          }
          else {
            exit = false;
            break;
          }
        }

        if (exit) {
          System.out.println(i+14);
          break;
        }
      }
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
}