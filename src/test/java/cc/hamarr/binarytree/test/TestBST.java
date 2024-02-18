package cc.hamarr.binarytree.test;

import cc.hamarr.utils.BinaryTrees;
import cc.hammarr.binarytree.BinarySearchTree;
import org.junit.Test;

import java.util.Scanner;

public class TestBST {


    @Test
    public void test_bst_add() {
        int[] data = new int[]{7, 4, 9, 2, 5, 8, 11, 3, 12, 21, 1};
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        for (int i=0; i<data.length; i++) {
            bst.add(data[i]);
        }

        BinaryTrees.println(BinaryTrees.wrap(bst));
    }
}
