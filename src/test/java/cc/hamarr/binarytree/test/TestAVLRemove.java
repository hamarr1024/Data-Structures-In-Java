package cc.hamarr.binarytree.test;

import cc.hamarr.utils.BinaryTrees;
import cc.hamarr.binarytree.AVLTree;

import java.util.Scanner;

public class TestAVLRemove {

    public static void main(String[] args) {
        int[] numbers = {4, 7, 5, 9, 22, 31, 97, 88};
        AVLTree<Integer> tree = new AVLTree<>();
        for (int i=0; i<numbers.length; i++) {
            tree.add(numbers[i]);
        }
        System.out.println("===========AVL初始化===========");
        BinaryTrees.println(BinaryTrees.wrap(tree));

        String cmd;
        Scanner sc =new Scanner(System.in);
        while (!tree.isEmpty()) {
            System.out.print("输入要删除的数字: ");
            cmd = sc.nextLine();
            if ("quit".equals(cmd)) {
                break;
            }
            tree.remove(Integer.parseInt(cmd));
            BinaryTrees.println(BinaryTrees.wrap(tree));
        }
    }
}
