public interface Node extends Comparable<Node> {
    public boolean isLeaf();
    public long getValue();
    public long sumSubtree();
    public char getID();
}

class HuffmanNode implements Node {
    Node smallChild;
    Node largeChild;
    long value;

    public HuffmanNode(Node n1, Node n2) {
        smallChild = n1;
        largeChild = n2;
        value = n1.getValue() + n2.getValue();
    }

    public boolean isLeaf() {
        return false;
    }

    public long getValue() {
        return value;
    }

    public char getID() {
        return '\u0000';
    }

    public int compareTo(Node other) {
        if (this.value < other.getValue()) return -1;
        if (this.value > other.getValue()) return 1;
        return 0;
    }

    public long sumSubtree() {
        return this.value + smallChild.sumSubtree() + largeChild.sumSubtree();
    }

    public String toString() {
        return "(Parent of: " + smallChild.toString() + ", " + largeChild.toString() + ")";
    }
}

class HuffmanLeaf implements Node {
    long value;
    char id;

    public HuffmanLeaf(long value, char id) {
        this.value = value;
        this.id = id;
    }

    public boolean isLeaf() {
        return true;
    }

    public long getValue() {
        return value;
    }

    public char getID() {
        return id;
    }

    public int compareTo(Node other) {
        if (this.value < other.getValue()) return -1;
        if (this.value > other.getValue()) return 1;
        return 0;
    }

    public long sumSubtree() {
        return this.value;
    }

    public String toString() {
        return id + "=" + value;
    }
}