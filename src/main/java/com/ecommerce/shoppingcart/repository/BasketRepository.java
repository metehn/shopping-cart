package com.ecommerce.shoppingcart.repository;

import com.ecommerce.shoppingcart.model.Basket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasketRepository extends JpaRepository<Basket, Long> {
    Basket findByBasketId(Long basketId);
}
