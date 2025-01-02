package com.ecommerce.shoppingcart.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class CustomerTest {

    private Customer customer;

    @BeforeEach
    public void setup() {
        customer = new Customer("test@example.com");
    }

    @Test
    public void testGetterAndSetter() {
        customer.setCustomerEmail("updated@example.com");

        Basket basket = mock(Basket.class);
        customer.setBasket(basket);

        assertThat(customer.getCustomerEmail()).isEqualTo("updated@example.com");
        assertThat(customer.getBasket()).isEqualTo(basket);
    }

    @Test
    public void testPrePersist() {
        customer.prePersist();

        assertThat(customer.getBasket()).isNotNull();
        assertThat(customer.getBasket().getCustomer()).isEqualTo(customer);
        assertThat(customer.getBasket().getBasketItems()).isEqualTo(Collections.emptyList());
    }

    @Test
    public void testPrePersistWithNoBasket() {
        customer.setBasket(null);

        customer.prePersist();

        assertThat(customer.getBasket()).isNotNull();
        assertThat(customer.getBasket().getCustomer()).isEqualTo(customer);
        assertThat(customer.getBasket().getBasketItems()).isEqualTo(Collections.emptyList());
    }

    @Test
    public void testPrePersistWithExistingBasket() {
        Basket existingBasket = mock(Basket.class);
        customer.setBasket(existingBasket);

        customer.prePersist();

        assertThat(customer.getBasket()).isEqualTo(existingBasket);
        verify(existingBasket, never()).setCustomer(any());
        verify(existingBasket, never()).setBasketItems(any());
    }
}
