package cc.hammarr.binarytree;

public abstract class BalancedBinarySearchTree<E extends Comparable<E>> extends BinarySearchTree<E>{

    /**
     * |                  |
     *  g                 p
     * /   \             / \
     * p    y   =>      n   g
     * / \                 /  \
     * n pRight      pRight    y
     *
     * @param node
     */
    protected void rotateRight(Node<E> node) {
        // 带入p,g,pRight
        final Node<E> g = node;
        final Node<E> p =  g.left;
        Node<E> pRight =  p.right;

        // 1. 更新左右
        g.left = pRight;
        p.right = g;

        // 2.更新parent
        // 2.1 更新p的parent
        p.parent = g.parent;
        if (g.isLeftChild()) {
            g.parent.left = p;
        } else if(g.isRightChild()) {
            g.parent.right = p;
        } else {
            root = p;
        }
        // 2.2 更新pRight的parent
        if (pRight != null) {
            pRight.parent = g;
        }
        // 2.3 更新g的parent
        g.parent = p;

       afterRotate(g, p);
    }


    protected void rotateLeft(Node<E> node) {
        // 带入p, g, pLeft
        final Node<E> g = node;
        final Node<E> p = g.right;
        final Node<E> pLeft = p.left;
        // 1. 更新左右节点
        g.right = pLeft;
        p.left = g;

        // 2. 更新parent
        // 2.1 让p称为根节点
        p.parent = g.parent;
        if (g.isLeftChild()) {
            g.parent.left = p;
        } else if (g.isRightChild()) {
            g.parent.right = p;
        }
        else {
            root = p;
        }

        // 2.2 让pLeft.parent指向g, 注意pLeft可能为空
        if (pLeft != null) {
            pLeft.parent = g;
        }

        // 2.3 让g.parent指向p
        g.parent = p;

        // 更新高度, 先更新g, 再更新p
        afterRotate(g, p);
    }

    protected void afterRotate(Node<E> g, Node<E> p) {
    }
}
