package cc.hamarr.binarytree.test;

import cc.hamarr.utils.BinaryTrees;
import cc.hamarr.binarytree.BinarySearchTree;

import java.util.Scanner;

public class TestBSTRemove {

    public static void main(String[] args) {
        int[] data = new int[]{7, 4, 9, 2, 5, 8, 11, 3, 12, 21, 1};
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        for (int i=0; i<data.length; i++) {
            bst.add(data[i]);
        }
        System.out.println("============== 初始化BST =================");
        BinaryTrees.println(BinaryTrees.wrap(bst));

        Scanner sc = new Scanner(System.in);
        while (!bst.isEmpty()) {
            System.out.print("输入要删除的元素: ");
            int toDel = Integer.parseInt(sc.nextLine());
            bst.remove(toDel);
            System.out.println("============= 删除后 =================");
            BinaryTrees.println(BinaryTrees.wrap(bst));
        }

        System.out.println("FINISH!");
    }
}
