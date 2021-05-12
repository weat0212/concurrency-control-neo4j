package btree;


import example.BPlusTree;

import java.util.Optional;

/**
 * @author I-Chung, Wang
 * @date 2021/4/26 下午 04:14
 */
public class InternalNode extends TreeNode {


    private int maxDegree;
    private int minDegree;
    private int degree;

    private Integer[] keys;
    private TreeNode[] childPointers;

    InternalNode leftSibling;
    InternalNode rightSibling;


    /*
    * ***********
    * Constructor
    * ***********
    * */
    public InternalNode(int m, Integer[] keys) {
        this.maxDegree = m;
        this.minDegree = (int)Math.ceil(m/2.0);
        this.degree = 0;
        this.keys = keys;
        this.childPointers = new TreeNode[this.maxDegree+1];
    }

    public InternalNode(int m, Integer[] keys, TreeNode[] pointers) {
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



    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public Integer[] getKeys() {
        return keys;
    }

    public void setKeys(Integer[] keys) {
        this.keys = keys;
    }

    public TreeNode[] getChildPointers() {
        return childPointers;
    }

    public void setChildPointers(TreeNode[] childPointers) {
        this.childPointers = childPointers;
    }



    /**
     * @param pointer : Point to the child list
     */
    public void appendChildPointer(TreeNode pointer) {
        this.childPointers[degree] = pointer;
        this.degree++;
    }


    /**
     * Follow the List to trace the index
     * @param pointer : pointer within the child pointers
     * @return Optional<Integer> : index Found or Not ? integer : empty
     */
    public Optional<Integer> findIndexOfPointer(TreeNode pointer) {
        for (int i = 0; i < childPointers.length; i++) {
            if (childPointers[i] == pointer) {
                return Optional.of(i);
            }
        }
        return Optional.empty();
    }

    /**
     * Given a pointer to a Node object and an integer index, this method
     * inserts the pointer at the specified index within the childPointers
     * instance variable. As a result of the insert, some pointers may be
     * shifted to the right of the index.
     * @param pointer: the Node pointer to be inserted
     * @param index: the index at which the insert is to take place
     */
    public void insertChildPointer(TreeNode pointer, int index) {
        for (int i = degree - 1; i >= index ;i--) {
            childPointers[i + 1] = childPointers[i];
        }
        this.childPointers[index] = pointer;
        this.degree++;
    }

    /**
     * This simple method determines if the InternalNode is deficient or not.
     * An InternalNode is deficient when its current degree of children falls
     * below the allowed minimum.
     * @return a boolean indicating whether the InternalNode is deficient
     * or not
     */
    public boolean isDeficient() {
        return this.degree < this.minDegree;
    }

    /**
     * This simple method determines if the InternalNode is capable of
     * lending one of its dictionary pairs to a deficient node. An InternalNode
     * can give away a dictionary pair if its current degree is above the
     * specified minimum.
     * @return a boolean indicating whether or not the InternalNode has
     * enough dictionary pairs in order to give one away.
     */
    public boolean isLendable() { return this.degree > this.minDegree; }

    /**
     * This simple method determines if the InternalNode is capable of being
     * merged with. An InternalNode can be merged with if it has the minimum
     * degree of children.
     * @return a boolean indicating whether or not the InternalNode can be
     * merged with
     */
    public boolean isMergeable() { return this.degree == this.minDegree; }

    /**
     * This simple method determines if the InternalNode is considered overfull,
     * i.e. the InternalNode object's current degree is one more than the
     * specified maximum.
     * @return a boolean indicating if the InternalNode is overfull
     */
    public boolean isOverfull() {
        return this.degree == maxDegree + 1;
    }

    /**
     * Given a pointer to a Node object, this method inserts the pointer to
     * the beginning of the childPointers instance variable.
     * @param pointer: the Node object to be prepended within childPointers
     */
    public void prependChildPointer(TreeNode pointer) {
        for (int i = degree - 1; i >= 0 ;i--) {
            childPointers[i + 1] = childPointers[i];
        }
        this.childPointers[0] = pointer;
        this.degree++;
    }

    /**
     * This method sets keys[index] to null. This method is used within the
     * parent of a merging, deficient LeafNode.
     * @param index: the location within keys to be set to null
     */
    public void removeKey(int index) { this.keys[index] = null; }

    /**
     * This method sets childPointers[index] to null and additionally
     * decrements the current degree of the InternalNode.
     * @param index: the location within childPointers to be set to null
     */
    public void removePointer(int index) {
        this.childPointers[index] = null;
        this.degree--;
    }

    /**
     * This method removes 'pointer' from the childPointers instance
     * variable and decrements the current degree of the InternalNode. The
     * index where the pointer node was assigned is set to null.
     * @param pointer: the Node pointer to be removed from childPointers
     */
    public void removePointer(TreeNode pointer) {
        for (int i = 0; i < childPointers.length; i++) {
            if (childPointers[i] == pointer) { this.childPointers[i] = null; }
        }
        this.degree--;
    }


    /**
     * Find the empty spot for new data
     * @param pointers
     * @return
     */
    public Optional<Integer> linearNullSearch(TreeNode[] pointers) {
        for (int i = 0; i <  pointers.length; i++) {
            if (pointers[i] == null) { return Optional.of(i); }
        }
        return Optional.empty();
    }
}
