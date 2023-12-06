package controller;

import lombok.RequiredArgsConstructor;
import mate.academy.onlinebookstoreproject.dto.BookDto;
import mate.academy.onlinebookstoreproject.dto.CreateBookRequestDto;
import mate.academy.onlinebookstoreproject.model.Book;
import mate.academy.onlinebookstoreproject.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {
    BookService bookService;
//    @GetMapping
//    public List<BookDto> getAll() {
//        return bookService.findAll();
//    }

    @PostMapping
    public BookDto save(@RequestBody CreateBookRequestDto requestDto) {
        return bookService.save(requestDto);
    }

    @GetMapping("/test")
    public String test() {
        System.out.println("hello word");
        return "i am alive";
    }

//    @PostMapping
//    public Book someMethod(@RequestBody Book somebook) {
//        Book book = new Book();
//        book.setTitle("sa");
//        book.setAuthor("sa");
//        book.setIsbn("1551");
//        book.setPrice(BigDecimal.valueOf(100L));
//        return book;
//    }

}
