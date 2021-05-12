package btree;

/**
 * @author I-Chung, Wang
 * @date 2021/4/30 下午 03:51
 */

/**
 * This class represents a dictionary pair that is to be contained within the
 * leaf nodes of the B+ tree. The class implements the Comparable interface
 * so that the DictionaryPair objects can be sorted later on.
 */
public class DictionaryPair implements Comparable<DictionaryPair> {

    int key;
    double value;

    /**
     * Constructor
     * @param key: the key of the key-value pair
     * @param value: the value of the key-value pair
     */
    public DictionaryPair(int key, double value) {
        this.key = key;
        this.value = value;
    }

    /**
     * This is a method that allows comparisons to take place between
     * DictionaryPair objects in order to sort them later on
     * @param o
     * @return
     */
    @Override
    public int compareTo(DictionaryPair o) {
    //TODO : Change return type
        return Integer.compare(key, o.key);
    }

}
