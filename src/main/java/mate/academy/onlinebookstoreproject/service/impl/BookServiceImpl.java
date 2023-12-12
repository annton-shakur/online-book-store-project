package mate.academy.onlinebookstoreproject.service.impl;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.onlinebookstoreproject.dto.BookDto;
import mate.academy.onlinebookstoreproject.dto.CreateBookRequestDto;
import mate.academy.onlinebookstoreproject.mapper.BookMapper;
import mate.academy.onlinebookstoreproject.model.Book;
import mate.academy.onlinebookstoreproject.repository.BookRepository;
import mate.academy.onlinebookstoreproject.service.BookService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private static final String CANNOT_FIND_BOOK_BY_ID_MSG = "Cannot find book by Id: ";
    private static final String FAILED_TO_UPDATE_BY_ID_MSG = "There is book with the provided ID: ";
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
        Book book = bookMapper.toModel(requestDto);
        bookRepository.save(book);
        return bookMapper.toDto(book);
    }

    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream()
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
    public BookDto update(Long id, CreateBookRequestDto requestDto) {
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(FAILED_TO_UPDATE_BY_ID_MSG + id));
        book.setPrice(requestDto.getPrice());
        book.setAuthor(requestDto.getAuthor());
        book.setIsbn(requestDto.getIsbn());
        book.setDescription(requestDto.getDescription());
        book.setTitle(requestDto.getTitle());
        book.setCoverImage(requestDto.getCoverImage());
        return bookMapper.toDto(bookRepository.save(book));
    }
}
