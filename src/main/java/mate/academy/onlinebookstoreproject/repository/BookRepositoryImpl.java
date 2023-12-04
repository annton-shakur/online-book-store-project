package mate.academy.onlinebookstoreproject.repository;

import java.util.List;
import mate.academy.onlinebookstoreproject.model.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BookRepositoryImpl implements BookRepository {
    private static final String CANNOT_SAVE_BOOK_TO_DB_MSG =
            "Cannot save book to database: ";
    private static final String FAILED_TO_GET_ALL_BOOKS_FROM_DB_MSG =
            "Failed to get all the books from DB!";
    private SessionFactory factory;

    @Autowired
    public BookRepositoryImpl(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public Book save(Book book) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(book);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(CANNOT_SAVE_BOOK_TO_DB_MSG + book, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return book;
    }

    @Override
    public List<Book> findAll() {
        try (Session session = factory.openSession()) {
            Query<Book> query = session.createQuery("from Book", Book.class);
            return query.getResultList();
        } catch (RuntimeException e) {
            throw new RuntimeException(FAILED_TO_GET_ALL_BOOKS_FROM_DB_MSG);
        }
    }
}
