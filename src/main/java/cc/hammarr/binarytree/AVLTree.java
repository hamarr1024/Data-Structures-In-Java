package cc.hammarr.binarytree;


public class AVLTree<E extends Comparable<E>> extends BalancedBinarySearchTree<E> {

    @Override
    protected Node<E> createNode(E element, Node<E> parent) {
        return new AVLNode<>(element, parent);
    }

    @Override
    protected void fixAfterInsertion(BinarySearchTree.Node<E> node) {
        // node是新插入的节点，因为是BST，node一定是叶子节点
        // 所以第一次node=node.parent, node指向了n，第二次指向了p，第三次指向了g...
        while ((node = node.parent) != null) {
            updateHeight(node);
            // 迭代地自底向上更新高度
            // 所以node的子节点的高度已经是最新的了，
            // 此时调用isBalanced就可以计算最新的平衡因子
            if (!isBalanced(node)){
                // 再平衡
                reBalance(node);
                // 父节点一定是平衡了，不需要重新计算高度
                break;
            }
        }
    }

    @Override
    protected void fixAfterRemoval(Node<E> node, Node<E> replacement) {
        // 首先要注意传入的node不可能为空
        // 其次，删除某个节点只会影响父节点的平衡，所以迭代去修复父节点即可
        while ((node = node.parent) != null) {
            // 重新计算高度
            if (isBalanced(node)) {
                updateHeight(node);
            } else {
                reBalance(node);
                // 因为再平衡会导致父节点左右子树的高度发生变化，所以需要继续递归
            }
        }
    }

    private boolean isBalanced(Node<E> node) {
        if (node == null) return true;
        return Math.abs(((AVLNode<E>) node).balanceFactor()) <= 1;
    }

    private void reBalance(Node<E> node) {
        // 找到n, p, g
        AVLNode<E> g = (AVLNode<E>) node;
        AVLNode<E> p = g.tallerChild();
        AVLNode<E> n = p.tallerChild();
        // LL-右旋转
        if (p.isLeftChild() && n.isLeftChild()) {
            rotateRight(g);
            return;
        }

        // LR-左旋转+右旋转
        if (p.isLeftChild() && n.isRightChild()) {
            rotateLeft(p);
            rotateRight(g);
            return;
        }

        // RR-左旋转
        if (p.isRightChild() && n.isRightChild()) {
            rotateLeft(g);
            return;
        }

        // RL-右旋转+左旋转
        rotateRight(p);
        rotateLeft(g);
    }


    @Override
    protected void afterRotate(Node<E> g, Node<E> p) {
        updateHeight(p);
        updateHeight(g);
    }

    private void updateHeight(Node<E> node) {
        ((AVLNode<E>) node).updateHeight();
    }

    public static class AVLNode<E extends Comparable<E>> extends BinarySearchTree.Node<E> {
        int height = 1;

        AVLNode(E element, Node<E> parent) {
            super(element, parent);
        }

        @Override
        public String toString() {
            return element.toString() + "[bf=" + balanceFactor() + "]";
        }

        public int getHeight() {
            return height;
        }

        public int balanceFactor() {
            int leftHeight = left == null ? 0 : ((AVLNode<E>) left).height;
            int rightHeight = right == null ? 0 : ((AVLNode<E>) right).height;
            return leftHeight - rightHeight;
        }

        public void updateHeight() {
            int leftHeight = left == null ? 0 : ((AVLNode<E>) left).height;
            int rightHeight = right == null ? 0 : ((AVLNode<E>) right).height;
            this.height = 1 + Math.max(leftHeight, rightHeight);
        }


        public AVLNode<E> tallerChild() {
            int leftHeight = left == null ? 0 : ((AVLNode<E>) left).height;
            int rightHeight = right == null ? 0 : ((AVLNode<E>) right).height;
            if (leftHeight < rightHeight) return (AVLNode<E>) right;
            else if (leftHeight > rightHeight) return (AVLNode<E>) left;
                // 如果高度相同，返回同方向的
            else return isLeftChild() ? (AVLNode<E>) left : (AVLNode<E>) right;
        }
    }
}
