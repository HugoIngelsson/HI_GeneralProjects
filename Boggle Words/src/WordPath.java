public class WordPath {
    String word;
    String path;
    int value;

    WordPath(String word, String path, int value) {
        this.word = word;
        this.path = path;
        this.value = value;
    }

    public String toString() {
        return word + " " + value + " ... " + path;
    }
}