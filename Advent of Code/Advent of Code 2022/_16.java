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
    try {
      Scanner in = new Scanner(f);
      HashMap<String, Valve> valves = new HashMap<String, Valve>();

      int i = 0;
      while (in.hasNext()) {
        String next = in.nextLine();
        String name = next.substring(6,8);
        int pressure = Integer.parseInt(next.substring(next.indexOf("=")+1, next.indexOf(";")));
        valves.put(name, new Valve(name, pressure, i++));
        
        String con = next.substring(next.indexOf("lead")+15);

        for (String s : con.split(", ")) {
          valves.get(name).addConnection(s);
        }
      }

      System.out.println(valves);
      System.out.println("recurring commence");
      System.out.println("FINAL: " + recur2("AA", "AA", 0, 0, 1, valves, "000000000000000000000000000000000000000000000000000000000000AAAA0"));
      //System.out.println(recur2("AA", "AA", 0, 0, 1, valves, "0000000000AAAA0"));
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  static HashMap<String, Integer> states = new HashMap<String, Integer>();
  public static int recur(String v, int tot, int pr, int round, HashMap<String, Valve> valves, String state) {
    tot += pr;
    
    if (states.containsKey(state) && states.get(state) == tot) return tot;
    else if (!states.containsKey(state) || states.get(state) < tot) states.put(state, tot);
    else return 0;
    
    if (round == 30) {
      //System.out.println(round + " " + tot + " " + pr + " " + state);
      return tot;
    }
    if (round >= 6 && tot < 10) {
      return 0;
    }
    int ret = 0;
    
    for (String s : valves.get(v).getConnections()) {
      //state = state.substring(0, 60) + s;
      String pass = state.substring(0, 60) + s + round;
      ret = Math.max(recur(s, tot, pr, round+1, valves, pass), ret);
    }
    
    if (!valves.get(v).isOpen() && valves.get(v).getFlow() != 0) {
      valves.get(v).switchValve();
      int id = valves.get(v).getID();
      //state = state.substring(0, id) + "1" + state.substring(id+1, 60) + v;
      String pass = state.substring(0, id) + "1" + state.substring(id+1, 60) + v + round;
      pr += valves.get(v).getFlow();
      ret = Math.max(recur(v, tot, pr, round+1, valves, pass), ret);
      valves.get(v).switchValve();
    }
    
    return ret;
  }

  static int max = 0;
  public static int recur2(String v, String e, int tot, int pr, int round, HashMap<String, Valve> valves, String state) {
    tot += pr;

    int rm1 = round - 1;
    String state2 = state.substring(0,60) + e + v + rm1;
    if (states.containsKey(state) && states.get(state) == tot) return tot;
    else if (!states.containsKey(state) || states.get(state) < tot) states.put(state, tot);
    else return 0;

    if (!v.equals(e)) {
      if (states.containsKey(state2) && states.get(state2) == tot) return tot;
      else if (!states.containsKey(state2) || states.get(state2) < tot) states.put(state2, tot);
      else if (round > 2) return 0;
    }

    //if (round == 5) System.out.println("05: " + tot);
    if (round == 10 && tot <= 220) return 0;
    //if (round == 10) System.out.println("10: " + tot);
    if (round == 15 && tot <= 800) return 0;
    //if (round == 15) System.out.println("15: " + tot);
    if (round == 20 && tot <= 1600) return 0;
    //if (round == 20) System.out.println("20: " + tot);
    if (round == 26) {
      if (tot > max) {
        System.out.println(round + " " + tot + " " + pr);
        max = tot;
      }
      return tot;
    }
    if (round >= 4 && tot < 1) {
      return 0;
    }
    int ret = 0;
    
    for (String s : valves.get(v).getConnections()) {
      for (String b : valves.get(e).getConnections()) {
        String pass = state.substring(0, 60) + s + b + round;
        ret = Math.max(recur2(s, b, tot, pr, round+1, valves, pass), ret);
      }
    }

    for (String s : valves.get(v).getConnections()) {
      if (!valves.get(e).isOpen() && valves.get(e).getFlow() != 0) {
        valves.get(e).switchValve();
        int id = valves.get(e).getID();
        String pass = state.substring(0, id) + "1" + state.substring(id+1, 60) + s + e + round;
        pr += valves.get(e).getFlow();
        ret = Math.max(recur2(s, e, tot, pr, round+1, valves, pass), ret);
        valves.get(e).switchValve();
        pr -= valves.get(e).getFlow();
      }
    }

    for (String b : valves.get(e).getConnections()) {
      if (!valves.get(v).isOpen() && valves.get(v).getFlow() != 0) {
        valves.get(v).switchValve();
        int id = valves.get(v).getID();
        String pass = state.substring(0, id) + "1" + state.substring(id+1, 60) + v + b + round;
        pr += valves.get(v).getFlow();
        ret = Math.max(recur2(v, b, tot, pr, round+1, valves, pass), ret);
        valves.get(v).switchValve();
        pr -= valves.get(v).getFlow();
      }
    }

    if (!valves.get(v).isOpen() && valves.get(v).getFlow() != 0 && !valves.get(e).isOpen() && valves.get(e).getFlow() != 0 && !e.equals(v)) {
      valves.get(v).switchValve();
      valves.get(e).switchValve();
      int id = valves.get(v).getID();
      String pass = state.substring(0, id) + "1" + state.substring(id+1, 60) + v + e + round;
      id = valves.get(e).getID();
      pass = pass.substring(0, id) + "1" + pass.substring(id+1, 60) + v + e + round;
      pr += valves.get(v).getFlow() + valves.get(e).getFlow();
      ret = Math.max(recur2(v, e, tot, pr, round+1, valves, pass), ret);
      valves.get(v).switchValve();
      valves.get(e).switchValve();
      pr -= (valves.get(v).getFlow() + valves.get(e).getFlow());
    }
    
    return ret;
  }
}

class Valve {
  boolean open;
  String name;
  int flow;
  ArrayList<String> connections;
  int id;
  
  public Valve(String s, int n, int i) {
    id = i;
    name = s;
    flow = n;
    connections = new ArrayList<String>();
    open = false;
  }

  public int getFlow() {
    return flow;
  }
  
  public boolean isOpen() {
    return open;
  }

  public void addConnection(String s) {
    connections.add(s);
  }

  public void switchValve() {
    open = !open;
  }

  public ArrayList<String> getConnections() {
    return connections;
  }

  public int getID() {
    return id;
  }

  public String toString() {
    return connections.toString();
  }
}