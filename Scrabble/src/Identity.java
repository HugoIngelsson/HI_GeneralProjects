import java.util.ArrayList;

public class Identity implements Comparable<Identity> {
    private String identity;
    private ArrayList<String> words;

    public Identity(String identity) {
        this.identity = identity;
        words = new ArrayList<String>();
    }

    public Identity(String identity, String firstWord) {
        this.identity = identity;
        words = new ArrayList<String>();
        words.add(firstWord);
    }

    public void addWord(String word) {
        words.add(word);
    }

    public ArrayList<String> getWords() {
        return words;
    }

    public String getIdentity() {
        return identity;
    }

    public int compareTo(Identity other) {
         if (identity.compareTo(other.getIdentity()) < 0) return -1;
        else if (identity.compareTo(other.getIdentity()) > 0) return 1;

        return 0;
    }
}
