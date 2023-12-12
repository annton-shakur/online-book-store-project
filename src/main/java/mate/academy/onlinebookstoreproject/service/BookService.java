package mate.academy.onlinebookstoreproject.service;

import java.util.List;
import mate.academy.onlinebookstoreproject.dto.BookDto;
import mate.academy.onlinebookstoreproject.dto.CreateBookRequestDto;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> findAll();

    BookDto findById(Long id);

    void deleteById(Long id);

    BookDto update(Long id, CreateBookRequestDto requestDto);
}
