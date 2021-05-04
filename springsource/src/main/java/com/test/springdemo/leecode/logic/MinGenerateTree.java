package com.test.springdemo.leecode.logic;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * 最小生成树
 */
public class MinGenerateTree {
    static List<NodeLine> nodeLink=new ArrayList<>();
    static{
        NodeLine abline=new NodeLine("a","b",3);
        NodeLine acline=new NodeLine("a","c",5);
        NodeLine bdline=new NodeLine("b","d",1);
        NodeLine celine=new NodeLine("c","e",7);
        NodeLine deline=new NodeLine("d","e",10);
        NodeLine efline=new NodeLine("e","f",2);
        NodeLine egline=new NodeLine("e","g",8);
        NodeLine gfline=new NodeLine("g","f",6);
        nodeLink.add(abline);
        nodeLink.add(acline);
        nodeLink.add(bdline);
        nodeLink.add(celine);
        nodeLink.add(deline);
        nodeLink.add(efline);
        nodeLink.add(egline);
        nodeLink.add(gfline);
    }

    public static void minPath(){
        Set<String> nodeSet=nodeSet();
        Set<String> newNodeSet=new HashSet<>();
        Map<String,Set<NodeLine>> map=nodeLineMap();
        while(nodeSet.size()>0) {
            NodeLine newNode=new NodeLine(null,null,Integer.MAX_VALUE);
            Iterator<String> iterator=nodeSet.iterator();
            while (iterator.hasNext()) {
                String node = iterator.next();
                System.out.println("node="+node+"]path="+ JSON.toJSONString(map.get(node)));
                newNode = minNodePath(newNode,map.get(node), newNodeSet);
                System.out.println("new node value="+newNode.lineValue+"]startnode="+newNode.startNode+" endnode="+newNode.endNode);
                if(newNode.lineValue==Integer.MAX_VALUE){
                    continue;
                }
            }
            if(newNode.lineValue==Integer.MAX_VALUE){
                continue;
            }
            nodeSet.remove(newNode.endNode);
            nodeSet.remove(newNode.startNode);
            newNodeSet.add(newNode.endNode);
            newNodeSet.add(newNode.startNode);
            System.out.println("start node="+newNode.startNode+" end node="+newNode.endNode+" value="+newNode.lineValue);
        }

    }

    public static void main(String[] args) {
        minPath();
    }

    private static NodeLine minNodePath(NodeLine newNode,Set<NodeLine> nodeLines, Set<String> newNodeSet) {
        for(NodeLine node:nodeLines){
            if(node.lineValue>newNode.lineValue){
                continue;
            }
            if(newNodeSet.isEmpty()){
                newNodeSet.add(node.startNode);
            }
            if(newNodeSet.contains(node.endNode) || newNodeSet.contains(node.startNode)){
                newNode.startNode=node.startNode;
                newNode.endNode=node.endNode;
                newNode.lineValue=node.lineValue;
            }
        }
        return newNode;
    }

    public static Set<String> nodeSet(){
        Set<String> nodeSet=new HashSet<>();
        nodeLink.forEach(e->{
            nodeSet.add(e.startNode);
            nodeSet.add(e.endNode);
        });
        return nodeSet;
    }
    public static Map<String,Set<NodeLine>> nodeLineMap(){
        Map<String,Set<NodeLine>> map=new HashMap<>();
        for(NodeLine nodeLine:nodeLink){
            String startNode=nodeLine.startNode;
            String endNode=nodeLine.endNode;
            NodeLine newNode=new NodeLine(startNode,endNode,nodeLine.lineValue);
            if(map.containsKey(startNode)){
                map.get(startNode).add(newNode);
            }else{
                HashSet set=new HashSet<>();
                set.add(newNode);
                map.put(startNode,set);
            }
            if(map.containsKey(endNode)){
                map.get(endNode).add(newNode);
            }else{
                HashSet set=new HashSet<>();
                set.add(newNode);
                map.put(endNode,set);
            }
        }
        return map;
    }

    private static class NodeLine{
        private String startNode;
        private String endNode;
        private Integer lineValue;
        public NodeLine(String startNode,String endNode,Integer lineValue){
            this.startNode=startNode;
            this.endNode=endNode;
            this.lineValue=lineValue;
        }

        public String getStartNode() {
            return startNode;
        }

        public void setStartNode(String startNode) {
            this.startNode = startNode;
        }

        public String getEndNode() {
            return endNode;
        }

        public void setEndNode(String endNode) {
            this.endNode = endNode;
        }

        public Integer getLineValue() {
            return lineValue;
        }

        public void setLineValue(Integer lineValue) {
            this.lineValue = lineValue;
        }
    }
}
