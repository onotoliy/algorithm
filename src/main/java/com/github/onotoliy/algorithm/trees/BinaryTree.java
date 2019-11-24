package com.github.onotoliy.algorithm.trees;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Бинарное дерево.
 *
 * @param <K> Тип ключа.
 * @param <V> Тип значения
 * @author Anatoliy Pokhresnyi
 */
public class BinaryTree<K extends Comparable<K>, V> implements Tree<K, V> {

    /**
     * Дерево.
     */
    private BinaryNode<K, V> root;

    /**
     * Создает новый элемент бинарного дерева.
     * @param key Ключ.
     * @param value Значение.
     * @return Элемент дерева.
     */
    protected BinaryNode<K, V> newNodeInstance(final K key, final V value) {
        return new BinaryNode<>(key, value, null, null);
    }

    /**
     * Операция после вставки элемента в дерево.
     *
     * @param node Дерево.
     */
    protected void postInsert(final Node<K, V> node) {

    }

    /**
     * Операция после удаление элемента из дерева.
     *
     * @param node Дерево.
     */
    protected void postRemove(final Node<K, V> node) {

    }

    @Override
    public Node<K, V> find(final K key) {
        return find(key, root);
    }

    @Override
    public Node<K, V> insert(final K key, final V value) {
        BinaryNode<K, V> node = root == null
            ? newNodeInstance(key, value)
            : insert(key, value, root);

        if (root == null) {
            root = node;
        }

        postInsert(root);

        return node;
    }

    @Override
    public Node<K, V> remove(final K key) {
        BinaryNode<K, V> removeNode = find(key, root);
        BinaryNode<K, V> parentNode = findParentNode(key, root);

        if (removeNode.getLeft() == null && removeNode.getRight() == null) {
            removeSheet(removeNode, parentNode);
            postRemove(root);
            return removeNode;
        }

        if (removeNode.getLeft() != null && removeNode.getRight() != null) {
            removeTwoChild(removeNode);
            postRemove(root);
            return removeNode;
        }

        if (removeNode.getLeft() == null || removeNode.getRight() == null) {
            removeOneChild(removeNode);
            postRemove(root);
            return removeNode;
        }

        return removeNode;
    }

    @Override
    public int height() {
        return root.height();
    }

    /**
     * Поиск элемента в дереве.
     *
     * @param key Ключ.
     * @param node Поддерево.
     * @return Найденый элемент.
     */
    private BinaryNode<K, V> find(final K key, final BinaryNode<K, V> node) {
        if (node == null) {
            return null;
        }

        if (isLeft(key, node.getKey())) {
            return find(key, toBinaryNode(node.getLeft()));
        }

        if (isRight(key, node.getKey())) {
            return find(key, toBinaryNode(node.getRight()));
        }

        return node;
    }

    /**
     * Поиск родительского узла элемента.
     *
     * @param key Ключ.
     * @param node Дерево.
     * @return Родительский узл элемента.
     */
    private BinaryNode<K, V> findParentNode(final K key,
                                            final Node<K, V> node) {
        if (node == null) {
            return null;
        }

        if (isLeft(key, node.getKey())) {
            return isNow(key, node.getLeft().getKey())
                ? toBinaryNode(node)
                : findParentNode(key, node.getLeft());
        }

        if (isRight(key, node.getKey())) {
            return isNow(key, node.getRight().getKey())
                ? toBinaryNode(node)
                : findParentNode(key, node.getRight());
        }

        return toBinaryNode(node);
    }

    /**
     * Получение самого правого элемента левого поддерева.
     *
     * @param node Дерево.
     * @return Самый правый элемент левого поддерева.
     */
    private BinaryNode<K, V> getLeftRightNode(final Node<K, V> node) {
        return getRightNode(node.getLeft());
    }

    /**
     * Получение самого правого элемента дерева.
     *
     * @param node Дерево.
     * @return Самый правый элемент дерева.
     */
    private BinaryNode<K, V> getRightNode(final Node<K, V> node) {
        return node.getRight() == null
            ? toBinaryNode(node)
            : getRightNode(node.getRight());
    }

