package mate.academy.onlinebookstoreproject.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;
import mate.academy.onlinebookstoreproject.model.Book;
import mate.academy.onlinebookstoreproject.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BookRepositoryImpl implements BookRepository {
    private static final String CANNOT_SAVE_BOOK_TO_DB_MSG =
            "Cannot save book to database: ";
    private EntityManagerFactory factory;

    @Autowired
    public BookRepositoryImpl(EntityManagerFactory factory) {
        this.factory = factory;
    }

    @Override
    public Book save(Book book) {
        EntityTransaction transaction = null;
        try (EntityManager entityManager = factory.createEntityManager()) {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(book);
            transaction.commit();
            return book;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException(CANNOT_SAVE_BOOK_TO_DB_MSG + book, e);
        }
    }

    @Override
    public List<Book> findAll() {
        try (EntityManager entityManager = factory.createEntityManager()) {
            return entityManager.createQuery("FROM Book", Book.class).getResultList();
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        try (EntityManager entityManager = factory.createEntityManager()) {
            Book book = entityManager.find(Book.class, id);
            return Optional.ofNullable(book);
        }
    }
}
