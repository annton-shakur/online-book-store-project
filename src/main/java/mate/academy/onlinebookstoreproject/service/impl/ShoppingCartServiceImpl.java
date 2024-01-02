package mate.academy.onlinebookstoreproject.service.impl;

import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import mate.academy.onlinebookstoreproject.dto.cart.AddToCartRequestDto;
import mate.academy.onlinebookstoreproject.dto.cart.CartItemUpdateDto;
import mate.academy.onlinebookstoreproject.dto.cart.ShoppingCartResponseDto;
import mate.academy.onlinebookstoreproject.exception.EntityNotFoundException;
import mate.academy.onlinebookstoreproject.mapper.ShoppingCartMapper;
import mate.academy.onlinebookstoreproject.model.Book;
import mate.academy.onlinebookstoreproject.model.CartItem;
import mate.academy.onlinebookstoreproject.model.ShoppingCart;
import mate.academy.onlinebookstoreproject.repository.BookRepository;
import mate.academy.onlinebookstoreproject.repository.CartItemRepository;
import mate.academy.onlinebookstoreproject.repository.ShoppingCartRepository;
import mate.academy.onlinebookstoreproject.repository.UserRepository;
import mate.academy.onlinebookstoreproject.service.ShoppingCartService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private static final String CANNOT_FIND_BOOK_BY_ID_MSG
            = "Cannot find book by ID: ";
    private static final String CANNOT_FIND_USER_BY_ID_MSG
            = "Cannot find user by ID: ";
    private static final String CANNOT_FIND_CART_BY_USER_ID_MSG
            = "Cannot find cart for the user with id: ";
    private static final String CANNOT_FIND_CART_ITEM_BY_ID_MSG
            = "Cannot find cart item by ID: ";
    private final ShoppingCartRepository shoppingCartRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;
    private final ShoppingCartMapper shoppingCartMapper;

    @Override
    @Transactional
    public ShoppingCartResponseDto addToCart(AddToCartRequestDto requestDto, Long userId) {
        CartItem cartItem = new CartItem();
        Book book = bookRepository.findById(requestDto.getBookId())
                .orElseThrow(() -> new EntityNotFoundException(
                                CANNOT_FIND_BOOK_BY_ID_MSG + requestDto.getBookId()));

        ShoppingCart shoppingCartFromDB = shoppingCartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    ShoppingCart shoppingCart = new ShoppingCart();
                    shoppingCart.setUser(userRepository.findById(userId).orElseThrow(
                            () -> new EntityNotFoundException(CANNOT_FIND_USER_BY_ID_MSG)
                    ));
                    shoppingCartRepository.save(shoppingCart);
                    return shoppingCart;
                });

        cartItem.setQuantity(requestDto.getQuantity());
        cartItem.setBook(book);
        cartItem.setShoppingCart(shoppingCartFromDB);
        Set<CartItem> set = new HashSet<>();
        cartItemRepository.save(cartItem);
        shoppingCartFromDB.setCartItems(set);
        shoppingCartFromDB.getCartItems().add(cartItem);
        return shoppingCartMapper.toDto(shoppingCartFromDB);
    }

    @Override
    public ShoppingCartResponseDto getCartByUsedId(Long id) {
        ShoppingCart shoppingCartFromDB = shoppingCartRepository.findByUserId(id)
                .orElseThrow(
                        () -> new EntityNotFoundException(CANNOT_FIND_CART_BY_USER_ID_MSG + id));
        return shoppingCartMapper.toDto(shoppingCartFromDB);
    }

    @Override
    public void deleteItem(Long id) {
        cartItemRepository.deleteById(id);
    }

    @Override
    public ShoppingCartResponseDto updateCartItem(
            Long cartItemId,
            CartItemUpdateDto updateDto,
            Long userId
    ) {
        CartItem cartItemFromDB = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new EntityNotFoundException(
                        CANNOT_FIND_CART_ITEM_BY_ID_MSG + cartItemId));
        CartItem cartItem = new CartItem();
        cartItem.setId(cartItemId);
        cartItem.setQuantity(updateDto.getQuantity());
        cartItem.setShoppingCart(cartItemFromDB.getShoppingCart());
        cartItem.setBook(cartItemFromDB.getBook());
        cartItemRepository.save(cartItem);
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        CANNOT_FIND_CART_BY_USER_ID_MSG + userId));
        shoppingCart.getCartItems().add(cartItem);
        return shoppingCartMapper.toDto(shoppingCart);
    }
}
