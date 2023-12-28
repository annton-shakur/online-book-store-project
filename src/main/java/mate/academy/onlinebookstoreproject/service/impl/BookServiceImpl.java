package mate.academy.onlinebookstoreproject.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import mate.academy.onlinebookstoreproject.dto.book.BookDto;
import mate.academy.onlinebookstoreproject.dto.book.BookDtoWithoutCategoryIds;
import mate.academy.onlinebookstoreproject.dto.book.CreateBookRequestDto;
import mate.academy.onlinebookstoreproject.exception.EntityNotFoundException;
import mate.academy.onlinebookstoreproject.mapper.BookMapper;
import mate.academy.onlinebookstoreproject.model.Book;
import mate.academy.onlinebookstoreproject.model.Category;
import mate.academy.onlinebookstoreproject.repository.BookRepository;
import mate.academy.onlinebookstoreproject.repository.CategoryRepository;
import mate.academy.onlinebookstoreproject.service.BookService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private static final String CANNOT_FIND_BOOK_BY_ID_MSG = "Cannot find book by Id: ";
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
        Book book = bookMapper.toModel(requestDto);
        setCategories(requestDto, book);
        bookRepository.save(book);
        return bookMapper.toDto(book);
    }

    @Override
    public List<BookDto> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable).stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto findById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(CANNOT_FIND_BOOK_BY_ID_MSG + id));
        return bookMapper.toDto(book);
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public BookDto updateById(Long id, CreateBookRequestDto requestDto) {
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(CANNOT_FIND_BOOK_BY_ID_MSG + id));
        book.setPrice(requestDto.getPrice());
        book.setAuthor(requestDto.getAuthor());
        book.setIsbn(requestDto.getIsbn());
        book.setDescription(requestDto.getDescription());
        book.setTitle(requestDto.getTitle());
        book.setCoverImage(requestDto.getCoverImage());
        setCategories(requestDto, book);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<BookDtoWithoutCategoryIds> getBooksByCategoryId(Long categoryId) {
        return bookRepository.findAllByCategoryId(categoryId).stream()
                .map(bookMapper::toBookDtoWithoutCategoryIds)
                .toList();
    }

    private void setCategories(CreateBookRequestDto requestDto, Book book) {
        Set<Category> categorySet = new HashSet<>();
        for (Long categoryId : requestDto.getCategoryIds()) {
            categorySet.add(
                    categoryRepository.findById(categoryId)
                            .orElseThrow(
                                    () -> new EntityNotFoundException(
                                            "Cannot find category by id: " + categoryId)));
        }
        book.setCategories(categorySet);
    }
}
