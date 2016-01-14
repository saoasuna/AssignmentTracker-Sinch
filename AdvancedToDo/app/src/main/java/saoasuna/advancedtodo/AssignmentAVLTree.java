package saoasuna.advancedtodo;

import java.util.UUID;

/**
 * Created by Ryan on 12/01/2016.
 */
public class AssignmentAVLTree {        // organizes the assignments in a tree based on duedate
    // left = earlier, right = same time or after

    private Node mRoot;
    private int mSize;

    public AssignmentAVLTree(Node root) {
        mRoot = root;
        mRoot.setHeight(0);
        mSize = 1;
    }

    //public boolean checkOverlap() {

    //}

    public Node search(Node node, Assignment assignment) { // search the subtree rooted at node for a particular assignment

        if (node == null) {
            return null;
        }

        if (assignment.getAssignmentTime() == node.getAssignment().getAssignmentTime()) {
            if (assignment.getUUID() == node.getAssignment().getUUID()) {
                return node;
            }
            else {
                return search(node.getRightChild(), assignment); // if due date is same but different assignments
            }
        }
        else if (assignment.getAssignmentTime() < node.getAssignment().getAssignmentTime()) {
            return search(node.getLeftChild(), assignment);
        }
        else {
            return search(node.getRightChild(), assignment); // if due date is after
        }
    }

    public Node subtreeMinimum(Node node) { // returns the minimum of the subtree rooted at node
        if(node.getLeftChild() == null) {
            return node;
        }
        else {
            return subtreeMinimum(node.getLeftChild());
        }
    }

    public Node findSuccessor(Node node) {
        if (node.getRightChild()!=null) {       // either the node has a right subtree or
            return subtreeMinimum(node.getRightChild());
        }
        else {
            Node parent = node.getParent();     // go up the tree until you encounter root or a node that is the left child of its parent
            while (parent!=null && parent.getRightChild()==node){
                node = parent;
                parent = node.getParent();
            }
            return parent;
        }

    }

    public Node insert(Assignment assignment) { // standard BST insert, returns reference to inserted Node
        Node currentNode = mRoot;
        mSize++;

        // if the tree is empty
        if (mRoot == null) {
            mRoot = new Node(assignment);
            return mRoot;
        }

        Node previousNode = null;
        while (currentNode != null) {
            previousNode = currentNode;
            if (assignment.getAssignmentTime() < currentNode.getAssignment().getAssignmentTime()) {
                currentNode = currentNode.getLeftChild();
            }
            else {
                currentNode = currentNode.getRightChild();
            }
        }
        Node newNode = new Node(assignment);
        newNode.setHeight(0);
        newNode.setParent(previousNode);
        if (assignment.getAssignmentTime() < previousNode.getAssignment().getAssignmentTime()) {
            previousNode.setLeftChild(newNode);
        }
        else {
            previousNode.setRightChild(newNode);
        }

        Node insertedNode = newNode;


        // go up the tree updating heights
        updateHeight(newNode);
        int oldHeight;
        while (newNode.getParent() != null) {
            oldHeight = newNode.getParent().getHeight();
            updateHeight(newNode.getParent());
            if(oldHeight == newNode.getParent().getHeight()) {
                break; // if none of the heights above will be affected then break
            }
            newNode = newNode.getParent();
        }

        return insertedNode;



    }

    public void AVLinsert(Assignment assignment) {
        Node inserted = insert(assignment);
        // there are four cases of imbalance that arise each insert:
        // according to washington cse 332, you only need to balance at most one node for each insert
        // however MIT's ocw 6.006 says that you need to balance the node and check the nodes above it
        // we will check the nodes above it just in case for now

        //node’s left-left grandchild is too tall - simple?
        //node’s left-right grandchild is too tall


        //node’s right-left grandchild is too tall
        //node’s right-right grandchild is too tall
        Node currentNode = inserted.getParent().getParent();
        while (currentNode != null) {
            updateHeight(currentNode);
            // left-left and left-right
            if (height(currentNode.getLeftChild()) - height(currentNode.getRightChild()) == 2) {
                // left-left
                if(height(currentNode.getLeftChild().getLeftChild()) > height(currentNode.getLeftChild().getRightChild())) {
                    rightRotate(currentNode);
                }
                else if(height(currentNode.getLeftChild().getRightChild()) > height(currentNode.getLeftChild().getLeftChild())) {
                    leftRotate(currentNode.getLeftChild());
                    rightRotate(currentNode);
                }
            }

            // right-right and right-left
            else if (height(currentNode.getRightChild()) - height(currentNode.getLeftChild()) == 2) {
                // right-right
                if(height(currentNode.getRightChild().getRightChild()) > height(currentNode.getRightChild().getLeftChild())) {
                    leftRotate(currentNode);
                }
                // right-left
                else if (height(currentNode.getRightChild().getLeftChild()) > height(currentNode.getLeftChild().getRightChild())){
                    rightRotate(currentNode.getRightChild());
                    leftRotate(currentNode);
                }
            }

            currentNode = currentNode.getParent();


        }


    }

