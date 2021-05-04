package com.test.springdemo.leecode.logic;

import java.util.ArrayList;
import java.util.List;

/**
 * ac 自动机
 */
public class ACAutoMachineDemo {
    private class TNode{
        private TNode parent;
        private TNode failure;
        private List<TNode> childs;
        private char v;
        private int level;

        public TNode(TNode parent,char value){
            this.parent=parent;
            this.v=value;
            this.childs=new ArrayList<>();
            this.level=parent==null ? 0:parent.level+1;
        }
        public TNode addNode(TNode parent,char key){
            List<TNode> childs=parent.childs;
            TNode tnode=null;
            if(!keyExists(childs,key)){
                tnode=new TNode(parent,key);
                childs.add(tnode);
            }else{
                for(TNode node:childs){
                    if(node.getV()==key){
                        tnode=node;
                        break;
                    }
                }
            }
            return tnode;
        }

        private boolean keyExists(List<TNode> childs, char key) {
            for(TNode node:childs){
                if(node.getV()==key){
                    return true;
                }
            }
            return false;
        }


        public TNode getParent() {
            return parent;
        }

        public void setParent(TNode parent) {
            this.parent = parent;
        }

        public TNode getFailure() {
            return failure;
        }

        public void setFailure(TNode failure) {
            this.failure = failure;
        }

        public List<TNode> getChilds() {
            return childs;
        }

        public void setChilds(List<TNode> childs) {
            this.childs = childs;
        }

        public char getV() {
            return v;
        }

        public void setV(char v) {
            this.v = v;
        }

        public TNode getChildNode(char key){
            List<TNode> child=getChilds();
            if(child==null || child.size()<=0){
                return null;
            }else{
                for(TNode node:child){
                    if(node.getV()==key){
                        return node;
                    }
                }
                return null;
            }
        }

        public boolean childContainch(char key){
            return getChildNode(key)==null ? false:true;
        }
    }
    private TNode root;

    public void initACData(String[] keywords){
        buildTree(keywords);
        addFailure();
        BFSPrintTree();
    }

    public List<String> findAll(String text){
        char[] keywords=text.toCharArray();
        TNode mid=root;
        List<String> result=new ArrayList<>();
        for(char keyword:keywords){
            TNode temp=null;
            while(temp==null){
                temp=mid.getChildNode(keyword);
                if(mid==root){
                    break;
                }
                if(temp==null){
                    mid=mid.failure;
                }
            }
            if(temp!=null){
                mid=temp;
                if(temp.failure==root && temp.getChilds().size()<=0){
                    String sb=""+temp.getV();
                    TNode index=temp;
                    while(index.parent!=null){
                        sb=index.parent.getV()+sb;
                        index=index.parent;
                    }
                    result.add(sb);
                }
            }
        }
        return result;

    }

    public void addFailure(){
        List<TNode> childs=root.getChilds();
        List<TNode> tempNodes=new ArrayList<>();
        for(TNode node:childs){
            node.failure=root;
            List<TNode> childs2=node.getChilds();
            for(TNode node2:childs2){
                tempNodes.add(node2);
            }
        }
        while(tempNodes.size()>0){
            List<TNode> temp=new ArrayList<>();
            for(TNode node:tempNodes){
                TNode p_failure=node.getParent().failure;
                // 子节点char不相等 继续下一个failure
                while(p_failure!=null && !p_failure.childContainch(node.getV())){
                    p_failure=p_failure.failure;
                }
                // root 节点
                if(p_failure==null){
                    node.failure=root;
                }else{
                    node.failure=p_failure.getChildNode(node.getV());
                }
                for(TNode node2:node.getChilds()){
                    temp.add(node2);
                }
            }
            tempNodes=temp;
        }
    }
    public void buildTree(String[] keywords){
        root=new TNode(null,' ');
        for(int i=0;i<keywords.length;i++){
            TNode temp=root;
            String keyword=keywords[i];
            char[] keychars=keyword.toCharArray();
            for(int j=0;j<keychars.length;j++){
                char keychar=keychars[j];
                temp=root.addNode(temp,keychar);
            }
        }
    }

    public void BFSPrintTree(){
        List<TNode> nodeList=new ArrayList<>();
        nodeList.add(root);
        for(int i=0;i<nodeList.size();i++){
            TNode failure=nodeList.get(i).failure;
            if(failure!=null){
                System.out.println("level="+nodeList.get(i).level+" value="+nodeList.get(i).getV()+" failure level="+failure.level+" failure value="+failure.getV());
            }else{
                System.out.println("level="+nodeList.get(i).level+" value="+nodeList.get(i).getV()+" failure null");
            }
            List<TNode> childs=nodeList.get(i).getChilds();
            nodeList.addAll(childs);
        }
    }

    public static void main(String[] args) {
        ACAutoMachineDemo machineDemo=new ACAutoMachineDemo();
        String[] keywords = new String[]{"我是好人","我是坏人","好人","坏人","世界","那么大","世界那么大","大"};
        machineDemo.initACData(keywords);
        String text="我是好人吗?这事需要问问自己,人能分成好人坏人吗?这恐怕谁也无法解答.世界那么大,给你的想法那么大,我们世界里,只能想想大而已";
        List<String> result=machineDemo.findAll(text);
        for(String s:result){
            System.out.println(s);
        }
    }

}
