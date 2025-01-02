package com.ecommerce.shoppingcart.model.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PricesResponseTest {

    @Test
    public void testGetterAndSetter() {
        PricesResponse pricesResponse = new PricesResponse();

        pricesResponse.setTotalPrice(250.0);
        pricesResponse.setDiscountPrice(200.0);

        assertThat(pricesResponse.getTotalPrice()).isEqualTo(250.0);
        assertThat(pricesResponse.getDiscountPrice()).isEqualTo(200.0);
    }
}
