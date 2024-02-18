package cc.hamarr.binarytree;

import static cc.hamarr.utils.ObjectUtils.assertNonNull;

public class BinarySearchTree<E extends Comparable<E>> implements BinaryTree<E> {
    protected Node<E> root;
    protected int size;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Node<E> root() {
        return root;
    }

    @Override
    public Node<E> find(E element) {
        assertNonNull(element, "element");
        Node<E> p = root;
        while (p != null) {
            int cmp = element.compareTo(p.element);
            if (cmp < 0) p = p.left;
            else if (cmp > 0) p = p.right;
            else return p;
        }
        return null;
    }

    @Override
    public E add(E element) {
        if (root == null) {
            root = createNode(element, null);
            size = 1;
            fixAfterInsertion(root);
            return null;
        }
        Node<E> p = root;
        Node<E> parent;
        int cmp;

        do {
            parent = p;
            cmp = element.compareTo(p.element);
            if (cmp < 0) p = p.left;
            else if (cmp > 0) p = p.right;
            else return p.element(element);
        } while (p != null);

        Node<E> newNode = createNode(element, parent);
        if (cmp < 0) parent.left = newNode;
        else parent.right = newNode;
        size++;
        fixAfterInsertion(newNode);
        return null;
    }

    @Override
    public E remove(E element) {
        Node<E> p = find(element);
        if (p == null) {
            return null;
        }

        E oldValue = p.element;
        deleteNode(p);
        return oldValue;
    }

    protected Node<E> createNode(E element, Node<E> parent) {
        return new Node<>(element, parent);
    }


    private void deleteNode(Node<E> node) {
        size--;
        // 度为2
        if (node.degree() == 2) {
            Node<E> s = successor(node);
            node.element = s.element;
            node = s;
        }

        // node的度一定是0或者1
        Node<E> replacement = node.left != null ? node.left : node.right;
        // 度为1
        if (replacement != null) {
            replacement.parent = node.parent;
            boolean isLeft = false;
            if (node.parent == null) {
                root = replacement;
            } else if (node == node.parent.left) {
                node.parent.left = replacement;
                isLeft = true;
            } else {
                node.parent.right = replacement;
            }

            fixAfterRemoval(node, replacement);
        } else if (node.parent == null) {
            root = null;
        }
        // 度为0, node是叶子节点
        else {
            boolean isLeft = node == node.parent.left;
             if (isLeft) {
                node.parent.left = null;
            }  else {
                node.parent.right = null;
            }
            // 删除的叶子节点，由于父节点已经断开了与node的连接, 所以修复node节点即可
            fixAfterRemoval(node, null);
        }

    }

    private Node<E> predecessor(Node<E> node) {
        if (node == null) return null;
        if (node.left != null) {
            Node<E> p = node.left;
            while (p.right != null) p = p.right;
            return p;
        }

        // 往上找
        Node<E> p = node;
        while (p.parent != null && p == p.parent.left) {
            p = p.parent;
        }

        return p.parent;
    }


    private Node<E> successor(Node<E> node) {
        if (node == null) return null;
        if (node.right != null) {
            Node<E> p = node.right;
            while (p.left != null) p = p.left;
            return p;
        }

        //往上找
        Node<E> p = node;
        while (p.parent != null && p == p.parent.right) {
            p = p.parent;
        }
        return p.parent;
    }


    protected void fixAfterInsertion(Node<E> x) {
        // do nothing for BST
    }

    // 传入删除的节点
    protected void fixAfterRemoval(Node<E> node, Node<E> replacement) {
        // do nothing for BST
    }


    public static class Node<E extends Comparable<E>> implements BinaryTree.Node<E> {
        E element;
        Node<E> left, right, parent;

        Node(E element, Node<E> parent) {
            this.element = element;
            this.parent = parent;
        }

        @Override
        public E element() {
            return element;
        }

        @Override
        public E element(E element) {
            E oldValue = this.element;
            this.element = element;
            return oldValue;
        }

        @Override
        public Node<E> left() {
            return left;
        }


        @Override
        public Node<E> right() {
            return right;
        }


        @Override
        public Node<E> parent() {
            return parent;
        }

        public boolean isLeftChild() {
            if (parent == null) return false;
            return this == parent.left;
        }

        public boolean isRightChild() {
            if (parent == null) return false;
            return this == parent.right;
        }
    }
}
