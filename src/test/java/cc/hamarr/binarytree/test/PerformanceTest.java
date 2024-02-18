package cc.hamarr.binarytree.test;

import cc.hammarr.binarytree.AVLTree;
import cc.hammarr.binarytree.BinarySearchTree;
import cc.hammarr.binarytree.RBTree;
import org.junit.Test;

import java.util.*;

public class PerformanceTest {

//    @Test
//    public void inorder_BST() {
//        List<Integer> data = new ArrayList<Integer>();
//
//        long start = System.currentTimeMillis();
//        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
//        for (int i=1;i<=1000_0000; i++) {
//            bst.add(i);
//        }
//        System.out.println("BST#add: " + (System.currentTimeMillis() - start) + " ms");
//        start = System.currentTimeMillis();
//        for (int i=1000_0000; i>= 1; i--) {
//            bst.remove(i);
//        }
//        System.out.println("BST#remove: " + (System.currentTimeMillis() - start) + " ms");
//    }


//
//    static int size = 1000_0000;
//    static List<Integer> data = new ArrayList<>(size);
//    static  {
//        for (int i=1;i<=size; i++) {
//            data.add(i);
//        }
//        Collections.shuffle(data);
//    }
//
//    @Test
//    public void random_BST() {
//        long start = System.currentTimeMillis();
//        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
//        for (int i=0; i< size; i++) {
//            bst.add(data.get(i));
//        }
//
//        System.out.println("BST#add: " + (System.currentTimeMillis() - start) + " ms");
//        start = System.currentTimeMillis();
//        for (int i=0; i<size; i++) {
//            bst.remove(data.get(i));
//        }
//        System.out.println("BST#remove: " + (System.currentTimeMillis() - start) + " ms");
//    }

    @Test
    public void inorder_AVL() {
        long start = System.currentTimeMillis();
        AVLTree<Integer> tree = new AVLTree<>();
        for (int i=1;i<=1000_0000; i++) {
            tree.add(i);
        }
        System.out.println("AVL#add: " + (System.currentTimeMillis() - start) + " ms");
        start = System.currentTimeMillis();
        for (int i=1000_0000; i>= 1; i--) {
            tree.remove(i);
        }
        System.out.println("AVL#remove: " + (System.currentTimeMillis() - start) + " ms");
    }
//
//    @Test
//    public void random_AVL() {
//        long start = System.currentTimeMillis();
//        AVLTree <Integer> tree = new AVLTree<>();
//        for (int i=0; i< size; i++) {
//            tree.add(data.get(i));
//        }
//
//        System.out.println("AVL#add: " + (System.currentTimeMillis() - start) + " ms");
//        start = System.currentTimeMillis();
//        for (int i=0; i<size; i++) {
//            tree.remove(data.get(i));
//        }
//        System.out.println("AVL#remove: " + (System.currentTimeMillis() - start) + " ms");
//    }

    @Test
    public void inorder_RBT() {
        long start = System.currentTimeMillis();
        RBTree<Integer> tree = new RBTree<>();
        for (int i=1;i<=1000_0000; i++) {
            tree.add(i);
        }
        System.out.println("RBT#add: " + (System.currentTimeMillis() - start) + " ms");
        start = System.currentTimeMillis();
        for (int i=1000_000; i>= 1; i--) {
            tree.remove(i);
        }
        System.out.println("RBT#remove: " + (System.currentTimeMillis() - start) + " ms");
    }


//    @Test
//    public void random_RBT() {
//        long start = System.currentTimeMillis();
//        AVLTree <Integer> tree = new AVLTree<>();
//        for (int i=0; i< size; i++) {
//            tree.add(data.get(i));
//        }
//
//        System.out.println("RBT#add: " + (System.currentTimeMillis() - start) + " ms");
//        start = System.currentTimeMillis();
//        for (int i=0; i<size; i++) {
//            tree.remove(data.get(i));
//        }
//        System.out.println("RBT#remove: " + (System.currentTimeMillis() - start) + " ms");
//    }


}
