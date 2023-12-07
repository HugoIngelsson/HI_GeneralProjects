import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;

class Main {
  static ArrayList<String> allExcerpts = new ArrayList<String>();
  static ArrayList<String> excerpts = new ArrayList<String>();
  static int clueLength = 10;
  static int toCompleteLength = 5;
  static ArrayList<String> errors = new ArrayList<String>();

  public static void main(String[] args) {
    System.out.println("Loading lines! Hold on for just a moment!");
    File f = new File("learByLine.txt");
    readValues(f);
    excerpts = allExcerpts;

    System.out.println("All loaded!\n");
    Scanner in = new Scanner(System.in);
    String acts = "1,2,3,4,5";

    while (true) {
      System.out.println("Please select a gamemode: ");
      System.out.println(" [1] Speaker (no act/scene)");
      System.out.println(" [2] Speaker (act/scene)");
      System.out.println(" [3] Finish the Line (no act/scene)");
      System.out.println(" [4] Finish the Line (act/scene)");
      System.out.println(" [5] Practice Mode (speaker)");
      System.out.println(" [6] Complete the Scene!");
      System.out.println(" [7] Options");
      String mode = "";
      int modeInt;

      while (!(mode.equals("1") || mode.equals("2") || mode.equals("3") || mode.equals("4") || mode.equals("5")
          || mode.equals("6") || mode.equals("7"))) {
        mode = in.nextLine();

        if (!(mode.equals("1") || mode.equals("2") || mode.equals("3") || mode.equals("4") || mode.equals("5")
            || mode.equals("6") || mode.equals("7")))
          System.out.println("Invalid Input!");
      }
      modeInt = Integer.parseInt(mode);

      System.out.println();
      String answer = "";

      while (!answer.equals("LEAVE")) {
        int n = (int) (Math.random() * excerpts.size());
        String line = excerpts.get(n);
        String speaker = line.substring(3, line.indexOf("~"));
        boolean redo = false;

        if (modeInt == 1 || modeInt == 2 || modeInt == 5) {
          if (modeInt == 5 && errors.size() > 0 && Math.random() > (1.5 / Math.log(errors.size() + 5.0))) {
            n = (int) (Math.random() * errors.size());
            line = errors.get(n);
            speaker = line.substring(3, line.indexOf("~"));
            redo = true;
          }
          if (modeInt == 2)
            System.out.println("Act " + line.charAt(0) + " Scene " + line.charAt(1));
          printLine(line.substring(4 + speaker.length()));

          answer = in.nextLine();
          answer = answer.toUpperCase();

          if (answer.equals(speaker)) {
            System.out.print("\nCORRECT!");
            if (modeInt == 5 && redo) {
              errors.remove(n);
            }
          } else if (answer.equals("LEAVE")) {
            System.out.print("\nLeft to gamemode selection");
          } else {
            System.out.print("\nWRONG! The speaker was " + speaker + ".");
            if (modeInt == 5 && !redo) {
              errors.add(line);
            }
          }
          if ((modeInt == 1 || modeInt == 5) && !answer.equals("LEAVE"))
            System.out.print(" This quotation was from Act " + line.charAt(0) + " Scene " + line.charAt(1));

          System.out.println("\n");
        } else if ((modeInt == 3 || modeInt == 4)
            && line.substring(4 + speaker.length()).split(" ").length >= clueLength + toCompleteLength) {
          if (modeInt == 4)
            System.out.println("Act " + line.charAt(0) + " Scene " + line.charAt(1));

          String finish = printFinishLine(line.substring(4 + speaker.length()));
          answer = in.nextLine();

          if (answer.toUpperCase().equals(finish.toUpperCase())) {
            System.out.print("\nCORRECT!");
          } else if (answer.equals("LEAVE")) {
            System.out.print("\nLeft to gamemode selection");
          } else {
            System.out.print("\nWRONG! We were looking for \"" + finish + "\"");
          }
          if (modeInt == 3 && !answer.equals("LEAVE"))
            System.out.print("\nThis quotation was spoken by " + speaker + " during Act " + line.charAt(0) + " Scene "
                + line.charAt(1));
          else if (!answer.equals("LEAVE")) {
            System.out.print("\nThis quotation was spoken by " + speaker);
          }

          System.out.println("\n");
        } else if (modeInt == 6) {
          String option = "";
          while (!actScene(option)) {
            System.out.print("Select Act+Scene (format x.y): ");
            option = in.nextLine();

            if (!actScene(option)) System.out.println("Invalid Input!");
          }
          
          String info = actSceneStartEnd(option);
          int start = Integer.parseInt(info.substring(0, info.indexOf("-")));
          int end = Integer.parseInt(info.substring(info.indexOf("-")+1));
          System.out.println();

          n = 0;
          int c = 0;
          while (!answer.equals("LEAVE") && start <= end) {
            line = excerpts.get(start);
            speaker = line.substring(3, line.indexOf("~"));

            printLine(line.substring(4 + speaker.length()));
            
            answer = in.nextLine();
            answer = answer.toUpperCase();

            if (answer.equals(speaker)) {
              System.out.println("\nCORRECT!\n");
              c++;
              n++;
            } else if (answer.equals("LEAVE")) {
              System.out.print("\nLeft to gamemode selection. ");
            } else {
              System.out.println("\nWRONG! The speaker was " + speaker + ".\n");
              n++;
            }

            start++;
          }

          if (start > end) {
            System.out.println("Finished Scene! Your accuracy for this scene was " + Math.round((double)10000*c/n) / 100 + "%!\n");
          }
          else if (n > 0) {
            System.out.println("Your accuracy before leaving was " + Math.round((double)10000*c/n) / 100 + "%\n");
          }
          answer = "LEAVE";
          
        } else if (modeInt == 7){
          String option = "";
          while (!option.equals("2")) {
            System.out.println("Option Select: ");
            System.out.println(" [1] Available Acts (current acts: " + acts + ")");
            System.out.println(" [2] LEAVE");

            while (!(option.equals("1") || option.equals("2"))) {
              System.out.print("Choose: ");
              option = in.nextLine();

              if (!(option.equals("1") || option.equals("2")))
                System.out.println("Invalid Input!");
            }

            if (option.equals("1")) {
              System.out.print("Choose act(s): ");
              String a = in.nextLine();
              if (actsFormatting(a)) {
                acts = a;
                System.out.println("\nHold on for a bit! Changing possible acts.\n");
                changeActs(acts);
              } else {
                System.out.println("\nInvalid input. Returning to options menu.\n");
              }
              option = "";
            } else {
              System.out.println("\nLeaving to main menu\n");
            }
          }
          answer = "LEAVE";
        }
      }
    }
  }

