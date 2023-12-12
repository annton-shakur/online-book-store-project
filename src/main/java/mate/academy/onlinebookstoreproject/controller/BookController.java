package mate.academy.onlinebookstoreproject.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.onlinebookstoreproject.dto.BookDto;
import mate.academy.onlinebookstoreproject.dto.CreateBookRequestDto;
import mate.academy.onlinebookstoreproject.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping
    public List<BookDto> getAll() {
        return bookService.findAll();
    }

    @GetMapping("/{id}")
    public BookDto findById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @PostMapping
    public BookDto save(@RequestBody CreateBookRequestDto requestDto) {
        return bookService.save(requestDto);
    }
}
