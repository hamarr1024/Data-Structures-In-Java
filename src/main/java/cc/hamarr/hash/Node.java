package cc.hamarr.hash;

public class Node<K, V> implements Map.Entry<K, V> {
    int hash;
    K key;
    V value;
    Node<K, V> next;

    public Node(int hash, K key, V value, Node<K,V> next) {
        this.hash = hash;
        this.key = key;
        this.value = value;
        this.next = next;
    }
    @Override
    public K getKey() {
        return this.key;
    }

    @Override
    public V getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return String.format("[%s=%s, hash=%d]", key, value, hash);
    }
}
