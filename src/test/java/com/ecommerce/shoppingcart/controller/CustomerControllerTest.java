package com.ecommerce.shoppingcart.controller;

import com.ecommerce.shoppingcart.model.Customer;
import com.ecommerce.shoppingcart.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerController customerController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void testAddCustomer() throws Exception {
        Customer customer = new Customer();
        customer.setCustomerEmail("test@example.com");

        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        mockMvc.perform(post("/customer/add")
                        .contentType("application/json")
                        .content("{\"customer_email\": \"test@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Customer added successfully."));

        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    public void testGetAllCustomers() throws Exception {
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer("test@metehan.com"));
        customers.add(new Customer("test.deneme@example.com"));

        when(customerRepository.findAll()).thenReturn(customers);

        mockMvc.perform(get("/customer/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].customerEmail").value("test@metehan.com"))
                .andExpect(jsonPath("$[1].customerEmail").value("test.deneme@example.com"));
    }

    @Test
    public void testGetCustomerByIdFound() throws Exception {
        Customer customer = new Customer("test@metehan.com");
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        mockMvc.perform(get("/customer/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerEmail").value("test@metehan.com"));
    }

    @Test
    public void testGetCustomerByIdNotFound() throws Exception {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/customer/{id}", 1))
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }

    @Test
    public void testAddCustomerError() throws Exception {
        Customer customer = new Customer();
        customer.setCustomerEmail("test@example.com");

        when(customerRepository.save(any(Customer.class))).thenThrow(new RuntimeException("Database error"));

        mockMvc.perform(post("/customer/add")
                        .contentType("application/json")
                        .content("{\"customerEmail\": \"test@example.com\"}"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Error adding customer."));

        verify(customerRepository, times(1)).save(any(Customer.class));
    }


}
