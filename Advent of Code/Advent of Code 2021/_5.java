import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;

class Main {
  static String[] values;
  static int[][] map = new int[1000][1000];

  public static int getFileSize(File f) {
    //This method counts the number of lines in the data file
    //We need this so we know how big to make our array
    int count = 0;
    try { 
        
        Scanner sc = new Scanner(f); 
        while (sc.hasNext()) 
        { 
          String str = sc.nextLine(); System.out.println(str); 
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
        int count = 0;
        while (sc.hasNext()) 
        { 
          String i = sc.nextLine();
          values[count++] = i;
        }
        sc.close(); 
      } catch (IOException e){ 
        e.printStackTrace(); 
      }

  }

  //step 2 - add your methods here

  public static void main(String[] args) {
      //step 1 - copy and paste your personal test numbers into the data.txt file
      File file = new File ("data.txt");
      int fileSize = getFileSize(file);
      values = new String[fileSize];
      readValues(file);
      //step 3 - add your method calls here
      mapHV(values);
      System.out.println(count());
    }

  public static void mapHV(String[] values) {
    int x1 = 0;
    int x2 = 0;
    int y1 = 0;
    int y2 = 0;

    for (String s : values) {
      x1 = Integer.parseInt(s.substring(0, s.indexOf(",")));
      y1 = Integer.parseInt(s.substring(s.indexOf(",")+1, s.indexOf(" ")));
      x2 = Integer.parseInt(s.substring(s.lastIndexOf(" ")+1, s.lastIndexOf(",")));
      y2 = Integer.parseInt(s.substring(s.lastIndexOf(",")+1));

      if (y1 == y2) {
        if (x1 < x2)
          for (int x = x1; x <= x2; x++)
            map[x][y1]++;
        else  
          for (int x = x1; x >= x2; x--)
            map[x][y1]++;
      }
      else if (x1 == x2) {
        if (y1 < y2)
          for (int y = y1; y <= y2; y++)
            map[x1][y]++;
        else  
          for (int y = y1; y >= y2; y--)
            map[x1][y]++;
      }
      else {
        if (x1 < x2) {
          if (y1 < y2) {
            for (int x = 0; x <= x2 - x1; x++)
              map[x1+x][y1+x]++;
          }
          else {
            for (int x = 0; x <= x2 - x1; x++)
              map[x1+x][y1-x]++;
          }
        }
        else {
          if (y1 < y2) {
            for (int x = 0; x >= x2 - x1; x--)
              map[x1+x][y1-x]++;
          }
          else {
            for (int x = 0; x >= x2 - x1; x--)
              map[x1+x][y1+x]++;
          }
        }
      }
    }
  }

  public static int count() {
    int count = 0;

    for (int y = 0; y < 1000; y++) {
      for (int x = 0; x < 1000; x++) {
        if (map[x][y] > 1) count++;
      }
    }

    return count;
  }
}