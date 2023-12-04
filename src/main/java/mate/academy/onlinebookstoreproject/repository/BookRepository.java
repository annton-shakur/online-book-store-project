package mate.academy.onlinebookstoreproject.repository;

import java.util.List;
import mate.academy.onlinebookstoreproject.model.Book;

public interface BookRepository {
    Book save(Book book);

    List<Book> findAll();
}
