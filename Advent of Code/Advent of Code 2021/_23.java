import java.util.*;

class Main {
  static String hallway = "...........";
  //Naming convention: first letter = top of stack
  static String[] holes = {"ADDC","BCBD","CBAA","DACB"};
  static int min = 100000;
  static int[] locations = {0,1,3,5,7,9,10};
  static HashMap<String, Integer> passed = new HashMap<String, Integer>();
  static ArrayList<String> visuals = new ArrayList<String>();

  public static void main(String[] args) {
    recur(0, 0, 0, 0, 0, holes, hallway, visuals);

    System.out.println(min);
    printSteps(visuals);
  }

  public static void printSteps(ArrayList<String> visuals) {
    visuals.replaceAll(n -> n.replace(".", " "));

    for (int i=0; i*5<visuals.size(); i++) {
      for (int j=i*5; j<(i+1)*5 && j<visuals.size(); j++)
        System.out.print("*************\t\t"); 
      System.out.println();
      for (int j=i*5; j<(i+1)*5 && j<visuals.size(); j++) 
        System.out.print("*" + visuals.get(j).substring(0,11) + "*\t\t");
      System.out.println();
      for (int j=i*5; j<(i+1)*5 && j<visuals.size(); j++) 
        System.out.print("***" + visuals.get(j).charAt(11) + "*" + visuals.get(j).charAt(15) + "*" + visuals.get(j).charAt(19) + "*" + visuals.get(j).charAt(23) + "***\t\t");
      System.out.println();
      for (int j=i*5; j<(i+1)*5-1 && j<visuals.size()-1; j++) 
        System.out.print("  *" + visuals.get(j).charAt(12) + "*" + visuals.get(j).charAt(16) + "*" + visuals.get(j).charAt(20) + "*" + visuals.get(j).charAt(24) + "*    -->  ");
      if (i*5+4 < visuals.size()) System.out.print("  *" + visuals.get(i*5+4).charAt(12) + "*" + visuals.get(i*5+4).charAt(16) + "*" + visuals.get(i*5+4).charAt(20) + "*" + visuals.get(i*5+4).charAt(24) + "*   ");
      else System.out.print("  *" + visuals.get(visuals.size()-1).charAt(12) + "*" + visuals.get(visuals.size()-1).charAt(16) + "*" + visuals.get(visuals.size()-1).charAt(20) + "*" + visuals.get(visuals.size()-1).charAt(24) + "*   ");
      System.out.println();
      for (int j=i*5; j<(i+1)*5 && j<visuals.size(); j++) 
        System.out.print("  *" + visuals.get(j).charAt(13) + "*" + visuals.get(j).charAt(17) + "*" + visuals.get(j).charAt(21) + "*" + visuals.get(j).charAt(25) + "*  \t\t");
      System.out.println();
      for (int j=i*5; j<(i+1)*5 && j<visuals.size(); j++) 
        System.out.print("  *" + visuals.get(j).charAt(14) + "*" + visuals.get(j).charAt(18) + "*" + visuals.get(j).charAt(22) + "*" + visuals.get(j).charAt(26) + "*  \t\t");
      System.out.println();
      for (int j=i*5; j<(i+1)*5 && j<visuals.size(); j++) 
        System.out.print("  *********  \t\t");
      System.out.println("\n");
    }
  }

  public static String toStr(String hallway, String[] holes) {
    for (int i=0; i<4; i++) {
      for (int n=0; n<4-holes[i].length(); n++) hallway += ".";
      hallway += holes[i];
    }

    return hallway;
  }

