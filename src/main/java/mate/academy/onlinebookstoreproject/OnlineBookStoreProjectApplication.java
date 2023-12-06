package mate.academy.onlinebookstoreproject;

import mate.academy.onlinebookstoreproject.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OnlineBookStoreProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineBookStoreProjectApplication.class, args);
    }
}
