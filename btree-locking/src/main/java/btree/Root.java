package btree;

/**
 * @author I-Chung, Wang
 * @date 2021/4/26 下午 04:19
 */

public class Root {

    private final long rootId;

    public Root(long rootId) {
        this.rootId = rootId;
    }


    public long getRootId() {
        return rootId;
    }
}
