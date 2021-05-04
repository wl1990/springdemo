package com.test.springdemo.leecode.link;

/**
 * 输出倒数第n个节点
 */
public class LastNNode {
    private static class Node<E>{
        private Node<E> next;
        private E value;

        public Node<E> getNext() {
            return next;
        }

        public void setNext(Node<E> next) {
            this.next = next;
        }

        public E getValue() {
            return value;
        }

        public void setValue(E value) {
            this.value = value;
        }
    }
    private static Node head=null;
    public static void insertHead(Node node){
        if(head==null){
            head=node;
        }else{
            node.next=head;
            head=node;
        }
    }
    public static void listNode(){
        Node temp=head;
        while(temp!=null){
            System.out.println(temp.value);
            temp=temp.next;
        }
    }
    public static <E> Node<E> getLastNNode(Node head,int n){
        Node slow=head;
        Node fast=head;
        for(int i=0;i<n;i++){
            fast=fast.next;
        }
        while(fast!=null){
            slow=slow.next;
            fast=fast.next;
        }
        return slow;

    }

    public static void main(String[] args) {
        for(int i=0;i<5;i++){
            Node node=new Node<Integer>();
            node.setNext(null);
            node.setValue(i);
            insertHead(node);
        }
        listNode();
        System.out.println(getLastNNode(head,1).getValue());

    }
}
