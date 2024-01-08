package mate.academy.onlinebookstoreproject.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import mate.academy.onlinebookstoreproject.dto.book.BookDto;
import mate.academy.onlinebookstoreproject.dto.book.CreateBookRequestDto;
import mate.academy.onlinebookstoreproject.model.Book;
import mate.academy.onlinebookstoreproject.model.Category;
import mate.academy.onlinebookstoreproject.repository.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerTest {
    protected static MockMvc mockMvc;
    private static CreateBookRequestDto theGreatGatsbyBookRequestDto;
    private static BookDto theGreatGatsbyBookDto;
    private static BookDto vanityFairBookDto;
    private static CreateBookRequestDto updateBookRequestDto;
    private static BookDto updateBookResponseDto;
    private static Category categoryOne;
    private static Category categoryTwo;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private BookRepository bookRepository;

    @BeforeAll
    static void beforeAll(@Autowired WebApplicationContext applicationContext) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();

        categoryOne = new Category();
        categoryOne.setId(1L);
        categoryOne.setName("Novel");
        categoryOne.setDescription("Basic one");

        categoryTwo = new Category();
        categoryTwo.setId(2L);
        categoryTwo.setName("Detective");
        categoryTwo.setDescription("Very thrilling");

        theGreatGatsbyBookRequestDto = new CreateBookRequestDto();
        theGreatGatsbyBookRequestDto.setTitle("The Great Gatsby");
        theGreatGatsbyBookRequestDto.setAuthor("F. Scott Fitzgerald");
        theGreatGatsbyBookRequestDto.setPrice(new BigDecimal("100.00"));
        theGreatGatsbyBookRequestDto.setDescription("A novel about american dream");
        theGreatGatsbyBookRequestDto.setCoverImage("the-great-gatsby.jpg");
        theGreatGatsbyBookRequestDto.setCategoryIds(Set.of(1L));
        theGreatGatsbyBookRequestDto.setIsbn("978-0-13-235088-4");

        theGreatGatsbyBookDto = new BookDto();
        theGreatGatsbyBookDto.setId(1L);
        theGreatGatsbyBookDto.setTitle("The Great Gatsby");
        theGreatGatsbyBookDto.setAuthor("F. Scott Fitzgerald");
        theGreatGatsbyBookDto.setPrice(new BigDecimal("100.00"));
        theGreatGatsbyBookDto.setDescription("A novel about american dream");
        theGreatGatsbyBookDto.setCoverImage("the-great-gatsby.jpg");
        theGreatGatsbyBookDto.setCategoryIds(Set.of(1L));
        theGreatGatsbyBookDto.setIsbn("978-0-13-235088-4");

        vanityFairBookDto = new BookDto();
        vanityFairBookDto.setId(2L);
        vanityFairBookDto.setTitle("Vanity Fair");
        vanityFairBookDto.setAuthor("William Thackeray");
        vanityFairBookDto.setPrice(new BigDecimal("90.00"));
        vanityFairBookDto.setDescription("A classic novel of social satire");
        vanityFairBookDto.setCategoryIds(Set.of(2L));
        vanityFairBookDto.setCoverImage("vanity-fair.jpg");
        vanityFairBookDto.setIsbn("978-0-19-953762-4");

        updateBookRequestDto = new CreateBookRequestDto();
        updateBookRequestDto.setTitle("The Great Gatsby UPDATED");
        updateBookRequestDto.setAuthor("F. Scott Fitzgerald UPDATED");
        updateBookRequestDto.setPrice(new BigDecimal("100.00"));
        updateBookRequestDto.setDescription("A novel about american dream UPDATED");
        updateBookRequestDto.setCoverImage("the-great-gatsby.jpg");
        updateBookRequestDto.setCategoryIds(Set.of(1L));
        updateBookRequestDto.setIsbn("978-0-13-235088-4");

        updateBookResponseDto = new BookDto();
        updateBookResponseDto.setId(1L);
        updateBookResponseDto.setTitle("The Great Gatsby UPDATED");
        updateBookResponseDto.setAuthor("F. Scott Fitzgerald UPDATED");
        updateBookResponseDto.setPrice(new BigDecimal("100.00"));
        updateBookResponseDto.setDescription("A novel about american dream UPDATED");
        updateBookResponseDto.setCoverImage("the-great-gatsby.jpg");
        updateBookResponseDto.setCategoryIds(Set.of(1L));
        updateBookResponseDto.setIsbn("978-0-13-235088-4");

    }

    @WithMockUser(username = "user", roles = {"USER"})
    @Test
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
    @DisplayName("Get a list of 2 books mapped to dto from the DB ")
    void getAll_Return_TwoBookDto() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/books")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        List<BookDto> expected = new ArrayList<>();
        expected.add(theGreatGatsbyBookDto);
        expected.add(vanityFairBookDto);
        BookDto[] actual = objectMapper.readValue(
                result.getResponse().getContentAsByteArray(), BookDto[].class);
        Assertions.assertIterableEquals(expected, Arrays.asList(actual));
    }

    @WithMockUser(username = "user", roles = {"USER"})
    @Test
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
    void findById() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        BookDto expected = theGreatGatsbyBookDto;
        BookDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), BookDto.class);

        EqualsBuilder.reflectionEquals(expected, actual);
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @Sql(scripts
            = "classpath:database/categories/insert-categories-into-categories-table.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/categories/delete-from-books-categories-table.sql",
            "classpath:database/categories/delete-categories-from-categories-table.sql",
            "classpath:database/books/delete-books-from-books-table.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Add a new book")
    void save_ValidBook_ReturnBookDto() throws Exception {
        String jsonRequest = objectMapper.writeValueAsString(theGreatGatsbyBookRequestDto);
        MvcResult result = mockMvc.perform(
                post("/api/books")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isCreated())
                .andReturn();
        BookDto expected = theGreatGatsbyBookDto;
        BookDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), BookDto.class);

        EqualsBuilder.reflectionEquals(expected, actual);
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
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
    void delete_WithValidBookId_ResponseStatusOk() throws Exception {
        MvcResult result = mockMvc.perform(
                        delete("/api/books/1")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        Optional<Book> deletedBook = bookRepository.findById(1L);
        Assertions.assertFalse(deletedBook.isPresent());
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
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
    void update_WithValidBook_ReturnUpdateBook() throws Exception {
        String jsonRequest = objectMapper.writeValueAsString(updateBookRequestDto);
        MvcResult mvcResult = mockMvc.perform(
                        put("/api/books/1")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();
        BookDto expected = updateBookResponseDto;
        BookDto actual = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(), BookDto.class);
        EqualsBuilder.reflectionEquals(expected, actual);
    }
}
