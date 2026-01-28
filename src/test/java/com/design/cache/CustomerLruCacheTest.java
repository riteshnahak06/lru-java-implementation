package com.design.cache;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerLruCacheTest {

    @Test
    void shouldReturnNullWhenCacheIsEmpty(){
        CustomerLruCache cache = new CustomerLruCache(2);
        Customer result = cache.getCustomer("1");
        assertNull(result);
    }
    @Test
    void shouldReturnCustomerAfterPut() {
        CustomerLruCache cache = new CustomerLruCache(2);
        Customer c1 = new Customer("1", "Alice");
        cache.putCustomer("1", c1);

        Customer result = cache.getCustomer("1");

        assertNotNull(result);
        assertEquals("Alice", result.name());
    }

    @Test
    void shouldEvictLeastRecentlyUsedCustomer() {
        CustomerLruCache cache = new CustomerLruCache(2);

        cache.putCustomer("1", new Customer("1", "A"));
        cache.putCustomer("2", new Customer("2", "B"));

        // Access 1 so 2 becomes LRU
        cache.getCustomer("1");

        // Add new entry → evicts 2
        cache.putCustomer("3", new Customer("3", "C"));

        assertNull(cache.getCustomer("2"));
        assertNotNull(cache.getCustomer("1"));
        assertNotNull(cache.getCustomer("3"));
    }

    @Test
    void shouldMoveCustomerToMostRecentOnAccess() {
        CustomerLruCache cache = new CustomerLruCache(2);

        cache.putCustomer("1", new Customer("1", "A"));
        cache.putCustomer("2", new Customer("2", "B"));

        // Access 1
        cache.getCustomer("1");

        // Add new entry → should evict 2
        cache.putCustomer("3", new Customer("3", "C"));

        assertNull(cache.getCustomer("2"));
        assertEquals("A", cache.getCustomer("1").name());
    }

    @Test
    void shouldUpdateExistingCustomerAndMoveToHead() {
        CustomerLruCache cache = new CustomerLruCache(2);

        cache.putCustomer("1", new Customer("1", "A"));
        cache.putCustomer("2", new Customer("2", "B"));

        // Update customer 1
        cache.putCustomer("1", new Customer("1", "A_UPDATED"));

        // Trigger eviction
        cache.putCustomer("3", new Customer("3", "C"));

        assertNull(cache.getCustomer("2"));
        assertEquals("A_UPDATED", cache.getCustomer("1").name());
    }

    @Test
    void shouldWorkWhenCapacityIsOne() {
        CustomerLruCache cache = new CustomerLruCache(1);

        cache.putCustomer("1", new Customer("1", "A"));
        cache.putCustomer("2", new Customer("2", "B"));

        assertNull(cache.getCustomer("1"));
        assertEquals("B", cache.getCustomer("2").name());
    }
}
