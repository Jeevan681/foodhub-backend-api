package com.jeevan.foodhub.mapper;

import com.jeevan.foodhub.dto.response.CartItemResponse;
import com.jeevan.foodhub.dto.response.CartResponse;
import com.jeevan.foodhub.entity.Cart;
import com.jeevan.foodhub.entity.CartItem;

import java.util.List;

public class CartMapper {

    private CartMapper() {
    }

    public static CartItemResponse toCartItemResponse(CartItem item) {

        double subtotal =
                item.getFood().getPrice() * item.getQuantity();

        return CartItemResponse.builder()
                .id(item.getId())
                .foodId(item.getFood().getId())
                .foodName(item.getFood().getName())
                .price(item.getFood().getPrice())
                .quantity(item.getQuantity())
                .subtotal(subtotal)
                .build();
    }

    public static CartResponse toCartResponse(Cart cart) {

        List<CartItemResponse> items = cart.getItems()
                .stream()
                .map(CartMapper::toCartItemResponse)
                .toList();

        double totalAmount = items.stream()
                .mapToDouble(CartItemResponse::getSubtotal)
                .sum();

        return CartResponse.builder()
                .cartId(cart.getId())
                .userId(cart.getUser().getId())
                .items(items)
                .totalAmount(totalAmount)
                .build();
    }
}