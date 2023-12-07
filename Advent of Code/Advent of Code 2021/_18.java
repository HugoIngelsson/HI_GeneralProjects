import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.*;

class Main {
  static String snail = "";
  static String[] snailList = new String[100];

  public static void main(String[] args) {
    File f = new File("data.txt");
    populate(f);
    int best = 0;

    for (int i=0; i<100; i++) {
      for (int j=0; j<100; j++) {
        if (i != j) {
          snail = "[" + snailList[i] + "," + snailList[j] + "]";

          boolean check = true;

          while (check) {
            while (explode() > 0);
            if (split() < 0 && explode() < 0) check = false;
          }

          System.out.println(snail);
          best = Math.max(best, checkMagnitude(snail));
          System.out.println(best);
        }
      }
    }

    System.out.println(best);
  }

  public static void populate(File f) {
    try {
      Scanner sc = new Scanner(f);
      int id = 0;

      while (sc.hasNext()) {
        snailList[id++] = sc.nextLine();
      }
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void sumSnail(File f) {
    try {
      Scanner sc = new Scanner(f);
      snail = sc.nextLine();
      boolean check = true;

      while (sc.hasNext()) {
        snail = "[" + snail + "," + sc.nextLine() + "]";
        System.out.println(snail);

        check = true;

        while (check) {
          while (explode() > 0);
          if (split() < 0 && explode() < 0) check = false;
        }
        
        System.out.println(snail);
      }
      
      System.out.println(checkMagnitude(snail));
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static int checkMagnitude(String s) {
    if (s.length() == 0) return 0;
    if (s.length() > 3) s = s.substring(1, s.length()-1);
    if (s.length() == 3)
      return 3*Integer.parseInt(s.substring(0,1)) + 2*Integer.parseInt(s.substring(2,3));

    int count = 0;
    int id = 0;
    String s1 = "";
    String s2 = "";

    do {
      if (s.charAt(id) == '[') count++;
      else if (s.charAt(id) == ']') count--;
      id++;
    }
    while (count > 0);
    id += s.substring(id).indexOf(",");

    s1 = s.substring(0,id);
    s2 = s.substring(id+1);

    return 3*checkMagnitude(s1) + 2*checkMagnitude(s2);
  }

  public static int split() {
    int next;

    for (int i=0; i<snail.length(); i++) {
      if (Character.isDigit(snail.charAt(i))) {
        Scanner sc = new Scanner(snail.substring(i));
        sc.useDelimiter("[^0-9]+");
        next = sc.nextInt();
        if (next > 9) {
          snail = snail.substring(0,i) + "[" + next/2 + "," + ((int)Math.round(next/2.0)) + "]" + sc.nextLine();
          sc.close();
          return 1;
        }
      }
    }

    return -1;
  }

  public static int explode() {
    int count = 0;

    for (int i=0; i<snail.length(); i++) {
      if (snail.charAt(i) == '[') count++;
      else if (snail.charAt(i) == ']') count--;

      if (count == 5) {
        int id_ = snail.substring(i+1).indexOf(",");
        int _1 = Integer.parseInt(snail.substring(i+1,id_+i+1));
        int _2 = Integer.parseInt(snail.substring(id_+i+2,snail.substring(i+1).indexOf("]")+i+1));

        for (int k=i-1; k>=0; k--) {
          if ((snail.charAt(k) == '[' || snail.charAt(k) == ',') && Character.isDigit(snail.charAt(k+1))) {
            Scanner sc = new Scanner(snail.substring(k+1));
            sc.useDelimiter("[^0-9]+");
            snail = snail.substring(0,k+1) + (sc.nextInt() + _1) + sc.nextLine();
            k = -1;
          }
        }

        for (int k=snail.substring(i).indexOf("]")+i; k<snail.length(); k++) {
          if ((snail.charAt(k) == '[' || snail.charAt(k) == ',') && Character.isDigit(snail.charAt(k+1))) {
            Scanner sc = new Scanner(snail.substring(k+1));
            sc.useDelimiter("[^0-9]+");
            snail = snail.substring(0,k+1) + (sc.nextInt() + _2) + sc.nextLine();
            k = snail.length();
          }



          // if (snail.charAt(k) == ',' && (id_ < snail.substring(k+1).indexOf(",")+1 || snail.substring(k+1).indexOf(",") < 0)) {
          //   snail = snail.substring(0,k+1) + (Integer.parseInt(snail.substring(k+1,k+id_)) + _2) + snail.substring(k+id_);
          //   k = snail.length();
          // }
          // else if (snail.charAt(k) == '[' && (snail.substring(k+1).indexOf(",") < snail.substring(k+1).indexOf("[") || snail.substring(k+1).indexOf("[") < 0)) {
          //   id_ = snail.substring(k).indexOf(",");
          //   snail = snail.substring(0,k+1) + (Integer.parseInt(snail.substring(k+1,k+id_)) + _2) + snail.substring(k+id_);
          //   k = snail.length();
          // }
        }

        i = snail.length();
      }
    }

    count = 0;
    for (int i=0; i<snail.length(); i++) {
      if (snail.charAt(i) == '[') count++;
      else if (snail.charAt(i) == ']') count--;

      if (count == 5) {
        snail = snail.substring(0,i) + "0" + snail.substring(snail.substring(i).indexOf("]")+1+i);
        return 1;
      }
    }

    return -1;
  }
}