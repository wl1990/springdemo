package com.test.springdemo.leecode.tree;

public class TreeNodeSameParent extends BTreeDemo<Integer>{
    /**
     * 节点的最小公共父节点
     */
    public  Node<Integer> sameParent(Node<Integer> node,Integer p,Integer q) throws Exception {
        if(p==null || q==null){
            throw new Exception("param exception");
        }
        while((p-node.getE())*(q-node.getE())>0){
            node=p-node.getE()<0 ? node.getLeft():node.getRight();
        }
        return node;
    }

    public static void main(String[] args) throws Exception {
        TreeNodeSameParent samePare=new TreeNodeSameParent();
        samePare.addNode(3);
        samePare.addNode(5);
        samePare.addNode(4);
        samePare.addNode(6);
        samePare.addNode(2);
        samePare.addNode(1);
        Node<Integer> node=samePare.sameParent(samePare.root,4,6);
        System.out.printf("value="+node.getE());
    }

}
