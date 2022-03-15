package com.ceviant.coding.challenge.exercise2;

import org.springframework.stereotype.Component;

@Component
public class Exercise2 {

    public int sumOfBinaryTreeLeafNodes(Node root){
        if (root == null) {
            return 0;
        }
        if (root.left == null && root.right == null){
            return root.value;
        }
        return sumOfBinaryTreeLeafNodes(root.left) + sumOfBinaryTreeLeafNodes(root.right);
    }
}


