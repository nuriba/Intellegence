import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.Math.max;

/**
 * This class includes methods to make the duties
 */
public class RelatedMethods{
    /**
     * this method gives the height of the node. If the node doesn't exist, it will give -1.
     * @param node is wanted to find its height
     * @return the length of the subtree under the node
     */
    public int findHeight(Node node){
        if (node== null)
            return -1;
        return node.height;
    }

    /**
     *This method rotate the given node with its right child.
     * @param node will be rotated with its right child.
     * @return the replaced node instead of input node
     */
    public Node leftRotate(Node node){
        Node head = node.rightNode; // to store the right child without losing
        node.rightNode=head.leftNode; // assign the new right child to input node
        head.leftNode=node; //changes the nodes.
        node.height = max(findHeight(node.rightNode),findHeight(node.leftNode)) +1; // checks the new height of the node.
        head.height = max(findHeight(head.leftNode),findHeight(head.rightNode)) + 1;
        return head;
    }

    /**
     * This method rotate the given node with its left child
     * @param node will be rotated with its left child.
     * @return the replaced node instead of input node.
     */
    public Node rightRotate(Node node){
        Node head = node.leftNode;
        node.leftNode = head.rightNode;
        head.rightNode= node;
        node.height = max(findHeight(node.rightNode),findHeight(node.leftNode)) +1;
        head.height = max(findHeight(head.leftNode),findHeight(head.rightNode)) + 1;
        return head;
    }

    /**
     *Sometimes double rotation can be needed. This method firstly rotates its left child with its right child,
     * then rotates itself with its right child.
     * @param node will be rotated double
     * @return after rotations, replaced with the input node
     */
    public Node doubleRightRotation(Node node){
        node.leftNode = leftRotate(node.leftNode);
        return rightRotate(node);
    }

    /**
     * the method rotates double. firstly rotates its right child with its left child and then
     * rotates itself with its right child.
     * @param node will be rotated double
     * @return after rotations, replaced with the input node
     */
    public Node doubleLeftRotation(Node node){
        node.rightNode= rightRotate(node.rightNode);
        return leftRotate(node);
    }

    /**
     * This method try to get balanced in AVL tree by changing nodes.
     * Balance means balance  factor should be between -1 and 1.
     * @param root is the beginning node to subtree
     * @return it will return the root after change.
     */
    public Node balance(Node root){
        if (root == null)
            return root;
        if (findHeight(root.leftNode)-findHeight(root.rightNode)> 1){ // Calculates balance factor and compare it with 1
            // if it's greater than 1, I need to make right rotation and maybe double right rotation.
            if (findHeight(root.leftNode.leftNode) >= findHeight(root.leftNode.rightNode))
                root = rightRotate(root);
            else
                root = doubleRightRotation(root);
        }
        else if (findHeight(root.leftNode)-findHeight(root.rightNode)<-1){ // Calculate balance factor and compare it with -1,
            // if it's smaller than -1, I need to make left rotation or double left rotation
            if (findHeight(root.rightNode.rightNode)>=findHeight(root.rightNode.leftNode))
                root = leftRotate(root);
            else
                root =doubleLeftRotation(root);
        }
        root.height = max(findHeight(root.leftNode),findHeight(root.rightNode)) +1; // update the height of the root
        return root;
    }

    /**
     * This method adds the new people to the tree at the lowest position
     * @param newPerson will be added to the member tree
     * @param root is the root of the subtree or tree.
     * @param writer to write the file which stores the outputs
     * @return the final root
     */
    public Node insert(Node newPerson, Node root, FileWriter writer) throws IOException {
        if (root==null){
            return newPerson;
        }
        if (newPerson.GMS<root.GMS){
            writer.write(root.name + " welcomed " + newPerson.name + "\n");
            writer.flush();
            root.leftNode=insert(newPerson,root.leftNode,writer);
        }else if (newPerson.GMS> root.GMS){
            writer.write(root.name + " welcomed " + newPerson.name + "\n");
            writer.flush();
            root.rightNode=insert(newPerson,root.rightNode,writer);
        }else{
            return root;
        }
        return balance(root);
    }

    /**
     * the method finds the smallest common parent.
     * @param node1 is the one of the nodes
     * @param node2 is the other node
     * @param boss is the root of the tree or subtree. It will change recursively
     * @return the common parent
     */
    public Node targetNode(Node node1,Node node2, Node boss){
        if (boss.GMS> node1.GMS && boss.GMS> node2.GMS)
            return targetNode(node1,node2,boss.leftNode);
        else if(boss.GMS<node1.GMS && boss.GMS<node2.GMS)
            return targetNode(node1,node2,boss.rightNode);
        else if(boss.GMS==node1.GMS || boss.GMS ==node2.GMS)
            return boss;
        else
            return boss;
    }

