package btree;


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
    private void appendChildPointer(TreeNode pointer) {
        this.childPointers[degree] = pointer;
        this.degree++;
    }


    /**
     * Follow the List to trace the index
     * @param pointer : pointer within the child pointers
     * @return Optional<Integer> : index Found or Not ? integer : empty
     */
    private Optional<Integer> findIndexOfPointer(TreeNode pointer) {
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
    private Optional<Integer> linearNullSearch(TreeNode[] pointers) {
        for (int i = 0; i <  pointers.length; i++) {
            if (pointers[i] == null) { return Optional.of(i); }
        }
        return Optional.empty();
    }
}
