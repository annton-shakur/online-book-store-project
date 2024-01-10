package mate.academy.onlinebookstoreproject.service.impl;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;
import mate.academy.onlinebookstoreproject.dto.cart.CartItemResponseDto;
import mate.academy.onlinebookstoreproject.dto.cart.ShoppingCartResponseDto;
import mate.academy.onlinebookstoreproject.mapper.ShoppingCartMapper;
import mate.academy.onlinebookstoreproject.model.Book;
import mate.academy.onlinebookstoreproject.model.CartItem;
import mate.academy.onlinebookstoreproject.model.Category;
import mate.academy.onlinebookstoreproject.model.ShoppingCart;
import mate.academy.onlinebookstoreproject.model.User;
import mate.academy.onlinebookstoreproject.repository.ShoppingCartRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ShoppingCartServiceImplTest {

    @Mock
    private static ShoppingCartMapper shoppingCartMapper;
    @Mock
    private static ShoppingCartRepository shoppingCartRepository;
    @InjectMocks
    private static ShoppingCartServiceImpl shoppingCartService;
    private static Book theGreatGatsbyBook;
    private static Category categoryOne;
    private static ShoppingCart shoppingCart;
    private static ShoppingCartResponseDto shoppingCartResponseDto;
    private static CartItem cartItem;
    private static CartItemResponseDto cartItemResponseDto;
    private static User user;

    @BeforeAll
    static void beforeAll() {
        user = new User();
        user.setId(1L);

        categoryOne = new Category();
        categoryOne.setId(1L);
        categoryOne.setName("Novel");
        categoryOne.setDescription("Basic one");

        theGreatGatsbyBook = new Book();
        theGreatGatsbyBook.setId(1L);
        theGreatGatsbyBook.setTitle("The Great Gatsby");
        theGreatGatsbyBook.setAuthor("F. Scott Fitzgerald");
        theGreatGatsbyBook.setPrice(new BigDecimal("100.00"));
        theGreatGatsbyBook.setDescription("A novel about american dream");
        theGreatGatsbyBook.setCoverImage("the-great-gatsby.jpg");
        theGreatGatsbyBook.setCategories(Set.of(categoryOne));
        theGreatGatsbyBook.setIsbn("978-0-13-235088-4");

        cartItem = new CartItem();
        cartItem.setBook(theGreatGatsbyBook);
        cartItem.setShoppingCart(shoppingCart);
        cartItem.setQuantity(1);
        cartItem.setId(1L);

        cartItemResponseDto = new CartItemResponseDto();
        cartItemResponseDto.setId(1L);
        cartItemResponseDto.setQuantity(1);
        cartItemResponseDto.setBookId(theGreatGatsbyBook.getId());
        cartItemResponseDto.setBookTitle(theGreatGatsbyBook.getTitle());

        shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCart.setId(1L);
        shoppingCart.setCartItems(Set.of(cartItem));

        shoppingCartResponseDto = new ShoppingCartResponseDto();
        shoppingCartResponseDto.setUserId(user.getId());
        shoppingCartResponseDto.setId(1L);
        shoppingCartResponseDto.setCartItems(Set.of(cartItemResponseDto));
    }

    @Test
    @DisplayName("Return shopping cart by user id")
    void getCartByUsedId_WithValidId_ReturnShoppingCartDto() {
        Mockito.when(shoppingCartRepository.findByUserId(1L))
                .thenReturn(Optional.of(shoppingCart));
        Mockito.when(shoppingCartMapper.toDto(shoppingCart))
                .thenReturn(shoppingCartResponseDto);

        ShoppingCartResponseDto expected = shoppingCartResponseDto;
        ShoppingCartResponseDto actual = shoppingCartService.getCartByUsedId(1L);

        Assertions.assertEquals(expected, actual);
    }
}
