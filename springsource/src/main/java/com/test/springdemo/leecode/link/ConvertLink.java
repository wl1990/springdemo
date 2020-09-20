package com.test.springdemo.leecode.link;

import java.util.LinkedList;
import java.util.List;

/**
 * 倒转链表
 */
public class ConvertLink<E> {

    private class Node<E>{
        private Node next;
        private Node pre;
        private E e;

        public Node(E e){
            this.e=e;
        }
        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public Node getPre() {
            return pre;
        }

        public void setPre(Node pre) {
            this.pre = pre;
        }

        public E getE() {
            return e;
        }

        public void setE(E e) {
            this.e = e;
        }
    }
    private Node head;
    private Node tail;

    public void initLink(){
        if(head==null){
            head=tail=new Node("");
        }

    }

    public void addHeadNode(E e){
        initLink();
        Node temp=new Node(e);
        if(head==tail){
            head.next=temp;
            temp.pre=head;
            temp.next=new Node("");
            tail=temp.next;
            tail.pre=temp;
        }else{
            head.next.pre=temp;
            temp.next=head.next;
            head.next=temp;
            temp.pre=head;
        }
    }
    public void addTailNode(E e){
        initLink();
        Node temp=new Node(e);
        if(head==tail){
            head.next=temp;
            temp.pre=head;
            temp.next=new Node("");
            tail=temp.next;
            tail.pre=temp;
        }else{
            tail.pre.next=temp;
            temp.pre=tail.pre;
            temp.next=tail;
            tail.pre=temp;
        }
    }

    public void each(){
        Node cur=head.next;
        while(cur!=null){
            System.out.println(cur.e);
            cur=cur.next;
        }
    }

    public void convertLink(Node head){
        Node cur=head.next;
        while(cur!=null && cur!=tail && cur.next!=tail){
            E e= (E) cur.getE();
            cur.setE(cur.next.getE());
            cur.next.setE(e);
            cur=cur.next.next;
        }
    }

    public static void convert(List<String> list){
        for(int i=0;i<list.size()-1;i=i+2){
            String temp=list.get(i);
            list.set(i,list.get(i+1));
            list.set(i+1,temp);
        }
    }

    private static void tailConvert(){
        ConvertLink<String> convertLink=new ConvertLink<String>();
        convertLink.initLink();
        convertLink.addTailNode("1");
        convertLink.addTailNode("2");
        convertLink.addTailNode("3");
        convertLink.addTailNode("4");
        convertLink.addTailNode("5");
        convertLink.convertLink(convertLink.head);
        convertLink.each();
    }
    private static void headConvert(){
        ConvertLink<String> convertLink=new ConvertLink<String>();
        convertLink.initLink();
        convertLink.addHeadNode("1");
        convertLink.addHeadNode("2");
        convertLink.addHeadNode("3");
        convertLink.addHeadNode("4");
        convertLink.addHeadNode("5");
        convertLink.convertLink(convertLink.head);
        convertLink.each();
    }

    public static void main(String[] args) {
        headConvert();
    }
}
