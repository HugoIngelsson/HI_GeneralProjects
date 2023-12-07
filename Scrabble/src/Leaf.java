public class Leaf implements SearchTreeMember {
    private Node parent;
    private Identity identity;

    public Leaf(Node parent) {
        this.parent = parent;
        this.identity = null;
    }
    
    public Leaf(Node parent, Identity identity) {
        this.parent = parent;
        this.identity = identity;
    }

    public String getIdentity() {
        return identity.getIdentity();
    }

    public Identity getIdentityObject() {
        return identity;
    }

    public Node getParent() {
        return parent;
    }

    public SearchTreeMember[] getChildren() {
        return null;
    }

    public void setChildren(SearchTreeMember[] children) {}
}
