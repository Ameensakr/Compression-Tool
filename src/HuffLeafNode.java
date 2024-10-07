public class HuffLeafNode implements HuffBaseNode {
    private char value;
    private int weight;

    public HuffLeafNode(char value, int weight) {
        this.value = value;
        this.weight = weight;
    }

    public boolean isLeaf() {
        return true;
    }

    public int weight() {
        return weight;
    }

    public char value() {
        return value;
    }
}
