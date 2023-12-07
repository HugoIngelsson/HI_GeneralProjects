import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.*;

class Main {
  public static void main(String[] args) {
    File f = new File("data.txt");
    sol(f);
  }

  public static void sol(File f) {
    int lcd = 17*7*13*2*19*3*5*11;
    int[][] starts = {{89, 74},
                      {75, 69, 87, 57, 84, 90, 66, 50},
                      {55},
                      {69, 82, 69, 56, 68},
                      {72, 97, 50},
                      {90, 84, 56, 92, 91, 91},
                      {63, 93, 55, 53},
                      {50, 61, 52, 58, 86, 68, 97}};
    Monkey[] monkeys = {new Monkey(17, 4, 7, "* 5"),
                       new Monkey(7, 3, 2, "+ 3"),
                       new Monkey(13, 0, 7, "+ 7"),
                       new Monkey(2, 0, 2, "+ 5"),
                       new Monkey(19, 6, 5, "+ 2"),
                       new Monkey(3, 6, 1, "* 19"),
                       new Monkey(5, 3, 1, "* old"),
                       new Monkey(11, 5, 4, "+ 4")};
    for (int i=0;i<8;i++) for (int j=0;j<starts[i].length; j++) monkeys[i].addItem(starts[i][j]);
    System.out.println(lcd);

    int recipient = 0;
    for (int i=0; i<10000; i++) {
      
      for (int j=0; j<8; j++) {
        int l = monkeys[j].getItemsLength();
        for (int k=0; k<l; k++) {
          recipient = monkeys[j].calcTurn();
          monkeys[recipient].addItem(monkeys[j].throwItem());
        }
      }
      
    }

    for (Monkey m : monkeys) {
      System.out.println(m);
    }
  }
}

class Monkey {
  ArrayList<Double> items;
  int thrown;
  int mod;
  int t;
  int f;
  String op;
  int lcd = 17*7*13*2*19*3*5*11;
  
  public Monkey(int m, int tr, int fa, String o) {
    items = new ArrayList<Double>();
    thrown = 0;
    mod = m;
    t = tr;
    f = fa;
    op = o;
  }

  public void addItem(double i) {
    items.add(i);
  }

  public int calcTurn() {
    
    if (op.startsWith("+")) {
      items.set(0, (items.get(0) + Integer.parseInt(op.substring(2))));
    }
    else if (op.equals("* old")) {
      items.set(0, items.get(0) * items.get(0));
    }
    else {
      items.set(0, items.get(0) * Integer.parseInt(op.substring(2)));
    }
    items.set(0, items.get(0)%lcd);

    if (items.get(0)%mod == 0) return t;
    else return f;
  }

  public double throwItem() {
    thrown++;
    return items.remove(0);
  }

  public int getItemsLength() {
    return items.size();
  }

  public String toString() {
    return "Monkey Threw " + thrown + " items " + items;
  }
}