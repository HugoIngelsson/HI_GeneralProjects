import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.math.BigDecimal;

class Main {
  static double[] vars = new double[4];
  static String input;
  static int n = 0;

  public static void main(String[] args) {
    File f = new File("data.txt");

    //3 --> 4
    //5 --> 6
    //7 --> 8
    //2 --> 9
    //1 --> 12
    //0 --> 13
    //10 --> 11
    //MAX == "95299897999897"
    //MIN == "31111121382151"
    input = "31111121382151";
    n = 0;
    vars[0] = 0;
    vars[1] = 0;
    vars[2] = 0;
    vars[3] = 0;

    readValues(f);
    if (vars[3] == 0.0) System.out.println(input);
  }

  public static void readValues(File f) {
    try {
      Scanner sc = new Scanner(f);
      while (sc.hasNext()) {
        String s = sc.nextLine();
        if (s.substring(0,3).equals("inp")) {
          inp(s.substring(4));
          System.out.println(vars[3] % 26 <= 9 + 9);
          System.out.println(vars[3] % 26 - 9);
        }
        else if (s.substring(0,3).equals("add")) {
          add(s.substring(4));
        }
        else if (s.substring(0,3).equals("mul")) {
          mul(s.substring(4));
        }
        else if (s.substring(0,3).equals("div")) {
          div(s.substring(4));
        }
        else if (s.substring(0,3).equals("mod")) {
          mod(s.substring(4));
        }
        else {
          eql(s.substring(4));
        }

        if (n < 10) System.out.println(Arrays.toString(vars));
      }
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void inp(String in) {
    int id;

    if (in.charAt(0) == 'w')
      id = 0;
    else if (in.charAt(0) == 'x')
      id = 1;
    else if (in.charAt(0) == 'y')
      id = 2;
    else
      id = 3;

    vars[id] = Integer.parseInt(input.substring(n++,n));
  }

  public static void add(String in) {
    int id;
    double add;

    if (in.charAt(0) == 'w')
      id = 0;
    else if (in.charAt(0) == 'x')
      id = 1;
    else if (in.charAt(0) == 'y')
      id = 2;
    else
      id = 3;

    if (Character.isDigit(in.charAt(2)) || in.charAt(2) == '-')
      add = Integer.parseInt(in.substring(2));
    else if (in.charAt(2) == 'w')
      add = vars[0];
    else if (in.charAt(2) == 'x')
      add = vars[1];
    else if (in.charAt(2) == 'y')
      add = vars[2];
    else
      add = vars[3];


    vars[id] += add;
  }

  public static void mul(String in) {
    int id;
    double mul;

    if (in.charAt(0) == 'w')
      id = 0;
    else if (in.charAt(0) == 'x')
      id = 1;
    else if (in.charAt(0) == 'y')
      id = 2;
    else
      id = 3;

    if (Character.isDigit(in.charAt(2)) || in.charAt(2) == '-')
      mul = Integer.parseInt(in.substring(2));
    else if (in.charAt(2) == 'w')
      mul = vars[0];
    else if (in.charAt(2) == 'x')
      mul = vars[1];
    else if (in.charAt(2) == 'y')
      mul = vars[2];
    else
      mul = vars[3];


    vars[id] *= mul;
  }

  public static void div(String in) {
    int id;
    double div;

    if (in.charAt(0) == 'w')
      id = 0;
    else if (in.charAt(0) == 'x')
      id = 1;
    else if (in.charAt(0) == 'y')
      id = 2;
    else
      id = 3;

    if (Character.isDigit(in.charAt(2)) || in.charAt(2) == '-')
      div = Integer.parseInt(in.substring(2));
    else if (in.charAt(2) == 'w')
      div = vars[0];
    else if (in.charAt(2) == 'x')
      div = vars[1];
    else if (in.charAt(2) == 'y')
      div = vars[2];
    else
      div = vars[3];


    vars[id] = Math.floor(vars[id] / div);
  }

  public static void mod(String in) {
    int id;
    double mod;

    if (in.charAt(0) == 'w')
      id = 0;
    else if (in.charAt(0) == 'x')
      id = 1;
    else if (in.charAt(0) == 'y')
      id = 2;
    else
      id = 3;

    if (Character.isDigit(in.charAt(2)) || in.charAt(2) == '-')
      mod = Integer.parseInt(in.substring(2));
    else if (in.charAt(2) == 'w')
      mod = vars[0];
    else if (in.charAt(2) == 'x')
      mod = vars[1];
    else if (in.charAt(2) == 'y')
      mod = vars[2];
    else
      mod = vars[3];


    vars[id] %= mod;
  }

  public static void eql(String in) {
    int id;
    double eql;

    if (in.charAt(0) == 'w')
      id = 0;
    else if (in.charAt(0) == 'x')
      id = 1;
    else if (in.charAt(0) == 'y')
      id = 2;
    else
      id = 3;

    if (Character.isDigit(in.charAt(2)) || in.charAt(2) == '-')
      eql = Integer.parseInt(in.substring(2));
    else if (in.charAt(2) == 'w')
      eql = vars[0];
    else if (in.charAt(2) == 'x')
      eql = vars[1];
    else if (in.charAt(2) == 'y')
      eql = vars[2];
    else
      eql = vars[3];


    if (vars[id] == eql) vars[id] = 1;
    else vars[id] = 0;
  }
}