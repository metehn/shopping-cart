package com.ecommerce.shoppingcart.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PricesResponse {
    private double totalPrice;
    private double discountPrice;
}
