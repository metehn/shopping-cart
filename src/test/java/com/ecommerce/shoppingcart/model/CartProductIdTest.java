package com.ecommerce.shoppingcart.model;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CartProductIdTest {

    @Test
    public void testGetterAndSetter() {
        CartProductId cartProductId = new CartProductId();
        cartProductId.setBasketId(1L);
        cartProductId.setProductId(2L);

        assertThat(cartProductId.getBasketId()).isEqualTo(1L);
        assertThat(cartProductId.getProductId()).isEqualTo(2L);
    }

    @Test
    public void testEqualsAndHashCode() {
        CartProductId id1 = new CartProductId(1L, 2L);
        CartProductId id2 = new CartProductId(1L, 2L);
        CartProductId id3 = new CartProductId(2L, 3L);

        assertThat(id1).isEqualTo(id2);
        assertThat(id1).isNotEqualTo(id3);

        assertThat(id1.hashCode()).isEqualTo(id2.hashCode());
        assertThat(id1.hashCode()).isNotEqualTo(id3.hashCode());
    }

    @Test
    public void testAllArgsConstructor() {
        CartProductId cartProductId = new CartProductId(1L, 2L);

        assertThat(cartProductId.getBasketId()).isEqualTo(1L);
        assertThat(cartProductId.getProductId()).isEqualTo(2L);
    }

    @Test
    public void testNoArgsConstructor() {
        CartProductId cartProductId = new CartProductId();

        assertThat(cartProductId.getBasketId()).isNull();
        assertThat(cartProductId.getProductId()).isNull();
    }
}
