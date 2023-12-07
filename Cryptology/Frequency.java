public class Frequency implements Comparable<Frequency> {
    char c;
    int freq;

    public Frequency(char c, int freq) {
        this.c = c;
        this.freq = freq;
    }

    public int compareTo(Frequency other) {
        if (this.freq < other.freq) return -1;
        else if (this.freq > other.freq) return 1;
        else return 1;
    }

    public String toString() {
        return "\'" + this.c + "\' occurs " + this.freq + " time(s)";
    }
}
