package btree;

import org.neo4j.graphdb.Entity;


/**
 * @author I-Chung, Wang
 * @date 2021/4/29 下午 02:33
 */
public class LeafNode extends Node {

    enum TypeOfLeaf {
        Node,
        Relationship
    }

    private final long NODE_ID;
//    TODO : Detect the type(Node or Relationship) of data
//    private final TypeOfLeaf TYPE_OF_LEAF;

    private Entity nodeOrRel;

    private LeafNode leftSibling;
    private LeafNode rightSibling;


    /**
     * Wrap the Node or Relationship into B-tree node
     * And get the ID
     * @param entity : The Node or Relationship
     */
    public LeafNode(Entity entity) {
        this.nodeOrRel = entity;
        NODE_ID = entity.getId();
    }

    /**
     * Get the ID which belonging to the GDB entity
     * @return ID value
     */
    public long getEntityId() {
        return nodeOrRel.getId();
    }
}