    /**
     * Вставка элемента в дерево.
     *
     * @param key Ключ.
     * @param value Значение.
     * @param node Дерево.
     * @return Элемент.
     */
    private BinaryNode<K, V> insert(final K key,
                                    final V value,
                                    final BinaryNode<K, V> node) {
        if (isLeft(key, node.getKey())) {
            return insert(key, value, node::setLeft, node::getLeft);
        }

        if (isRight(key, node.getKey())) {
            return insert(key, value, node::setRight, node::getRight);
        }

        return node;
    }

    /**
     * Вставка элемента в дерево.
     *
     * @param key Ключ.
     * @param value Значение.
     * @param setter Вставка элемента.
     * @param getter Получение элемента.
     * @return Элемент.
     */
    private BinaryNode<K, V> insert(final K key,
                                    final V value,
                                    final Consumer<Node<K, V>> setter,
                                    final Supplier<Node<K, V>> getter) {
        if (getter.get() == null) {
            setter.accept(newNodeInstance(key, value));

            return toBinaryNode(getter.get());
        } else {
            return insert(key, value, toBinaryNode(getter.get()));
        }
    }

    /**
     * Удаление элемента из дерева. Элемент не содержит потомков.
     *
     * @param node Элемент.
     * @param parentNode Родительский элемент.
     */
    private void removeSheet(final BinaryNode<K, V> node,
                             final BinaryNode<K, V> parentNode) {
        if (parentNode.getKey() == null) {
            node.setKey(null);
            node.setValue(null);
            node.setLeft(null);
            node.setRight(null);
        } else {
            if (isLeft(node.getKey(), parentNode.getKey())) {
                parentNode.setLeft(null);
            } else {
                parentNode.setRight(null);
            }
        }
    }

    /**
     * Удаление элемента из дерева. Элемент содержит двух потомков.
     *
     * @param node Элемент.
     */
    private void removeTwoChild(final BinaryNode<K, V> node) {
        BinaryNode<K, V> rightLeftNode = getLeftRightNode(node);
        BinaryNode<K, V> rightLeftParentNode =
            findParentNode(rightLeftNode.getKey(), node);

        if (rightLeftParentNode.getKey() == null) {
            node.setValue(rightLeftNode.getValue());
            node.setKey(rightLeftNode.getKey());
            node.setRight(null);
        } else {
            if (isLeft(rightLeftNode.getKey(), rightLeftParentNode.getKey())) {
                rightLeftParentNode.setLeft(rightLeftNode.getRight());
                node.setKey(rightLeftNode.getKey());
                node.setValue(rightLeftNode.getValue());
            } else {
                rightLeftParentNode.setRight(rightLeftNode.getRight());
                node.setKey(rightLeftNode.getKey());
                node.setValue(rightLeftNode.getValue());
            }
        }
    }

    /**
     * Удаление элемента из дерева. Элемент содержит одного потомка.
     *
     * @param node Элемент.
     */
    private void removeOneChild(final BinaryNode<K, V> node) {
        BinaryNode<K, V> source = node.getLeft() == null
            ? toBinaryNode(node.getRight()) : toBinaryNode(node.getLeft());

        node.setKey(source.getKey());
        node.setValue(source.getValue());
        node.setLeft(source.getLeft());
        node.setRight(source.getRight());
    }

    /**
     * Преобразование {@code Node} в {@code BinaryNode}.
     *
     * @param node Дерево в формате {@code Node}.
     * @return Дерево в формате {@code BinaryNode}.
     */
    private BinaryNode<K, V> toBinaryNode(final Node<K, V> node) {
        return (BinaryNode<K, V>) node;
    }

    /**
     * Сравнение ключей. Текущий элемент.
     *
     * @param key1 Ключ родительского элемента.
     * @param key2 Ключ элемента.
     * @return Результат проверки.
     */
    private boolean isNow(final K key1, final K key2) {
        return key1.compareTo(key2) == 0;
    }

    /**
     * Сравнение ключей. Левое поддерево.
     *
     * @param key1 Ключ родительского элемента.
     * @param key2 Ключ элемента.
     * @return Результат проверки.
     */
    private boolean isLeft(final K key1, final K key2) {
        return key1.compareTo(key2) < 0;
    }

    /**
     * Сравнение ключей. Правое поддерево.
     *
     * @param key1 Ключ родительского элемента.
     * @param key2 Ключ элемента.
     * @return Результат проверки.
     */
    private boolean isRight(final K key1, final K key2) {
        return key1.compareTo(key2) > 0;
    }

    @Override
    public String toString() {
        return root.toString();
    }
}
