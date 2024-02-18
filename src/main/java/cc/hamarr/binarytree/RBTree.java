package cc.hamarr.binarytree;

import cc.hamarr.utils.BinaryTrees;

public class RBTree<E extends Comparable<E>> extends BalancedBinarySearchTree<E> {
    static final boolean RED = true;
    static final boolean BLACK = false;

    @Override
    protected void fixAfterInsertion(Node<E> node) {
        RBNode<E> parent = (RBNode<E>) node.parent;
        // 添加的是根节点
        if (parent == null) {
            color(node, BLACK);
            return;
        }
        // 父节点是黑色
        if (isBlack(parent)) return;

        // 进入到这里parent一定是红色
        // uncle节点
        RBNode<E> uncle = (RBNode<E>) parent.sibling();
        // 祖父节点
        RBNode<E> grand = (RBNode<E>) parent.parent;
        // 叔父节点是红色，上溢，
        // 1. parent, uncle染成黑色
        // 2. 祖父节点向上合并
        if (isRed(uncle)) {
            color(parent, BLACK);
            color(uncle, BLACK);
            color(grand, RED);
            fixAfterInsertion(grand);
            return;
        }

        // 叔父节点是黑色，需要旋转
        // 判断LL, RR, LR, RL
        if (parent.isLeftChild()) {
            if (node.isLeftChild()) {
                // LL
                // parent 染成黑色，grand染成红色, 右旋grand
                color(parent, BLACK);
                color(grand, RED);
                rotateRight(grand);
            } else {
                // LR
                // 自己染成黑色，grand染成红色, 左旋parent，右旋grand
                color(node, BLACK);
                color(grand, RED);
                rotateLeft(parent);
                rotateRight(grand);
            }
        } else {
            if (node.isRightChild()) {
                // RR
                // parent染成黑色，grand染成红色，左旋
                color(parent, BLACK);
                color(grand, RED);
                rotateLeft(grand);
            } else {
                // RL
                // 自己染成黑色，grand染成红色，右旋parent， 左旋grand
                color(node, BLACK);
                color(grand, RED);
                rotateRight(parent);
                rotateLeft(grand);
            }
        }
    }


    /**
     * node-待删除节点
     * replacement-取代node的节点
     *
     * @param node
     * @param replacement
     */
    @Override
    protected void fixAfterRemoval(Node<E> node, Node<E> replacement) {
        // 删除红色不用处理
        if (isRed(node)) return;
        // 染色即可
        if (isRed(replacement)) {
            color(replacement, BLACK);
            return;
        }
        Node<E> parent = node.parent;
        // 如果删除的是根节点
        if (parent == null) return;

        // 删除黑色节点
        boolean isLeft = parent.left == null || node == parent.left;
        if (isLeft) {
            Node<E> sibling = node.parent.right;
            // 兄弟为红
            if (colorOf(sibling) == RED) {
                // 1. 要把左侄子拿过来当兄弟，所以要左旋p
                // 2. 将父亲染红，兄弟染黑，
                color(parent, RED);
                color(sibling, BLACK);
                rotateLeft(parent);
                sibling = parent.right;
            }
            // 兄弟为空, 父节点下来并染黑,递归修复父节点
            if ((colorOf(sibling.left) == BLACK && colorOf(sibling.right) == BLACK)) {
                boolean isParentBlack = colorOf(parent) == BLACK;
                color(parent, BLACK);
                color(sibling, RED);
                if (isParentBlack) {
                    fixAfterRemoval(parent, null);
                }
            }
            // 兄弟为黑, 且至少有一个红孩子
            else  {
                // 我们期望的是RR的情况，这样左旋即可
                // 符合RR的右兄弟的左右孩子都是红，或者兄弟的右孩子为红，左孩子为空
                // 所以遇到兄弟的左孩子为红，右孩子为空的情况，需要先转成RR的状态
                if (colorOf(sibling.right) == BLACK) {
                    rotateRight(sibling);
                    // 注意更新兄弟，因为侄子变成了兄弟
                    sibling = parent.right;
                }

                color(sibling, colorOf(parent));
                color(sibling.right, BLACK);
                color(parent, BLACK);
                rotateLeft(parent);
            }


        } else { // 对称操作

            Node<E> sibling = node.parent.left;
            // 兄弟为红
            if (colorOf(sibling) == RED) {
                // 1. 要把右侄子拿过来当兄弟，所以要右旋p
                // 2. 将父亲染红，兄弟染黑
                color(parent, RED);
                color(sibling, BLACK);
                rotateRight(parent);
                sibling = parent.left;
            }

            // 父节点下来并染黑,递归修复父节点
            if (sibling == null) {
                BinaryTrees.println(BinaryTrees.wrap(this));
                System.out.println("???");
            }
            if ((colorOf(sibling.right) == BLACK && colorOf(sibling.left) == BLACK)) {
                boolean isParentBlack = colorOf(parent) == BLACK;
                color(parent, BLACK);
                color(sibling, RED);
                if (isParentBlack) {
                    fixAfterRemoval(parent, null);
                }
            }
            // 兄弟为黑, 且至少有一个红孩子
            else {
                // 兄弟左边是黑，先旋转
                if (colorOf(sibling.left) == BLACK) {
                    rotateLeft(sibling);
                    sibling = parent.left;
                }

                rotateRight(parent);
                color(sibling, colorOf(parent));
                color(parent, BLACK);
                color(sibling.left, BLACK);
            }


        }
    }


    private Node<E> leftOf(Node<E> node) {
        return node == null ? null : node.left;
    }

    private Node<E> rightOf(Node<E> node) {
        return node == null ? null : node.right;
    }

    private Node<E> parentOf(Node<E> node) {
        return node == null ? null : node.parent;
    }

    private Node<E> color(Node<E> node, boolean color) {
        if (node == null) return node;
        RBNode<E> rbNode = (RBNode<E>) node;
        rbNode.color = color;
        return node;
    }

    @Override
    protected Node<E> createNode(E element, Node<E> parent) {
        return new RBNode<>(element, parent);
    }

    private boolean colorOf(Node<E> node) {
        if (node == null) return BLACK;
        return ((RBNode<E>) node).color;
    }

    private boolean isBlack(Node<E> node) {
        return colorOf(node) == BLACK;
    }

    private boolean isRed(Node<E> node) {
        return colorOf(node) == RED;
    }


    public static class RBNode<E extends Comparable<E>> extends BinarySearchTree.Node<E> {
        boolean color;

        RBNode(E element, Node<E> parent) {
            super(element, parent);
            this.color = RED;
        }


        public Node<E> sibling() {
            if (isLeftChild()) return this.parent.right;
            else if (isRightChild()) return this.parent.left;
            return null;
        }

        @Override
        public String toString() {
            if (color == RED) {
                return "[" + element.toString() + "]";
            } else {
                return element.toString();
            }
        }
    }
}
