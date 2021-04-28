package btree;

import org.neo4j.graphdb.Entity;

import java.util.List;

/**
 * @author I-Chung, Wang
 * @date 2021/4/26 下午 04:14
 */
abstract class TreeNode {

    enum Type {
        LEAF,
        INTERNAL
    }

    private final long NODE_ID;

    private Entity nodeOrRel;
    private List<TreeNode> children;
    private TreeNode ancestors;



    /*
    * Wrap the Node or Relationship into B-tree node
    */
    public TreeNode(Entity nodeOrRel) {
        this.nodeOrRel = nodeOrRel;
        NODE_ID = nodeOrRel.getId();
    }

    /**
     * Get the ID which belonging to the GDB entity
     *
     * @return ID value
     */
    public long getEntityId() {
        return nodeOrRel.getId();
    }
}
