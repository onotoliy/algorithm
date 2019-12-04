package com.github.onotoliy.algorithm.trees;

/**
 * Элемент дерева.
 *
 * @param <K> Тип ключа.
 * @param <V> Тип значения
 * @author Anatoliy Pokhresnyi
 */
public interface Node<K extends Comparable<K>, V> {

    /**
     * Возвращает левое поддерово.
     *
     * @return Левое поддерево.
     */
    Node<K, V> getLeft();

    /**
     * Возвращает правое поддерево.
     *
     * @return Правое поддерево.
     */
    Node<K, V> getRight();

    /**
     * Возвращает ключ.
     *
     * @return Ключ.
     */
    K getKey();

    /**
     * Возвращает значение.
     *
     * @return Значение.
     */
    V getValue();

    /**
     * Вычисляет высоту поддерева.
     *
     * @return Высота поддерева.
     */
    int height();
}
