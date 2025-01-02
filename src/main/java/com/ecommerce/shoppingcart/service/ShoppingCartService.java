package com.ecommerce.shoppingcart.service;

import com.ecommerce.shoppingcart.model.Basket;
import com.ecommerce.shoppingcart.model.CartProduct;
import com.ecommerce.shoppingcart.model.Product;
import com.ecommerce.shoppingcart.model.response.PricesResponse;
import com.ecommerce.shoppingcart.repository.CartProductRepository;
import com.ecommerce.shoppingcart.repository.BasketRepository;
import com.ecommerce.shoppingcart.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartService {

    @Autowired
    private BasketRepository basketRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartProductRepository cartProductRepository;
    @Autowired
    private DiscountService discountService;

    public PricesResponse calculateTotal(long basketId) {
        return discountService.calculateTotalPriceWithDiscount(basketId);
    }

    public String addProductToBasket(Long basketId, Long productId, int quantity) {
        Basket basket = basketRepository.findById(basketId).orElseThrow(() -> new RuntimeException("Basket not found"));

        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

        CartProduct cartProduct = new CartProduct();
        cartProduct.setProduct(product);
        cartProduct.setCartQuantity(quantity);
        cartProduct.setCartPrice(product.getProductPrice() * quantity);
        cartProduct.setBasket(basket);

        basket.getBasketItems().add(cartProduct);

        cartProductRepository.save(cartProduct);
        basketRepository.save(basket);

        return "Product added to basket successfully!";
    }

    public String removeProductFromBasket(Long basketId, Long productId) {
        Basket basket = basketRepository.findById(basketId).orElseThrow(() -> new RuntimeException("Basket not found"));

        CartProduct cartProduct = basket.getBasketItems().stream()
                .filter(cp -> cp.getProduct().getProductId() == productId)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found in basket"));

        basket.getBasketItems().remove(cartProduct);

        cartProductRepository.delete(cartProduct);

        basketRepository.save(basket);

        return "Product removed from basket successfully!";
    }

    public String updateProductQuantity(Long basketId, Long productId, int newQuantity) {
        Basket basket = basketRepository.findById(basketId).orElseThrow(() -> new RuntimeException("Basket not found"));

        CartProduct cartProduct = basket.getBasketItems().stream()
                .filter(cp -> cp.getProduct().getProductId() == productId)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found in basket"));

        if (newQuantity < 1) {
            throw new RuntimeException("Quantity cannot be less than 1");
        }

        cartProduct.setCartQuantity(newQuantity);
        cartProduct.setCartPrice(cartProduct.getProduct().getProductPrice() * newQuantity);

        cartProductRepository.save(cartProduct);

        return "Product quantity updated successfully!";
    }

}

