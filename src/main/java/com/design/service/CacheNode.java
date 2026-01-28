package com.design.service;

public class CacheNode {
    String key;
    Customer value;
    CacheNode previous;
    CacheNode next;

    public CacheNode(String key, Customer value) {
        this.key = key;
        this.value = value;
    }
}
