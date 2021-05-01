package btree;

import example.BPlusTree;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;

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

    private int binarySearch(DictionaryPair[] dps, int numPairs, int t) {
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

    private LeafNode findLeafNode(int key) {

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

    private LeafNode findLeafNode(InternalNode node, int key) {

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

    private int findIndexOfPointer(TreeNode[] pointers, LeafNode node) {
        int i;
        for (i = 0; i < pointers.length; i++) {
            if (pointers[i] == node) { break; }
        }
        return i;
    }

    private int getMidpoint() {
        return (int)Math.ceil((this.m + 1) / 2.0) - 1;
    }
}
