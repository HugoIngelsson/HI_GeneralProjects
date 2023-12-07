import java.util.*;
import java.io.*;

class Main {
  static ArrayList<String>[] term = new ArrayList[2];
  
  public static void main(String[] args) {
    System.out.println("-- Starting to read values. --");
    File f = new File("terms.txt");
    readValues(f);
    populateFile("shuffled.txt");
  }

  /* Takes the values from "term"
  *  and randomly shuffles them into
  *  the file "shuffled.txt" */
  public static void populateFile(String fileName) {
    try {
      FileWriter fw = new FileWriter(fileName, false);
      BufferedWriter bw = new BufferedWriter(fw);
  
      while (term[0].size() > 0) {
        int rand = (int)(Math.random()*term[0].size());

        bw.write(term[0].remove(rand) + "\t" + term[1].remove(rand));
        if (term[0].size() > 0) bw.newLine();
      }
      
      bw.close();
      System.out.println("-- Values successfully shuffled. --");
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  /* Reads the values of terms.txt
  *  and puts them into the variable
  *  "term." */
  public static void readValues(File f) {
    try {
      Scanner sc = new Scanner(f);
      String str;
      term[0] = new ArrayList<String>();
      term[1] = new ArrayList<String>();

      while (sc.hasNext()) {
        str = sc.nextLine();

        term[0].add(str.substring(0,str.indexOf("ö")));
        term[1].add(str.substring(str.indexOf("ö")+1));
      }

      System.out.println("-- Values successfully copied. Beginning shuffling. --");
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
}