public class Entry {
    String word;
    String lastWord;
    boolean isWord;
    Entry[] crosses;
    int[] crossOverlaps;
    boolean frozen;
    int numChanges;
    int lastMove;

    public Entry(String word, boolean isWord) {
        this.word = word;
        lastWord = "";
        this.isWord = isWord;
        crosses = new Entry[word.length()];
        crossOverlaps = new int[word.length()];
        frozen = false;
        numChanges = 1;
        lastMove = -100;
    }

    public void makeFrozen() {
        frozen = true;
    }

    public void insertCross(Entry e, int place, int intersection) {
        if (crosses.length == 0) {
            crosses = new Entry[word.length()];
            crossOverlaps = new int[word.length()];
        }

        crosses[place] = e;
        crossOverlaps[place] = intersection;
    }

    public void setLetter(char c, int i) {
        word = word.substring(0, i) + c + word.substring(i+1);
    }

    public void setWord(String s) {
        lastWord = word;
        word = s;
        numChanges++;
    }

    public void setIsWord(boolean b) {
        isWord = b;
    }
    
    public void setLastMove(int i) {
        lastMove = i;
    }

    public String getWord() {
        return word;
    }

    public boolean getIsWord() {
        return isWord;
    }

    public Entry[] getCrosses() {
        return crosses;
    }

    public int[] getOverlaps() {
        return crossOverlaps;
    }

    public boolean getIsFrozen() {
        return frozen;
    }

    public int getChanges() {
        return numChanges;
    }

    public String getLastWord() {
        return lastWord;
    }

    public int getLastMove() {
        return lastMove;
    }

    public String[][] splitWords() {
        String[][] ret = new String[word.length()][2];

        for (int i=0; i<word.length(); i++) {
            ret[i][0] = crosses[i].getWord().substring(0,crossOverlaps[i]);
            ret[i][1] = crosses[i].getWord().substring(crossOverlaps[i]+1);
        }

        return ret;
    }

    public boolean shouldChange() {
        if (!isWord) return true;
        for (Entry e : crosses) if (!e.getIsWord())return true;
        return false;
    }

    public boolean[] crossesAreWords() {
        boolean[] ret = new boolean[word.length()];

        for (int i=0; i<word.length(); i++)
            ret[i] = crosses[i].getIsWord();

        return ret;
    }

    public String toString() {
        return word + " " + isWord;
    }
}