    public void leftRotate(Node node) { // reassigns left, right, parent pointers and updates heights
        Node y = node.getRightChild(); // get reference to right child
        node.setRightChild(y.getLeftChild()); // update node's right child

        if(y.getLeftChild() != null) {  // if y's left child isn't null, then set its parent pointer to x
            y.getLeftChild().setParent(node);
        }
        y.setParent(node.getParent()); // set y's parent to node's parent
        if (node.getParent() == null) { // if node was root, then update mRoot to be y
            mRoot = y;
        }
        else if (node == node.getParent().getLeftChild()) { // if node was a left child, update node's parent left child pointer to point
            // to y
            node.getParent().setLeftChild(y);
        }
        else { // similar to above above, for right child
            node.getParent().setRightChild(y);
        }
        y.setLeftChild(node); // set node to y's left child
        node.setParent(y); // set node's parent to y

    }

    public void rightRotate(Node node) {
        Node x = node.getLeftChild(); // get reference to left child
        node.setLeftChild(x.getRightChild()); // update node's right child

        if(x.getRightChild() != null) {  // if x's right child isn't null, then set its parent pointer to x
            x.getRightChild().setParent(node);
        }
        x.setParent(node.getParent()); // set x's parent to node's parent
        if (node.getParent() == null) { // if node was root, then update mRoot to be x
            mRoot = x;
        }
        else if (node == node.getParent().getLeftChild()) { // if node was a left child, update node's parent left child pointer to point
            // to x
            node.getParent().setLeftChild(x);
        }
        else { // similar to above above, for right child
            node.getParent().setRightChild(x);
        }
        x.setRightChild(node); // set node to x's left child
        node.setParent(x); // set node's parent to x

    }


    public void transplant(Node deleted, Node replacement) {
        // this function replaces deleted by replacement; it ONLY updates the parents pointer to replacement and replacement's
        // parent pointer

        // "cuts off the subtree rooted at deleted, and replaces it with node replacement"

    // updating replacement's left and right child pointers will be
        // the responsibility of the caller

        if (deleted.getParent() == null) { // if transplanting the root
            mRoot = replacement;
        }
        else if (deleted == deleted.getParent().getLeftChild()) { // if it's a left child
            deleted.getParent().setLeftChild(replacement);
        }
        else { // it's a right child
            deleted.getParent().setRightChild(replacement);
        }

        if (replacement != null) {
            replacement.setParent(deleted.getParent());
        }

    }

    public void delete(Assignment assignment) { // standard BST delete
        Node toBeDeleted = search(mRoot, assignment);
        if (toBeDeleted == null) {
            return;
        }
        mSize--;

        // has no left child (or no child at all)
        if (toBeDeleted.getLeftChild() == null) {
            transplant(toBeDeleted, toBeDeleted.getRightChild());
            // update height
        }
        // has only a left child
        else if (toBeDeleted.getRightChild() == null) {
            transplant(toBeDeleted, toBeDeleted.getLeftChild());
            // update height
        }
        // has two children
        else {
            Node successor = subtreeMinimum(toBeDeleted.getRightChild());
            if (successor.getParent() != toBeDeleted) { // if the successor is not a direct child
                transplant(successor, successor.getRightChild()); // the successor does not have a left child, thus easy replace by right
                                                                        // replace the successor by its right child
                successor.setRightChild(toBeDeleted.getRightChild()); // the successor is moved up to take the place of toBeDeleted
                successor.getRightChild().setParent(successor);
            }
            transplant(toBeDeleted, successor);
            successor.setLeftChild(toBeDeleted.getLeftChild());
            successor.getLeftChild().setParent(successor);
            // update heights
        }


    }

    public void AVLDelete(Assignment assignment) { // "lazy deletion", much simpler and sufficient in practice
        // (according to cse 332 from university of washington
        // standard BST delete
        Node toBeDeleted = search(mRoot, assignment);

        // has no left child (or no child at all)
        if (toBeDeleted.getLeftChild() == null) {
            transplant(toBeDeleted, toBeDeleted.getRightChild());
            // update height
        }
        // has only a left child
        else if (toBeDeleted.getRightChild() == null) {
            transplant(toBeDeleted, toBeDeleted.getLeftChild());
            // update height
        }
        // has two children
        else {
            Node successor = subtreeMinimum(toBeDeleted.getRightChild());
            if (successor.getParent() != toBeDeleted) { // if the successor is not a direct child
                transplant(successor, successor.getRightChild()); // the successor does not have a left child, thus easy replace by right
                // replace the successor by its right child
                successor.setRightChild(toBeDeleted.getRightChild()); // the successor is moved up to take the place of toBeDeleted
                successor.getRightChild().setParent(successor);
            }
            transplant(toBeDeleted, successor);
            successor.setLeftChild(toBeDeleted.getLeftChild());
            successor.getLeftChild().setParent(successor);
            // update heights
        }


        // rotations?

    }

    public int height(Node node) {
        if (node == null) {
            return -1;
        }
        else{
            return node.getHeight();
        }
    }

    public void updateHeight(Node node) {
        int longestPath;
        if (height(node.getLeftChild()) > height(node.getRightChild())) {
            longestPath = height(node.getLeftChild());
        }
        else {
            longestPath = height(node.getRightChild());
        }
        node.setHeight(longestPath+1);
    }


}
