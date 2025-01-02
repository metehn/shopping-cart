package com.ecommerce.shoppingcart.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class CartProductId implements Serializable {

    @Column(name = "basket_id")
    private Long basketId;

    @Column(name = "product_id")
    private Long productId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartProductId)) return false;
        CartProductId that = (CartProductId) o;
        return Objects.equals(basketId, that.basketId) &&
                Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(basketId, productId);
    }
}

