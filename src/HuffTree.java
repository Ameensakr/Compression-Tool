public class HuffTree implements Comparable<HuffTree> {
    private HuffBaseNode root;

    public HuffTree(HuffBaseNode root) {
        this.root = root;
    }

    public int weight() {
        return root.weight();
    }

    public int compareTo(HuffTree tree) {
        if(root.weight() < tree.weight()) {
            return -1;
        } else if(root.weight() == tree.weight()) {
            return 0;
        } else {
            return 1;
        }
    }

    public HuffBaseNode root() {
        return root;
    }
}
