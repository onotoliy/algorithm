package com.github.onotoliy.algorithm.trees;

/**
 * Дерево.
 *
 * @param <K> Тип ключа.
 * @param <V> Тип значения
 * @author Anatoliy Pokhresnyi
 */
public interface Tree<K extends Comparable<K>, V> {

    /**
     * Поиск элемента в дереве.
     *
     * @param key Ключ элемента.
     * @return Найденый элемент дерева.
     */
    Node<K, V> find(K key);

    /**
     * Вставка элемента в дерево.
     *
     * @param key Ключ.
     * @param value Значение.
     * @return Элемент.
     */
    Node<K, V> insert(K key, V value);

    /**
     * Удаление элемента из дерева.
     *
     * @param key Ключ.
     * @return Элемент.
     */
    Node<K, V> remove(K key);

    /**
     * Вычислет высоту дерева.
     *
     * @return Высота дерева.
     */
    int height();

}
