package com.design.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class CustomerLruCache {
    private final int maxCapacity;
    private final Map<String,CacheNode> map=new ConcurrentHashMap<>();
    private CacheNode head;
    private CacheNode tail;

    // enable eviction when limit exceed
    public CustomerLruCache(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public Customer getCustomer(String customerId){
        CacheNode cacheNode=map.get(customerId);
        if (cacheNode==null) {
            return null;
        }
        // move it head as it was recently used
        moveToHead(cacheNode);
        // get customer data
        return cacheNode.value;
    }

    private synchronized void moveToHead(CacheNode cacheNode) {
        // remove the node from existing place

        removeNode(cacheNode);
        
        // add to head
        addToHead(cacheNode);
    }

    private synchronized void addToHead(CacheNode cacheNode) {
        cacheNode.previous=null; // as we want it as head
        cacheNode.next=head;
        if (head!=null){
            head.previous=cacheNode;
        }
        head=cacheNode;
    }

    private synchronized void removeNode(CacheNode cacheNode) {

        // if node is not head
        if (cacheNode.previous!=null){
            cacheNode.previous.next=cacheNode.next;
        }else {
            // if node is head
            head=cacheNode.next;
        }

        // if node is not tail
        if (cacheNode.next!=null){
            cacheNode.next.previous=cacheNode.previous;
        }else {
            // node is tail
            tail = cacheNode.previous;
        }
    }




}
