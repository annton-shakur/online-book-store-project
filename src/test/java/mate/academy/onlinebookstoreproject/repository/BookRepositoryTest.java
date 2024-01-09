package mate.academy.onlinebookstoreproject.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import mate.academy.onlinebookstoreproject.model.Book;
import mate.academy.onlinebookstoreproject.model.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@Sql(scripts = {
        "classpath:database/categories/insert-categories-into-categories-table.sql",
        "classpath:database/books/insert-books-into-books-table.sql",
        "classpath:database/categories/insert-into-books-categories-table.sql"
}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {
        "classpath:database/categories/delete-from-books-categories-table.sql",
        "classpath:database/categories/delete-categories-from-categories-table.sql",
        "classpath:database/books/delete-books-from-books-table.sql"
}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryTest {
    private static Category categoryOne;
    private static Category categoryTwo;
    private static Book theGreatGatsbyBook;
    private static Book vanityFairBook;
    private static List<Book> bookList;
    private static final long VALID_ID = 1L;
    @Autowired
    private BookRepository bookRepository;

    @BeforeAll
    static void beforeAll() {
        categoryOne = new Category();
        categoryOne.setId(1L);
        categoryOne.setName("Novel");
        categoryOne.setDescription("Basic one");

        categoryTwo = new Category();
        categoryTwo.setId(2L);
        categoryTwo.setName("Detective");
        categoryTwo.setDescription("Very thrilling");

        theGreatGatsbyBook = new Book();
        theGreatGatsbyBook.setId(1L);
        theGreatGatsbyBook.setTitle("The Great Gatsby");
        theGreatGatsbyBook.setAuthor("F. Scott Fitzgerald");
        theGreatGatsbyBook.setPrice(new BigDecimal("100.00"));
        theGreatGatsbyBook.setDescription("A novel about american dream");
        theGreatGatsbyBook.setCoverImage("the-great-gatsby.jpg");
        theGreatGatsbyBook.setCategories(Set.of(categoryOne));
        theGreatGatsbyBook.setIsbn("978-0-13-235088-4");

        vanityFairBook = new Book();
        vanityFairBook.setId(2L);
        vanityFairBook.setTitle("Vanity Fair");
        vanityFairBook.setAuthor("William Thackeray");
        vanityFairBook.setPrice(new BigDecimal("90.00"));
        vanityFairBook.setDescription("A classic novel of social satire");
        vanityFairBook.setCategories(Set.of(categoryTwo));
        vanityFairBook.setCoverImage("vanity-fair.jpg");
        vanityFairBook.setIsbn("978-0-19-953762-4");

        bookList = new ArrayList<>();
        bookList.add(theGreatGatsbyBook);
        bookList.add(vanityFairBook);
    }

    @Test
    @DisplayName("Return a list of valid books by category id")
    void findAllByCategoryId_WithValidCategoryId_ReturnBook() {
        List<Book> actual = bookRepository.findAllByCategoryId(VALID_ID);
        assertEquals(List.of(theGreatGatsbyBook), actual);
    }

    @Test
    @DisplayName("Return a valid book by id")
    void findById_WithValidId_ReturnBookById() {
        Book actual = bookRepository.findById(VALID_ID).get();
        assertEquals(theGreatGatsbyBook, actual);
        assertEquals(theGreatGatsbyBook.getCategories(), actual.getCategories());
    }

    @Test
    @DisplayName("Return a valid list of all the books from db")
    void findAll_ReturnValidListOfBooks() {
        List<Book> actual = bookRepository.findAll();
        Assertions.assertEquals(2, actual.size());
        Assertions.assertIterableEquals(bookList, actual);
        Set<Category> categoriesExpected = new HashSet<>();
        categoriesExpected.add(categoryOne);
        categoriesExpected.add(categoryTwo);
        Set<Category> categoriesActual = actual.stream()
                .flatMap(b -> b.getCategories().stream())
                .collect(Collectors.toSet());
        assertEquals(categoriesExpected, categoriesActual);
    }
}
