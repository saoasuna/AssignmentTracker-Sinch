package saoasuna.advancedtodo;

/**
 * Created by Ryan on 12/01/2016.
 */
public class Node {

    private Node mLeftChild; // node pointers
    private Node mRightChild;
    private Node mParent;
    private int mHeight;
    private Assignment mAssignment;

    public Node(Assignment assignment) {
        mParent = null;
        mAssignment = assignment;
        mLeftChild = null;
        mRightChild = null;
    }

    public Node getLeftChild() {
        return mLeftChild;
    }

    public Node getRightChild() {
        return mRightChild;
    }

    public Node getParent() {
        return mParent;
    }


    public Assignment getAssignment() {
        return mAssignment;
    }

    public int getHeight() {
        return mHeight;
    }
    public void setHeight(int height) {
         mHeight = height;
    }

    public void setLeftChild(Node leftChild) {
        mLeftChild = leftChild;
    }

    public void setRightChild(Node rightChild) {
        mRightChild = rightChild;
    }

    public void setParent(Node parent) {
        mParent = parent;
    }
}
