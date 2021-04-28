package btree;

import java.io.Serializable;

/**
 * @author I-Chung, Wang
 * @date 2021/4/26 下午 04:19
 */

public class BTree<K extends Comparable<K>, V> implements Serializable {

    private volatile Root root;

    private static final long serialVersionUID = -80614811640020525L;
    private static final int DEFAULT_DEGREE = 3;


    //Degree
    private final int degree;

    private final int minKeys;

    private final int maxKeys;

    public BTree(int degree) {
        this.degree = degree;
        this.minKeys = degree - 1;
        this.maxKeys = 2 * degree - 1;
        this.root = new Root();
    }

    public void setRoot(Root root) {
        this.root = root;
    }

}
