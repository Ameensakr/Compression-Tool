public class HuffInternalNode implements HuffBaseNode {
    private HuffBaseNode left;
    private HuffBaseNode right;
    private int weight;

    public HuffInternalNode(HuffBaseNode left, HuffBaseNode right) {
        this.left = left;
        this.right = right;
        this.weight = left.weight() + right.weight();
    }

    public boolean isLeaf() {
        return false;
    }

    public int weight() {
        return weight;
    }

    public HuffBaseNode left() {
        return left;
    }

    public HuffBaseNode right() {
        return right;
    }
}
