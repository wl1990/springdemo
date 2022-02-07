package com.test.springdemo.leecode.minpath.dijk;

import java.util.List;

public class Node<K,V> {
    private K name;
    private V path;
    private List<Node> nextNodes;

    public K getName() {
        return name;
    }

    public void setName(K name) {
        this.name = name;
    }

    public V getPath() {
        return path;
    }

    public void setPath(V path) {
        this.path = path;
    }

    public List<Node> getNextNodes() {
        return nextNodes;
    }

    public void setNextNodes(List<Node> nextNodes) {
        this.nextNodes = nextNodes;
    }
}
