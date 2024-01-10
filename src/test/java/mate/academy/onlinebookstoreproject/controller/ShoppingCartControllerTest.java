package mate.academy.onlinebookstoreproject.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.Set;
import mate.academy.onlinebookstoreproject.dto.cart.CartItemResponseDto;
import mate.academy.onlinebookstoreproject.dto.cart.ShoppingCartResponseDto;
import mate.academy.onlinebookstoreproject.model.Book;
import mate.academy.onlinebookstoreproject.model.CartItem;
import mate.academy.onlinebookstoreproject.model.Category;
import mate.academy.onlinebookstoreproject.model.Role;
import mate.academy.onlinebookstoreproject.model.ShoppingCart;
import mate.academy.onlinebookstoreproject.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

@Sql(scripts = {
        "classpath:database/categories/insert-categories-into-categories-table.sql",
        "classpath:database/books/insert-books-into-books-table.sql",
        "classpath:database/categories/insert-into-books-categories-table.sql",
        "classpath:database/shopping-carts/insert-shopping-cart.sql",
        "classpath:database/cart-items/insert-cart-item.sql"

}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {
        "classpath:database/categories/delete-from-books-categories-table.sql",
        "classpath:database/cart-items/delete-cart-items.sql",
        "classpath:database/shopping-carts/delete-from-shopping-carts.sql",
        "classpath:database/categories/delete-categories-from-categories-table.sql",
        "classpath:database/books/delete-books-from-books-table.sql"

}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ShoppingCartControllerTest {
    protected static MockMvc mockMvc;
    private static Book theGreatGatsbyBook;
    private static Category categoryOne;
    private static ShoppingCart shoppingCart;
    private static ShoppingCartResponseDto shoppingCartResponseDto;
    private static CartItem cartItem;
    private static CartItemResponseDto cartItemResponseDto;
    private static User user;
    private static Role role;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(@Autowired WebApplicationContext applicationContext) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();

        role = new Role();
        role.setId(1L);
        role.setRoleName(Role.RoleName.USER);

        user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john@example.com");
        user.setPassword("$2a$10$kmYQ3CgmYPzWNsK9q8oLF.VCoXCDeXqY2suHL2Z6GdnN18CtLkjTO");
        user.setRoles(Set.of(role));

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

    @BeforeEach
    void setUp() {
        Authentication authentication
                = new UsernamePasswordAuthenticationToken(user,
                user.getPassword(), user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @WithMockUser(username = "user")
    @Test
    void getCart() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/cart")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ShoppingCartResponseDto expected = shoppingCartResponseDto;
        ShoppingCartResponseDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), ShoppingCartResponseDto.class);

        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }
}
