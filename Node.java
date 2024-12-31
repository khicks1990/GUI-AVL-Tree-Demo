/**
   Node class.
*/
public class Node
{
    int value;
    Node left, right;

    // Constructor for leaf nodes.
    Node(int val)
    {
        value = val;
        left = null;
        right = null;
    }

    // Constructor for non-leaf nodes.
    Node(int val, Node leftChild, Node rightChild)
    {
        value = val;
        left = leftChild;
        right = rightChild;
    }
}