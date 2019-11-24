package com.github.onotoliy.algorithm.trees;

import org.junit.Assert;
import org.junit.Test;

public class BinaryTreeTest {

    @Test
    public void test() {
        Tree<Long, Long> binaryTree = new BinaryTree<>();

        binaryTree.insert(10L, 10L);
        binaryTree.insert(15L, 15L);
        binaryTree.insert(20L, 20L);
        binaryTree.insert(25L, 25L);
        binaryTree.insert(30L, 30L);
        binaryTree.insert(35L, 35L);
        binaryTree.insert(40L, 40L);
        binaryTree.insert(45L, 45L);
        binaryTree.insert(50L, 50L);
        binaryTree.insert(55L, 55L);
        binaryTree.insert(60L, 60L);
        binaryTree.insert(65L, 65L);
        binaryTree.insert(70L, 70L);
        binaryTree.insert(75L, 75L);
        binaryTree.insert(80L, 80L);

        Assert.assertEquals(Long.valueOf(40), binaryTree.find(40L).getKey());
        Assert.assertEquals(Long.valueOf(80), binaryTree.find(80L).getKey());
        Assert.assertNull(binaryTree.find(16L));
        Assert.assertEquals(15, binaryTree.height());
        Assert.assertEquals("b 10\n"
                + "  r 15\n"
                + "    r 20\n"
                + "      r 25\n"
                + "        r 30\n"
                + "          r 35\n"
                + "            r 40\n"
                + "              r 45\n"
                + "                r 50\n"
                + "                  r 55\n"
                + "                    r 60\n"
                + "                      r 65\n"
                + "                        r 70\n"
                + "                          r 75\n" +
                "                            r 80",
            binaryTree.toString());

        binaryTree.remove(80L);
        binaryTree.remove(40L);
        binaryTree.remove(10L);


    }

}
