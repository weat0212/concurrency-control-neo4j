package btree;

import example.BPlusTree;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;

/**
 * @author I-Chung, Wang
 * @date 2021/4/26 下午 04:19
 */

/*
* Graph Data Simplified B Plus Tree
* This is a tree for Native-Graph database(Neo4j)
* */
public class GSBTree<K extends Comparable<K>, V> implements Serializable {

    int m;
    private volatile InternalNode root;

    private static final long serialVersionUID = -80614811640020525L;
    private static final int DEFAULT_DEGREE = 3;


    //Degree
    private final int degree;
    private final int minKeys;
    private final int maxKeys;


    /*
     * ***********
     * Constructor
     * ***********
     * */
    public GSBTree(int degree) {
        this.degree = degree;
        this.minKeys = degree - 1;
        this.maxKeys = 2 * degree - 1;
        this.root = new InternalNode(m, null);
    }

    public void setRoot(InternalNode root) {
        this.root = root;
    }


    /*
     * *********
     * FUNCTIONS
     * *********
     * */

    public int binarySearch(DictionaryPair[] dps, int numPairs, int t) {
        Comparator<DictionaryPair> c = new Comparator<DictionaryPair>() {
            @Override
            public int compare(DictionaryPair o1, DictionaryPair o2) {
                Integer a = o1.key;
                Integer b = o2.key;
                return a.compareTo(b);
            }
        };
        return Arrays.binarySearch(dps, 0, numPairs, new DictionaryPair(t, 0), c);
    }

    public LeafNode findLeafNode(int key) {

        // Initialize keys and index variable
        Integer[] keys = this.root.getKeys();
        int i;

        // Find next node on path to appropriate leaf node
        for (i = 0; i < this.root.getDegree() - 1; i++) {
            if (key < keys[i]) { break; }
        }

		/* Return node if it is a LeafNode object,
		   otherwise repeat the search function a level down */
        TreeNode child = this.root.getChildPointers()[i];
        if (child instanceof LeafNode) {
            return (LeafNode)child;
        } else {
            return findLeafNode((InternalNode)child, key);
        }
    }

    public LeafNode findLeafNode(InternalNode node, int key) {

        // Initialize keys and index variable
        Integer[] keys = node.getKeys();
        int i;

        // Find next node on path to appropriate leaf node
        for (i = 0; i < node.getDegree() - 1; i++) {
            if (key < keys[i]) { break; }
        }

		/* Return node if it is a LeafNode object,
		   otherwise repeat the search function a level down */
        TreeNode childNode = node.getChildPointers()[i];
        if (childNode instanceof LeafNode) {
            return (LeafNode)childNode;
        } else {
            return findLeafNode((InternalNode)node.getChildPointers()[i], key);
        }
    }

    public int findIndexOfPointer(TreeNode[] pointers, LeafNode node) {
        int i;
        for (i = 0; i < pointers.length; i++) {
            if (pointers[i] == node) { break; }
        }
        return i;
    }

    public int getMidpoint() {
        return (int)Math.ceil((this.m + 1) / 2.0) - 1;
    }


    // TODO : Handling Sibling problem
    public void handleDeficiency(InternalNode in) {

        InternalNode sibling;
        InternalNode parent = in.parent;
        TreeNode[] childPointers = parent.getChildPointers();

        // Remedy deficient root node
        if (this.root == in) {
            for (TreeNode childPointer : childPointers) {
                if (childPointer != null) {
                    if (childPointer instanceof InternalNode) {
                        this.root = (InternalNode) childPointer;
                        this.root.parent = null;
                    } else if (childPointer instanceof LeafNode) {
                        this.root = null;
                    }
                }
            }
        }

        // Borrow:
        else if (in.leftSibling != null && in.leftSibling.isLendable()) {
            sibling = in.leftSibling;
        } else if (in.rightSibling != null && in.rightSibling.isLendable()) {
            sibling = in.rightSibling;

            // Copy 1 key and pointer from sibling (atm just 1 key)
            int borrowedKey = sibling.getKeys()[0];
            TreeNode pointer = sibling.getChildPointers()[0];

            // Copy root key and pointer into parent
            in.getKeys()[in.getDegree() - 1] = parent.getKeys()[0];
            childPointers[in.getDegree()] = pointer;

            // Copy borrowedKey into root
            parent.getKeys()[0] = borrowedKey;

            // Delete key and pointer from sibling
            sibling.removePointer(0);
            Arrays.sort(sibling.getKeys());
            sibling.removePointer(0);
            shiftDown(childPointers, 1);
        }

        // Merge:
        else if (in.leftSibling != null && in.leftSibling.isMergeable()) {

        } else if (in.rightSibling != null && in.rightSibling.isMergeable()) {
            sibling = in.rightSibling;

            // Copy rightmost key in parent to beginning of sibling's keys &
            // delete key from parent
            sibling.getKeys()[sibling.getDegree() - 1] = parent.getKeys()[parent.getDegree() - 2];
            Arrays.sort(sibling.getKeys(), 0, sibling.getDegree());
            parent.getKeys()[parent.getDegree() - 2] = null;

            // Copy in's child pointer over to sibling's list of child pointers
            for (int i = 0; i < childPointers.length; i++) {
                if (childPointers[i] != null) {
                    sibling.prependChildPointer(childPointers[i]);
                    childPointers[i].parent = sibling;
                    in.removePointer(i);
                }
            }

            // Delete child pointer from grandparent to deficient node
            parent.removePointer(in);

            // Remove left sibling
            sibling.leftSibling = in.leftSibling;
        }

        // Handle deficiency a level up if it exists
        if (parent != null && parent.isDeficient()) {
            handleDeficiency(parent);
        }
    }

    public boolean isEmpty() {
        return root == null;
    }


    public void shiftDown(TreeNode[] pointers, int amount) {
        TreeNode[] newPointers = new TreeNode[this.m + 1];
        if (pointers.length - amount >= 0)
            System.arraycopy(pointers, amount, newPointers, amount - amount, pointers.length - amount);
        pointers = newPointers;
    }


}
