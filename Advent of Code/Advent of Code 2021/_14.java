import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.*;

class Main {
  static HashMap<String, String> keys = new HashMap<String, String>();
  static HashMap<String, Double> eff = new HashMap<String, Double>();
  static String template = "";

  public static void main(String[] args) {
    File f = new File("data.txt");
    populate(f);

    System.out.println(template);
    System.out.println(eff);
    for (int i=0; i<40; i++) {
      efficientInsert();
    }
    System.out.println(eff);
    System.out.printf("%.0f",efficientGetDifference());
  }


  //Efficient
  public static double efficientGetDifference() {
    HashMap<Character, Double> vals = new HashMap<Character, Double>();

    for (Map.Entry entry : eff.entrySet()) {
      String s = (String) entry.getKey();
      char c1 = s.charAt(0);
      char c2 = s.charAt(1);

      if (vals.containsKey(c1)) vals.replace(c1, vals.get(c1)+(double)entry.getValue());
      else vals.put(c1, (double)entry.getValue());

      if (vals.containsKey(c2)) vals.replace(c2, vals.get(c2)+(double)entry.getValue());
      else vals.put(c2, (double)entry.getValue());
    }
    vals.replace('S', vals.get('S')+1);
    vals.replace('V', vals.get('V')+1);

    double min = Double.MAX_VALUE;
    double max = Double.MIN_VALUE;

    for (Map.Entry entry : vals.entrySet()) {
      min = Math.min(min, (double)entry.getValue());
      max = Math.max(max, (double)entry.getValue());
    }

    return (max - min)/2.0;
  }

  public static void efficientInsert() {
    HashMap<String, Double> newer = new HashMap<String, Double>();

    for (Map.Entry entry : eff.entrySet()) {
      String key = (String)entry.getKey();
      double ele = (double)entry.getValue();

      String p1 = key.charAt(0) + keys.get(key);
      String p2 = keys.get(key) + key.charAt(1);

      if (newer.containsKey(p1)) newer.replace(p1, newer.get(p1) + ele);
      else newer.put(p1, ele);

      if (newer.containsKey(p2)) newer.replace(p2, newer.get(p2) + ele);
      else newer.put(p2, ele);
    }

    eff = newer;
  }

  //Inefficient
  public static int getDifference() {
    HashMap<Character, Integer> count = new HashMap<Character, Integer>();

    for (int i=0; i<template.length(); i++) {
      char c = template.charAt(i);

      if (count.containsKey(c)) count.replace(c, count.get(c)+1);
      else count.put(c, 1);
    }
    System.out.println(count);

    int max = 0;
    int min = 100000000;
    for (Map.Entry mapElement : count.entrySet()) {
      max = Math.max(max, (int)mapElement.getValue());
      min = Math.min(min, (int)mapElement.getValue());
    }

    return max - min;
  }

  public static void insert() {
    for (int i=0; i<template.length()-1; i++) {
      template = template.substring(0,i) + getMapping(template.substring(i,i+2)) + template.substring(i+1);
      i++;
    }
  }

  public static String getMapping(String key) {
    return "" + key.charAt(0) + keys.get(key);
  }

  public static void populate(File f) {
    try {
      Scanner sc = new Scanner(f);
      template = sc.nextLine();
      sc.nextLine();

      for (int i=0; i<template.length()-1; i++) {
        if (eff.containsKey(template.substring(i,i+2))) eff.replace(template.substring(i,i+2), eff.get(template.substring(i,i+2))+1);
        else eff.put(template.substring(i,i+2), 1.0);
      }

      while (sc.hasNext()) {
        String s = sc.nextLine();

        keys.put(s.substring(0,2), s.substring(6));
      }
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
}