package cc.hamarr.binarytree;

public interface BinaryTree<E> {
    int size();

    boolean isEmpty();

    Node<E> root();

    Node<E> find(E element);

    E add(E element);

    E remove(E element);
    
    public interface Node<E> {
        E element();
        E element(E element);

        Node<E> left();

        Node<E> right();
        Node<E> parent();

        default int degree() {
            if (left() == null && right() == null) return 0;
            if (left() == null || right() == null) return 1;
            return 2;
        }
        default boolean hasLeft() {
            return left() != null;
        }

        default boolean hasRight() {
            return right() != null;
        }
    }
}
