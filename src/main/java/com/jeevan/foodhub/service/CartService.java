package com.jeevan.foodhub.service;

import com.jeevan.foodhub.dto.request.AddToCartRequest;
import com.jeevan.foodhub.dto.request.UpdateCartItemRequest;
import com.jeevan.foodhub.dto.response.CartResponse;

public interface CartService {

    CartResponse addToCart(
            String userEmail,
            AddToCartRequest request
    );
    CartResponse getCart(String userEmail);
    CartResponse updateCartItem(String userEmail, Long itemId, UpdateCartItemRequest request);
    void removeCartItem(String userEmail, Long itemId);
    void clearCart(String userEmail);
}