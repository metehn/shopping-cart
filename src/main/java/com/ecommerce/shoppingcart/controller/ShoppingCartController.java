package com.ecommerce.shoppingcart.controller;

import com.ecommerce.shoppingcart.model.dto.CartProductDTO;
import com.ecommerce.shoppingcart.model.response.PricesResponse;
import com.ecommerce.shoppingcart.service.ShoppingCartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/v1/cart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService cartService;

    @GetMapping("/total/{basketId}")
    public ResponseEntity<PricesResponse> getCartTotal(@PathVariable long basketId) {
        PricesResponse response = cartService.calculateTotal(basketId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/addProduct")
    public ResponseEntity<String> addProductToBasket(@RequestBody CartProductDTO cartProductDTO) {
        try {
            String response = cartService.addProductToBasket(cartProductDTO.getBasketId(), cartProductDTO.getProductId(), cartProductDTO.getQuantity());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error adding product to basket: " + e.getMessage());
        }
    }

    @PostMapping("/removeProduct")
    public ResponseEntity<String> removeProductFromBasket(@RequestBody CartProductDTO cartProductDTO) {
        try {
            String response = cartService.removeProductFromBasket(cartProductDTO.getBasketId(), cartProductDTO.getProductId());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error removing product from basket: " + e.getMessage());
        }
    }

    @PostMapping("/updateProductQuantity")
    public ResponseEntity<String> updateProductQuantity(@RequestBody CartProductDTO cartProductDTO) {
        try {
            String response = cartService.updateProductQuantity(cartProductDTO.getBasketId(),
                    cartProductDTO.getProductId(), cartProductDTO.getQuantity());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error updating product quantity: " + e.getMessage());
        }
    }

}
