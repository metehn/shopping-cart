package com.ecommerce.shoppingcart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import com.ecommerce.shoppingcart.model.Customer;
import com.ecommerce.shoppingcart.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void testSave() {
        Customer customer = new Customer();
        customer.setCustomerId(1L);
        customer.setCustomerEmail("test@test.com");

        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        customerService.save(customer);

        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    void testFindAll() {
        Customer c1 = new Customer();
        c1.setCustomerId(1L);
        c1.setCustomerEmail("c1@test.com");

        Customer c2 = new Customer();
        c2.setCustomerId(2L);
        c2.setCustomerEmail("c2@test.com");

        List<Customer> mockCustomers = Arrays.asList(c1, c2);
        when(customerRepository.findAll()).thenReturn(mockCustomers);

        List<Customer> customers = customerService.findAll();

        verify(customerRepository, times(1)).findAll();
        assertThat(customers).hasSize(2);
        assertThat(customers).contains(c1, c2);
    }

    @Test
    void testFindById_found() {
        long id = 10L;
        Customer customer = new Customer();
        customer.setCustomerId(id);
        customer.setCustomerEmail("found@test.com");

        when(customerRepository.findById(id)).thenReturn(Optional.of(customer));

        Optional<Customer> result = customerService.findById(id);

        verify(customerRepository, times(1)).findById(id);
        assertThat(result).isPresent();
        assertThat(result.get().getCustomerId()).isEqualTo(id);
        assertThat(result.get().getCustomerEmail()).isEqualTo("found@test.com");
    }

    @Test
    void testFindById_notFound() {
        long id = 999L;
        when(customerRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Customer> result = customerService.findById(id);

        verify(customerRepository, times(1)).findById(id);
        assertThat(result).isEmpty();
    }
}
