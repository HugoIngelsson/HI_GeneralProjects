import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;

class Main {
  static int[] values;

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
        
        String s = sc.nextLine();
        String[] sList = s.split(",");
        
        values = new int[sList.length];

        for (int i=0; i<values.length; i++) {
          values[i] = Integer.parseInt(sList[i]);
        }

        sc.close(); 
      } catch (IOException e){ 
        e.printStackTrace(); 
      }

  }

  public static void main(String[] args) {
    File file = new File ("data.txt");
    int fileSize = getFileSize(file);
    readValues(file);

    double min = getFuel(values, 0);
    for (int i=1; i<1000; i++) {
      min = Math.min(min, getFuel(values, i));
    }
    System.out.printf("%.0f", min);
  }

  public static double getFuel(int[] values, int desired) {
    double sum = 0;
    int dist = 0;

    for (int n : values) {
      dist = Math.abs(n - desired);
      sum += dist * (dist + 1) / 2;
    }

    return sum;
  }
}