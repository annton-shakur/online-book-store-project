package mate.academy.onlinebookstoreproject;

import java.math.BigDecimal;
import mate.academy.onlinebookstoreproject.model.Book;
import mate.academy.onlinebookstoreproject.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OnlineBookStoreProjectApplication {

    @Autowired
    private BookService bookService;

    public static void main(String[] args) {
        SpringApplication.run(OnlineBookStoreProjectApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            Book book = new Book();
            book.setTitle("The Great Gatsby");
            book.setAuthor("Scott Fitzgerald");
            book.setIsbn("9780333791035");
            book.setPrice(BigDecimal.valueOf(100L));
            book.setDescription("Some description");

            bookService.save(book);
            System.out.println(bookService.findAll());
        };
    }
}
