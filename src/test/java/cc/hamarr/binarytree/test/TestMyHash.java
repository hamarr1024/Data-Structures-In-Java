package cc.hamarr.binarytree.test;

import cc.hamarr.hash.HashMap;
import org.junit.Test;

public class TestMyHash {

    @Test
    public void test_init() {
        HashMap<String, String> map = new HashMap<>();
        map.put("24", "Idx：2");
        map.put("46", "Idx：2");
        map.put("68", "Idx：2");
        map.put("29", "Idx：7");
        map.put("150", "Idx：12");
        map.put("172", "Idx：12");
        map.put("194", "Idx：12");
        map.put("271", "Idx：12");
        System.out.println("ok");
    }
}
