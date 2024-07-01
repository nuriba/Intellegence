/**
 * This class is to store the information about a member. Thanks to this class,
 * I can store all members in an AVL tree
 */
public class Node {
    String name;
    float GMS;
    String stringGMS; // to write GMS easily. Otherwise, float can create the loss of same digits.
    Node rightNode;
    Node leftNode;
    int height; // the length of the subtree under this node.
    Node(String name, String GMS){
        this.name=name;
        this.stringGMS = GMS;
        this.GMS = Float.parseFloat(GMS);
        height=0;
    }
}
