package com.ecommerce.shoppingcart.service;

import com.ecommerce.shoppingcart.model.CartProduct;
import com.ecommerce.shoppingcart.model.response.PricesResponse;
import com.ecommerce.shoppingcart.repository.CartProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class DiscountServiceTest {

    @Mock
    private CartProductRepository cartProductRepository;

    @InjectMocks
    private DiscountService discountService;

    @BeforeEach
    public void setup() {
    }

    @Test
    public void testCalculateTotalPriceWithDiscount() {
        CartProduct product1 = new CartProduct();
        product1.setCartPrice(10);
        product1.setCartQuantity(2);

        CartProduct product2 = new CartProduct();
        product2.setCartPrice(5);
        product2.setCartQuantity(1);

        when(cartProductRepository.findByBasket_BasketId(1L)).thenReturn(Arrays.asList(product1, product2));

        PricesResponse response = discountService.calculateTotalPriceWithDiscount(1L);

        assertThat(response.getTotalPrice()).isEqualTo(25.0);
        assertThat(response.getDiscountPrice()).isEqualTo(25.0);
    }

    @Test
    public void testGetDiscount() {
        assertThat(discountService.getDiscount(300.0)).isEqualTo(0.15);
        assertThat(discountService.getDiscount(150.0)).isEqualTo(0.10);
        assertThat(discountService.getDiscount(50.0)).isEqualTo(0.00);
    }
}
