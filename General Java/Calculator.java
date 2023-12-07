import java.util.Scanner;
import java.util.ArrayList;

public class Calculator {
  public static void main(String[] args) {
    System.out.println("--Starting Up--\n");
    Scanner input = new Scanner(System.in);
    Solver eq1 = new Solver("0");

    System.out.println("\nPlease input an equation: ");
    eq1.setEquation(input.nextLine());

    while (!(eq1.getEquation().equals("OFF") || eq1.getEquation().equals(""))) {
      System.out.println("\n" + eq1.toString());
      System.out.println("The answer is: " + eq1.evaluate(eq1.getEquation())); 

      System.out.println("\nPlease input an equation: ");
      eq1.setEquation(input.nextLine());
    }

    System.out.println("You turned off the calculator"); 
  }
}

class Solver {
  private String equation;

  public Solver(String equation) {
    this.equation = equation;
  }

  public String getEquation() {
    return equation;
  }

  public void setEquation(String equation) {
    this.equation = equation;
  }

  public String toString() {
    return "Your equation is: " + equation;
  }

  public double evaluate(String eq) {
    int end = 0;

    if (eq.contains("(")) {
      double next;

      //System.out.println("\nFirst, you want to take care of the parentheses! If you want to get help with these in more detail, input them as a separate equation!");

      for (int i=0; i<eq.length(); i++) {
        if (eq.charAt(i)=='(') {
          end = idOfPara(eq, i+1);
          if (end<0) 
            System.out.println("SYNTAX ERROR");
          else  {
            next = evaluateInner(eq.substring(i+1,end));
            eq =  eq.substring(0,i+1) + next + eq.substring(end);
          }
        }
      }
    }

    if (eq.contains("Infinity")) {
      //System.out.println("\nIt looks like something evaluates to infinity! You might need to make some further analysis to solve this equation!");
      return Math.pow(0,-1);
    }

    if (eq.contains("(") || eq.contains("pi") || eq.contains("e")) {
      //System.out.print("\nYou want to clean up your equation with paranthesis and constant rules before moving on:\n\t" + eq + " --> ");
      eq = fixPara(eq);
    }
    
    if (eq.contains("pi")) {
      //System.out.print("\nYou should replace all instances of 'pi' with its numerical value:\n\t" + eq + " --> ");
      eq = eq.replaceAll("pi", Double.toString(Math.PI));
    }

    if (eq.contains("e")) {
      //System.out.print("\nYou should replace all instances of 'e' with its numerical value:\n\t" + eq + " --> ");
      eq = eq.replaceAll("e", Double.toString(Math.E));
    }
    
    eq = functions(eq);
    return organize(eq);
  }

  public double evaluateInner(String eq) {
    int end = 0;

    if (eq.contains("(")) {
      for (int i=0; i<eq.length(); i++) {
        if (eq.charAt(i)=='(') {
          end = idOfPara(eq, i+1);
          if (end<0) 
            System.out.println("SYNTAX ERROR");
          else  {
            eq =  eq.substring(0,i+1) + evaluate(eq.substring(i+1,end)) + eq.substring(end);
          }
        }
      }
    }

    eq = fixPara(eq);

    eq = eq.replaceAll("e", Double.toString(Math.E));
    eq = eq.replaceAll("pi", Double.toString(Math.PI));
    eq = eq.replaceAll("Infinity", Double.toString(Double.MAX_VALUE));
    
    eq = functions(eq);
    return organize(eq);
  }

  public String functions(String eq) {
    char check;
    int num;

    for (int i=0; i<eq.length(); i++) {
      check = eq.charAt(i);
      if (check > 96 && check < 123) {
        //Split it into two sections to make it more efficient
        if (check == 'a') {
          num = i + 6 + getNext(eq.substring(i+6));
          if (eq.substring(i,i+6).equals("arcsin")) {
            eq = eq.substring(0,i) + Math.asin(Double.parseDouble(eq.substring(i+6, num))) + eq.substring(num);
          }
          else if (eq.substring(i,i+6).equals("arccos")) {
            eq = eq.substring(0,i) + Math.acos(Double.parseDouble(eq.substring(i+6, num))) + eq.substring(num);
          }
          else if (eq.substring(i,i+6).equals("arctan")) {
            eq = eq.substring(0,i) + Math.atan(Double.parseDouble(eq.substring(i+6, num))) + eq.substring(num);
          }
          else {
            System.out.println("ERROR");
            eq = "";
          }
        }
        else if (check == 'l') {
          if (eq.substring(i,i+2).equals("ln")) {
            num = i + 2 + getNext(eq.substring(i+2));
            eq = eq.substring(0,i) + Math.log(Double.parseDouble(eq.substring(i+2, num))) + eq.substring(num);
          }
          else if (eq.substring(i,i+3).equals("log")) {
            num = i + 3 + getNext(eq.substring(i+3));
            eq = eq.substring(0,i) + (Math.log(Double.parseDouble(eq.substring(i+3, num))) / Math.log(10)) + eq.substring(num);
          }
          else {
            System.out.println("ERROR");
            eq = "";
          }
        }
        else {
          num = i + 3 + getNext(eq.substring(i+3));
          if (eq.substring(i,i+3).equals("sin")) {
            eq = eq.substring(0,i) + Math.sin(Double.parseDouble(eq.substring(i+3, num))) + eq.substring(num);
          }
          else if (eq.substring(i,i+3).equals("cos")) {
            eq = eq.substring(0,i) + Math.cos(Double.parseDouble(eq.substring(i+3, num))) + eq.substring(num);
          }
          else if (eq.substring(i,i+3).equals("tan")) {
            eq = eq.substring(0,i) + Math.tan(Double.parseDouble(eq.substring(i+3, num))) + eq.substring(num);
          }
          else {
            System.out.println("ERROR");
            eq = "";
          }
        }
      }
    }

    return eq;
  }

