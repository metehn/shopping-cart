package com.ecommerce.shoppingcart.service;

import com.ecommerce.shoppingcart.model.Basket;
import com.ecommerce.shoppingcart.model.CartProduct;
import com.ecommerce.shoppingcart.model.CartProductId;
import com.ecommerce.shoppingcart.model.Product;
import com.ecommerce.shoppingcart.repository.BasketRepository;
import com.ecommerce.shoppingcart.repository.CartProductRepository;
import com.ecommerce.shoppingcart.repository.ProductRepository;
import com.ecommerce.shoppingcart.model.dto.PricesResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.ArrayList;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ShoppingCartServiceTest {

    @Mock
    private BasketRepository basketRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CartProductRepository cartProductRepository;

    @Mock
    private DiscountService discountService;

    @InjectMocks
    private ShoppingCartService shoppingCartService;

    private Basket basket;
    private Product product;
    private CartProduct cartProduct;

    @BeforeEach
    public void setup() {
        basket = new Basket();
        ArrayList<CartProduct> list = new ArrayList();
        basket.setBasketItems(list);

        product = new Product();
        product.setProductId(1L);
        product.setProductName("Laptop");
        product.setProductPrice(14800.00);

        cartProduct = new CartProduct();
        cartProduct.setProduct(product);
        cartProduct.setCartQuantity(2);
        cartProduct.setCartPrice(product.getProductPrice() * cartProduct.getCartQuantity());
    }

    @Test
    public void testCalculateTotal() {
        PricesResponse pricesResponse = new PricesResponse();
        pricesResponse.setTotalPrice(29600.00);
        pricesResponse.setDiscountPrice(25000.00);

        when(discountService.calculateTotalPriceWithDiscount(1L)).thenReturn(pricesResponse);

        PricesResponse response = shoppingCartService.calculateTotal(1L);

        assertThat(response.getTotalPrice()).isEqualTo(29600.00);
        assertThat(response.getDiscountPrice()).isEqualTo(25000.00);
    }

    @Test
    public void testAddProductToBasket() {
        when(basketRepository.findById(1L)).thenReturn(Optional.of(basket));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        String response = shoppingCartService.addProductToBasket(1L, 1L, 2);

        assertThat(response).isEqualTo("Product added to basket successfully!");
        assertThat(basket.getBasketItems()).hasSize(1);
        verify(cartProductRepository, times(1)).save(any(CartProduct.class));
        verify(basketRepository, times(1)).save(any(Basket.class));
    }

    @Test
    public void testRemoveProductFromBasket() {
        basket.getBasketItems().add(cartProduct);
        when(basketRepository.findById(1L)).thenReturn(Optional.of(basket));

        String response = shoppingCartService.removeProductFromBasket(1L, 1L);

        assertThat(response).isEqualTo("Product removed from basket successfully!");
        assertThat(basket.getBasketItems()).isEmpty();
        verify(cartProductRepository, times(1)).delete(cartProduct);
        verify(basketRepository, times(1)).save(basket);
    }

    @Test
    public void testUpdateProductQuantity() {
        basket.getBasketItems().add(cartProduct);
        when(basketRepository.findById(1L)).thenReturn(Optional.of(basket));

        String response = shoppingCartService.updateProductQuantity(1L, 1L, 3);

        assertThat(response).isEqualTo("Product quantity updated successfully!");
        assertThat(cartProduct.getCartQuantity()).isEqualTo(3);
        assertThat(cartProduct.getCartPrice()).isEqualTo(44400.00);
        verify(cartProductRepository, times(1)).save(cartProduct);
    }

    @Test
    public void testUpdateProductQuantityWithInvalidQuantity() {
        basket.getBasketItems().add(cartProduct);
        when(basketRepository.findById(1L)).thenReturn(Optional.of(basket));

        try {
            shoppingCartService.updateProductQuantity(1L, 1L, 0);
        } catch (RuntimeException e) {
            assertThat(e.getMessage()).isEqualTo("Quantity cannot be less than 1");
        }

        verify(cartProductRepository, never()).save(cartProduct);
    }

    @Test
    public void testUpdateProductQuantityProductNotFound() {
        basket.setBasketItems(new ArrayList<>());
        when(basketRepository.findById(1L)).thenReturn(Optional.of(basket));

        try {
            shoppingCartService.updateProductQuantity(1L, 1L, 3);
        } catch (RuntimeException e) {
            assertThat(e.getMessage()).isEqualTo("Product not found in basket");
        }

        verify(cartProductRepository, never()).save(any(CartProduct.class));
    }

    @Test
    public void testUpdateProductQuantityValid() {
        basket.getBasketItems().add(cartProduct);
        when(basketRepository.findById(1L)).thenReturn(Optional.of(basket));

        String response = shoppingCartService.updateProductQuantity(1L, 1L, 4);

        assertThat(response).isEqualTo("Product quantity updated successfully!");
        assertThat(cartProduct.getCartQuantity()).isEqualTo(4);
        assertThat(cartProduct.getCartPrice()).isEqualTo(59200.00);

        verify(cartProductRepository, times(1)).save(cartProduct);
    }

    @Test
    public void testUpdateProductQuantityNegativeQuantity() {
        basket.getBasketItems().add(cartProduct);
        when(basketRepository.findById(1L)).thenReturn(Optional.of(basket));

        try {
            shoppingCartService.updateProductQuantity(1L, 1L, -1);
        } catch (RuntimeException e) {
            assertThat(e.getMessage()).isEqualTo("Quantity cannot be less than 1");
        }

        verify(cartProductRepository, never()).save(any(CartProduct.class));
    }

    @Test
    public void testRemoveProductFromBasketProductNotFound() {
        basket.setBasketItems(new ArrayList<>());
        when(basketRepository.findById(1L)).thenReturn(Optional.of(basket));

        try {
            shoppingCartService.removeProductFromBasket(1L, 1L);
        } catch (RuntimeException e) {
            assertThat(e.getMessage()).isEqualTo("Product not found in basket");
        }

        verify(cartProductRepository, never()).delete(any(CartProduct.class));
    }


    @Test
    public void testAddProductToBasketProductNotFound() {
        when(basketRepository.findById(1L)).thenReturn(Optional.of(basket));
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        try {
            shoppingCartService.addProductToBasket(1L, 1L, 2);
        } catch (RuntimeException e) {
            assertThat(e.getMessage()).isEqualTo("Product not found");
        }

        verify(cartProductRepository, never()).save(any(CartProduct.class));
    }
    @Test
    public void testRemoveProductFromBasketProductFound() {
        CartProduct cartProduct = new CartProduct();
        cartProduct.setProduct(product);
        basket.getBasketItems().add(cartProduct);
        when(basketRepository.findById(1L)).thenReturn(Optional.of(basket));

        String response = shoppingCartService.removeProductFromBasket(1L, 1L);

        assertThat(response).isEqualTo("Product removed from basket successfully!");
        assertThat(basket.getBasketItems()).isEmpty();
        verify(cartProductRepository, times(1)).delete(cartProduct);
        verify(basketRepository, times(1)).save(basket);
    }
    @Test
    public void testRemoveProductFromBasketProductFound2() {
        CartProduct cartProduct = new CartProduct();
        CartProduct cartProduct2 = new CartProduct();
        Product product2 = new Product();
        product2.setProductId(2L);
        product2.setProductName("Laptop2");
        product2.setProductPrice(148002.00);
        cartProduct.setProduct(product);
        cartProduct2.setProduct(product2);
        basket.getBasketItems().add(cartProduct);
        basket.getBasketItems().add(cartProduct2);
        when(basketRepository.findById(1L)).thenReturn(Optional.of(basket));

        String response = shoppingCartService.removeProductFromBasket(1L, 2L);

        assertThat(response).isEqualTo("Product removed from basket successfully!");
    }

    @Test
    public void testUpdateProductQuantity2() {
        CartProduct cartProduct = new CartProduct();
        CartProduct cartProduct2 = new CartProduct();
        Product product2 = new Product();
        product2.setProductId(2L);
        product2.setProductName("Laptop2");
        product2.setProductPrice(148002.00);
        cartProduct.setProduct(product);
        cartProduct2.setProduct(product2);
        basket.getBasketItems().add(cartProduct);
        basket.getBasketItems().add(cartProduct2);
        when(basketRepository.findById(1L)).thenReturn(Optional.of(basket));

        String response = shoppingCartService.updateProductQuantity(1L, 2L,3);

        assertThat(response).isEqualTo("Product quantity updated successfully!");
    }

    @Test
    public void testAddExistingProductThrowsException() {
        Basket basket = new Basket();
        basket.setBasketId(1L);

        Product product = new Product();
        product.setProductId(1L);
        product.setProductName("Laptop");
        product.setProductPrice(1000.00);

        CartProduct existingCartProduct = new CartProduct();
        existingCartProduct.setCartProductId(new CartProductId(1L, 1L));
        existingCartProduct.setBasket(basket);
        existingCartProduct.setProduct(product);
        existingCartProduct.setCartQuantity(2);
        existingCartProduct.setCartPrice(2000.00);

        when(basketRepository.findById(1L)).thenReturn(Optional.of(basket));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(cartProductRepository.findByBasket_BasketIdAndProduct_ProductId(1L, 1L))
                .thenReturn(Optional.of(existingCartProduct));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            shoppingCartService.addProductToBasket(1L, 1L, 3);
        });

        assertThat(exception.getMessage()).isEqualTo("Product already exists!");

        verify(cartProductRepository, never()).save(any(CartProduct.class));
        verify(basketRepository, never()).save(any(Basket.class));
    }
}
