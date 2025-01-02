package com.ecommerce.shoppingcart.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collections;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long customerId;

    @Column(nullable = false, unique = true)
    private String customerEmail;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    private Basket basket;

    public Customer(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    @PrePersist
    public void prePersist() {
        if (this.basket == null) {
            this.basket = new Basket();
            this.basket.setCustomer(this);
            this.basket.setBasketItems(Collections.emptyList());
        }
    }
}

