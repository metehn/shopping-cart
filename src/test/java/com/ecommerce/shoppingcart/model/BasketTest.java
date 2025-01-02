package com.ecommerce.shoppingcart.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BasketTest {

    @Test
    public void testGetterAndSetter() {
        Basket basket = new Basket();

        basket.setBasketId(1L);

        Customer customer = new Customer();
        customer.setCustomerEmail("test@example.com");
        basket.setCustomer(customer);

        CartProduct cartProduct = new CartProduct();
        cartProduct.setCartProductId(1L);
        basket.setBasketItems(List.of(cartProduct));

        assertThat(basket.getBasketId()).isEqualTo(1L);
        assertThat(basket.getCustomer().getCustomerEmail()).isEqualTo("test@example.com");
        assertThat(basket.getBasketItems()).hasSize(1);
        assertThat(basket.getBasketItems().get(0).getCartProductId()).isEqualTo(1L);
    }
}
