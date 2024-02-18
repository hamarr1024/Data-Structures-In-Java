package cc.hamarr.binarytree.test;

import cc.hamarr.utils.BinaryTrees;
import cc.hamarr.binarytree.AVLTree;
import org.junit.Test;

import java.util.Scanner;

public class TestAVLAdd {

    public static void main(String[] args) {
        AVLTree<Integer> tree = new AVLTree<>();
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
    @Test
    public void test() {
        System.out.println(Math.ceil(3.0/2));
    }
}
