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

    ArrayList<Double> list = new ArrayList<Double>();
    //getCorruption(values[0]);

    for (int i = 0; i<values.length; i++) {
      if (getCorruption(values[i]) > 0) values[i] = "";
      else {
        list.add(autoComplete(values[i]));
      }
    }

    while (list.size() > 1) {
      list = removeMinMax(list);
    }

    System.out.printf("%.0f",list.get(0));
  }

  public static ArrayList<Double> removeMinMax(ArrayList<Double> list) {
    double min = list.get(0);
    int minId = 0;
    double max = list.get(0);
    int maxId = 0;

    for (int i=1; i<list.size(); i++) {
      if (list.get(i) < min) {
        minId = i;
        min = list.get(i);
      }
    }

    list.remove(minId);

    for (int i=1; i<list.size(); i++) {
      if (list.get(i) > max) {
        maxId = i;
        max = list.get(i);
      }
    }

    list.remove(maxId);
    return list;
  }

  public static double autoComplete(String s) {
    double tot = 0;
    String stack = "";

    for (int i = 0; i<s.length(); i++) {
      //System.out.println(stack);
      char c = s.charAt(i);

      if (c == '(') stack += "(";
      else if (c == ')' && stack.length() > 0 && stack.charAt(stack.length()-1) == '(') {
        stack = stack.substring(0,stack.length()-1);
      }
      else if (c == '[') stack += "[";
      else if (c == ']' && stack.length() > 0 && stack.charAt(stack.length()-1) == '[') {
        stack = stack.substring(0,stack.length()-1);
      }
      else if (c == '{') stack += "{";
      else if (c == '}' && stack.length() > 0 && stack.charAt(stack.length()-1) == '{') {
        stack = stack.substring(0,stack.length()-1);
      }
      else if (c == '<') stack += "<";
      else {
        stack = stack.substring(0,stack.length()-1);
      }
    }

    for (int i=stack.length()-1; i>=0; i--) {
      tot *= 5;
      char c = stack.charAt(i);
      if (c == '(') tot += 1;
      else if (c == '[') tot += 2;
      else if (c == '{') tot += 3;
      else if (c == '<') tot += 4;
      stack = stack.substring(0,i);
    }

    System.out.println(tot);
    return tot;
  }

  public static int getCorruption(String s) {
    String stack = "";

    for (int i = 0; i<s.length(); i++) {
      //System.out.println(stack);
      char c = s.charAt(i);

      if (c == '(') stack += "(";
      else if (c == ')') {
        if (stack.length() > 0 && stack.charAt(stack.length()-1) == '(') stack = stack.substring(0,stack.length()-1);
        else return 3;
      }
      else if (c == '[') stack += "[";
      else if (c == ']') {
        if (stack.length() > 0 && stack.charAt(stack.length()-1) == '[') stack = stack.substring(0,stack.length()-1);
        else return 57;
      }
      else if (c == '{') stack += "{";
      else if (c == '}') {
        if (stack.length() > 0 && stack.charAt(stack.length()-1) == '{') stack = stack.substring(0,stack.length()-1);
        else return 1197;
      }
      else if (c == '<') stack += "<";
      else {
        if (stack.length() > 0 && stack.charAt(stack.length()-1) == '<') stack = stack.substring(0,stack.length()-1);
        else return 25137;
      }
    }

    return 0;
  }
}