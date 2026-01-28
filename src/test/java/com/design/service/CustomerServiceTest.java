package com.design.service;

import com.design.cache.Customer;
import com.design.cache.CustomerLruCache;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CustomerServiceTest {
    @Test
    void shouldCallRepositoryOnCacheMiss() {

        CustomerRepository repository = mock(CustomerRepository.class);
        CustomerLruCache cache = new CustomerLruCache(2);

        Customer customer = new Customer("1", "Alice");
        when(repository.findById("1")).thenReturn(customer);

        CustomerService service =
                new CustomerService(repository, cache);

        Customer result = service.getCustomer("1");

        assertEquals("Alice", result.name());
        verify(repository, times(1)).findById("1");
    }

    @Test
    void shouldNotCallRepositoryOnCacheHit() {

        CustomerRepository repository = mock(CustomerRepository.class);
        CustomerLruCache cache = new CustomerLruCache(2);

        when(repository.findById("1"))
                .thenReturn(new Customer("1", "Alice"));

        CustomerService service =
                new CustomerService(repository, cache);

        // First call → MISS → repo called
        service.getCustomer("1");

        // Second call → HIT → repo NOT called again
        service.getCustomer("1");

        verify(repository, times(1)).findById("1");
    }

    @Test
    void shouldCallRepositoryAgainAfterEviction() {

        CustomerRepository repository = mock(CustomerRepository.class);
        CustomerLruCache cache = new CustomerLruCache(2);

        when(repository.findById("1"))
                .thenReturn(new Customer("1", "A"));
        when(repository.findById("2"))
                .thenReturn(new Customer("2", "B"));
        when(repository.findById("3"))
                .thenReturn(new Customer("3", "C"));

        CustomerService service =
                new CustomerService(repository, cache);

        service.getCustomer("1"); // cached
        service.getCustomer("2"); // cached
        service.getCustomer("3"); // evicts 1

        service.getCustomer("1"); // MISS again

        verify(repository, times(2)).findById("1");
    }




}
