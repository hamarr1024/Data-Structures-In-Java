package cc.hamarr.hash;

public class HashMap<K, V> implements Map<K, V> {

    //============= static utilities ===============

    // 找到>=capacity最近的2的n次方
    // int的低30位全部变成1，然后+1， 也就是最大是2^30 -1
    private static int tableSizeFor(int capacity) {
        int n = capacity - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;

        if (n < 0) return 1;
        if (n > MAXIMUM_CAPACITY) return MAXIMUM_CAPACITY;
        return n + 1;
    }

    private static int hash(Object key) {
        // 1. null, 返回0
        if (key == null) {
            return 0;
        }

        // 2. Object.hashCode, 高16位^低16位, 不能丢失符号信息，所以无符号右移，前面补0
        int h = key.hashCode();
        return h ^ h >>> 16;
    }
    //==============================================

    // 初始化容量16
    private static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;
    // 最大容量设计为2^30, 因为再扩容就是2^31超过int最大值了
    private static final int MAXIMUM_CAPACITY = 1 << 30;

    private static final int TREEIFY_THRESHOLD = 8;


    private Node<K, V>[] table;

    // 元素的个数
    private int size;

    // 扩容的阈值 = capacity * loadFactor
    private int threshold;

    // 负载因子
    private final float loadFactor;


    public HashMap(int capacity, float loadFactor) {
        this.loadFactor = loadFactor;
        // 创建的时候threshold不是通过loadFactor * capacity计算的，
        // 因为每次扩容都要重新计算threshold, 相当于一个迭代的过程, 迭代需要一个初始值，而这个初始值只能是用户给的
        this.threshold = tableSizeFor(capacity);
    }

    public HashMap(int capacity) {
        this(capacity, DEFAULT_LOAD_FACTOR);
    }

    public HashMap() {
        this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
    }


    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public boolean containsKey(K key) {
        return false;
    }

    @Override
    public V get(K key) {
        return null;
    }

    @Override
    public V put(K key, V val) {
        // 1. 计算哈希值
        int hash = hash(key);
        // 2. 真正的添加操作
        return putVal(hash, key, val);
    }


    @Override
    public V remove(K key) {
        return null;
    }


    //=========== private methods =============
    private V putVal(int hash, K key, V val) {
        // 临时变量，这是个好习惯，虽然HashMap不是线程安全的，但是至少不会因为其它线程的操作导致频繁刷新缓存
        Node<K, V>[] tab;
        int n;
        int index;
        Node<K, V> p;
        // 1. 判断桶数组是否为空，如果为空，先扩容
        if ((tab = table) == null || (n = table.length) == 0) {
            n = (tab = resize()).length;
        }

        // 2. 一般情况
        // 2.1 计算索引下标
        // 因为数组长度是2^n, 因此取模运算可以这样做
        index = hash & (n - 1);
        Node<K, V> e; // 记录需要覆盖的值
        K k;
        p = tab[index];
        // 2.2 如果目标元素为空，直接插入
        if (p == null) {
            tab[index] = new Node<>(hash, key, val, null);
        } else {
            // 2.3 目标元素不为空, 走到这里
            // 2.3.1 如果key和目标元素相等，覆盖, return
            // 判断2个key相等, 首先hash值必须相等，然后两个对象== (包含都是空的情况) 或者equals是true
            if ((p.hash == hash) &&
                    ((k = p.key) == key || (key != null && key.equals(k)))) {
                e = p; // 找到需要覆盖的值
            } else if (p instanceof TreeNode) {
                // TODO: 处理e是红黑树节点的情况
                e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, val);
            } else {
                // 2.3.2 否则遍历链表，如果找到相等的就覆盖， 否则就插到尾部
                // 当退出循环发现q为空, 说明需要插入到尾部,
                for (int binCount = 0; ; binCount++) {
                    // 无限循环，同时统计链表数量
                    // 退出循环情况1: 遍历到链表尾部
                    if ((e = p.next) == null) { // 走到链表尾部了，需要插入节点
                        p.next = new Node<>(hash, key, val, null);
                        // 超过树化阈值需要树化
                        if (binCount >= TREEIFY_THRESHOLD - 1) {
                            treeifyBin(tab, hash);
                        }
                        break;
                    }

                    // 退出循环情况2： 找到覆盖的值
                    if (e.hash == hash &&
                            ((k = e.key) == key || (key != null && key.equals(k)))) {
                        break;
                    }
                    //
                    p = e;
                }
            }

            // 如果找到覆盖的值，进行覆盖
            if (e != null) {
                V oldVal = e.value;
                return oldVal;
            }
        }


