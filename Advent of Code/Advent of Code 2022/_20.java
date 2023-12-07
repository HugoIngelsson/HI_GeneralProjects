import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) {
    File f = new File("data.txt");
    sol(f);
  }

  public static void sol(File f) {
    try {
      Scanner in = new Scanner(f);
      ArrayList<Double> code = new ArrayList<Double>();

      while (in.hasNext()) {
        code.add(in.nextInt()*811589153.0);
      }

      ArrayList<String> isDuped = new ArrayList<String>();
      ArrayList<Double> toMix = new ArrayList<Double>();
      int j = 0;
      for (Double i : code) {
        toMix.add(i);
        isDuped.add("" + j++);
      }

      for (int k=0; k<10; k++) {
        j = 0;
        for (Double i : code) {
          int id = isDuped.indexOf("" + j++);
          
          toMix.remove(id);
          isDuped.remove(id);
          
          toMix.add((int)((id+i-1+10000000000.0*(code.size()-1))%(code.size()-1)+1), i);
          isDuped.add((int)((id+i-1+10000000000.0*(code.size()-1))%(code.size()-1)+1), "" + (j-1));
        }
      }

      int id = toMix.indexOf(0.0);
      System.out.printf("%f", toMix.get((id+1000)%toMix.size()) + toMix.get((id+2000)%toMix.size()) + toMix.get((id+3000)%toMix.size()));
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
}