import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;

class Main {
  static String[] values;

  public static int getFileSize(File f) {
    //This method counts the number of lines in the data file
    //We need this so we know how big to make our array
    int count = 0;
    try { 
        
        Scanner sc = new Scanner(f); 
        while (sc.hasNext()) 
        { 
          String str = sc.nextLine();
          count++;
        }
        sc.close(); 
    } catch (IOException e){ 
        e.printStackTrace(); 
    }
    return count;
  }

  public static void readValues(File f) {
    //This reads the numbers in the data file 
    //And stores them in the array "values"
    try { 
        Scanner sc = new Scanner(f);
        int i = 0;
        
        while (sc.hasNext()) {
          values[i++] = sc.nextLine();
        }

        sc.close(); 
      } catch (IOException e){ 
        e.printStackTrace(); 
      }

  }

  public static void main(String[] args) {
    File file = new File ("data.txt");
    int fileSize = getFileSize(file);
    values = new String[fileSize];
    readValues(file);
    
    int tot = 0;
    String help = "";

    for (String s : values) {
      String in = s.substring(0,s.indexOf("|")-1);
      String[] key = decode(in.split(" "));
      String out = s.substring(s.indexOf("|") + 2);
      String[] outList = out.split(" ");
      help = "";

      for (String w : outList) {
        help += assignNum(w, key);
      }

      System.out.println(help);
      tot += Integer.parseInt(help);
    }

    System.out.println(tot);
  }

  public static String assignNum(String in, String[] key) {
    boolean[] bool = new boolean[7];

    for (String s : in.split("(?!^)")) {
      for (int i=0; i<7; i++) {
        if (s.equals(key[i])) bool[i] = true;
      }
    }

    if (bool[0]) {
      if (bool[1]) {
        if (bool[4]) {
          if (bool[2] && bool[3]) return "8";
          else if (bool[2]) return "0";
          else return "6";
        }
        else {
          if (bool[2]) return "9";
          else return "5";
        }
      }
      else {
        if (bool[4]) return "2";
        else if (bool[3]) return "3";
        else return "7";
      }
    }
    else {
      if (bool[1]) return "4";
      else return "1";
    }
  }

  public static String[] decode(String[] in) {
    //0 = top, 1 = tl, 2 = tr, 3 = m, 4 = br, 5 = bl, 6 = b
    String[] positions = new String[7];
    int count = 0;
    String temp = "";
    String _1 = "";
    String _4 = "";
    String _2 = "";

    for (String s : in) {
      if (s.length() == 2) {
        _1 = s;
        break;
      }
    }

    for (String s : in) {
      if (s.length() == 3) {
        for (String c : s.split("(?!^)")) {
          if (!_1.contains(c)) positions[0] = c;
        }
      }
      else if (s.length() == 4) {
        for (String c : s.split("(?!^)")) {
          if (!_1.contains(c)) _4 += c;
        }
      }
    }

    for (String s : in) {
      if (s.length() == 6) {
        count = 0;
        for (String c : s.split("(?!^)")) {
          if (_1.contains(c) || _4.contains(c) || c.equals(positions[0])) {
            count++;
          }
          else {
            temp = c;
          }
        }

        if (count == 5 && s.length() == 6) {
          positions[6] = temp;
        }
      }
    }

    for (String s : in) {
      if (s.length() == 7) {
        for (String c : s.split("(?!^)")) {
          if (!(_1.contains(c) || _4.contains(c) || c.equals(positions[0]) || c.equals(positions[6]))) {
            positions[4] = c;
          }
        }
      }
    }

    for (String s : in) {
      if (s.length() == 5) {
        count = 0;
        temp = "";
        for (String c : s.split("(?!^)")) {
          if (c.equals(positions[0]) || c.equals(positions[4]) || c.equals(positions[6])) {
            count++;
          }
          else {
            temp += c;
          }
        }

        if (count == 3) {
          _2 = temp;
          if (_2.contains(_1.substring(1))) {
            positions[2] = _1.substring(1);
            positions[5] = _1.substring(0,1);
          }
          else {
            positions[5] = _1.substring(1);
            positions[2] = _1.substring(0,1);
          }
          if (_2.contains(_4.substring(1))) {
            positions[3] = _4.substring(1);
            positions[1] = _4.substring(0,1);
          }
          else {
            positions[1] = _4.substring(1);
            positions[3] = _4.substring(0,1);
          }
        }
      }
    }

    return positions;
  }

  //Problem 1
  public static void problem1(String[] values) {
    for (int i=0; i<values.length; i++) {
      values[i] = values[i].substring(values[i].indexOf("|") + 1);
    }

    String[] split;
    int sum = 0;
    int len;

    for (String s : values) {
      split = s.split(" ");

      for (String p : split) {
        len = p.length();
        if (len == 2 || len == 3 || len == 4 || len == 7) sum++;
      }
    }

    System.out.println(sum);
  }
}