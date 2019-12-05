package com.github.onotoliy.algorithm.trees;

import org.junit.Test;

public class AVLTreeTest {

    @Test
    public void test() {
        Tree<Long, Long> avlTree = new AVLTree<>();
        for (long i = 0; i < 100; i = i + 5) {
            avlTree.insert(i, i);
        }

        for (long i = 0; i < 100; i = i + 5) {
            avlTree.remove(i);
        }

        for (long i = 200; i > 100; i = i - 5) {
            avlTree.insert(i, i);
        }

        for (long i = 200; i > 100; i = i - 5) {
            avlTree.remove(i);
        }

        avlTree.insert(100L, 100L);
        avlTree.insert(150L, 150L);
        avlTree.insert(120L, 120L);

        avlTree.insert(50L, 50L);
        avlTree.insert(75L, 75L);
        avlTree.insert(25L, 25L);
        avlTree.insert(35L, 35L);




        System.out.println(avlTree.toString());
    }
}
