public class Node implements SearchTreeMember {
    private Node parent;
    private SearchTreeMember[] children;

    public Node(Node parent) {
        this.parent = parent;
        children = null;
    }

    public Node() {
        parent = null;
        children = null;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void setChildren(SearchTreeMember[] children) {
        this.children = children;
    }

    public SearchTreeMember[] getChildren() {
        return children;
    }

    public Node getParent() {
        return parent;
    }

    public String getIdentity() {
        return null;
    }

    public Identity getIdentityObject() {
        return null;
    }
}