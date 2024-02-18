package cc.hamarr.hash;

// 双链表
public class LinkedNode<K, V> extends Node<K, V> {
    // 前驱节点
    LinkedNode<K, V> prev;
    public LinkedNode(int hash, K key, V value, Node<K, V> next) {
        super(hash, key, value, next);
    }
}
