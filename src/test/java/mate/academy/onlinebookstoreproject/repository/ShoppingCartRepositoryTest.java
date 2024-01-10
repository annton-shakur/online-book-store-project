package mate.academy.onlinebookstoreproject.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;
import mate.academy.onlinebookstoreproject.model.Book;
import mate.academy.onlinebookstoreproject.model.CartItem;
import mate.academy.onlinebookstoreproject.model.Category;
import mate.academy.onlinebookstoreproject.model.Role;
import mate.academy.onlinebookstoreproject.model.ShoppingCart;
import mate.academy.onlinebookstoreproject.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

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
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ShoppingCartRepositoryTest {
    private static Book theGreatGatsbyBook;
    private static Category categoryOne;
    private static ShoppingCart shoppingCart;
    private static CartItem cartItem;
    private static User user;
    private static Role role;
    private static final Long VALID_ID = 1L;
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @BeforeAll
    static void beforeAll() {
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

        shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCart.setId(1L);
        shoppingCart.setCartItems(Set.of(cartItem));
    }

    @Test
    void findByUserId() {
        ShoppingCart expected = shoppingCart;
        ShoppingCart actual = shoppingCartRepository.findByUserId(VALID_ID).get();
        assertEquals(expected, actual);
        assertEquals(Set.of(cartItem), actual.getCartItems());
        Set<Book> actualBooks = actual.getCartItems().stream()
                .map(CartItem::getBook)
                .collect(Collectors.toSet());
        assertEquals(Set.of(theGreatGatsbyBook), actualBooks);
    }
}