  public static boolean actScene(String in) {
    if (in.length() != 3) return false;
    if (in.charAt(1) != '.') return false;

    String[] check = {"1.1","1.2","1.3","1.4","1.5",
                      "2.1","2.2","2.3","2.4",
                      "3.1","3.2","3.3","3.4","3.5","3.6","3.7",
                      "4.1","4.2","4.3","4.4","4.5","4.6","4.7",
                      "5.1","5.2","5.3"};
    
    for (String s : check) if (s.equals(in)) return true;
    return false;
  }

  public static void changeActs(String in) {
    excerpts = new ArrayList<String>();

    for (int i = 0; i < in.length(); i += 2) {
      if (Character.getNumericValue(in.charAt(i)) == 1) {
        for (int j = 0; j < 279; j++) {
          excerpts.add(allExcerpts.get(j));
        }
      } else if (Character.getNumericValue(in.charAt(i)) == 2) {
        for (int j = 279; j < 482; j++) {
          excerpts.add(allExcerpts.get(j));
        }
      } else if (Character.getNumericValue(in.charAt(i)) == 3) {
        for (int j = 482; j < 667; j++) {
          excerpts.add(allExcerpts.get(j));
        }
      } else if (Character.getNumericValue(in.charAt(i)) == 4) {
        for (int j = 667; j < 905; j++) {
          excerpts.add(allExcerpts.get(j));
        }
      } else if (Character.getNumericValue(in.charAt(i)) == 5) {
        for (int j = 905; j < 1065; j++) {
          excerpts.add(allExcerpts.get(j));
        }
      }
    }
  }

  public static boolean actsFormatting(String in) {
    if (in.length() > 9)
      return false;
    for (int i = 0; i < in.length(); i += 2) {
      if (Character.getNumericValue(in.charAt(i)) < 1 | Character.getNumericValue(in.charAt(i)) > 5)
        return false;
    }
    return true;
  }

  // PRE-CONDITION: Input length is >= clueLength + toCompleteLength
  public static String printFinishLine(String text) {
    for (int i = 0; i < text.length(); i++) {
      if (text.charAt(i) == '\\') {
        text = text.substring(0, i) + " " + text.substring(i + 2);
        i--;
      }
    }
    text = text.replaceAll("  ", " ");
    text = text.replaceAll("  ", " ");
    String[] print = text.split(" ");

    for (int i = 0; i < clueLength; i++) {
      System.out.print(print[i] + " ");
    }
    System.out.println("...");

    String ret = "";
    for (int i = clueLength; i < clueLength + toCompleteLength; i++) {
      ret += print[i] + " ";
    }
    System.out.print("... ");

    return ret.substring(0, ret.length() - 1);
  }

  public static void printLine(String text) {
    while (text.indexOf("\\n") >= 0) {
      if (!text.startsWith("\\n"))
        System.out.println(text.substring(0, text.indexOf("\\n")));
      text = text.substring(text.indexOf("\\n") + 2);
    }

    System.out.println(text);
    System.out.print("SPEAKER: ");
  }

  public static void readValues(File f) {
    try {
      Scanner in = new Scanner(f);
      while (in.hasNext()) {
        allExcerpts.add(in.nextLine());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static String actSceneStartEnd(String in) {
    switch (in) {
        case("1.1"): return "0-84";
        case("1.2"): return "85-133";
        case("1.3"): return "134-140";
        case("1.4"): return "141-249";
        case("1.5"): return "250-278";
        case("2.1"): return "279-320";
        case("2.2"): return "321-385";
        case("2.3"): return "386-386";
        case("2.4"): return "387-481";
        case("3.1"): return "482-492";
        case("3.2"): return "493-506";
        case("3.3"): return "507-510";
        case("3.4"): return "511-568";
        case("3.5"): return "569-577";
        case("3.6"): return "578-613";
        case("3.7"): return "614-666";
        case("4.1"): return "667-696";
        case("4.2"): return "697-725";
        case("4.3"): return "726-746";
        case("4.4"): return "747-751";
        case("4.5"): return "752-770";
        case("4.6"): return "771-861";
        case("4.7"): return "862-904";
        case("5.1"): return "905-935";
        case("5.2"): return "936-941";
        case("5.3"): return "942-1064";
    }

    return "";
  }
}