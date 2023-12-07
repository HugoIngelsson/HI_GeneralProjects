public class DictionaryNode {
    private DictionaryNode parent;
    private DictionaryNode[] children;
    private String identity;

    public DictionaryNode(DictionaryNode parent) {
        this.parent = parent;
        children = null;
    }

    public DictionaryNode() {
        parent = null;
        children = null;
    }

    public void setParent(DictionaryNode parent) {
        this.parent = parent;
    }

    public void setChildren(DictionaryNode[] children) {
        this.children = children;
    }

    public DictionaryNode[] getChildren() {
        return children;
    }

    public DictionaryNode getParent() {
        return parent;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getIdentity() {
        return identity;
    }

    public Identity getIdentityObject() {
        return null;
    }
}
