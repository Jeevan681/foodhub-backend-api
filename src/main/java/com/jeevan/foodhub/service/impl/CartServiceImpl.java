package com.jeevan.foodhub.service.impl;

import com.jeevan.foodhub.dto.request.AddToCartRequest;
import com.jeevan.foodhub.dto.request.UpdateCartItemRequest;
import com.jeevan.foodhub.dto.response.CartResponse;
import com.jeevan.foodhub.entity.*;
import com.jeevan.foodhub.exception.ResourceNotFoundException;
import com.jeevan.foodhub.mapper.CartMapper;
import com.jeevan.foodhub.repository.*;
import com.jeevan.foodhub.service.CartService;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

    private final UserRepository userRepository;
    private final FoodRepository foodRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public CartServiceImpl(
            UserRepository userRepository,
            FoodRepository foodRepository,
            CartRepository cartRepository,
            CartItemRepository cartItemRepository) {

        this.userRepository = userRepository;
        this.foodRepository = foodRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public CartResponse addToCart(
            String userEmail,
            AddToCartRequest request) {

        User user = userRepository
                .findByEmail(userEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Food food = foodRepository
                .findById(request.getFoodId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Food not found"));

        Cart cart = cartRepository
                .findByUser(user)
                .orElseGet(() -> {

                    Cart newCart = Cart.builder()
                            .user(user)
                            .build();

                    return cartRepository.save(newCart);
                });

        CartItem cartItem = cartItemRepository
                .findByCartAndFood(cart, food)
                .orElse(null);

        if (cartItem == null) {

            cartItem = CartItem.builder()
                    .cart(cart)
                    .food(food)
                    .quantity(request.getQuantity())
                    .build();

        } else {

            cartItem.setQuantity(
                    cartItem.getQuantity()
                            + request.getQuantity()
            );
        }

        cartItemRepository.save(cartItem);

        cart.getItems().clear();
        cart.getItems().addAll(
                cartItemRepository.findByCart(cart)
        );

        return CartMapper.toCartResponse(cart);
    }
    @Override
    public CartResponse getCart(String userEmail) {

        User user = userRepository
                .findByEmail(userEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Cart cart = cartRepository
                .findByUser(user)
                .orElseGet(() -> {

                    Cart newCart = Cart.builder()
                            .user(user)
                            .build();

                    return cartRepository.save(newCart);
                });

        cart.setItems(
                cartItemRepository.findByCart(cart)
        );

        return CartMapper.toCartResponse(cart);
    }
    @Override
    public CartResponse updateCartItem(String userEmail, Long itemId, UpdateCartItemRequest request) {

        User user = userRepository
                .findByEmail(userEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Cart cart = cartRepository
                .findByUser(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart not found"));

        CartItem cartItem = cartItemRepository
                .findById(itemId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart item not found"));

        if (!cartItem.getCart().getId().equals(cart.getId())) {
            throw new ResourceNotFoundException(
                    "Cart item does not belong to this user"
            );
        }

        cartItem.setQuantity(request.getQuantity());

        cartItemRepository.save(cartItem);

        cart.setItems(
                cartItemRepository.findByCart(cart)
        );

        return CartMapper.toCartResponse(cart);
    }
    @Override
    public void removeCartItem(
            String userEmail,
            Long itemId) {

        User user = userRepository
                .findByEmail(userEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Cart cart = cartRepository
                .findByUser(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart not found"));

        CartItem cartItem = cartItemRepository
                .findById(itemId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart item not found"));

        if (!cartItem.getCart().getId().equals(cart.getId())) {
            throw new ResourceNotFoundException(
                    "Cart item does not belong to this user"
            );
        }

        cartItemRepository.delete(cartItem);
    }
    @Override
    public void clearCart(String userEmail) {

        User user = userRepository
                .findByEmail(userEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Cart cart = cartRepository
                .findByUser(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart not found"));

        cartItemRepository.deleteAll(
                cartItemRepository.findByCart(cart)
        );
    }
}