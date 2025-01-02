package com.ecommerce.shoppingcart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import com.ecommerce.shoppingcart.model.CartProduct;
import com.ecommerce.shoppingcart.model.dto.PricesResponse;
import com.ecommerce.shoppingcart.repository.CartProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DiscountServiceTest {

    @Mock
    private CartProductRepository cartProductRepository;

    @InjectMocks
    private DiscountService discountService;

    @Test
    void calculateTotalPriceWithDiscount_whenTotalIsLessThan100_shouldApplyNoDiscount() {
        CartProduct cartProduct1 = new CartProduct();
        cartProduct1.setCartPrice(20.0);
        cartProduct1.setCartQuantity(2);

        CartProduct cartProduct2 = new CartProduct();
        cartProduct2.setCartPrice(10.0);
        cartProduct2.setCartQuantity(3);

        List<CartProduct> cartProducts = Arrays.asList(cartProduct1, cartProduct2);
        when(cartProductRepository.findByBasket_BasketId(1L)).thenReturn(cartProducts);

        PricesResponse response = discountService.calculateTotalPriceWithDiscount(1L);

        assertThat(response.getTotalPrice()).isEqualTo(70.0);
        assertThat(response.getDiscountPrice()).isEqualTo(70.0);
    }

    @Test
    void calculateTotalPriceWithDiscount_whenTotalIsBetween100and200_shouldApply10PercentDiscount() {
        CartProduct cartProduct = new CartProduct();
        cartProduct.setCartPrice(50.0);
        cartProduct.setCartQuantity(3);

        when(cartProductRepository.findByBasket_BasketId(2L))
                .thenReturn(Collections.singletonList(cartProduct));

        PricesResponse response = discountService.calculateTotalPriceWithDiscount(2L);

        assertThat(response.getTotalPrice()).isEqualTo(150.0);
        assertThat(response.getDiscountPrice()).isEqualTo(135.0);
    }

    @Test
    void calculateTotalPriceWithDiscount_whenTotalIsMoreThan200_shouldApply15PercentDiscount() {
        CartProduct cartProduct = new CartProduct();
        cartProduct.setCartPrice(100.0);
        cartProduct.setCartQuantity(3);

        when(cartProductRepository.findByBasket_BasketId(3L))
                .thenReturn(Collections.singletonList(cartProduct));

        PricesResponse response = discountService.calculateTotalPriceWithDiscount(3L);

        assertThat(response.getTotalPrice()).isEqualTo(300.0);
        assertThat(response.getDiscountPrice()).isEqualTo(255.0);
    }
}
