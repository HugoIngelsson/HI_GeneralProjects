import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.*;

class Main {
  static int ret;
  static ArrayList<Integer> sizes = new ArrayList<Integer>();
  
  public static void main(String[] args) {
    File f = new File("data.txt");
    solution(f);
  }

  public static void solution(File f) {
    try {
      Scanner in = new Scanner(f);
      boolean c = true;
      String next = in.nextLine();
      Directory directory =  new Directory("/");
      Directory main =  directory;

      while (in.hasNext()) {
        if (c) next = in.nextLine();

        if (next.startsWith("$ cd")) {
          if (next.equals("$ cd /")) {
            directory = main;
          }
          else if (!next.equals("$ cd ..")) {
            directory = directory.getChildName(next.substring(5));
          }
          else {
            directory = directory.getParent();
          }

          c = true;
        }
        else {
          next = in.nextLine();
          while (!next.startsWith("$") && in.hasNext()) {
            if (next.startsWith("dir")) {
              directory.addChild(new Directory(directory, next.substring(4)));
            }
            else {
              directory.incVal(Integer.parseInt(next.substring(0, next.indexOf(" "))));
            }
            
            next = in.nextLine();
          }
          if (!in.hasNext()) {
            if (next.startsWith("dir")) {
              directory.addChild(new Directory(directory, next.substring(4)));
            }
            else {
              directory.incVal(Integer.parseInt(next.substring(0, next.indexOf(" "))));
            }
          }
          
          c = false;
        }
      }

      System.out.println("Small files: " + recur(main));
      getSizes(main);
      Collections.sort(sizes);

      int available = 70000000 - main.sumVal();
      
      for (Integer i : sizes) {
        if (i >= 30000000 - available) {
          System.out.println("Data to remove: " + i);
          break;
        }
      }
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void getSizes(Directory d) {
    if (!sizes.contains(d.sumVal())) sizes.add(d.sumVal());

    for (Directory dir : d.getChildren()) {
      getSizes(dir);
    }
  }
  public static int recur(Directory d) {
    int sum = 0;
    if (d.sumVal() <= 100000) sum += d.sumVal();

    for (Directory dir : d.getChildren()) {
      sum += recur(dir);
    }

    sizes.add(sum);
    return sum;
  }
}

class Directory {
  Directory parent;
  String name;
  ArrayList<Directory> children;
  int val;
  int layer;

  public Directory(String n) {
    parent = null;
    name = n;
    children = new ArrayList<Directory>();
    val = 0;
  }
  
  public Directory(Directory p, String n) {
    parent = p;
    name = n;
    children = new ArrayList<Directory>();
    val = 0;
  }

  public void incVal(int n) {
    val += n;
  }

  public void addChild(Directory c) {
    if (!children.contains(c)) children.add(c);
  }

  public void setParent(Directory p) {
    parent = p;
  }
  
  public Directory getParent() {
    return parent;
  }

  public int getVal() {
    return val;
  }

  public int getLayer() {
    return layer;
  }

  public String getName() {
    return name;
  }

  public ArrayList<Directory> getChildren() {
    return children;
  }

  public Directory getChildName(String n) {
    for (Directory d : children) {
      if (d.getName().equals(n)) return d;
    }

    return null;
  }

  public int sumVal() {
    int sum = val;
    for (Directory d : children) {
      sum += d.sumVal();
    }
    
    return sum;
  }

  public String toString() {
    return "Parent: " + parent.getName() + " .. Children: " + children + " .. Val: " + val;
  }
}