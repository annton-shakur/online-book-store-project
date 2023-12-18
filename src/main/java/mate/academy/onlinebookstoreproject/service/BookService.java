package mate.academy.onlinebookstoreproject.service;

import java.util.List;
import mate.academy.onlinebookstoreproject.dto.book.BookDto;
import mate.academy.onlinebookstoreproject.dto.book.CreateBookRequestDto;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> findAll(Pageable pageable);

    BookDto findById(Long id);

    void deleteById(Long id);

    BookDto updateById(Long id, CreateBookRequestDto requestDto);
}
