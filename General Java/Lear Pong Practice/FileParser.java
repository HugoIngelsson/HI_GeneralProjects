import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;
import java.io.FileWriter;

class Main {
  static int test = 0;
  static ArrayList<Excerpt> excerpts = new ArrayList<Excerpt>();
  
  public static void main(String[] args) {
    File file = new File("lear.txt");
    readValues(file);

    for (Excerpt e : excerpts) {
      e.removeCues();
    }

    File write = new File("learLines.txt");
    writeLinesOf(write, "LEAR");
  }

  //Used to get condensed version of text
  public static void writeLinesOf(File f, String sp) {
    try {
      FileWriter writer = new FileWriter(f, false);
      
      for (Excerpt e : excerpts) {
        if (e.getSpeaker().equals(sp)) {
          writer.write(e.toString().replaceAll("\\n", "/") + System.getProperty("line.separator"));
        }
      }
      writer.close();
    }
    catch(IOException e) {
      e.printStackTrace();
    }
  }
  
  public static void writeValues(File f) {
    try {
      FileWriter writer = new FileWriter(f, false);
      
      for (Excerpt e : excerpts) {
        writer.write(e.toString() + System.getProperty("line.separator"));
      }
      writer.close();
    }
    catch(IOException e) {
      e.printStackTrace();
    }
  }

  //Used for reading dirty data
  public static void readValues(File f) {
    try {
      Scanner in = new Scanner(f);
      String next, speaker = "", quote = "", code;
      int act = 0, scene = 0;

      for (int i=0; in.hasNext(); i++) {
        next = in.nextLine();
        code = getStarter(next);

        if (code.equals("ACT")) {
          if (quote.length() > 0 && speaker.length() > 0)
            excerpts.add(new Excerpt(speaker, quote, act, scene));
          
          act = Integer.parseInt(next.substring(4));
          in.nextLine();

          speaker = "";
          quote = "";
        }
        else if (code.equals("Scene")) {
          if (quote.length() > 0 && speaker.length() > 0)
            excerpts.add(new Excerpt(speaker, quote, act, scene));
          
          scene = Integer.parseInt(next.substring(6));
          in.nextLine();
          
          speaker = "";
          quote = "";
        }
        else if (code.length() > 0) {
          if (quote.length() > 0 && speaker.length() > 0)
            excerpts.add(new Excerpt(speaker, quote, act, scene));
          
          speaker = code;
          if (next.length() > code.length()) quote = next.substring(code.length() + 2) + "/";
          else quote = "";
        }
        else {
          if (next.length() > 0) quote += next + "/";
        }
      }

      excerpts.add(new Excerpt(speaker, quote, act, scene));
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static String getStarter(String in) {
    if (in.startsWith("ACT")) return "ACT";
    if (in.startsWith("Scene")) return "Scene";

    if (in.startsWith("LEAR")) return "LEAR";
    if (in.startsWith("GONERIL")) return "GONERIL";
    if (in.startsWith("ALBANY")) return "ALBANY";
    if (in.startsWith("OSWALD")) return "OSWALD";
    if (in.startsWith("REGAN")) return "REGAN";
    if (in.startsWith("CORNWALL")) return "CORNWALL";
    if (in.startsWith("CORDELIA")) return "CORDELIA";
    if (in.startsWith("FRANCE")) return "FRANCE";
    if (in.startsWith("BURGUNDY")) return "BURGUNDY";
    if (in.startsWith("KENT")) return "KENT";
    if (in.startsWith("FOOL")) return "FOOL";
    if (in.startsWith("GLOUCESTER")) return "GLOUCESTER";
    if (in.startsWith("EDGAR")) return "EDGAR";
    if (in.startsWith("EDMUND")) return "EDMUND";
    if (in.startsWith("CURAN")) return "CURAN";
    if (in.startsWith("OLD MAN")) return "OLD MAN";
    if (in.startsWith("KNIGHT")) return "KNIGHT";
    if (in.startsWith("GENTLEMEN") || in.startsWith("GENTLEMAN")) return "GENTLEMAN";
    if (in.startsWith("FIRST SERVANT")) return "FIRST SERVANT";
    if (in.startsWith("SECOND SERVANT")) return "SECOND SERVANT";
    if (in.startsWith("THIRD SERVANT")) return "THIRD SERVANT";
    if (in.startsWith("MESSENGER")) return "MESSENGER";
    if (in.startsWith("DOCTOR")) return "DOCTOR";
    if (in.startsWith("CAPTAINS")) return "CAPTAINS";
    if (in.startsWith("HERALD")) return "HERALD";

    return "";
  }
}

class Excerpt {
  private String speaker;
  private String text;
  private int act;
  private int scene;
  
  public Excerpt(String speaker, String text, int act, int scene) {
    this.speaker = speaker;
    this.text = text;
    this.act = act;
    this.scene = scene;
  }

  public void removeCues() {
    int id = 0;
    
    for (int i=0; i<text.length(); i++) {
      if (text.charAt(i) == '[') {
        id = i;
      }
      else if (text.charAt(i) == ']') {
        text = text.substring(0, id) + text.substring(i+1);
        i = id-1;
      }
    }

    text = text.replaceAll("  ", " ");
    text = text.replaceAll("//", "/");
    if (text.endsWith("/")) text = text.substring(0, text.length()-1);
    if (text.endsWith("/")) text = text.substring(0, text.length()-1);
  }

  public String toString() {
    return act + "" + scene + " " + speaker + "~" + text;
  }

  public String getSpeaker() {
    return speaker;
  }
}