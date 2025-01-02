package com.ecommerce.shoppingcart.service;

import com.ecommerce.shoppingcart.model.CartProduct;
import com.ecommerce.shoppingcart.model.response.PricesResponse;
import com.ecommerce.shoppingcart.repository.CartProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscountService {

    @Autowired
    private CartProductRepository cartProductRepository;

    public PricesResponse calculateTotalPriceWithDiscount(long basketId) {
        List<CartProduct> cartProducts = cartProductRepository.findByBasket_BasketId(basketId);

        double totalPrice = cartProducts.stream()
                .mapToDouble(cp -> cp.getCartPrice() * cp.getCartQuantity())
                .sum();

        double discountRate = getDiscount(totalPrice);

        double discountPrice = totalPrice - (totalPrice * discountRate);

        PricesResponse response = new PricesResponse();
        response.setTotalPrice(totalPrice);
        response.setDiscountPrice(discountPrice);

        return response;
    }

    public double getDiscount(double totalPrice) {
        if (totalPrice > 200) {
            return 0.15;
        } else if (totalPrice >= 100) {
            return 0.10;
        } else {
            return 0.00;
        }
    }
}

