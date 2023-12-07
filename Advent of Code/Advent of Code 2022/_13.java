import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.*;

class Main {
  public static void main(String[] args) {
    File f = new File("data.txt");
    readInput(f);
    sol2(f);
  }

  public static void sol2(File f) {
    try {
      Scanner in = new Scanner(f);
      ArrayList<String> packets = new ArrayList<String>();
      packets.add("[[2]]");
      packets.add("[[6]]");

      int i = 0;
      while (in.hasNext()) {
        String l1 = in.nextLine();
        String l2 = in.nextLine();
        if (in.hasNext()) in.nextLine();

        i = 0;
        while (i < packets.size() && compareLists(packets.get(i), l1) >= 0) {
          i++;
        }
        packets.add(i, l1);

        i = 0;
        while (i < packets.size() && compareLists(packets.get(i), l2) >= 0) {
          i++;
        }
        packets.add(i, l2);
      }

      System.out.println(packets.indexOf("[[2]]")+1);
      System.out.println(packets.indexOf("[[6]]")+1);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void readInput(File f) {
    try {
      Scanner in = new Scanner(f);
      ArrayList<String> packets = new ArrayList<String>();

      int i = 1;
      int sum = 0;
      while (in.hasNext()) {
        String l1 = in.nextLine();
        String l2 = in.nextLine();
        if (in.hasNext()) in.nextLine();
        
        if (compareLists(l1, l2) == 1) sum += i;
        System.out.println("Sum: " + sum);
        i++;
      }

      System.out.println("Sum: " + sum);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static int compareLists(String s1, String s2) {
    if (s1.startsWith(",")) return compareLists(s1.substring(1), s2);
    if (s2.startsWith(",")) return compareLists(s1, s2.substring(1));
    
    if (s1.length() == 0 && s2.length() == 0) return 0;
    else if (s1.length() == 0) return 1;
    else if (s2.length() == 0) return -1;
    
    if (s1.startsWith("[") && s2.startsWith("[")) {
      int comp = compareLists(s1.substring(1, findBracket(s1)), s2.substring(1, findBracket(s2)));
      if (comp == 0) {
        return compareLists(s1.substring(findBracket(s1)+1), s2.substring(findBracket(s2)+1));
      } else return comp;
    }
    else if (s1.startsWith("[")) {
      int comp;
      if (s2.contains(","))
        comp = compareLists(s1, "[" + s2.substring(0, findComma(s2)) + "]" + s2.substring(findComma(s2)));
      else comp = compareLists(s1, "[" + s2 + "]");
      
      if (comp == 0) {
        if (s2.contains(","))
          return compareLists(s1.substring(findBracket(s1)+1), s2.substring(findComma(s2)+1));
        else return compareLists(s1.substring(findBracket(s1)+1), s2.substring(findComma(s2)));
      } else return comp;
    }
    else if (s2.startsWith("[")) {
      int comp;
      if (s1.contains(","))
        comp = compareLists("[" + s1.substring(0, findComma(s1)) + "]" + s1.substring(findComma(s1)), s2);
      else comp = compareLists("[" + s1 + "]", s2);
      
      if (comp == 0) {
        if (s2.contains(","))
          return compareLists(s1.substring(findComma(s1)+1), s2.substring(findBracket(s2)+1));
        else return compareLists(s1.substring(findComma(s1)), s2.substring(findBracket(s2)+1));
      } else return comp;
    }
    else {
      int comp = Integer.parseInt(s1.substring(0,findComma(s1))) - Integer.parseInt(s2.substring(0,findComma(s2)));
      if (comp < 0) return 1;
      else if (comp == 0) {
        if (s1.contains(",") && s2.contains(",")) {
          return compareLists(s1.substring(findComma(s1)+1),s2.substring(findComma(s2)+1));
        } else if (s1.contains(",")) {
          return -1;
        } else if (s2.contains(",")) {
          return 1;
        } else return 0;
      }
      else return -1;
    }
  }

  public static int findBracket(String s) {
    int c = 0;
    for (int i=0; i<s.length(); i++) {
      if (s.charAt(i) == '[') c++;
      else if (s.charAt(i) == ']') {
        c--;
        if (c == 0) return i;
      }
    }

    return -1;
  }

  public static int findComma(String s) {
    if (s.contains(",")) return s.indexOf(",");
    else return s.length();
  }
}