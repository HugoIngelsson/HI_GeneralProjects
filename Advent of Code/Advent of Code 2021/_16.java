class Main {
  static String hexa = "620D7800996600E43184312CC01A88913E1E180310FA324649CD5B9DA6BFD107003A4FDE9C718593003A5978C00A7003C400A70025400D60259D400B3002880792201B89400E601694804F1201119400C600C144008100340013440021279A5801AE93CA84C10CF3D100875401374F67F6119CA46769D8664E76FC9E4C01597748704011E4D54D7C0179B0A96431003A48ECC015C0068670FA7EF1BC5166CE440239EFC226F228129E8C1D6633596716E7D4840129C4C8CA8017FCFB943699B794210CAC23A612012EB40151006E2D4678A4200EC548CF12E4FDE9BD4A5227C600F80021D08219C1A00043A27C558AA200F4788C91A1002C893AB24F722C129BDF5121FA8011335868F1802AE82537709999796A7176254A72F8E9B9005BD600A4FD372109FA6E42D1725EDDFB64FFBD5B8D1802323DC7E0D1600B4BCDF6649252B0974AE48D4C0159392DE0034B356D626A130E44015BD80213183A93F609A7628537EB87980292A0D800F94B66546896CCA8D440109F80233ABB3ABF3CB84026B5802C00084C168291080010C87B16227CB6E454401946802735CA144BA74CFF71ADDC080282C00546722A1391549318201233003361006A1E419866200DC758330525A0C86009CC6E7F2BA00A4E7EF7AD6E873F7BD6B741300578021B94309ABE374CF7AE7327220154C3C4BD395C7E3EB756A72AC10665C08C010D0046458E72C9B372EAB280372DFE1BCA3ECC1690046513E5D5E79C235498B9002BD132451A5C78401B99AFDFE7C9A770D8A0094EDAC65031C0178AB3D8EEF8E729F2C200D26579BEDF277400A9C8FE43D3030E010C6C9A078853A431C0C0169A5CB00400010F8C9052098002191022143D30047C011100763DC71824200D4368391CA651CC0219C51974892338D0";
  static String bi = "";
  static int total = 0;
  static double temp = 0;
  static String keep;

  public static void main(String[] args) {
    hexaToBinary(hexa);
    System.out.printf("%.0f", handlePacket(bi, -1, 0));

    System.out.println("\n" + total);
  }

  //PRECONDITION: A string of only binary values.
  public static double handlePacket(String s, int toCheck, int tc) {
    double[] compare = new double[2];
    compare[0] = -1;
    double op = -1;
    double cur = -1;

    while (s.length() > 7 && toCheck != 0) {
      total += Integer.parseInt(s.substring(0,3),2);
      int type = Integer.parseInt(s.substring(3,6),2);
      
      if (type == 4) { //Type ID of 4; get the length to start the next packet
        cur = getLiteral(s);
        s = s.substring(literalPacket(s));
        toCheck--;
      }
      else {
        if (s.charAt(6) == '0') { //Operator type 1; check next 15 characters
          int lenPack = Integer.parseInt(s.substring(7,22),2);
          cur = handlePacket(s.substring(22,22+lenPack), -1, type);
          s = s.substring(22+lenPack);
          toCheck--;
        }
        else { //Operator type 2; check next 11 characters
          int numPack = Integer.parseInt(s.substring(7,18),2);
          cur = handlePacket(s.substring(18), numPack, type);
          s = keep;
          toCheck--;
        }
      }

      if (tc == 0) {
        if (op == -1) {
          op = cur;
        }
        else {
          op += cur;
        }
      }
      else if (tc == 1) {
        if (op == -1) {
          op = cur;
        }
        else {
          op *= cur;
        }
      }
      else if (tc == 2) {
        if (op == -1) {
          op = cur;
        }
        else {
          op = Math.min(op, cur);
        }
      }
      else if (tc == 3) {
        if (op == -1) {
          op = cur;
        }
        else {
          op = Math.max(op, cur);
        }
      }
      else if (tc == 5) {
        if (compare[0] == -1) {
          compare[0] = cur;
        }
        else {
          if (compare[0] > cur) op = 1;
          else op = 0;
        }
      }
      else if (tc == 6) {
        if (compare[0] == -1) {
          compare[0] = cur;
        }
        else {
          if (compare[0] < cur) op = 1;
          else op = 0;
        }
      }
      else if (tc == 7) {
        if (compare[0] == -1) {
          compare[0] = cur;
        }
        else {
          if (compare[0] == cur) op = 1;
          else op = 0;
        }
      }
    }

    keep = s;
    return op;
  }

  public static double getLiteral(String s) {
    int i=6;
    String c = "";

    while (s.charAt(i) == '1') {
      c += s.substring(i+1, i+5);
      i += 5;
    }

    c += s.substring(i+1, i+5);

    return parseBinaryDouble(c);
  }

  public static int literalPacket(String s) {
    int i=6;
    String c = "";

    while (s.charAt(i) == '1') {
      i += 5;
    }

    return i+5;
  }

  public static double parseBinaryDouble(String s) {
    double total = 0;

    for (int i=0; i<s.length(); i++) {
      if (s.charAt(s.length()-1-i) == '1')
        total += Math.pow(2,i);
    }

    return total;
  }

  public static void hexaToBinary(String hexa) {
    for (String s : hexa.split("(?!^)")) {
      if (s.equals("0")) bi += "0000";
      else if (s.equals("1")) bi += "0001";
      else if (s.equals("2")) bi += "0010";
      else if (s.equals("3")) bi += "0011";
      else if (s.equals("4")) bi += "0100";
      else if (s.equals("5")) bi += "0101";
      else if (s.equals("6")) bi += "0110";
      else if (s.equals("7")) bi += "0111";
      else if (s.equals("8")) bi += "1000";
      else if (s.equals("9")) bi += "1001";
      else if (s.equals("A")) bi += "1010";
      else if (s.equals("B")) bi += "1011";
      else if (s.equals("C")) bi += "1100";
      else if (s.equals("D")) bi += "1101";
      else if (s.equals("E")) bi += "1110";
      else if (s.equals("F")) bi += "1111";
    }
  }
}