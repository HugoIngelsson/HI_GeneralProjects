import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) {
    File f = new File("data.txt");
    sol(f);
  }

  static double sum;
  static double[] limsPos;
  static double[] limsNeg;
  
  public static void sol(File f) {
    try {
      Scanner in = new Scanner(f);

      sum = 0;
      while (in.hasNext()) {
        String next = in.nextLine();

        for (int i=next.length()-1; i>=0; i--) {
          if (next.charAt(i) == '2') sum += 2.0 * Math.pow(5, next.length() - i - 1);
          else if (next.charAt(i) == '1') sum += 1.0 * Math.pow(5, next.length() - i - 1);
          else if (next.charAt(i) == '-') sum -= 1.0 * Math.pow(5, next.length() - i - 1);
          else if (next.charAt(i) == '=') sum -= 2.0 * Math.pow(5, next.length() - i - 1);
        }
      }

      int maxPow = maxPower(sum);
      limsPos = new double[maxPow+1];
      limsNeg = new double[maxPow+1];
      for (int i=0; i<=maxPow; i++) {
        limsPos[i] = limitSum(i, 2.0);
        limsNeg[i] = limitSum(i, -2.0);
      }
      
      System.out.println(maxPower(sum));
      System.out.printf("%f\n", sum);
      getNum(maxPower(sum), sum, "");
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void getNum(int pow, double num, String ret) {
    if (pow == -1) {
      if (num == 0) System.out.println(ret);
      return;
    }
    else if (num > limsPos[pow] || num < limsNeg[pow]) return;

    getNum(pow-1, num-2.0*Math.pow(5, pow), ret + "2");
    getNum(pow-1, num-1.0*Math.pow(5, pow), ret + "1");
    getNum(pow-1, num, ret + "0");
    getNum(pow-1, num+1.0*Math.pow(5, pow), ret + "-");
    getNum(pow-1, num+2.0*Math.pow(5, pow), ret + "=");
  }

  public static int maxPower(double n) {
    int i = 0;
    while (2.0 * Math.pow(5, i) <= n) i++;

    return i;
  }

  public static double limitSum(int pow, double mult) {
    double sum = 0;
    while (pow >= 0) sum += Math.pow(5, pow--);

    return mult * sum;
  }
}