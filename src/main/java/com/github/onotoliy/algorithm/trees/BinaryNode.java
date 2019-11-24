package com.github.onotoliy.algorithm.trees;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Реализация элемента бинарного дерева.
 *
 * @param <K> Тип ключа.
 * @param <V> Тип значения
 * @author Anatoliy Pokhresnyi
 */
public class BinaryNode<K extends Comparable<K>, V> implements Node<K, V> {

    /**
     * Ключ.
     */
    private K key;

    /**
     * Значение.
     */
    private V value;

    /**
     * Левое поддерево.
     */
    private Node<K, V> left;

    /**
     * Правое поддерево.
     */
    private Node<K, V> right;

    /**
     * Конструктор.
     *
     * @param aKey Ключ.
     * @param aValue Значение.
     * @param aLeft Левое поддерево.
     * @param aRight Правое поддерево.
     */
    public BinaryNode(final K aKey,
                      final V aValue,
                      final Node<K, V> aLeft,
                      final Node<K, V> aRight) {
        this.key = aKey;
        this.value = aValue;
        this.left = aLeft;
        this.right = aRight;
    }

    @Override
    public Node<K, V> getLeft() {
        return left;
    }

    /**
     * Устанавливает левое поддерево.
     *
     * @param aLeft Левое поддерево.
     */
    void setLeft(final Node<K, V> aLeft) {
        this.left = aLeft;
    }

    @Override
    public Node<K, V> getRight() {
        return right;
    }

    /**
     * Устанавливает правое поддерево.
     *
     * @param aRight Правое поддерево.
     */
    void setRight(final Node<K, V> aRight) {
        this.right = aRight;
    }

    @Override
    public K getKey() {
        return key;
    }

    /**
     * Устанавливает ключ.
     *
     * @param aKey Ключ.
     */
    void setKey(final K aKey) {
        this.key = aKey;
    }

    @Override
    public V getValue() {
        return value;
    }

    /**
     * Устанавливает значение.
     *
     * @param aValue Значение.
     */
    void setValue(final V aValue) {
        this.value = aValue;
    }

    @Override
    public int height() {
        if (left == null && right == null) {
            return 1;
        }

        if (left == null) {
            return right.height() + 1;
        }

        if (right == null) {
            return left.height() + 1;
        }

        return Math.max(left.height(), right.height()) + 1;
    }

    @Override
    public int balance() {
        int rightHeight = getRight() == null ? 0 : getRight().height();
        int leftHeight = getLeft() == null ? 0 : getLeft().height();

        return rightHeight - leftHeight;
    }

    @Override
    public String toString() {
        return String.join("\n", prettyTree(0, "b ", this));
    }

    /**
     * Дерево в печатном формате.
     *
     * @param level Уровень.
     * @param name Название.
     * @param node Дерево.
     * @return Дерево в печатном формате.
     */
    private List<String> prettyTree(final int level,
                                    final String name,
                                    final Node<K, V> node) {
        if (node == null) {
            return Collections.emptyList();
        }

        List<String> levels = new LinkedList<>();
        String spaces = IntStream.range(0, level)
                                 .mapToObj(e -> "  ")
                                 .collect(Collectors.joining());

        levels.add(spaces + name + node.getKey());
        levels.addAll(prettyTree(level + 1, "l ", node.getLeft()));
        levels.addAll(prettyTree(level + 1, "r ", node.getRight()));

        return levels;
    }

}
