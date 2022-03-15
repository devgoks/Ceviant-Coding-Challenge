package com.ceviant.coding.challenge.Exercise2;

import com.ceviant.coding.challenge.exercise2.Exercise2;
import com.ceviant.coding.challenge.exercise2.Node;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class Exercise2Test {

    @Autowired
    private Exercise2 exercise2;

    @Test
    public void testSumOfBinaryTreeLeafNodes1(){
        Node root = new Node(1);
        root.left = new Node(2);
        root.right = new Node(3);
        root.left.left = new Node(4);
        root.left.right = new Node(5);
        root.right.left = new Node(6);
        root.right.right = new Node(7);
        root.right.left.right = new Node(8);
        Assertions.assertEquals(exercise2.sumOfBinaryTreeLeafNodes(root),24);
    }

    @Test
    public void testSumOfBinaryTreeLeafNodes2(){
        Node root = new Node(1);
        root.left = new Node(2);
        root.right = new Node(3);
        root.left.left = new Node(4);
        Assertions.assertEquals(exercise2.sumOfBinaryTreeLeafNodes(root),7);
    }
}
