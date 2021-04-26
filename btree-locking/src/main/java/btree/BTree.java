package btree;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author I-Chung, Wang
 * @date   2021/4/26 下午 04:19
 */

public class BTree implements Closeable {

    private volatile Root root;

    @Override
    public void close() throws IOException {

    }
}
