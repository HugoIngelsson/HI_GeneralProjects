import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Huffman {
    private ArrayList<Node> nodes;
    private HashMap<Character, String> mappings;

    public Huffman(ArrayList<Frequency> freqs) {
        nodes = new ArrayList<Node>();
        mappings = new HashMap<Character, String>();

        for (Frequency f : freqs) {
            nodes.add(new HuffmanLeaf(f.freq, f.c));
        }
        Collections.sort(nodes);
    }

    public void developTree() {
        if (nodes.size() == 1) return;

        nodes.add(new HuffmanNode(nodes.remove(0), nodes.remove(0)));
        
        // temporary stand-in for actually sorting on insert
        Collections.sort(nodes);

        developTree();
    }

    public void arrangeMappings(Node node, String path) {
        if (node.isLeaf()) mappings.put(node.getID(), path);

        //arrangeMappings(node.)
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }
}
