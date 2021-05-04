package com.test.springdemo.leecode.tree;

import com.alibaba.fastjson.JSON;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 哈夫曼树
 */
public class HafmanTree {
    private static class Node{
        private Node left;
        private Node right;
        private String value;

        public Node getLeft() {
            return left;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public Node getRight() {
            return right;
        }

        public void setRight(Node right) {
            this.right = right;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static void main(String[] args) throws Exception {
        String msg="aabcccdeefffggggg";
        Map<String,Integer> charRight=getRight(msg);
        System.out.println("map="+JSON.toJSONString(charRight));
        List<String> sortChars=sorMap(charRight);
        System.out.println("sort list="+JSON.toJSONString(sortChars));
        Node root=hafumantree(sortChars);
        levelTree(root);
    }

    private static Node hafumantree(List<String> list) throws Exception {
        if(list==null || list.size()<=1){
            throw new Exception("param exception");
        }
        Node left=null;
        Node right=null;
        Node root=null;
        for(int i=list.size()-1;i>0;){
            if(right==null){
                right=new Node();
                right.value=list.get(i);
            }
            if(left==null){
                left=new Node();
                left.value=list.get(--i);
            }
            root=new Node();
            root.left=left;
            root.right=right;
            right=root;
            left=null;
        }
        return root;
    }

    private static void levelTree(Node node){
        List<Node> nodeList=new ArrayList<>();
        nodeList.add(node);
        int i=0;
        while(i<nodeList.size()){
            node=nodeList.get(i++);
            System.out.println("parent node value="+JSON.toJSONString(node.getValue()));
            Node left=node.getLeft();
            Node right=node.getRight();
            if(left!=null){
                nodeList.add(left);
            }
            if(right!=null){
                nodeList.add(right);
            }
        }
        System.out.println("node list="+JSON.toJSONString(nodeList));
    }

    public static Map<String,Integer> getRight(String msg){
        Map<String,Integer> map=new HashMap<>();
        for(int i=0;i<msg.length();i++){
            String chars=msg.substring(i,i+1);
            if(map.containsKey(chars)){
                map.put(chars,map.get(chars)+1);
            }else{
                map.put(chars,1);
            }
        }
        return map;
    }
    public static List<String> sorMap(Map<String,Integer> map){
        List<String> list=new LinkedList<>();
        for(Map.Entry<String,Integer> entry:map.entrySet()){
            Integer value=entry.getValue();
            String key=entry.getKey();
            if(list.size()==0){
                list.add(0,key);
                continue;
            }
            list=insertSort(list,map,key,value);
        }
        return list;
    }

    private static List<String> insertSort(List<String> list, Map<String, Integer> map, String key, Integer value) {
        for(int i=0;i<list.size();i++){
            String curvalue=list.get(i);
            if(map.get(curvalue)<value){
                List<String> newlist=new LinkedList<>();
                if(i==0){
                    newlist.add(0,key);
                    newlist.addAll(list);
                    return newlist ;
                }
                newlist.addAll(list.subList(0,i));
                newlist.add(i,key);
                newlist.addAll(list.subList(i,list.size()));
                return newlist;
            }else{
                if(i==list.size()-1){
                    List<String> newlist=new LinkedList<>();
                    newlist.addAll(list);
                    newlist.add(key);
                    return newlist;
                }
            }
        }
        return new LinkedList<>();
    }
}
