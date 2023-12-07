class Main {
  static int p1 = 5;
  static int s1 = 0;
  static int p2 = 10;
  static int s2 = 0;
  static int rolls = 0;
  static int die = 1;
  static double universes = 0;
  static double uni1 = 0;
  static double uni2 = 0;

  public static void main(String[] args) {
    optimizedQuantum(true, 0, 0, 4, 9, 1);
    System.out.printf("%.0f", uni1);
    System.out.println();
    System.out.printf("%.0f", uni2);
  }

  public static void optimizedQuantum(boolean p, int s1, int s2, int p1, int p2, double uni) {
    if (p) {
      if (s2 >= 21)  {
        uni2 += uni;
        return;
      }
      else {
        optimizedQuantum(false, s1+(p1+3)%10+1, s2, p1+3, p2, uni);
        optimizedQuantum(false, s1+(p1+4)%10+1, s2, p1+4, p2, uni*3);
        optimizedQuantum(false, s1+(p1+5)%10+1, s2, p1+5, p2, uni*6);
        optimizedQuantum(false, s1+(p1+6)%10+1, s2, p1+6, p2, uni*7);
        optimizedQuantum(false, s1+(p1+7)%10+1, s2, p1+7, p2, uni*6);
        optimizedQuantum(false, s1+(p1+8)%10+1, s2, p1+8, p2, uni*3);
        optimizedQuantum(false, s1+(p1+9)%10+1, s2, p1+9, p2, uni);
      }
    }
    else {
      if (s1 >= 21)  {
        uni1 += uni;
        return;
      }
      else {
        optimizedQuantum(true, s1, s2+(p2+3)%10+1, p1, p2+3, uni);
        optimizedQuantum(true, s1, s2+(p2+4)%10+1, p1, p2+4, uni*3);
        optimizedQuantum(true, s1, s2+(p2+5)%10+1, p1, p2+5, uni*6);
        optimizedQuantum(true, s1, s2+(p2+6)%10+1, p1, p2+6, uni*7);
        optimizedQuantum(true, s1, s2+(p2+7)%10+1, p1, p2+7, uni*6);
        optimizedQuantum(true, s1, s2+(p2+8)%10+1, p1, p2+8, uni*3);
        optimizedQuantum(true, s1, s2+(p2+9)%10+1, p1, p2+9, uni);
      }
    }
  }

  //Initial Thoughts for Problem 2; way too slow (has to run several billion instances to sum up the universes)
  public static void quantum(boolean player, int s1, int s2, int p1, int p2, int count) {
    if (s1 >= 21 || s2 > 21) {
      universes++;
      return;
    }
    else {
      if (count > 3) {
        count = 1;
        if (player) {
          p1 = (p1-1)%10+1;
          s1 += p1;
          player = false;
        }
        else {
          p2 = (p2-1)%10+1;
          s2 += p2;
          player = true;
        } 
      }

      if (player) {
        quantum(player, s1, s2, p1+1, p2, count+1);
        quantum(player, s1, s2, p1+2, p2, count+1);
        quantum(player, s1, s2, p1+3, p2, count+1);
      }
      else {
        quantum(player, s1, s2, p1, p2+1, count+1);
        quantum(player, s1, s2, p1, p2+2, count+1);
        quantum(player, s1, s2, p1, p2+3, count+1);
      }
    }
  }

  //Solution 1
  public static void p1() {
    for (int i=0; i<3; i++) {
      p1 += die++;
      if (die > 100) die = 1;
      rolls++;
    }

    p1 = (p1-1)%10+1;
    s1 += p1;

    if (s1 >= 1000) System.out.println(rolls*s2);
    else p2();
  }

  public static void p2() {
    for (int i=0; i<3; i++) {
      p2 += die++;
      if (die > 100) die = 1;
      rolls++;
    }

    p2 = (p2-1)%10+1;
    s2 += p2;

    if (s2 >= 1000) System.out.println(rolls*s1);
    else p1();
  }
}