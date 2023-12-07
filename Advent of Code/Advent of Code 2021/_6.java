import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;

class Main {
  static ArrayList<Integer> list1 = new ArrayList<Integer>();
  static double[] list = new double[9];

  public static void main(String[] args) {
    try {
      File file = new File("data.txt");
      Scanner sc = new Scanner(file);
      String data = sc.nextLine();
      int temp;

      while (data.contains(",")) {
        temp = Integer.parseInt(data.substring(0, data.indexOf(",")));
        data = data.substring(data.indexOf(",") + 1);
        list[temp]++;
      }

      list[Integer.parseInt(data)]++;

      for (int i=0; i<256; i++) {
        efficientBreed();
      }

      double total = 0;
      for (int i=0; i<9; i++)
        total += list[i];

      System.out.printf("%.0f", total);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  //Solution 2 - better/optimized
  public static void efficientBreed() {
    double news = list[0];

    for (int i=1; i<9; i++) {
      list[i-1] = list[i];
    }

    list[6] += news;
    list[8] = news;
  }

  //Solution 1
  public static void breed(ArrayList<Integer> list) {
    int size = list.size();

    for (int i=0; i<size; i++) {
      if (list.get(i) == 0) {
        list.set(i, 6);
        list.add(8);
      }
      else {
        list.set(i, list.get(i) - 1);
      }
    }
  }
}