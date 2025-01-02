package com.ecommerce.shoppingcart.repository;

import com.ecommerce.shoppingcart.model.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartProductRepository extends JpaRepository<CartProduct, Long> {
    List<CartProduct> findByBasket_BasketId(Long basketId);
    Optional<CartProduct> findByBasket_BasketIdAndProduct_ProductId(Long basketId, Long productId);
}
