package com.ecommerce.shoppingcart.controller;

import com.ecommerce.shoppingcart.model.dto.CartProductDTO;
import com.ecommerce.shoppingcart.model.dto.PricesResponse;
import com.ecommerce.shoppingcart.service.ShoppingCartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ShoppingCartControllerTest {

    @Mock
    private ShoppingCartService cartService;

    @InjectMocks
    private ShoppingCartController cartController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(cartController).build();
    }

    @Test
    public void testGetCartTotal() throws Exception {
        PricesResponse pricesResponse = new PricesResponse();
        pricesResponse.setTotalPrice(250.0);
        pricesResponse.setDiscountPrice(200.0);

        when(cartService.calculateTotal(1L)).thenReturn(pricesResponse);

        mockMvc.perform(get("/api/v1/cart/total/{basketId}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPrice").value(250.0))
                .andExpect(jsonPath("$.discountPrice").value(200.0));

        verify(cartService, times(1)).calculateTotal(1L);
    }

    @Test
    public void testAddProductToBasket() throws Exception {
        CartProductDTO cartProductDTO = new CartProductDTO();
        cartProductDTO.setBasketId(1L);
        cartProductDTO.setProductId(1L);
        cartProductDTO.setQuantity(2);

        when(cartService.addProductToBasket(1L, 1L, 2)).thenReturn("Product added to basket successfully.");

        mockMvc.perform(post("/api/v1/cart/addProduct")
                        .contentType("application/json")
                        .content("{\"basketId\": 1, \"productId\": 1, \"quantity\": 2}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Product added to basket successfully."));

        verify(cartService, times(1)).addProductToBasket(1L, 1L, 2);
    }

    @Test
    public void testRemoveProductFromBasket() throws Exception {
        CartProductDTO cartProductDTO = new CartProductDTO();
        cartProductDTO.setBasketId(1L);
        cartProductDTO.setProductId(1L);

        when(cartService.removeProductFromBasket(1L, 1L)).thenReturn("Product removed from basket successfully.");

        mockMvc.perform(post("/api/v1/cart/removeProduct")
                        .contentType("application/json")
                        .content("{\"basketId\": 1, \"productId\": 1}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Product removed from basket successfully."));

        verify(cartService, times(1)).removeProductFromBasket(1L, 1L);
    }

    @Test
    public void testUpdateProductQuantity() throws Exception {
        CartProductDTO cartProductDTO = new CartProductDTO();
        cartProductDTO.setBasketId(1L);
        cartProductDTO.setProductId(1L);
        cartProductDTO.setQuantity(3);

        when(cartService.updateProductQuantity(1L, 1L, 3)).thenReturn("Product quantity updated successfully.");

        mockMvc.perform(post("/api/v1/cart/updateProductQuantity")
                        .contentType("application/json")
                        .content("{\"basketId\": 1, \"productId\": 1, \"quantity\": 3}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Product quantity updated successfully."));

        verify(cartService, times(1)).updateProductQuantity(1L, 1L, 3);
    }

    @Test
    public void testAddProductToBasketError() throws Exception {
        CartProductDTO cartProductDTO = new CartProductDTO();
        cartProductDTO.setBasketId(1L);
        cartProductDTO.setProductId(1L);
        cartProductDTO.setQuantity(2);

        when(cartService.addProductToBasket(1L, 1L, 2)).thenThrow(new RuntimeException("Error adding product"));

        mockMvc.perform(post("/api/v1/cart/addProduct")
                        .contentType("application/json")
                        .content("{\"basketId\": 1, \"productId\": 1, \"quantity\": 2}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error adding product to basket: Error adding product"));

        verify(cartService, times(1)).addProductToBasket(1L, 1L, 2);
    }
    @Test
    public void testRemoveProductFromBasketError() throws Exception {
        CartProductDTO cartProductDTO = new CartProductDTO();
        cartProductDTO.setBasketId(1L);
        cartProductDTO.setProductId(999L);

        when(cartService.removeProductFromBasket(1L, 999L)).thenThrow(new RuntimeException("Product not found in basket"));

        mockMvc.perform(post("/api/v1/cart/removeProduct")
                        .contentType("application/json")
                        .content("{\"basketId\": 1, \"productId\": 999}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error removing product from basket: Product not found in basket"));

        verify(cartService, times(1)).removeProductFromBasket(1L, 999L);
    }

    @Test
    public void testUpdateProductQuantityError() throws Exception {
        CartProductDTO cartProductDTO = new CartProductDTO();
        cartProductDTO.setBasketId(1L);
        cartProductDTO.setProductId(1L);
        cartProductDTO.setQuantity(0);

        when(cartService.updateProductQuantity(1L, 1L, 0)).thenThrow(new RuntimeException("Quantity cannot be less than 1"));

        mockMvc.perform(post("/api/v1/cart/updateProductQuantity")
                        .contentType("application/json")
                        .content("{\"basketId\": 1, \"productId\": 1, \"quantity\": 0}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error updating product quantity: Quantity cannot be less than 1"));

        verify(cartService, times(1)).updateProductQuantity(1L, 1L, 0);
    }



}
