package com.ecommerce.shoppingcart.controller;

import com.ecommerce.shoppingcart.model.Customer;
import com.ecommerce.shoppingcart.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.Optional;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testAddCustomer() throws Exception {
        Customer customer = new Customer();
        customer.setCustomerId(1L);
        customer.setCustomerEmail("example@test.com");

        doNothing().when(customerService).save(any(Customer.class));

        mockMvc.perform(post("/customer/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isOk())
                .andExpect(content().string("Customer added successfully."));

        verify(customerService, times(1)).save(any(Customer.class));
    }

    @Test
    void testAddCustomerError() throws Exception {
        doThrow(new RuntimeException("DB Error!")).when(customerService).save(any(Customer.class));

        mockMvc.perform(post("/customer/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new Customer())))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Error adding customer."));
    }

    @Test
    void testGetAllCustomers() throws Exception {
        Customer c1 = new Customer();
        c1.setCustomerId(1L);
        c1.setCustomerEmail("abc@test.com");

        Customer c2 = new Customer();
        c2.setCustomerId(2L);
        c2.setCustomerEmail("xyz@test.com");

        when(customerService.findAll()).thenReturn(Arrays.asList(c1, c2));

        mockMvc.perform(get("/customer/"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        verify(customerService, times(1)).findAll();
    }

    @Test
    void testGetCustomerByIdFound() throws Exception {
        Customer c1 = new Customer();
        c1.setCustomerId(1L);
        c1.setCustomerEmail("found@test.com");

        when(customerService.findById(1L)).thenReturn(Optional.of(c1));

        mockMvc.perform(get("/customer/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        verify(customerService, times(1)).findById(1L);
    }

    @Test
    void testGetCustomerByIdNotFound() throws Exception {
        when(customerService.findById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/customer/999"))
                .andExpect(status().isNotFound());

        verify(customerService, times(1)).findById(999L);
    }
}
