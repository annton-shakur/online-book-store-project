package mate.academy.onlinebookstoreproject.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import mate.academy.onlinebookstoreproject.dto.book.BookDto;
import mate.academy.onlinebookstoreproject.dto.book.BookDtoWithoutCategoryIds;
import mate.academy.onlinebookstoreproject.dto.book.CreateBookRequestDto;
import mate.academy.onlinebookstoreproject.exception.EntityNotFoundException;
import mate.academy.onlinebookstoreproject.mapper.BookMapper;
import mate.academy.onlinebookstoreproject.model.Book;
import mate.academy.onlinebookstoreproject.model.Category;
import mate.academy.onlinebookstoreproject.repository.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {
    private static Book theGreatGatsbyBook;
    private static BookDto theGreatGatsbyBookDto;
    private static Book theGreatGatsbyWithoutId;
    private static BookDtoWithoutCategoryIds bookDtoWithoutCategoryIds;
    private static Book vanityFairBook;
    private static Book vanityFairBookUpdated;
    private static BookDto vanityFairBookDto;
    private static BookDto vanityFairBookDtoUpdated;
    private static CreateBookRequestDto vanityFairBookRequestDtoUpdated;
    private static CreateBookRequestDto theGreatGatsbyBookRequestDto;
    private static Category categoryOne;
    private static Category categoryTwo;
    private static List<Book> bookList;
    private static Pageable pageable;
    private static Long validId;
    private static Long validId2;
    private static Long invalidId;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookMapper bookMapper;
    @InjectMocks
    private BookServiceImpl bookService;

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
        theGreatGatsbyBook.setPrice(BigDecimal.valueOf(100));
        theGreatGatsbyBook.setDescription("A novel about american dream");
        theGreatGatsbyBook.setCoverImage("the-great-gatsby.jpg");
        theGreatGatsbyBook.setCategories(Set.of(categoryOne));
        theGreatGatsbyBook.setIsbn("978-0-13-235088-4");

        theGreatGatsbyWithoutId = new Book();
        theGreatGatsbyWithoutId.setTitle("The Great Gatsby");
        theGreatGatsbyWithoutId.setAuthor("F. Scott Fitzgerald");
        theGreatGatsbyWithoutId.setPrice(BigDecimal.valueOf(100));
        theGreatGatsbyWithoutId.setDescription("A novel about american dream");
        theGreatGatsbyWithoutId.setCoverImage("the-great-gatsby.jpg");
        theGreatGatsbyWithoutId.setCategories(Set.of(categoryOne));
        theGreatGatsbyWithoutId.setIsbn("978-0-13-235088-4");

        theGreatGatsbyBookDto = new BookDto();
        theGreatGatsbyBookDto.setId(1L);
        theGreatGatsbyBookDto.setTitle("The Great Gatsby");
        theGreatGatsbyBookDto.setAuthor("F. Scott Fitzgerald");
        theGreatGatsbyBookDto.setPrice(BigDecimal.valueOf(100));
        theGreatGatsbyBookDto.setDescription("A novel about american dream");
        theGreatGatsbyBookDto.setCoverImage("the-great-gatsby.jpg");
        theGreatGatsbyBookDto.setCategoryIds(Set.of(1L));
        theGreatGatsbyBookDto.setIsbn("978-0-13-235088-4");

        theGreatGatsbyBookRequestDto = new CreateBookRequestDto();
        theGreatGatsbyBookRequestDto.setTitle("The Great Gatsby");
        theGreatGatsbyBookRequestDto.setAuthor("F. Scott Fitzgerald");
        theGreatGatsbyBookRequestDto.setPrice(BigDecimal.valueOf(100));
        theGreatGatsbyBookRequestDto.setDescription("A novel about american dream");
        theGreatGatsbyBookRequestDto.setCoverImage("the-great-gatsby.jpg");
        theGreatGatsbyBookRequestDto.setCategoryIds(Set.of(1L));
        theGreatGatsbyBookRequestDto.setIsbn("978-0-13-235088-4");

        bookDtoWithoutCategoryIds = new BookDtoWithoutCategoryIds();
        bookDtoWithoutCategoryIds.setId(1L);
        bookDtoWithoutCategoryIds.setTitle("The Great Gatsby");
        bookDtoWithoutCategoryIds.setAuthor("F. Scott Fitzgerald");
        bookDtoWithoutCategoryIds.setPrice(BigDecimal.valueOf(100));
        bookDtoWithoutCategoryIds.setDescription("A novel about american dream");
        bookDtoWithoutCategoryIds.setCoverImage("the-great-gatsby.jpg");
        bookDtoWithoutCategoryIds.setIsbn("978-0-13-235088-4");

        vanityFairBook = new Book();
        vanityFairBook.setId(2L);
        vanityFairBook.setTitle("Vanity Fair");
        vanityFairBook.setAuthor("William Thackeray");
        vanityFairBook.setPrice(BigDecimal.valueOf(90));
        vanityFairBook.setDescription("A classic novel of social satire");
        vanityFairBook.setCategories(Set.of(categoryTwo));
        vanityFairBook.setCoverImage("vanity-fair.jpg");
        vanityFairBook.setIsbn("978-0-19-953762-4");

        vanityFairBookDto = new BookDto();
        vanityFairBookDto.setId(2L);
        vanityFairBookDto.setTitle("Vanity Fair");
        vanityFairBookDto.setAuthor("William Thackeray");
        vanityFairBookDto.setPrice(BigDecimal.valueOf(90));
        vanityFairBookDto.setDescription("A classic novel of social satire");
        vanityFairBookDto.setCategoryIds(Set.of(2L));
        vanityFairBookDto.setCoverImage("vanity-fair.jpg");
        vanityFairBookDto.setIsbn("978-0-19-953762-4");

        vanityFairBookUpdated = new Book();
        vanityFairBookUpdated.setTitle("Vanity Fair UPDATED");
        vanityFairBookUpdated.setAuthor("William Thackeray");
        vanityFairBookUpdated.setPrice(BigDecimal.valueOf(90));
        vanityFairBookUpdated.setDescription("A classic novel of social satire");
        vanityFairBookUpdated.setCategories(Set.of(categoryTwo));
        vanityFairBookUpdated.setCoverImage("vanity-fair.jpg");
        vanityFairBookUpdated.setIsbn("978-0-19-953762-4");

        vanityFairBookDtoUpdated = new BookDto();
        vanityFairBookDtoUpdated.setId(2L);
        vanityFairBookDtoUpdated.setTitle("Vanity Fair UPDATED");
        vanityFairBookDtoUpdated.setAuthor("William Thackeray");
        vanityFairBookDtoUpdated.setPrice(BigDecimal.valueOf(90));
        vanityFairBookDtoUpdated.setDescription("A classic novel of social satire");
        vanityFairBookDtoUpdated.setCategoryIds(Set.of(2L));
        vanityFairBookDtoUpdated.setCoverImage("vanity-fair.jpg");
        vanityFairBookDtoUpdated.setIsbn("978-0-19-953762-4");

        vanityFairBookRequestDtoUpdated = new CreateBookRequestDto();
        vanityFairBookRequestDtoUpdated.setTitle("Vanity Fair UPDATED");
        vanityFairBookRequestDtoUpdated.setAuthor("William Thackeray");
        vanityFairBookRequestDtoUpdated.setPrice(BigDecimal.valueOf(90));
        vanityFairBookRequestDtoUpdated.setDescription("A classic novel of social satire");
        vanityFairBookRequestDtoUpdated.setCategoryIds(Set.of(2L));
        vanityFairBookRequestDtoUpdated.setCoverImage("vanity-fair.jpg");
        vanityFairBookRequestDtoUpdated.setIsbn("978-0-19-953762-4");

        bookList = new ArrayList<>();
        bookList.add(theGreatGatsbyBook);
        bookList.add(vanityFairBook);

        pageable = PageRequest.of(0, 5);

        validId = 1L;
        validId2 = 2L;
        invalidId = 100L;
    }

    @Test
    @DisplayName("Save a new book and return its DTO")
    void save() {
        Mockito.when(bookMapper.toModel(theGreatGatsbyBookRequestDto))
                .thenReturn(theGreatGatsbyWithoutId);
        Mockito.when(bookRepository.save(theGreatGatsbyWithoutId))
                .thenReturn(theGreatGatsbyBook);
        Mockito.when(bookMapper.toDto(theGreatGatsbyWithoutId))
                .thenReturn(theGreatGatsbyBookDto);

        BookDto actual = bookService.save(theGreatGatsbyBookRequestDto);
        BookDto expected = theGreatGatsbyBookDto;

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Return 2 bookdtos mapped from books that were returned from the DB")
    void findAll_ExistingBooks_ReturnListOf2Books() {
        Page<Book> page = new PageImpl<>(bookList, pageable, bookList.size());
        Mockito.when(bookRepository.findAll(pageable)).thenReturn(page);
        Mockito.when(bookMapper.toDto(theGreatGatsbyBook)).thenReturn(theGreatGatsbyBookDto);
        Mockito.when(bookMapper.toDto(vanityFairBook)).thenReturn(vanityFairBookDto);

        List<BookDto> expected = new ArrayList<>();
        expected.add(theGreatGatsbyBookDto);
        expected.add(vanityFairBookDto);
        List<BookDto> actual = bookService.findAll(pageable);

        Assertions.assertIterableEquals(expected, actual);
    }

    @Test
    @DisplayName("Return bookdto mapped from book that was found in DB by id")
    void findById_WithValidId_ReturnBookDto() {
        BookDto expected = vanityFairBookDto;
        Mockito.when(bookRepository.findById(validId)).thenReturn(Optional.of(vanityFairBook));
        Mockito.when(bookMapper.toDto(vanityFairBook)).thenReturn(vanityFairBookDto);

        BookDto actual = bookService.findById(validId);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Throw EntityNotFoundException when trying to find a book by invalid id")
    void findById_WithInvalidId_ThrowException() {
        Mockito.when(bookRepository.findById(invalidId))
                .thenThrow(new EntityNotFoundException("Cannot find book by id: " + invalidId));
        Assertions.assertThrows(
                EntityNotFoundException.class, () -> bookRepository.findById(invalidId));
    }

    @Test
    @DisplayName("Delete book from DB by id")
    void deleteById_WithValidId_DeleteBook() {
        Mockito.doNothing().when(bookRepository).deleteById(validId);
        Assertions.assertDoesNotThrow(() -> bookService.deleteById(validId));
        Mockito.verify(bookRepository, Mockito.times(1)).deleteById(validId);
    }

    @Test
    @DisplayName("""
            Return dto of the book that was located an updated using its id and requestDto
            """)
    void updateById_WithValidId_UpdateBook() {
        Mockito.when(bookRepository.existsById(validId2))
                .thenReturn(true);
        Mockito.when(bookMapper.toModel(vanityFairBookRequestDtoUpdated))
                .thenReturn(vanityFairBookUpdated);
        Mockito.when(bookRepository.save(vanityFairBookUpdated))
                .thenReturn(vanityFairBookUpdated);
        Mockito.when(bookMapper.toDto(vanityFairBookUpdated))
                .thenReturn(vanityFairBookDtoUpdated);

        BookDto expected = vanityFairBookDtoUpdated;
        BookDto actual = bookService.updateById(validId2, vanityFairBookRequestDtoUpdated);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Throw an exception when trying to update book by non-existing id")
    void updateById_WithInvalidId_ThrowException() {
        Mockito.when(bookRepository.existsById(invalidId))
                .thenThrow(new EntityNotFoundException("Cannot find book by id: " + invalidId));
        Assertions.assertThrows(
                EntityNotFoundException.class, () -> bookRepository.existsById(invalidId));
    }

    @Test
    @DisplayName("""
    Return a list of BookDtoWithoutCategoryIds based on the provided category id
                """)
    void getBooksByCategoryId() {
        List<Book> booksFromCategoryOne = new ArrayList<>();
        booksFromCategoryOne.add(theGreatGatsbyBook);
        Mockito.when(bookRepository.findAllByCategoryId(categoryOne.getId()))
                .thenReturn(booksFromCategoryOne);

        Mockito.when(bookMapper.toBookDtoWithoutCategoryIds(theGreatGatsbyBook))
                .thenReturn(bookDtoWithoutCategoryIds);

        List<BookDtoWithoutCategoryIds> expected = new ArrayList<>();
        expected.add(bookDtoWithoutCategoryIds);
        List<BookDtoWithoutCategoryIds> actual
                = bookService.getBooksByCategoryId(categoryOne.getId());

        Assertions.assertIterableEquals(expected, actual);
    }
}
