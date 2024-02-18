package cc.hamarr.binarytree.test;

import cc.hamarr.utils.BinaryTrees;
import cc.hamarr.binarytree.RBTree;

import java.util.Scanner;

public class TestRBTreeRemove {

    public static void main(String[] args) {
        int[] data = new int[]{1,2,3,4,5,6,7,8};
        RBTree<Integer> tree = new RBTree<>();
        for (int i=0; i<data.length; i++) {
            tree.add(data[i]);
        }

        BinaryTrees.println(BinaryTrees.wrap(tree));

        Scanner sc = new Scanner(System.in);
        String cmd;
        do {
            System.out.print("删除一个数字: ");
            cmd = sc.nextLine();
            if ("quit".equals(cmd)) break;
            tree.remove(Integer.parseInt(cmd));
            BinaryTrees.println(BinaryTrees.wrap(tree));
        } while (true);

        System.out.println("FINISH!");
    }
}
