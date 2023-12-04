package mate.academy.onlinebookstoreproject.service;

import java.util.List;
import mate.academy.onlinebookstoreproject.model.Book;
import mate.academy.onlinebookstoreproject.repository.BookRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
    private BookRepositoryImpl bookRepository;

    @Autowired
    public BookServiceImpl(BookRepositoryImpl bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }
}