        // 走到这里说明一定是插入了新节点
        size++;
        if (size >= threshold) {
            resize();
        }
        return null;
    }


    // 扩容, 返回扩容后的table
    private Node<K, V>[] resize() {
        Node<K, V>[] oldTable = table;
        int oldCapacity = oldTable == null ? 0 : oldTable.length;
        int oldThreshold = threshold;
        int newCapacity, newThreshold = 0;

        // 特殊情况先判断
        if (oldCapacity == 0) { // 第一次扩容, 目标容量设置为threshold
            if (oldThreshold > 0) {
                newCapacity = oldThreshold;
            } else { // 初始化容量为0，意味着使用默认值
                newCapacity = DEFAULT_INITIAL_CAPACITY;
                newThreshold = (int) (DEFAULT_INITIAL_CAPACITY * DEFAULT_LOAD_FACTOR);
            }
        } else { // 一般的扩容情况
            // 1. 先判断容量是否达到最大值
            if (oldCapacity >= MAXIMUM_CAPACITY) { // 不允许扩容
                threshold = Integer.MAX_VALUE;
                return oldTable;
            } else {
                // 2. 一般情况
                // 扩容为原来的两倍
                newCapacity = oldCapacity << 1;
                // 容量未达到最大值，并且旧容量>=16, threshold才变为原来的两倍
                // 如果初始化容量是1，第一次扩容后threshold为0，到这里扩容后容量为2，threshold乘以2后为0，但其实应该是1
                // 如果初始容量是2, 第1次扩容后threshold为1, 到这里扩容后容量为4，threshold乘以2后为2，但其实应该是3
                // 如果初始容量是4，第1次扩容后threshold为3，到这里扩容后容量是8，threshold乘以2后为6，ok
                // 所以这里是避免出现初始化容量<=2的情况, 不能简单乘以2
                if (newCapacity < MAXIMUM_CAPACITY && oldCapacity >= DEFAULT_INITIAL_CAPACITY) {
                    newThreshold = oldThreshold << 1;
                }
            }
        }

        if (newThreshold == 0) {
            float ft = (float) newCapacity * loadFactor;
            if (newCapacity < MAXIMUM_CAPACITY && ft < (float) MAXIMUM_CAPACITY) {
                newThreshold = (int) ft;
            } else {
                // 当初始化容量非常大, 比如为1<<30，oldCapacity会变成1<<30, 这时候要把newThreshold调成int最大值，不允许扩容了
                newThreshold = Integer.MAX_VALUE;
            }
        }

        // 更新threshold
        threshold = newThreshold;
        @SuppressWarnings("unchecked")
        Node<K, V>[] newTable = (Node<K, V>[]) new Node[newCapacity];
        table = newTable;
        // TODO: 数据迁移, 考虑红黑树的情况
        if (oldTable != null) {
            // 遍历桶
            for (int i = 0; i < oldCapacity; i++) {
                Node<K, V> e = oldTable[i];
                if (e == null) { // 元素为空, 不需要迁移
                    continue;
                }

                // 元素不为空的情况
                // 需要计算新数组的下标，并且得到新链表和旧链表的头结点
                // 还需要2个指针指向高低位链表的尾结点，等迁移完了要断开连接, 避免2个链表出现相连的情况
                Node<K, V> lowHead = null, lowTail = null;
                Node<K, V> highHead = null, highTail = null;
                Node<K, V> next;
                // 牢记, e指向正在处理的节点, next指向下一个要处理的节点, 等e==null, 链表就处理完了
                do {
                    next = e.next;
                    // 处理低位情况
                    if ((e.hash & oldCapacity) == 0) {
                        if (lowTail == null) { // 说明第一次遇到低位节点
                            lowHead = e;
                        } else {
                            lowTail.next = e; // 添加到链表尾部
                        }
                        lowTail = e; // 指向刚处理完的节点
                    } else {
                        if (highTail == null) {
                            highHead = e;
                        } else {
                            highTail.next = e;
                        }
                        highTail = e;
                    }

                    // 处理下一个节点
                    e = next;
                } while (e != null);

                // 如果低位链表存在
                if (lowHead != null) {
                    newTable[i] = lowHead;
                    lowTail.next = null;
                }
                // 计算高位链表的下标, 等于旧下标+oldCapacity
                if (highHead != null) {
                    table[i + oldCapacity] = highHead;
                    highTail.next = null;
                }
            }
        }
        return newTable;
    }

    private void treeifyBin(Node<K, V>[] table, int hash) {

    }
}
