/**
    This class implements AVLTrees.
 */
public class AVLTree extends BinaryTree
{    
    // Convenience method casts the inherited root
    // from Node to AVLNode.
    private AVLNode getRoot()
    {
        return (AVLNode) root;
    }

    /**
       The getHeight method computes the height of an AVL tree.
       @param tree An AVL tree.
       @return The height of the AVL tree.
     */
    static int getHeight(AVLNode tree)
    {
        if (tree == null)
            return -1;
        else
            return tree.height;        
    }

    /**
       The add method adds a value to this AVL tree.
       @param x The value to add.
       @return true.
     */
    public boolean add(int x)
    {
        root = add((AVLNode) root, x);
        return true;
    }

    /**
       The add method adds a value to an AVL tree.
       @return The root of the augmented AVL tree.
     */
    private AVLNode add(AVLNode bTree, int x)
    {
        if (bTree == null)
        {
            return new AVLNode(x);
        }
        if (x < bTree.value)
        {
            bTree.left = add(bTree.getLeft(), x);
        } else
        {
            bTree.right = add(bTree.getRight(), x);
        }

        // Compute heights of the left and right subtrees
        // and rebalance the tree if needed.
        int leftHeight = getHeight(bTree.getLeft());
        int rightHeight = getHeight(bTree.getRight());
        if (Math.abs(leftHeight - rightHeight) == 2)
        {
            return balance(bTree);
        } else
        {
            bTree.resetHeight();
            return bTree;
        }
    }

    /**
     * Balances the AVL tree rooted at the given node.
     * 
     * @param bTree The root node of the subtree that needs balancing.
     * @return The new root node after the balancing.
     */
    private AVLNode balance(AVLNode bTree) {
        // Calculate the heights of the right and left subtrees
        int rHeight = getHeight(bTree.getRight());
        int lHeight = getHeight(bTree.getLeft());

        if (rHeight > lHeight) {
            // If the right subtree is taller

            AVLNode rightChild = bTree.getRight();
            int rrHeight = getHeight(rightChild.getRight());
            int rlHeight = getHeight(rightChild.getLeft());

            if (rrHeight > rlHeight) {
                // If the right-right subtree is taller, perform a right-right rotation
                return rrBalance(bTree);
            } else {
                // If the right-left subtree is taller, perform a right-left rotation
                return rlBalance(bTree);
            }
        } else {
            // If the left subtree is taller

            AVLNode leftChild = bTree.getLeft();
            int llHeight = getHeight(leftChild.getLeft());
            int lrHeight = getHeight(leftChild.getRight());

            if (llHeight > lrHeight) {
                // If the left-left subtree is taller, perform a left-left rotation
                return llBalance(bTree);
            } else {
                // If the left-right subtree is taller, perform a left-right rotation
                return lrBalance(bTree);
            }
        }
    }

    /**
     * Performs a right-right rotation to balance the AVL tree.
     * 
     * @param bTree The root node of the subtree that needs balancing.
     * @return The new root node after the rotation.
     */
    private AVLNode rrBalance(AVLNode bTree) {
        // Store references to the right child and the left child of the right child
        AVLNode rightChild = bTree.getRight();
        AVLNode rightLeftChild = rightChild.getLeft();

        // Perform the rotation
        rightChild.left = bTree; // Make the right child the new root node
        bTree.right = rightLeftChild; // Move the left child of the right child to the right of the original root

        // Update the heights of the affected nodes
        bTree.resetHeight(); // Recalculate the height of the original root node
        rightChild.resetHeight(); // Recalculate the height of the new root node

        // Return the new root node
        return rightChild;
    }

    /**
     * Performs a right-left rotation to balance the AVL tree.
     * 
     * @param bTree The root node of the subtree that needs balancing.
     * @return The new root node after the rotation.
     */
    private AVLNode rlBalance(AVLNode bTree) {
        // Store references to the nodes involved in the rotation
        AVLNode root = bTree;
        AVLNode rNode = root.getRight();
        AVLNode rlNode = rNode.getLeft();
        AVLNode rlrTree = rlNode.getRight();
        AVLNode rllTree = rlNode.getLeft();

        // Build the restructured tree
        rNode.left = rlrTree; // Make the right-left grandchild the left child of the right child
        root.right= rllTree; // Make the left grandchild the right child of the original root
        rlNode.left= root; // Make the original root the left child of the newly created root
        rlNode.right =rNode; // Make the right child the right child of the newly created root

        // Adjust heights
        rNode.resetHeight(); // Recalculate the height of the right child
        root.resetHeight(); // Recalculate the height of the original root
        rlNode.resetHeight(); // Recalculate the height of the new root

        // Return the new root node
        return rlNode;
    }

    /**
     * Performs a left-left rotation to balance the AVL tree.
     * 
     * @param bTree The root node of the subtree that needs balancing.
     * @return The new root node after the rotation.
     */
    private AVLNode llBalance(AVLNode bTree) {
        // Store references to the left child and the right child of the left child
        AVLNode leftChild = bTree.getLeft();
        AVLNode lrTree = leftChild.getRight();

        // Perform the rotation
        leftChild.right = bTree; // Make the left child the new root node
        bTree.left = lrTree; // Move the right child of the left child to the left of the original root

        // Update the heights of the affected nodes
        bTree.resetHeight(); // Recalculate the height of the original root node
        leftChild.resetHeight(); // Recalculate the height of the new root node

        // Return the new root node
        return leftChild;
    }

    /**
     * Performs a left-right rotation to balance the AVL tree.
     * 
     * @param bTree The root node of the subtree that needs balancing.
     * @return The new root node after the rotation.
     */
    private AVLNode lrBalance(AVLNode bTree) {
        // Store references to the nodes involved in the rotation
        AVLNode root = bTree;
        AVLNode lNode = root.getLeft();
        AVLNode lrNode = lNode.getRight();
        AVLNode lrlTree = lrNode.getLeft();
        AVLNode lrrTree = lrNode.getRight();

        // Build the restructured tree
        lNode.right = lrlTree; // Make the left-right grandchild the right child of the left child
        root.left = lrrTree; // Make the right grandchild the left child of the original root
        lrNode.left = lNode; // Make the left child the left child of the newly created root
        lrNode.right = root; // Make the original root the right child of the newly created root

        // Adjust heights
        lNode.resetHeight(); // Recalculate the height of the left child
        root.resetHeight(); // Recalculate the height of the original root
        lrNode.resetHeight(); // Recalculate the height of the new root

        // Return the new root node
        return lrNode;
    }
}
