package mate.academy.onlinebookstoreproject.service.impl;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.onlinebookstoreproject.dto.BookDto;
import mate.academy.onlinebookstoreproject.dto.CreateBookRequestDto;
import mate.academy.onlinebookstoreproject.mapper.BookMapper;
import mate.academy.onlinebookstoreproject.model.Book;
import mate.academy.onlinebookstoreproject.repository.impl.BookRepositoryImpl;
import mate.academy.onlinebookstoreproject.service.BookService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private static final String CANNOT_FIND_BOOK_BY_ID_MSG = "Cannot find book by Id: ";
    private final BookRepositoryImpl bookRepository;
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
}
