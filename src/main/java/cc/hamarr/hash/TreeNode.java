package cc.hamarr.hash;


public class TreeNode<K, V> extends LinkedNode<K, V> {
    TreeNode<K, V> parent;
    TreeNode<K, V> left, right;
    boolean red;

    public TreeNode(int hash, K key, V value, Node<K, V> next) {
        super(hash, key, value, next);
    }

    // TODO: 红黑树插入
    TreeNode<K,V> putTreeVal(HashMap<K,V> map, Node<K,V>[] tab,
                             int h, K k, V v) {
        return null;
    }
}
