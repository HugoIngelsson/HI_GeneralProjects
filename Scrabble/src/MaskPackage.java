public class MaskPackage {
    Vector vector;
    String frame;
    String lettersAvailable;
    int minDir;
    int maxDir;

    public MaskPackage(Vector vector, String frame, String lettersAvailable, int minDir, int maxDir) {
        this.vector = vector;
        this.frame = frame;
        this.lettersAvailable = lettersAvailable;
        this.minDir = minDir;
        this.maxDir = maxDir;
    }

    public String toString() {
        return vector.toString() + " " + frame + " " + minDir + " " + maxDir;
    }
}
