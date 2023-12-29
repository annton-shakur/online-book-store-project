package mate.academy.onlinebookstoreproject.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.onlinebookstoreproject.dto.book.BookDto;
import mate.academy.onlinebookstoreproject.dto.book.BookDtoWithoutCategoryIds;
import mate.academy.onlinebookstoreproject.dto.book.CreateBookRequestDto;
import mate.academy.onlinebookstoreproject.exception.EntityNotFoundException;
import mate.academy.onlinebookstoreproject.mapper.BookMapper;
import mate.academy.onlinebookstoreproject.model.Book;
import mate.academy.onlinebookstoreproject.repository.BookRepository;
import mate.academy.onlinebookstoreproject.service.BookService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private static final String CANNOT_FIND_BOOK_BY_ID_MSG = "Cannot find book by Id: ";
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
        Book book = bookMapper.toModel(requestDto);
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
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException(CANNOT_FIND_BOOK_BY_ID_MSG + id);
        }
        Book book = bookMapper.toModel(requestDto);
        book.setId(id);
        Book updatedBook = bookRepository.save(book);
        return bookMapper.toDto(updatedBook);
    }

    @Override
    public List<BookDtoWithoutCategoryIds> getBooksByCategoryId(Long categoryId) {
        return bookRepository.findAllByCategoryId(categoryId).stream()
                .map(bookMapper::toBookDtoWithoutCategoryIds)
                .toList();
    }
}
