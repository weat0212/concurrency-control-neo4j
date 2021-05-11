package btree;

/**
 * @author I-Chung, Wang
 * @date 2021/4/30 下午 03:51
 */
public class DictionaryPair implements Comparable<DictionaryPair> {

    int key;
    double value;

    public DictionaryPair(int key, double value) {
        this.key = key;
        this.value = value;
    }


    //TODO : Change return type
    @Override
    public int compareTo(DictionaryPair o) {
        return Integer.compare(key, o.key);
    }

}