    /**
     * This method gives me the rank of the given element
     * @param boss the root of tree or subtree. it changes recursively
     * @param searchingElement is the element I need to find its rank.
     * @param currentRank store the rank of the element. it increases until I found the element in the tree
     * @return the rank of the element
     */
    public int search(Node boss, Node searchingElement, int currentRank){
        if (boss== null)
            return 0;
        if (boss.GMS<searchingElement.GMS){
            currentRank++;
            return search(boss.rightNode,searchingElement, currentRank);
        }
        else if(boss.GMS> searchingElement.GMS){
            currentRank++;
            return search(boss.leftNode,searchingElement, currentRank);
        }
        else
            return currentRank;
    }

    /**
     * finds the elements same with the wanted rank.
     * @param boss the root of tree or subtree. it changes recursively
     * @param wantedRank is the rank number
     * @param currentRank is therank of current root.
     * @param storeNodes is the list of all elements having the same rank
     */
    public void findSameRank(Node boss,int wantedRank, int currentRank, ArrayList<Node> storeNodes){
        if (boss==null)
            return;
        if(wantedRank==currentRank){
            storeNodes.add(boss);
            return;
        }
        currentRank++; // increase the rank because we are going to next child
        findSameRank(boss.leftNode,wantedRank,currentRank,storeNodes);
        currentRank--; // decrease it because recursive will change the number. to protect this number.
        currentRank++;
        findSameRank(boss.rightNode,wantedRank,currentRank,storeNodes);
        currentRank--;
        return;
    }

    /**
     * finds the maximum number of independent nodes. Nodes shouldn't be dependent directly.
     * @param boss the root of tree or subtree. it changes recursively
     * @param elements is the list of all independent nodes
     */
    public void division(Node boss, HashMap<Node,Integer> elements){
        if (boss == null)
            return;
        division(boss.leftNode,elements);
        division(boss.rightNode,elements);
        if (elements.getOrDefault(boss.leftNode,0)==0 && elements.getOrDefault(boss.rightNode,0)==0) // if the list doesn't contain any sub node of the node,
            // it is the one of the independent node.
            elements.put(boss,1);
    }

    /**
     * delete the member from the tree, and balance the tree again.
     * @param boss the root of tree or subtree. it changes recursively
     * @param willDeleteGMS is wanted to delete.
     * @param i is control the deletion number and thanks to this, i can prevent writing wrong sentence to the output file.
     * @param writer is to write to the file.
     * @return the root
     */
    public Node deletion(Node boss, float willDeleteGMS, int i,FileWriter writer) throws IOException {
        if (boss== null)
            return boss;
        if (boss.GMS<willDeleteGMS)
            boss.rightNode = deletion(boss.rightNode,willDeleteGMS,i,writer);
        else if  (boss.GMS>willDeleteGMS)
            boss.leftNode = deletion(boss.leftNode,willDeleteGMS,i,writer);
        else if (boss.leftNode != null && boss.rightNode!=null) { // two children
            Node temp = findMin(boss.rightNode);
            writer.write(boss.name + " left the family, replaced by " + temp.name + "\n");
            writer.flush();
            boss.name = temp.name;
            boss.GMS = temp.GMS;
            boss.stringGMS = temp.stringGMS;
            i++;
            boss.rightNode = deletion(boss.rightNode, temp.GMS,i,writer);
        }else{
            Node temp = null;
            if (boss.rightNode != null) // only one child
                temp = boss.rightNode;
            else
                temp = boss.leftNode;
            if(temp != null && i==0) { // only one child, and this deletion will be the first deletion.
                writer.write(boss.name + " left the family, replaced by " + temp.name + "\n");
                writer.flush();
                boss = temp;
            }else if (temp == null && i==0){ // no children and this deletion will be the first deletion
                writer.write(boss.name + " left the family, replaced by nobody" + "\n");
                writer.flush();
                boss=temp;
            }else{
                boss=temp; // anyone but it's different because I shouldn't write the sentence to the file twice
            }
        }
        return balance(boss);
    }

    /**
     * finds smallest elements in the given tree or subtree.
     * @param node the root of tree or subtree. it changes recursively
     * @return the smallest node.
     */
    public Node findMin(Node node){
        if (node== null)
            return node;
        Node temp = node;
        while (temp.leftNode != null)
            temp = temp.leftNode;
        return temp;
    }
}
