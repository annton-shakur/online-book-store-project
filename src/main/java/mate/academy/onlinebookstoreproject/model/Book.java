package mate.academy.onlinebookstoreproject.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import lombok.Data;
import org.springframework.lang.NonNull;

@Entity
@Data
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String title;
    @NonNull
    private String author;
    @NonNull
    @Column(unique = true)
    private String isbn;
    @NonNull
    private BigDecimal price;
    private String description;
    private String coverImage;

    public Book() {

    }
}
