package com.ecommerce.shoppingcart.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CartProduct {

    @EmbeddedId
    private CartProductId cartProductId;

    @MapsId("basketId")
    @ManyToOne
    @JoinColumn(name = "basket_id")
    private Basket basket;

    @MapsId("productId")
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int cartQuantity;
    private double cartPrice;
}

