package com.ecommerce.shoppingcart.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductTest {

    @Test
    public void testGetterAndSetter() {
        Product product = new Product();

        product.setProductId(1L);
        product.setProductName("Laptop");
        product.setProductPrice(14800.00);

        assertThat(product.getProductId()).isEqualTo(1L);
        assertThat(product.getProductName()).isEqualTo("Laptop");
        assertThat(product.getProductPrice()).isEqualTo(14800.00);
    }
}