  public static void recur(int total, int A, int B, int C, int D, String[] holes, String hallway, ArrayList<String> vs) {
    String str = toStr(hallway, holes);
    ArrayList<String> show = (ArrayList<String>)vs.clone();
    show.add(str);

    if (passed.containsKey(str)) {
      if (passed.get(str) <= total) return;
      else passed.replace(str, total);
    }
    else passed.put(str, total);

    if (total >= min || A > 8 || B > 8 || C > 8 || D > 8) {
      System.out.println(hallway + " " + holes[0] + " " + holes[1] + " " + holes[2] + " " + holes[3] + " " + min + " " + total); 
      return;
    }
    if (complete(holes)) {
      min = total;
      visuals = show;
      return;
    }

    int temp;
    for (int i=0; i<4; i++) {
      if (holes[i].length() > 0 && !inDesired(holes, i)) {
        for (int j=0; j<4; j++) {
          if (i != j && goodHole(holes[i].charAt(0), holes, j)) {
            temp = movementHandler((i+1)*100, (j+1)*100, hallway, holes);
            if (temp > 0) {
              if (holes[i].charAt(0) == 'A') {
                holes[i] = holes[i].substring(1);
                holes[j] = "A" + holes[j];
                recur(total+temp, A+1, B, C, D, holes, hallway, show);
                holes[i] = "A" + holes[i];
                holes[j] = holes[j].substring(1);
              }
              else if (holes[i].charAt(0) == 'B') {
                holes[i] = holes[i].substring(1);
                holes[j] = "B" + holes[j];
                recur(total+temp*10, A, B+1, C, D, holes, hallway, show);
                holes[i] = "B" + holes[i];
                holes[j] = holes[j].substring(1);
              }
              else if (holes[i].charAt(0) == 'C') {
                holes[i] = holes[i].substring(1);
                holes[j] = "C" + holes[j];
                recur(total+temp*100, A, B, C+1, D, holes, hallway, show);
                holes[i] = "C" + holes[i];
                holes[j] = holes[j].substring(1);
              }
              else {
                holes[i] = holes[i].substring(1);
                holes[j] = "D" + holes[j];
                recur(total+temp*1000, A, B, C, D+1, holes, hallway, show);
                holes[i] = "D" + holes[i];
                holes[j] = holes[j].substring(1);
              }
            }
          }
        }
        for (int j : locations) {
          temp = movementHandler((i+1)*100, j, hallway, holes);
          if (temp > 0) {
            if (holes[i].charAt(0) == 'A') {
              hallway = setCharAt(hallway, 'A', j);
              holes[i] = holes[i].substring(1);
              recur(total + temp, A+1, B, C, D, holes, hallway, show);
              hallway = setCharAt(hallway, '.', j);
              holes[i] = "A" + holes[i];
            }
            else if (holes[i].charAt(0) == 'B') {
              hallway = setCharAt(hallway, 'B', j);
              holes[i] = holes[i].substring(1);
              recur(total + temp*10, A, B+1, C, D, holes, hallway, show);
              hallway = setCharAt(hallway, '.', j);
              holes[i] = "B" + holes[i];
            }
            else if (holes[i].charAt(0) == 'C') {
              hallway = setCharAt(hallway, 'C', j);
              holes[i] = holes[i].substring(1);
              recur(total + temp*100, A, B, C+1, D, holes, hallway, show);
              hallway = setCharAt(hallway, '.', j);
              holes[i] = "C" + holes[i];
            }
            else {
              hallway = setCharAt(hallway, 'D', j);
              holes[i] = holes[i].substring(1);
              recur(total + temp*1000, A, B, C, D+1, holes, hallway, show);
              hallway = setCharAt(hallway, '.', j);
              holes[i] = "D" + holes[i];
            }
          }
        }
      }
    }
    for (int i : locations) {
      if (hallway.charAt(i) != '.') {
        for (int j=0; j<4; j++) {
          if (goodHole(hallway.charAt(i), holes, j)) {
            temp = movementHandler(i, (j+1)*100, hallway, holes);
            if (temp > 0) {
              if (hallway.charAt(i) == 'A') {
                hallway = setCharAt(hallway, '.', i);
                holes[j] = "A" + holes[j];
                recur(total + temp, A+1, B, C, D, holes, hallway, show);
                hallway = setCharAt(hallway, 'A', i);
                holes[j] = holes[j].substring(1);
              }
              else if (hallway.charAt(i) == 'B') {
                hallway = setCharAt(hallway, '.', i);
                holes[j] = "B" + holes[j];
                recur(total + temp*10, A, B+1, C, D, holes, hallway, show);
                hallway = setCharAt(hallway, 'B', i);
                holes[j] = holes[j].substring(1);
              }
              else if (hallway.charAt(i) == 'C') {
                hallway = setCharAt(hallway, '.', i);
                holes[j] = "C" + holes[j];
                recur(total + temp*100, A, B, C+1, D, holes, hallway, show);
                hallway = setCharAt(hallway, 'C', i);
                holes[j] = holes[j].substring(1);
              }
              else {
                hallway = setCharAt(hallway, '.', i);
                holes[j] = "D" + holes[j];
                recur(total + temp*1000, A, B, C, D+1, holes, hallway, show);
                hallway = setCharAt(hallway, 'D', i);
                holes[j] = holes[j].substring(1);
              }
            }
          }
        }
      }
    }
    
  }

