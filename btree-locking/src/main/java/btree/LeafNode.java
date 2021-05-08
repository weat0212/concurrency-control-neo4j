package btree;

import example.BPlusTree;
import org.neo4j.graphdb.Entity;

import java.util.Arrays;
import java.util.Optional;


/**
 * @author I-Chung, Wang
 * @date 2021/4/29 下午 02:33
 */
public class LeafNode extends TreeNode {

    public LeafNode leftSibling;
    public LeafNode rightSibling;

    enum TypeOfLeaf {
        Node,
        Relationship
    }

    private final long NODE_ID;
//    TODO : Detect the type(Node or Relationship) of data
//    private final TypeOfLeaf TYPE_OF_LEAF;

    private Entity nodeOrRel;

    int maxNumPairs;
    int minNumPairs;
    int numPairs;

    DictionaryPair[] dictionary;

    /*
     * ***********
     * Constructor
     * ***********
     * */


    public LeafNode(int m, DictionaryPair dp, long node_id, Entity entity) {
        this.maxNumPairs = m - 1;
        this.minNumPairs = (int)(Math.ceil(m/2) - 1);
        this.dictionary = new DictionaryPair[m];

        this.nodeOrRel = entity;
        NODE_ID = entity.getId();

        this.numPairs = 0;
        this.insert(dp);
    }


    public LeafNode(int m, DictionaryPair[] dps, InternalNode parent, Entity entity) {

        this.maxNumPairs = m - 1;
        this.minNumPairs = (int)(Math.ceil(m/2) - 1);

        this.dictionary = dps;
        this.numPairs = linearNullSearch(dps).get();

        this.nodeOrRel = entity;
        NODE_ID = entity.getId();

        this.parent = parent;
    }



    /*
     * *********
     * FUNCTIONS
     * *********
     * */

    /**
     * Get the ID which belonging to the GDB entity
     * @return ID value
     */
    public long getEntityId() {
        return nodeOrRel.getId();
    }

    private Optional<Integer> linearNullSearch(DictionaryPair[] dps) {
        for (int i = 0; i <  dps.length; i++) {
            if (dps[i] == null) { return Optional.of(i); }
        }
        return Optional.empty();
    }

    public boolean insert(DictionaryPair dp) {
        if (this.isFull()) {

            /* Flow of execution goes here when numPairs == maxNumPairs */

            return false;
        } else {

            // Insert dictionary pair, increment numPairs, sort dictionary
            this.dictionary[numPairs] = dp;
            numPairs++;
            Arrays.sort(this.dictionary, 0, numPairs);

            return true;
        }
    }

    public void delete(int index) {

        // Delete dictionary pair from leaf
        this.dictionary[index] = null;

        // Decrement numPairs
        numPairs--;
    }

    public boolean isDeficient() { return numPairs < minNumPairs; }

    private boolean isFull() {
        return numPairs == maxNumPairs;
    }

    public boolean isLendable() {
        return numPairs > minNumPairs;
    }

    public boolean isMergeable() {
        return numPairs == minNumPairs;
    }

}


