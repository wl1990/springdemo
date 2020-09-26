package com.test.springdemo.leecode.tree;

import java.util.Arrays;

public class BTreeDemo<E> {
    private class Node<E>{
        private Node left;
        private Node right;
        private E e;
    }

    private Node<E> root=null;

    public void addNode(E e){
        Node<E> node=new Node<>();
        node.e=e;
        if(root==null){
            root=node;
            return;
        }
        Node curNode=root;
        for(;;){
            if(String.valueOf(curNode.e).compareTo(String.valueOf(node.e))<0){
                if(curNode.right==null){
                    curNode.right=node;
                    return;
                }else{
                    curNode=curNode.right;
                }
            }
            if(String.valueOf(curNode.e).compareTo(String.valueOf(node.e))>0){
                if(curNode.left==null){
                    curNode.left=node;
                    return;
                }else{
                    curNode=curNode.left;
                }
            }
        }
    }
    // 前序遍历
    public void prePrint(Node<E> root){
        if(root==null){
            return;
        }
        System.out.print("-> " + root.e);
        prePrint(root.left);
        prePrint(root.right);
    }
     // 中序
    public void midPrint(Node<E> root){
        if(root==null){
            return;
        }
        midPrint(root.left);
        System.out.print("-> " + root.e);
        midPrint(root.right);
    }
    // 后序
    public void sufPrint(Node<E> root){
        if(root==null){
            return;
        }
        sufPrint(root.left);
        sufPrint(root.right);
        System.out.print("-> " + root.e);
    }

    Node[] pathArray=new Node[20];
    int nodeSize=0;
    // 打印节点路径
    public void leafNodePath(Node<E> node){
        if(node==null){
            return;
        }
        if(node.left==null && node.right==null){
            printPath(pathArray);
            System.out.println("->" + node.e);
        }
        if(pathArray.length>20){
            Node[] newPathArray=new Node[pathArray.length*2];
            for(int i=0;i<pathArray.length;i++){
                newPathArray[i]=pathArray[i];
            }
            pathArray=newPathArray;
        }
        pathArray[nodeSize++]=node;
        leafNodePath(node.left);
        leafNodePath(node.right);
        if(nodeSize>0){
            pathArray[--nodeSize]=null;
        }
    }

    private void printPath(Node[] pathArray) {
        if(pathArray==null){
            return;
        }
        for(int i=0;i<nodeSize;i++){
            System.out.print("->" + pathArray[i].e);
        }
    }

    public static void main(String[] args) {
        BTreeDemo<Long> bTreeDemo=new BTreeDemo<>();
        bTreeDemo.addNode(3L);
        bTreeDemo.addNode(5L);
        bTreeDemo.addNode(4L);
        bTreeDemo.addNode(6L);
        bTreeDemo.addNode(2L);
        bTreeDemo.addNode(1L);
        bTreeDemo.prePrint(bTreeDemo.root);
        System.out.println("");
        bTreeDemo.midPrint(bTreeDemo.root);
        System.out.println("");
        bTreeDemo.sufPrint(bTreeDemo.root);
//        bTreeDemo.leafNodePath(bTreeDemo.root);
    }


}
