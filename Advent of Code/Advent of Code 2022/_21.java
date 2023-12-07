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
      ArrayList<Monkey> monkeys = new ArrayList<Monkey>();

      monkeys.add(new Monkey("humn", 3582317956029.0));
      while (in.hasNext()) {
        String next = in.nextLine();
        if (next.length() < 15) {
          monkeys.add(new Monkey(next.substring(0,4), (double)Integer.parseInt(next.substring(6))));
        }
        else {
          monkeys.add(new Monkey(next.substring(0,4), next.substring(6,10), next.substring(13,17), next.charAt(11)));
        }
      }

      int i=0;
      Monkey cur = monkeys.get(i++);
      while (!cur.getName().equals("root") || cur.readyHear()) {
        if (!cur.readyHear()) {
          String name = cur.getName();
          double num = cur.getVal();

          for (Monkey m : monkeys) {
            if (m.readyHear()) {
              if (m.getOP1().equals(name)) {
                m.op1Done(num);
                if (!m.readyHear()) m.setNum();
              }
              if (m.getOP2().equals(name)) {
                m.op2Done(num);
                if (!m.readyHear()) m.setNum();
              }
            }
          }
        }
        cur = monkeys.get((i++)%monkeys.size());
      }
      cur.printOPES();
      //System.out.printf("%f", cur.getVal());
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
}

class Monkey {
  String name;
  boolean heard;
  boolean h1;
  boolean h2;
  double num;
  String op1;
  double ope1;
  String op2;
  double ope2;
  char operator;
  
  public Monkey(String name, double num) {
    this.name = name;
    this.num = num;
    heard = true;
    h1 = true;
    h2 = true;
  }

  public Monkey(String name, String op1, String op2, char operator) {
    this.name = name;
    this.op1 = op1;
    this.op2 = op2;
    this.operator = operator;
    heard = false;
    h1 = false;
    h2 = false;
  }

  public String getName() {
    return name;
  }

  public double getVal() {
    return num;
  }
  
  public boolean readyHear() {
    return !heard;
  }

  public String getOP1() {
    return op1;
  }

  public void op1Done(double ope1) {
    this.ope1 = ope1;
    h1 = true;
    if (h2) heard = true;
  }

  public String getOP2() {
    return op2;
  }

  public void printOPES() {
    System.out.println(ope1 + " " + ope2);
    System.out.printf("%f\n%f", ope1,  ope2);
  }

  public void op2Done(double ope2) {
    this.ope2 = ope2;
    h2 = true;
    if (h1) heard = true;
  }

  public void setNum() {
    if (operator == '+') {
      num = ope1 + ope2;
    } 
    else if (operator == '-') {
      num = ope1 - ope2;
    }
    else if (operator == '*') {
      num = ope1 * ope2;
    }
    else if (operator == '/') {
      num = ope1 / ope2;
    }
  }
}