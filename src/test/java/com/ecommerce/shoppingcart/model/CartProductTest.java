package com.ecommerce.shoppingcart.model;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CartProductTest {

    @Test
    public void testGetterAndSetter() {
        Product product = new Product();
        product.setProductId(1L);
        product.setProductName("Laptop");
        product.setProductPrice(14800.00);

        Basket basket = new Basket();
        basket.setBasketId(1L);

        CartProduct cartProduct = new CartProduct();
        cartProduct.setCartProductId(1L);
        cartProduct.setCartQuantity(2);
        cartProduct.setCartPrice(29600.00);
        cartProduct.setProduct(product);
        cartProduct.setBasket(basket);

        assertThat(cartProduct.getCartProductId()).isEqualTo(1L);
        assertThat(cartProduct.getCartQuantity()).isEqualTo(2);
        assertThat(cartProduct.getCartPrice()).isEqualTo(29600.00);
        assertThat(cartProduct.getProduct().getProductName()).isEqualTo("Laptop");
        assertThat(cartProduct.getBasket().getBasketId()).isEqualTo(1L);
    }
}
