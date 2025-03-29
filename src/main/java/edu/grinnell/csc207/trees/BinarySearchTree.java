package edu.grinnell.csc207.trees;

import java.util.List;
import java.util.ArrayList;

/**
 * A binary tree that satisifies the binary search tree invariant.
 */
public class BinarySearchTree<T extends Comparable<? super T>> {

    ///// From the reading

    /**
     * A node of the binary search tree.
     */
    private static class Node<T> {
        T value;
        Node<T> left;
        Node<T> right;

        /**
         * @param value the value of the node
         * @param left the left child of the node
         * @param right the right child of the node
         */
        Node(T value, Node<T> left, Node<T> right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }

        /**
         * @param value the value of the node
         */
        Node(T value) {
            this(value, null, null);
        }
    }

    private Node<T> root;

    /**
     * Constructs a new empty binary search tree.
     */
    public BinarySearchTree() { }

    private int sizeH(Node<T> node) {
        if (node == null) {
            return 0;
        } else {
            return 1 + sizeH(node.left) + sizeH(node.right);
        }
    }

    /**
     * @return the number of elements in the tree
     */
    public int size() {
        return sizeH(root);
    }

    /**
     * @param value the value to be added
     * @param root the node in the tree we are currently in
     * @return the node now containing the given value
     */
    private Node<T> insertH(T value, Node<T> root) {
        if (root == null) {
            return new Node<T>(value);
        } else {
            if (value.compareTo(root.value) < 0) {
                root.left = insertH(value, root.left);
            } else {
                root.right = insertH(value, root.right);
            }
            return root;
        }
    }

    /**
     * @param value the value to add to the tree
     */
    public void insert(T value) {
        root = insertH(value, root);
    }

    ///// Part 1: Traversals

    /**
     * @param node the node we are currently in
     * @param list the list of the values in the tree
     */
    private void toListInorderH(Node<T> node, List<T> list) {
        if (node == null) {
            return;
        }
        toListInorderH(node.left, list);
        list.add(node.value);
        toListInorderH(node.right, list);
    }
    
    /**
     * @return the elements of this tree collected via an in-order traversal
     */
    public List<T> toListInorder() {
        List<T> list = new ArrayList<T>();
        toListInorderH(root, list);
        return list;
    }

    /**
     * @param node the node we are currently in
     * @param list the list of the values in the tree
     */
    private void toListPreorderH(Node<T> node, List<T> list) {
        if (node == null) {
            return;
        }
        list.add(node.value);
        toListPreorderH(node.left, list);
        toListPreorderH(node.right, list);
    }
    
    /**
     * @return the elements of this tree collected via a pre-order traversal
     */
    public List<T> toListPreorder() {
        List<T> list = new ArrayList<T>();
        toListPreorderH(root, list);
        return list;
    }

    /**
     * @param node the node we are currently in
     * @param list the list of the values in the tree
     */
    private void toListPostorderH(Node<T> node, List<T> list) {
        if (node == null) {
            return;
        }
        toListPostorderH(node.left, list);
        toListPostorderH(node.right, list);
        list.add(node.value);
    }
    
    /**
     * @return the elements of this tree collected via a post-order traversal
     */
    public List<T> toListPostorder() {
        List<T> list = new ArrayList<T>();
        toListPostorderH(root, list);
        return list;
    }

    ///// Part 2: Contains
    
    /**
     * @param node the node we are currently in
     * @param value the value we are looking for
     * @return whether or not the value was found in the tree
     */
    private boolean containsH(Node<T> node, T value) {
        if (node == null) {
            return false;
        } else if (node.value.compareTo(value) > 0) {
            return containsH(node.left, value);
        } else if (node.value.compareTo(value) < 0) {
            return containsH(node.right, value);
        } else {
            return true;
        }
    }

    /**
     * @param value the value to search for
     * @return true if and only if the tree contains <code>value</code>
     */
    public boolean contains(T value) {
        return containsH(root, value);
    }

    ///// Part 3: Pretty Printing

    private String toStringPreorderH(Node<T> node, String str) {
        if (node == null) {
            return "";
        }
        return ", " + node.value + toStringPreorderH(node.left, str) + toStringPreorderH(node.right, str);
    }

    /**
     * @return a string representation of the tree obtained via an pre-order traversal in the
     *         form: "[v0, v1, ..., vn]"
     */
    public String toStringPreorder() {
        if (root == null) {
            return "[]";
        }
        String str = "[";
        str = str + root.value + toStringPreorderH(root.left, str) + toStringPreorderH(root.right, str) + "]";
        return str;
    }

    ///// Part 4: Deletion
  
    /*
     * The three cases of deletion are:
     * 1. The node containing the value has no children.
     * 2. The node containing the value has one child.
     * 3. The node containing the value has two children.
     */

    private void rightmost(Node<T> node, Node<T> save) {
        if (node.right == null) {
            node.right = save;
        } else {
            rightmost(node.right, save);
        }
    }

    private Node deleteH(Node<T> node, T value) {
        if (node == null) {
            return null;
        } else if (node.value.compareTo(value) > 0) {
            node.left = deleteH(node.left, value);
        } else if (node.value.compareTo(value) < 0) {
            node.right = deleteH(node.right, value);
        } else {
            if (node.left == null && node.right == null) {
                node = null;
            } else if (node.left == null) {
                node = node.right;
            } else if (node.right == null) {
                node = node.left;
            } else {
                Node<T> save = node.right;
                node = node.left;
                rightmost(node, save);
            }
        }
        return node;
    }
    
    /**
     * Modifies the tree by deleting the first occurrence of <code>value</code> found
     * in the tree.
     *
     * @param value the value to delete
     */
    public void delete(T value) {
        if (contains(value)) {
            root = deleteH(root, value);
        } else {
            throw new IllegalArgumentException();
        }
    }
}
