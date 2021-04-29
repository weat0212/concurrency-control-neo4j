package btree;


import example.BPlusTree;
import org.neo4j.graphdb.NotFoundException;

import java.util.Optional;

/**
 * @author I-Chung, Wang
 * @date 2021/4/26 下午 04:14
 */
public class InternalNode extends Node{


    private int maxDegree;
    private int minDegree;
    private int degree;

    private InternalNode leftSibling;
    private InternalNode rightSibling;
    private Integer[] keys;
    private Node[] childPointers;


    /*
    * ***********
    * Constructor
    * ***********
    * */
    private InternalNode(int m, Integer[] keys) {
        this.maxDegree = m;
        this.minDegree = (int)Math.ceil(m/2.0);
        this.degree = 0;
        this.keys = keys;
        this.childPointers = new Node[this.maxDegree+1];
    }

    private InternalNode(int m, Integer[] keys, Node[] pointers) {
        this.maxDegree = m;
        this.minDegree = (int)Math.ceil(m/2.0);
        this.degree = linearNullSearch(pointers).get();
        this.keys = keys;
        this.childPointers = pointers;
    }

    /*
    * *********
    * FUNCTIONS
    * *********
    * */

    /**
     * @param pointer : Point to the child list
     */
    private void appendChildPointer(Node pointer) {
        this.childPointers[degree] = pointer;
        this.degree++;
    }


    /**
     * Follow the List to trace the index
     * @param pointer : pointer within the child pointers
     * @return Optional<Integer> : index Found or Not ? integer : empty
     */
    private Optional<Integer> findIndexOfPointer(Node pointer) {
        for (int i = 0; i < childPointers.length; i++) {
            if (childPointers[i] == pointer) {
                return Optional.of(i);
            }
        }
        return Optional.empty();
    }


    /**
     * Find the empty spot for new data
     * @param pointers
     * @return
     */
    private Optional<Integer> linearNullSearch(Node[] pointers) {
        for (int i = 0; i <  pointers.length; i++) {
            if (pointers[i] == null) { return Optional.of(i); }
        }
        return Optional.empty();
    }
}
