package com.ecommerce.shoppingcart.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartProductDTO {
    private long basketId;
    private long productId;
    private int quantity;
}
