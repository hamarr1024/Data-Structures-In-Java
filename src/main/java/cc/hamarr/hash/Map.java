package cc.hamarr.hash;

public interface Map<K, V> {
    int size();

    boolean isEmpty();

    // 查询操作
    boolean containsKey(K key);

    V get(K key);

    // 添加
    V put(K key, V val);

    // 删除
    V remove(K key);


    interface Entry<K, V> {
        K getKey();

        V getValue();

    }
}
