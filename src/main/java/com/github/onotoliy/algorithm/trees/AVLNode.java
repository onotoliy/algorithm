package com.github.onotoliy.algorithm.trees;

/**
 * Реализация элемента AVL дерева.
 *
 * @param <K> Тип ключа.
 * @param <V> Тип значения
 * @author Anatoliy Pokhresnyi
 */
public class AVLNode<K extends Comparable<K>, V> extends BinaryNode<K, V> {

    /**
     * Конструктор.
     *
     * @param key Ключ.
     * @param value Значение.
     * @param left Левое поддерево.
     * @param right Правое поддерево.
     */
    public AVLNode(final K key,
                   final V value,
                   final Node<K, V> left,
                   final Node<K, V> right) {
        super(key, value, left, right);
    }

    /**
     * Вычисляет баланс поддерева.
     *
     * @return Баланс поддерева.
     */
    public int balance() {
        int rightHeight = getRight() == null ? 0 : getRight().height();
        int leftHeight = getLeft() == null ? 0 : getLeft().height();

        return rightHeight - leftHeight;
    }
}
