package com.design.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class CustomerLruCache {

    private final int maxCapacity;
    private final Map<String, CacheNode> map = new ConcurrentHashMap<>();
    private CacheNode head;
    private CacheNode tail;

    // enable eviction when limit exceed
    public CustomerLruCache(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public synchronized void putCustomer(String customerId, Customer customer) {
        CacheNode node = map.get(customerId);

        // if customer exist
        if (node != null) {
            node.value = customer;
            moveToHead(node); // move as recently updated
            return;
        }

        CacheNode newNode = new CacheNode(customerId, customer);
        map.put(customerId, newNode);
        addToHead(newNode); // coz recently used

        if (map.size() > maxCapacity) {
            evictLeastRecentUsed();
        }
    }

    public Customer getCustomer(String customerId) {
        CacheNode cacheNode = map.get(customerId);
        if (cacheNode == null) {
            return null;
        }
        // move it head as it was recently used
        moveToHead(cacheNode);
        // get customer data
        return cacheNode.value;
    }

    private synchronized void moveToHead(CacheNode cacheNode) {
        if (cacheNode == head) {
            return;
        }
        // remove the node from existing place
        removeNode(cacheNode);
        // add to head
        addToHead(cacheNode);
    }

    private synchronized void addToHead(CacheNode cacheNode) {
        cacheNode.previous = null; // as we want it as head
        cacheNode.next = head;

        if (head != null) {
            head.previous = cacheNode;
        }

        head = cacheNode;

        if (tail == null) {
            tail = cacheNode;
        }
    }

    private synchronized void removeNode(CacheNode cacheNode) {

        // if node is not head
        if (cacheNode.previous != null) {
            cacheNode.previous.next = cacheNode.next;
        } else {
            // if node is head
            head = cacheNode.next;
        }

        // if node is not tail
        if (cacheNode.next != null) {
            cacheNode.next.previous = cacheNode.previous;
        } else {
            // node is tail
            tail = cacheNode.previous;
        }

        cacheNode.previous = null;
        cacheNode.next = null;
    }

    private synchronized void evictLeastRecentUsed() {
        if (tail != null) {
            map.remove(tail.key); // remove from data
            removeNode(tail);     // remove from doubly ll chain
        }
    }


}