  public String fixPara(String eq) {
    char check;

    for (int i=0; i<eq.length(); i++) {
      check = eq.charAt(i);

      if (check=='(') {
        if (i>0 && (Character.isDigit(eq.charAt(i-1)) || !fix(check))) 
          eq = eq.substring(0,i) + "*" + eq.substring(i+1);
        else 
          eq = eq.substring(0,i) + eq.substring(i+1);
      }
      else if (check==')') {
        if (i<eq.length()-1 && fix(eq.charAt(i+1)))
          eq = eq.substring(0,i) + "*" + eq.substring(i+1);
        else 
          eq = eq.substring(0,i) + eq.substring(i+1);
      }
      else if (check > 96 && check < 123 || check == 'e' || (i<eq.length()-1 && eq.substring(i,i+2) == "pi")) {
        if (i>0 && (Character.isDigit(eq.charAt(i-1)) || !fix(check))) 
          eq = eq.substring(0,i) + "*" + eq.substring(i);
        else 
          eq = eq.substring(0,i) + eq.substring(i);
      }
    }

    return eq;
  }

  //Assigns each number in the input String a separate index in an ArrayList
  //Then, assigns each number except the last one its respective operator in another ArrayList
  //Finally calls on solve() to go through the order of operations and solve the input equation
  public double organize(String eq) {
    ArrayList<Double> nums = new ArrayList<Double>();
    ArrayList<Character> symbols = new ArrayList<Character>();
    int num = getNext(eq);

    while (num > 0) {
      nums.add(Double.parseDouble(eq.substring(0,num)));
      eq = eq.substring(num);
      if (eq.length() > 0) {
        symbols.add(eq.charAt(0));
        eq = eq.substring(1);
      }

      num = getNext(eq);
    }

    return solve(nums, symbols);
  }

  //Goes through the order of operations for the ArrayLists
  public double solve(ArrayList<Double> nums, ArrayList<Character> symbols) {
    char cur;

    for (int i=0; i<symbols.size(); i++) {
      cur = symbols.get(i);
      if (cur == '^') {
        nums.set(i+1, Math.pow(nums.get(i),nums.get(i+1)));
        nums.remove(i);
        symbols.remove(i);
        i--;
      }
    }

    for (int i=0; i<symbols.size(); i++) {
      cur = symbols.get(i);
      if (cur == '*') {
        nums.set(i+1, nums.get(i)*nums.get(i+1));
        nums.remove(i);
        symbols.remove(i);
        i--;
      }
      else if (cur == '/') {
        nums.set(i+1, nums.get(i)/nums.get(i+1));
        nums.remove(i);
        symbols.remove(i);
        i--;
      }
    }

    for (int i=0; i<symbols.size(); i++) {
      cur = symbols.get(i);
      if (cur == '+') {
        nums.set(i+1, nums.get(i)+nums.get(i+1));
        nums.remove(i);
        symbols.remove(i);
        i--;
      }
      else if (cur == '-') {
        nums.set(i+1, nums.get(i)-nums.get(i+1));
        nums.remove(i);
        symbols.remove(i);
        i--;
      }
    }
    return nums.get(0);
  }

  public int idOfPara(String str, int begin) {
    int count = 0;

    for (int i=begin; i<str.length(); i++) {
      if (str.charAt(i)=='(') {
        count++;
      }
      else if (str.charAt(i)==')') {
        if (count==0)
          return i;
        count--;
      }
    }

    System.out.println("ERROR");
    return -1;
  }

  public boolean fix(char check) {
    if (check=='^' || check=='*' || check=='/' || check=='+' || check=='-') return false;

    return true;
  }

  public int getNext(String str) {
    int dots = 0;
    int Es = 0;
    boolean eCheck = false;

    for (int i=0; i<str.length(); i++) {
      if (i==0 && str.charAt(i)=='-');
      else if (Character.isDigit(str.charAt(i)));
      else if (str.charAt(i)=='.') {
        if (dots==1) return i;
        dots++;
      }
      else if (str.charAt(i)=='E') {
        if (Es == 1) return i;
        Es++;
        eCheck = true;
      }
      else if (eCheck) {
        if (str.charAt(i) != '-') return i;
        eCheck = false;
      }
      else return i;
    }

    return str.length();
  }
}