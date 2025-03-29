package edu.grinnell.csc207.trees;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class BinarySearchTreeTests {

    private BinarySearchTree<Integer> makeSampleTree() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>();
        tree.insert(5);
        tree.insert(0);
        tree.insert(7);
        tree.insert(11);
        tree.insert(6);
        return tree;
    }
    
    private BinarySearchTree<Integer> makeEmptyTree() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>();
        return tree;
    }
    
    private BinarySearchTree<Integer> makeOneElementTree() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>();
        tree.insert(1);
        return tree;
    }
    
    private BinarySearchTree<String> makeStringTree() {
        BinarySearchTree<String> tree = new BinarySearchTree<String>();
        tree.insert("coven");
        tree.insert("");
        tree.insert("section");
        tree.insert("superfluous");
        tree.insert("offset");
        return tree;
    }

    @Test
    public void emptyTreeTest() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>();
        assertEquals(0, tree.size());
    }

    @Test
    public void basicSizeInsertTest() {
        BinarySearchTree<Integer> tree = makeSampleTree();
        assertEquals(5, tree.size());
    }

    @Test
    public void basicToListInorderTest() {
        // N.B., need to upcast the Integer[] array to avoid a ClassCastException that
        // arises from downcasting the result of toArray to Integer[].
        assertArrayEquals((Object[]) new Integer[] {0, 5, 6, 7, 11},
                          makeSampleTree().toListInorder().toArray());
    }
    
    @Test
    public void emptyToListInorderTest() {
        assertArrayEquals((Object[]) new Integer[] { },
                          makeEmptyTree().toListInorder().toArray());
    }
    
    @Test
    public void oneElementToListInorderTest() {
        assertArrayEquals((Object[]) new Integer[] {1},
                          makeOneElementTree().toListInorder().toArray());
    }
    
    @Test
    public void stringToListInorderTest() {
        assertArrayEquals((Object[]) new String[] {"", "coven", "offset", "section", "superfluous"},
                          makeStringTree().toListInorder().toArray());
    }

    @Test
    public void basicToListPreorderTest() {
        assertArrayEquals((Object[]) new Integer[] {5, 0, 7, 6, 11},
                          makeSampleTree().toListPreorder().toArray());
    }
    
    @Test
    public void emptyToListPreorderTest() {
        assertArrayEquals((Object[]) new Integer[] { },
                          makeEmptyTree().toListPreorder().toArray());
    }
    
    @Test
    public void oneElementToListPreorderTest() {
        assertArrayEquals((Object[]) new Integer[] {1},
                          makeOneElementTree().toListPreorder().toArray());
    }
    
    @Test
    public void stringToListPreorderTest() {
        assertArrayEquals((Object[]) new String[] {"coven", "", "section", "offset", "superfluous"},
                          makeStringTree().toListPreorder().toArray());
    }

    @Test
    public void basicToListPostorderTest() {
        assertArrayEquals((Object[]) new Integer[] {0, 6, 11, 7, 5},
                          makeSampleTree().toListPostorder().toArray());
    }
    
    @Test
    public void emptyToListPostorderTest() {
        assertArrayEquals((Object[]) new Integer[] { },
                          makeEmptyTree().toListPostorder().toArray());
    }
    
    @Test
    public void oneElementToListPostorderTest() {
        assertArrayEquals((Object[]) new Integer[] {1},
                          makeOneElementTree().toListPostorder().toArray());
    }
    
    @Test
    public void stringToListPostorderTest() {
        assertArrayEquals((Object[]) new String[] {"", "offset", "superfluous", "section", "coven"},
                          makeStringTree().toListPostorder().toArray());
    }

    @Test
    public void basicContainsTest() {
        BinarySearchTree<Integer> tree = makeSampleTree();
        assertEquals(true, tree.contains(5));
        assertEquals(false, tree.contains(8));
    }
    
    @Test
    public void emptyContainsTest() {
        BinarySearchTree<Integer> tree = makeEmptyTree();
        assertEquals(false, tree.contains(10));
        assertEquals(false, tree.contains(-12));
    }
    
    @Test
    public void oneElementContainsTest() {
        BinarySearchTree<Integer> tree = makeOneElementTree();
        assertEquals(true, tree.contains(1));
        assertEquals(false, tree.contains(7));
    }
    
    @Test
    public void stringContainsTest() {
        BinarySearchTree<String> tree = makeStringTree();
        assertEquals(true, tree.contains("offset"));
        assertEquals(false, tree.contains("cake"));
    }

    @Test
    public void basicToStringPreorderTest() {
        assertEquals("[5, 0, 7, 6, 11]", makeSampleTree().toStringPreorder());
    }
    
    @Test
    public void emptyToStringPreorderTest() {
        assertEquals("[]", makeEmptyTree().toStringPreorder());
    }
    
    @Test
    public void oneElementToStringPreorderTest() {
        assertEquals("[1]", makeOneElementTree().toStringPreorder());
    }
    
    @Test
    public void stringToStringPreorderTest() {
        assertEquals("[coven, , section, offset, superfluous]", makeStringTree().toStringPreorder());
    }

    @Test
    public void basicDeleteTest() {
        BinarySearchTree<Integer> tree = makeSampleTree();
        assertEquals(5, tree.size());
        assertTrue(tree.contains(6));
        tree.delete(6);
        assertEquals(4, tree.size());
        assertFalse(tree.contains(6));
    }
    
    @Test
    public void noChildrenDeleteTest() {
        BinarySearchTree<Integer> tree = makeSampleTree();
        assertEquals(5, tree.size());
        assertTrue(tree.contains(6));
        tree.delete(6);
        assertEquals(4, tree.size());
        assertFalse(tree.contains(6));
        assertEquals("[5, 0, 7, 11]", tree.toStringPreorder());
    }
    
    @Test
    public void oneChildDeleteTest() {
        BinarySearchTree<Integer> tree = makeSampleTree();
        tree.insert(14);
        assertEquals(6, tree.size());
        assertTrue(tree.contains(11));
        tree.delete(11);
        assertEquals(5, tree.size());
        assertFalse(tree.contains(11));
        assertEquals("[5, 0, 7, 6, 14]", tree.toStringPreorder());
    }
    
    @Test
    public void twoChildrenDeleteTest() {
        BinarySearchTree<Integer> tree = makeSampleTree();
        assertEquals(5, tree.size());
        assertTrue(tree.contains(5));
        tree.delete(5);
        assertEquals(4, tree.size());
        assertFalse(tree.contains(5));
        assertEquals("[0, 7, 6, 11]", tree.toStringPreorder());
    }
    
    @Test
    public void emptyDeleteTest() {
        BinarySearchTree<Integer> tree = makeEmptyTree();
        assertEquals(0, tree.size());
        assertFalse(tree.contains(6));
        assertThrows(IllegalArgumentException.class, () -> {
            tree.delete(6);
        });
        assertEquals(0, tree.size());
        assertFalse(tree.contains(6));
    }
    
    @Test
    public void oneElementDeleteTest() {
        BinarySearchTree<Integer> tree = makeOneElementTree();
        assertEquals(1, tree.size());
        assertTrue(tree.contains(1));
        tree.delete(1);
        assertEquals(0, tree.size());
        assertFalse(tree.contains(0));
    }
    
    @Test
    public void stringDeleteTest() {
        BinarySearchTree<String> tree = makeStringTree();
        assertEquals(5, tree.size());
        assertTrue(tree.contains("offset"));
        tree.delete("offset");
        assertEquals(4, tree.size());
        assertFalse(tree.contains("offset"));
    }
}
