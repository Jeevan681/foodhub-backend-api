package com.jeevan.foodhub.controller;

import com.jeevan.foodhub.dto.request.AddToCartRequest;
import com.jeevan.foodhub.dto.request.UpdateCartItemRequest;
import com.jeevan.foodhub.dto.response.CartResponse;
import com.jeevan.foodhub.service.CartService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(
            CartService cartService) {

        this.cartService = cartService;
    }

    @PostMapping("/items")
    public ResponseEntity<CartResponse> addToCart(

            Authentication authentication,

            @Valid
            @RequestBody
            AddToCartRequest request) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                cartService.addToCart(
                        email,
                        request
                )
        );
    }
    @GetMapping
    public ResponseEntity<CartResponse> getCart(
            Authentication authentication) {

        return ResponseEntity.ok(
                cartService.getCart(
                        authentication.getName()
                )
        );
    }
    @PutMapping("/items/{itemId}")
    public ResponseEntity<CartResponse> updateCartItem(
            Authentication authentication,
            @PathVariable Long itemId,
            @Valid
            @RequestBody
            UpdateCartItemRequest request) {
        return ResponseEntity.ok(
                cartService.updateCartItem(
                        authentication.getName(),
                        itemId,
                        request
                )
        );
    }
    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<String> removeCartItem(

            Authentication authentication,

            @PathVariable Long itemId) {

        cartService.removeCartItem(
                authentication.getName(),
                itemId
        );

        return ResponseEntity.ok(
                "Item removed from cart successfully"
        );
    }
    @DeleteMapping
    public ResponseEntity<String> clearCart(
            Authentication authentication) {

        cartService.clearCart(
                authentication.getName()
        );

        return ResponseEntity.ok(
                "Cart cleared successfully"
        );
    }
}