  public static boolean inDesired(String[] holes, int id) {
    if (id == 0) {
      for (int i=0; i<holes[0].length(); i++) {
        if (holes[0].charAt(i) != 'A') return false;
      }
    }
    else if (id == 1) {
      for (int i=0; i<holes[1].length(); i++) {
        if (holes[1].charAt(i) != 'B') return false;
      }
    }
    else if (id == 2) {
      for (int i=0; i<holes[2].length(); i++) {
        if (holes[2].charAt(i) != 'C') return false;
      }
    }
    else {
      for (int i=0; i<holes[3].length(); i++) {
        if (holes[3].charAt(i) != 'D') return false;
      }
    }

    return true;
  }

  public static String switchSpots(String s, int i, int j) {
    if (i < j) {
      return s.substring(0,i) + s.charAt(j) + s.substring(i+1,j) + s.charAt(i) + s.substring(j+1);
    }
    else {
      return s.substring(0,j) + s.charAt(i) + s.substring(j+1,i) + s.charAt(j) + s.substring(i+1);
    }
  }

  public static String setCharAt(String s, char c, int i) {
    return s.substring(0,i) + c + s.substring(i+1);
  }

  public static boolean complete(String holes[]) {
    return holes[0].equals("AAAA") && holes[1].equals("BBBB") && holes[2].equals("CCCC") && holes[3].equals("DDDD");
  }

  public static boolean goodHole(char type, String[] hole, int id) {
    if (hole[id].length() > 3) return false;
    if (type == 'A') {
      if (id != 0) return false;
      return (!(hole[id].contains("B") || hole[id].contains("C") || hole[id].contains("D")));
    }
    else if (type == 'B') {
      if (id != 1) return false;
      return (!(hole[id].contains("A") || hole[id].contains("C") || hole[id].contains("D")));
    }
    else if (type == 'C') {
      if (id != 2) return false;
      return (!(hole[id].contains("A") || hole[id].contains("B") || hole[id].contains("D")));
    }
    else if (type == 'D') {
      if (id != 3) return false;
      return (!(hole[id].contains("A") || hole[id].contains("B") || hole[id].contains("C")));
    }

    return false;
  }

  public static int movementHandler(int pos1, int pos2, String hallway, String[] holes) {
    int ret = 0;
    int temp;

    if (pos1 > 10) {
      ret += hallHole(holes[pos1/100-1]);
      if (pos1 >= 400) pos1 = 8;
      else if (pos1 >= 300) pos1 = 6;
      else if (pos1 >= 200) pos1 = 4;
      else pos1 = 2;

      if (hallway.charAt(pos1) != '.') return -1;
    }
    if (pos2 > 10) {
      ret += hallHole(holes[pos2/100-1]) - 1;
      if (pos2 >= 400) pos2 = 8;
      else if (pos2 >= 300) pos2 = 6;
      else if (pos2 >= 200) pos2 = 4;
      else pos2 = 2;

      if (hallway.charAt(pos2) != '.') return -1;
    }
    temp = hallToHall(pos1, pos2, hallway);
    if (temp >= 0) ret += temp;
    else return -1;
    return ret;
  }

  public static int hallHole(String hole) {
    return 5 - hole.length();
  }

  public static int hallToHall(int pos1, int pos2, String hallway) {
    int ret = 0;

    if (pos1 < pos2) {
      for (int i=pos1+1; i<=pos2; i++) {
        if (hallway.charAt(i) != '.') return -1;
        ret++;
      }
    }
    else {
      for (int i=pos2; i<pos1; i++) {
        if (hallway.charAt(i) != '.') return -1;
        ret++;
      }
    }

    return ret;
  }
}