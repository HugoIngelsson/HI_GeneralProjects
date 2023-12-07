import java.util.ArrayList;

public class RadixSorter {
  private ArrayList<Integer> list;
  private ArrayList<Integer> finalized = new ArrayList<Integer>();
  private ArrayList<ArrayList<Integer>> organizer;

  public RadixSorter() {
    list = new ArrayList<Integer>();
  }

  public RadixSorter(ArrayList<Integer> list) {
    this.list = list;
  }

  public void addValue(int n) {
    list.add(n);
  }

  public ArrayList<Integer> getSorted() {
    return finalized;
  }

  public ArrayList<Integer> sort() {
    int n = 0;

    while (list.size() > 0) {
      sortDigits(n++);
    }

    list = finalized;
    return finalized;
  }

  public void sortDigits(int pow) {
    organizer = new ArrayList<ArrayList<Integer>>();
    for (int i=0; i<10; i++) {
      organizer.add(new ArrayList<Integer>());
    }

    int c;

    for (Integer n : list) {
      if (n >= Math.pow(10, pow)) {
        c = (int)(n / Math.pow(10, pow));
        c %= 10;

        organizer.get(c).add(n);
      }
      else {
        finalized.add(n);
      }
    }

    list.clear();
    for (ArrayList<Integer> l : organizer) {
      for (Integer n : l) {
        list.add(n);
      }
    }
  }

  public boolean isSorted() {
    for (int i=0; i<finalized.size()-1; i++) {
      if (finalized.get(i) > finalized.get(i+1)) return false;
    }

    return true;
  }

  public String toString() {
    return "" + list.toString();
  }
}