package com.github.onotoliy.algorithm.trees;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * AVL дерево.
 *
 * @param <K> Тип ключа.
 * @param <V> Тип значения
 * @author Anatoliy Pokhresnyi
 */
public class AVLTree<K extends Comparable<K>, V> extends BinaryTree<K, V> {

    /**
     * Нужен правый поворот.
     */
    private static final int RIGHT_ROTATION = 2;

    /**
     * Нужен левый поворот.
     */
    private static final int LEFT_ROTATION = -2;

    @Override
    protected AVLNode<K, V> newNodeInstance(final K key, final V value) {
        return new AVLNode<>(key, value, null, null);
    }

    @Override
    protected void postInsert(final Node<K, V> node) {
        preRotation(toAVLNode(node));
    }

    @Override
    protected void postRemove(final Node<K, V> node) {
        preRotation(toAVLNode(node));
    }

    /**
     * Преобразование {@code Node} в {@code AVLNode}.
     *
     * @param node Дерево в формате {@code Node}.
     * @return Дерево в формате {@code AVLNode}.
     */
    private AVLNode<K, V> toAVLNode(final Node<K, V> node) {
        return (AVLNode<K, V>) node;
    }

    /**
     * Подготовка дерева к вращению.
     *
     * @param node Дерево.
     */
    private void preRotation(final AVLNode<K, V> node) {
        rotation(0, node)
            .entrySet()
            .stream()
            .max(Comparator.comparing(Map.Entry::getKey))
            .map(Map.Entry::getValue)
            .map(this::toAVLNode)
            .ifPresent(this::rotation);
    }

    /**
     * Поиск поддеревьев с нарушенным балансом.
     *
     * @param level Уровень.
     * @param node Поддерево.
     * @return Поддревья с нарушенным балансом.
     */
    private Map<Integer, Node<K, V>> rotation(final int level,
                                              final AVLNode<K, V> node) {
        if (node == null) {
            return Map.of();
        }

        Map<Integer, Node<K, V>> map = new HashMap<>();

        int balance = node.balance();
        if (balance == RIGHT_ROTATION || balance == LEFT_ROTATION) {
            map.put(level, node);
        }

        map.putAll(rotation(level + 1, toAVLNode(node.getLeft())));
        map.putAll(rotation(level + 1, toAVLNode(node.getRight())));

        return map;
    }

    /**
     * Поворот дерева.
     *
     * @param node Дерево.
     */
    private void rotation(final AVLNode<K, V> node) {
        if (node.balance() == RIGHT_ROTATION) {
            if (toAVLNode(node.getRight()).balance() < 0) {
                rightRotation(node.getRight());
            }

            leftRotation(node);
        }

        if (node.balance() == LEFT_ROTATION) {
            if (toAVLNode(node.getLeft()).balance() > 0) {
                leftRotation(node.getLeft());
            }

            rightRotation(node);
        }
    }

    /**
     * Правый поворот дерева.
     *
     * @param node Дерево.
     */
    private void rightRotation(final Node<K, V> node) {
        AVLNode<K, V> p = toAVLNode(node);
        AVLNode<K, V> q = toAVLNode(p.getLeft());
        AVLNode<K, V> right =
            new AVLNode<>(p.getKey(), p.getValue(), q.getRight(), p.getRight());

        p.setKey(q.getKey());
        p.setValue(q.getValue());
        p.setLeft(q.getLeft());
        p.setRight(right);
    }

    /**
     * Левый поворот дерева.
     *
     * @param node Дерево.
     */
    private void leftRotation(final Node<K, V> node) {
        AVLNode<K, V> q = toAVLNode(node);
        AVLNode<K, V> p = toAVLNode(q.getRight());
        AVLNode<K, V> left =
            new AVLNode<>(q.getKey(), q.getValue(), q.getLeft(), p.getLeft());

        q.setKey(p.getKey());
        q.setValue(p.getValue());
        q.setLeft(left);
        q.setRight(p.getRight());
    }
}
