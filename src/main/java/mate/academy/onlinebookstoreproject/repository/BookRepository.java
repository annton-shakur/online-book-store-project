package mate.academy.onlinebookstoreproject.repository;

import mate.academy.onlinebookstoreproject.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

}
