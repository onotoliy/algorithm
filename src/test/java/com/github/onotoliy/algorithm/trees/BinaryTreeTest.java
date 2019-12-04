package com.github.onotoliy.algorithm.trees;

import org.junit.Test;

public class BinaryTreeTest {

    @Test
    public void test() {
        Tree<Long, Long> binaryTree = new BinaryTree<>();

        binaryTree.insert(1000L, 1000L);
        binaryTree.insert(500L, 500L);
        binaryTree.insert(1500L, 1500L);
        binaryTree.insert(250L, 250L);
        binaryTree.insert(750L, 750L);
        binaryTree.insert(1250L, 1250L);
        binaryTree.insert(1750L, 1750L);
        binaryTree.insert(125L, 125L);
        binaryTree.insert(375L, 375L);
        binaryTree.insert(125L, 125L);
        binaryTree.insert(375L, 375L);
        binaryTree.insert(625L, 625L);
        binaryTree.insert(875L, 875L);
        binaryTree.insert(1125L, 1125L);
        binaryTree.insert(1375L, 1375L);
        binaryTree.insert(1625L, 1625L);
        binaryTree.insert(1875L, 1875L);

        System.out.println(binaryTree.toString());

        binaryTree.find(1875L);
        binaryTree.find(1625L);
        binaryTree.find(11111L);

        binaryTree.height();

        binaryTree.remove(125L);
        binaryTree.remove(875L);

        binaryTree.height();

        binaryTree.remove(250L);
        binaryTree.remove(750L);
        binaryTree.remove(1375L);
        binaryTree.remove(1625L);
        binaryTree.remove(1250L);
        binaryTree.remove(1750L);

        binaryTree.height();

        binaryTree.remove(500L);
        binaryTree.remove(1500L);
        binaryTree.remove(1000L);
        binaryTree.remove(11111L);

        binaryTree.height();

        binaryTree.remove(625L);
        binaryTree.remove(375L);
        binaryTree.remove(1125L);
        binaryTree.remove(1875L);

        System.out.println(binaryTree.toString());

        binaryTree.insert(1000L, 1000L);
        binaryTree.insert(500L, 500L);
        binaryTree.remove(1000L);
        binaryTree.remove(500L);

        System.out.println(binaryTree.toString());

    }

}
