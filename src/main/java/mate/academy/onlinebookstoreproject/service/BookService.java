package mate.academy.onlinebookstoreproject.service;

import java.util.List;
import mate.academy.onlinebookstoreproject.model.Book;

public interface BookService {
    Book save(Book book);

    List<Book> findAll();
}
