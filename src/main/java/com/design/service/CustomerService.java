package com.design.service;

import com.design.cache.Customer;
import com.design.cache.CustomerLruCache;

public class CustomerService {

    private final CustomerRepository repository;
    private final CustomerLruCache lruCache;

    public CustomerService(CustomerRepository repository, CustomerLruCache lruCache) {
        this.repository = repository;
        this.lruCache = lruCache;
    }

    public Customer getCustomer(String customerId){
        Customer cached=lruCache.getCustomer(customerId);
        if (cached!=null){
            return cached;
        }

        Customer customer=repository.findById(customerId);
        if (customerId!=null){
            lruCache.putCustomer(customerId,customer);
        }
        return customer;
    }
}
