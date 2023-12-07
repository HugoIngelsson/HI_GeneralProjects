import java.util.*;

public class FindVariableCompression {
    ArrayList<Frequency> frequencies;
    int[] bestCounts;
    int[] bestSplit;
    int[] sumTil;
    String[] assignments;

    public FindVariableCompression(ArrayList<Frequency> frequencies) {
        this.frequencies = frequencies;
        bestCounts = new int[frequencies.size()];
        bestSplit = new int[frequencies.size()];
        sumTil = new int[frequencies.size()];
        assignments = new String[frequencies.size()];

        sumTil[0] = frequencies.get(0).freq;
        for (int i=1; i<frequencies.size(); i++) {
            sumTil[i] = sumTil[i-1] + frequencies.get(i).freq;
        }
    }

    public void assignMappings(String prefix, int i) {
        if (i < 0) return;

        if (i - Math.pow(2, bestSplit[i]-1) <= 0) {
            for (int delta=0; delta<Math.pow(2, bestSplit[i]-1) && i-delta>=0; delta++) {
                assignments[i-delta] = prefix + intToBinary(delta, bestSplit[i]-2);
            }
        }
        else {
            for (int delta=0; delta<Math.pow(2, bestSplit[i]-1) && i-delta>=0; delta++) {
                assignments[i-delta] = prefix + "0" + intToBinary(delta, bestSplit[i]-2);
            }
        }

        assignMappings(prefix + "1", i-(int)Math.pow(2, bestSplit[i]-1));
    }

    public static String intToBinary(int n, int split) {
        if (split < 0) return "";

        if (n - Math.round(Math.pow(2, split)) >= 0) return "1" + intToBinary(n - (int)Math.round(Math.pow(2, split)), split-1);
        else return "0" + intToBinary(n, split-1);
    }

    public void developDP() {
        bestCounts[0] = sumTil[0];
        bestSplit[0] = 1;

        for (int i=1; i<frequencies.size(); i++) {
            bestAt(i);
        }
    }

    public void bestAt(int n) {
        int i = 1;
        while (Math.pow(2, i-1) <= 2*n+1) {
            int id = n - (int)Math.pow(2, i-1);
            int subCost;

            if (id < 0) subCost = sumTil[n]*(i-1);
            else subCost = sumTil[id] + bestCounts[id] + (sumTil[n]-sumTil[id])*i;

            if (bestCounts[n] == 0 || subCost <= bestCounts[n]) {
                bestCounts[n] = subCost;
                bestSplit[n] = i;
            }

            i++;
        }
    }
}
