package com.design.service;

import com.design.cache.Customer;

public interface CustomerRepository {

    Customer findById(String customerId);
}
