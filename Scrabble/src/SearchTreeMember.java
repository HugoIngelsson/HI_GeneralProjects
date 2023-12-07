public interface SearchTreeMember {
    public Node getParent();
    public SearchTreeMember[] getChildren();
    public void setChildren(SearchTreeMember[] children);
    public String getIdentity();
    public Identity getIdentityObject();
}