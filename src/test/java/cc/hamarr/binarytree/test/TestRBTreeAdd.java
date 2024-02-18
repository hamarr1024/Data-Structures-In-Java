package cc.hamarr.binarytree.test;

import cc.hamarr.utils.BinaryTrees;
import cc.hammarr.binarytree.AVLTree;
import cc.hammarr.binarytree.RBTree;

import java.util.Scanner;

public class TestRBTreeAdd {

    public static void main(String[] args) {
        RBTree<Integer> tree = new RBTree<>();
        Scanner sc = new Scanner(System.in);
        String cmd;
        do {
            System.out.print("插入一个数字: ");
            cmd = sc.nextLine();
            if ("quit".equals(cmd)) break;
            tree.add(Integer.parseInt(cmd));
            BinaryTrees.println(BinaryTrees.wrap(tree));
        } while (true);

        System.out.println("FINISH!");
    }
}
