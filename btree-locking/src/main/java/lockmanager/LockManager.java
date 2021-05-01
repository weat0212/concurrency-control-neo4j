package lockmanager;

import org.neo4j.kernel.impl.locking.community.RWLock;

import java.util.HashMap;
import java.util.Map;

/**
 * @author I-Chung, Wang
 * @date 2021/5/1 下午 04:47
 */

/*
* Substitute LockManagerImpl from
* org.neo4j.kernel.impl.locking.community;
* */
public class LockManager {

    private final Map<Object,RWLock> resourceLockMap = new HashMap<>();

}
