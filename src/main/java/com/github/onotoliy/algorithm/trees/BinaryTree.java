package com.github.onotoliy.algorithm.trees;

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
    private BinaryNode<K, V> root = newNodeInstance(null, null);

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
        if (root.getKey() == null) {
            root.setKey(key);
            root.setValue(value);

            return root;
        }

        BinaryNode<K, V> node = insert(key, value, root);

        postInsert(root);

        return node;
    }

    @Override
    public Node<K, V> remove(final K key) {
        FamilyNode family = getFamilyNode(key, root);

        if (family == null) {
            return null;
        }

        boolean emptyLeft = family.node.getLeft() == null;
        boolean emptyRight = family.node.getRight() == null;

        if (emptyLeft && emptyRight) {
            removeSheet(toBinaryNode(family.node),
                        toBinaryNode(family.parentNode));
        } else {
            if (emptyLeft || emptyRight) {
                removeOneChild(toBinaryNode(family.node),
                               toBinaryNode(family.parentNode));
            } else {
                removeTwoChild(toBinaryNode(family.node));
            }
        }

        postRemove(root);

        return family.node;
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

        if (isLeft(key, node)) {
            return find(key, toBinaryNode(node.getLeft()));
        }

        if (isRight(key, node)) {
            return find(key, toBinaryNode(node.getRight()));
        }

        return node;
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
        if (isLeft(key, node)) {
            if (node.getLeft() == null) {
                node.setLeft(newNodeInstance(key, value));

                return toBinaryNode(node.getLeft());
            } else {
                return insert(key, value, toBinaryNode(node.getLeft()));
            }
        }

        if (isRight(key, node)) {
            if (node.getRight() == null) {
                node.setRight(newNodeInstance(key, value));

                return toBinaryNode(node.getRight());
            } else {
                return insert(key, value, toBinaryNode(node.getRight()));
            }
        }

        return node;
    }

    /**
     * Удаление элемента из дерева. Элемент содержит двух потомков.
     *
     * @param node Элемент.
     */
    private void removeTwoChild(final BinaryNode<K, V> node) {
        FamilyNode family = getLeftRightFamilyNode(node);

        BinaryNode<K, V> rightLeftNode = toBinaryNode(family.node);
        BinaryNode<K, V> rightLeftParentNode = toBinaryNode(family.parentNode);

        if (isLeft(rightLeftNode.getKey(), rightLeftParentNode)) {
            rightLeftParentNode.setLeft(rightLeftNode.getRight());
        } else {
            rightLeftParentNode.setRight(rightLeftNode.getLeft());
        }

        node.setKey(rightLeftNode.getKey());
        node.setValue(rightLeftNode.getValue());
    }

    /**
     * Удаление элемента из дерева. Элемент содержит одного потомка.
     *
     * @param node Элемент.
     * @param parentNode Родительский элемент.
     */
    private void removeOneChild(final BinaryNode<K, V> node,
                                final BinaryNode<K, V> parentNode) {
        if (parentNode == null) {
            Node<K, V> source = node.getLeft() == null
                ? node.getRight()
                : node.getLeft();

            node.setKey(source.getKey());
            node.setValue(source.getValue());
            node.setRight(source.getRight());
            node.setLeft(source.getLeft());

            return;
        }

        if (isLeft(node.getKey(), parentNode)) {
            if (node.getLeft() == null) {
                parentNode.setLeft(node.getRight());
            } else {
                parentNode.setLeft(node.getLeft());
            }
        } else {
            if (node.getLeft() == null) {
                parentNode.setRight(node.getRight());
            } else {
                parentNode.setRight(node.getLeft());
            }
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
        if (parentNode == null) {
            node.setKey(null);
            node.setValue(null);
            node.setLeft(null);
            node.setRight(null);

            return;
        }

        if (isLeft(node.getKey(), parentNode)) {
            parentNode.setLeft(null);
        } else {
            parentNode.setRight(null);
        }
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
     * @param node Элемент дерева.
     * @return Результат проверки.
     */
    private boolean isNow(final K key1, final Node<K, V> node) {
        return key1.compareTo(node.getKey()) == 0;
    }

    /**
     * Сравнение ключей. Левое поддерево.
     *
     * @param key Ключ родительского элемента.
     * @param node Элемент дерева.
     * @return Результат проверки.
     */
    private boolean isLeft(final K key, final Node<K, V> node) {
        return key.compareTo(node.getKey()) < 0;
    }

    /**
     * Сравнение ключей. Правое поддерево.
     *
     * @param key Ключ родительского элемента.
     * @param node Элемент дерева.
     * @return Результат проверки.
     */
    private boolean isRight(final K key, final Node<K, V> node) {
        return key.compareTo(node.getKey()) > 0;
    }

    /**
     * Получение самого правого элемента левого поддерева и его родительский
     * элемент.
     *
     * @param node Дерево.
     * @return Самый правый элемент левого поддерева и его родительский
     *         элемент.
     */
    private FamilyNode getLeftRightFamilyNode(final Node<K, V> node) {
        return getRightFamilyNode(node, node.getLeft());
    }

    /**
     * Получение самого правого элемента дерева и его родительский элемент.
     *
     * @param node Дерево.
     * @param parentNode Родительский элемент.
     * @return Самый правый элемент дерева и его родительский элемент.
     */
    private FamilyNode getRightFamilyNode(final Node<K, V> parentNode,
                                          final Node<K, V> node) {
        if (node.getRight() == null) {
            return new FamilyNode(parentNode, node);
        }

        return getRightFamilyNode(node, node.getRight());
    }

    /**
     * Получение элемента дерева и его родительский элемент.
     *
     * @param key Ключ.
     * @param node Дерево.
     * @return Элемент дерева и его родительский элемент.
     */
    private FamilyNode getFamilyNode(final K key, final Node<K, V> node) {
        if (isNow(key, node)) {
            return new FamilyNode(null, node);
        }

        return isLeft(key, node)
            ? getFamilyNode(key, node, node.getLeft())
            : getFamilyNode(key, node, node.getRight());
    }

    /**
     * Получение элемента дерева и его родительский элемент.
     *
     * @param key Ключ.
     * @param parent Родительский элемент.
     * @param node Дерево.
     * @return Элемент дерева и его родительский элемент.
     */
    private FamilyNode getFamilyNode(final K key,
                                     final Node<K, V> parent,
                                     final Node<K, V> node) {
        if (node == null) {
            return null;
        }

        return isNow(key, node)
            ? new FamilyNode(parent, node)
            : getFamilyNode(key, node);
    }

    @Override
    public String toString() {
        return root.toString();
    }

    /**
     * Элемент дерева и его родительский элемент.
     */
    private final class FamilyNode {
        /**
         * Родительский элемент.
         */
        private final Node<K, V> parentNode;

        /**
         * Элемент.
         */
        private final Node<K, V> node;

        /**
         * Конструктор.
         *
         * @param aParentNode Родительский элемент.
         * @param aNode Элемент.
         */
        private FamilyNode(final Node<K, V> aParentNode,
                           final Node<K, V> aNode) {
            this.parentNode = aParentNode;
            this.node = aNode;
        }
    }
}
