package mate.academy.onlinebookstoreproject.service;

import mate.academy.onlinebookstoreproject.dto.cart.AddToCartRequestDto;
import mate.academy.onlinebookstoreproject.dto.cart.CartItemUpdateDto;
import mate.academy.onlinebookstoreproject.dto.cart.ShoppingCartResponseDto;

public interface ShoppingCartService {
    ShoppingCartResponseDto addToCart(AddToCartRequestDto requestDto, Long userId);

    ShoppingCartResponseDto getCartByUsedId(Long id);

    void deleteItem(Long id);

    ShoppingCartResponseDto updateCartItem(Long cartItemId, CartItemUpdateDto updateDto, Long id);
}
