package cc.hamarr.binarytree.test;

import org.junit.Test;

import java.util.HashMap;

public class TestHash {

    @Test
    public void test_shift() {
        int i = -5;
        System.out.println(i + ":" + Integer.toBinaryString(i));
        System.out.println(i + ":" + Integer.toBinaryString(i >> 2));
        System.out.println(i + ":" + Integer.toBinaryString(i >>> 3));
    }

    @Test
    public void test_initial_capacity_2() {
        HashMap<String, Integer> map = new HashMap<>(2);
        map.put("a" ,1);
    }

    @Test
    public void test_initial_capacity_not_set() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("a", 1);
    }


    @Test
    public void test_initial_capacity_capcity_16() {
        HashMap<String, Integer> map = new HashMap<>(16);
        map.put("a", 1);
    }

    public static void main(String[] args) {
        HashMap<String, Integer> map = new HashMap<>(16, 0.5f);
        map.put(null, 1);
        System.out.println("ok");
    }
}
