package com.yubiaohyb.sharedemo.designpattern.structural;

/**
 * description  -  functionDescrption
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2020/2/5 12:39
 */
public class CompositionTest {

    interface BinaryTree {
        String getValue();
        BinaryTree getLeftChild();
        BinaryTree getRightChild();
        void preOrderTraversal();
        void inOrderTraversal();
        void postOrderTraversal();
    }

    class BinaryTreeNode implements BinaryTree {
        private String value;
        private BinaryTree leftChild;
        private BinaryTree rightChild;

        @Override
        public String getValue() {
            return value;
        }

        @Override
        public BinaryTree getLeftChild() {
            return leftChild;
        }

        @Override
        public BinaryTree getRightChild() {
            return rightChild;
        }

        @Override
        public void preOrderTraversal() {
            leftChild.preOrderTraversal();
            System.out.print(value);
            rightChild.preOrderTraversal();
        }

        @Override
        public void inOrderTraversal() {
            System.out.print(value);
            leftChild.inOrderTraversal();
            rightChild.inOrderTraversal();
        }

        @Override
        public void postOrderTraversal() {
            leftChild.postOrderTraversal();
            rightChild.postOrderTraversal();
            System.out.println(value);
        }
    }



}
