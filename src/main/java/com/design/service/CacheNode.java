package com.design.service;

public class CacheNode {
    String key;
    Customer value;
    Customer previous;
    Customer next;

    public CacheNode(String key, Customer value) {
        this.key = key;
        this.value = value;
    }
}
