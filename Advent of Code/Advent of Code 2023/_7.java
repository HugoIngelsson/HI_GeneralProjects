import java.util.*;
import java.io.*;

public class _7 {
    public static void main(String[] args) throws IOException {
        System.out.println(p1());
        System.out.println(p2());
    }

    public static String p2() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        String in;

        @SuppressWarnings("unchecked")
        ArrayList<Set>[] sets = new ArrayList[7];
        for (int i=0; i<7; i++) sets[i] = new ArrayList<Set>();

        int numCards = 0;
        while ((in = br.readLine()) != null) {
            int[] counts = new int[14];
            String set = "";
            for (int i=0; i<5; i++) {
                counts[toInt2(in.charAt(i))]++;
                set += toChar(toInt2(in.charAt(i)));
            }

            int maxCount = 0;
            boolean fullHouse = false;
            boolean twoPair = false;
            for (int count = 5; count > 0; count--) {
                for (int i=13; i>0; i--) {
                    if (counts[i] == count) {
                        if (maxCount == 3 && counts[i] == 2 || maxCount == 2 && counts[i] == 3)
                            fullHouse = true;
                        if (maxCount == 2 && counts[i] == 2)
                            twoPair = true;
                        maxCount = Math.max(maxCount, counts[i]);
                    }
                    else if (counts[i] + counts[0] == count && maxCount < counts[i] + counts[0]) {
                        maxCount = counts[i] + counts[0];
                        counts[0] = 0;
                        counts[i] = 0;
                    }
                }
            }

            int val = Integer.parseInt(in.substring(6));
            if (fullHouse) sets[2].add(new Set(set, val));
            else if (twoPair) sets[4].add(new Set(set, val));
            else switch(maxCount) {
                case(5): sets[0].add(new Set(set, val)); break;
                case(4): sets[1].add(new Set(set, val)); break;
                case(3): sets[3].add(new Set(set, val)); break;
                case(2): sets[5].add(new Set(set, val)); break;
                case(1): sets[6].add(new Set(set, val)); break;
            }
            numCards++;
        }

        int total = 0;
        for (int i=0; i<7; i++) {
            Collections.sort(sets[i]);
            for (Set s : sets[i]) {
                total += s.val*numCards--;
            }
        }

        br.close();
        return "" + total;
    }

    public static String p1() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        String in;

        @SuppressWarnings("unchecked")
        ArrayList<Set>[] sets = new ArrayList[7];
        for (int i=0; i<7; i++) sets[i] = new ArrayList<Set>();

        int numCards = 0;
        while ((in = br.readLine()) != null) {
            int[] counts = new int[14];
            String set = "";
            for (int i=0; i<5; i++) {
                counts[toInt(in.charAt(i))]++;
                set += toChar(toInt(in.charAt(i)));
            }

            int maxCount = 0;
            boolean fullHouse = false;
            boolean twoPair = false;
            for (int count = 5; count > 0; count--) {
                for (int i=13; i>=0; i--) {
                    if (counts[i] == count) {
                        if (maxCount == 3 && counts[i] == 2 || maxCount == 2 && counts[i] == 3)
                            fullHouse = true;
                        if (maxCount == 2 && counts[i] == 2)
                            twoPair = true;
                        maxCount = Math.max(maxCount, counts[i]);
                    }
                }
            }

            int val = Integer.parseInt(in.substring(6));
            if (fullHouse) sets[2].add(new Set(set, val));
            else if (twoPair) sets[4].add(new Set(set, val));
            else switch(maxCount) {
                case(5): sets[0].add(new Set(set, val)); break;
                case(4): sets[1].add(new Set(set, val)); break;
                case(3): sets[3].add(new Set(set, val)); break;
                case(2): sets[5].add(new Set(set, val)); break;
                case(1): sets[6].add(new Set(set, val)); break;
            }
            numCards++;
        }

        int total = 0;
        for (int i=0; i<7; i++) {
            Collections.sort(sets[i]);
            for (Set s : sets[i]) {
                total += s.val*numCards--;
            }
        }

        br.close();
        return "" + total;
    }

    public static int toInt(char c) {
        switch (c) {
            case('1'): return 0;
            case('2'): return 1;
            case('3'): return 2;
            case('4'): return 3;
            case('5'): return 4;
            case('6'): return 5;
            case('7'): return 6;
            case('8'): return 7;
            case('9'): return 8;
            case('T'): return 9;
            case('J'): return 10;
            case('Q'): return 11;
            case('K'): return 12;
            case('A'): return 13;
        }

        return -1;
    }

    public static char toChar(int i) {
        switch (i) {
            case (0): return 'A';
            case (1): return 'B';
            case (2): return 'C';
            case (3): return 'D';
            case (4): return 'E';
            case (5): return 'F';
            case (6): return 'G';
            case (7): return 'H';
            case (8): return 'J';
            case (9): return 'K';
            case (10): return 'L';
            case (11): return 'M';
            case (12): return 'N';
            case (13): return 'O';
        }

        return 'Z';
    }

    public static int toInt2(char c) {
        switch (c) {
            case('2'): return 1;
            case('3'): return 2;
            case('4'): return 3;
            case('5'): return 4;
            case('6'): return 5;
            case('7'): return 6;
            case('8'): return 7;
            case('9'): return 8;
            case('T'): return 9;
            case('J'): return 0;
            case('Q'): return 11;
            case('K'): return 12;
            case('A'): return 13;
        }

        return -1;
    }
}

class Set implements Comparable<Set> {
    String cards;
    int val;

    public Set(String cards, int val) {
        this.cards = cards;
        this.val = val;
    }

    public int compareTo(Set other) {
        return -cards.compareTo(other.cards);
    }

    public String toString() {
        return cards + " " + val;
    }
}