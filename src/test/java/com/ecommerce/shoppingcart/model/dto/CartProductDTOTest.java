package com.ecommerce.shoppingcart.model.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CartProductDTOTest {

    @Test
    public void testSetterAndGetter() {
        CartProductDTO cartProductDTO = new CartProductDTO();

        cartProductDTO.setBasketId(1L);
        cartProductDTO.setProductId(100L);
        cartProductDTO.setQuantity(3);

        assertThat(cartProductDTO.getBasketId()).isEqualTo(1L);
        assertThat(cartProductDTO.getProductId()).isEqualTo(100L);
        assertThat(cartProductDTO.getQuantity()).isEqualTo(3);
    }

}

