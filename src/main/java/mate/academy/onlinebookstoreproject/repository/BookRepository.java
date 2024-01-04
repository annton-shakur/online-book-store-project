package mate.academy.onlinebookstoreproject.repository;

import java.util.List;
import java.util.Optional;
import mate.academy.onlinebookstoreproject.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("""
            select distinct b
            from Book b
            join fetch b.categories c
            where c.id = :categoryId
            """)
    List<Book> findAllByCategoryId(Long categoryId);

    @Query("""
            select distinct b
            from Book b
            join fetch b.categories
            where b.id = :id
            """)
    Optional<Book> findById(Long id);

    @Query("""
    select distinct b
    from Book b
    join fetch b.categories
            """)
    Page<Book> findAll(Pageable pageable);

}
