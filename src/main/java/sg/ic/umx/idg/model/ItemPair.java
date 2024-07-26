package sg.ic.umx.idg.model;

public class ItemPair {

    private Item left;
    private Item right;
    private boolean knownDirect;

    public ItemPair() {
        // Empty Constructor
    }

    public Item getLeft() {
        return left;
    }

    public Item getRight() {
        return right;
    }

    public boolean isKnownDirect() {
        return knownDirect;
    }

    public void setLeft(Item left) {
        this.left = left;
    }

    public void setRight(Item right) {
        this.right = right;
    }

    public void setKnownDirect(boolean knownDirect) {
        this.knownDirect = knownDirect;
    }

}
