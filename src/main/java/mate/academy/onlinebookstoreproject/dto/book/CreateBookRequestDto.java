package mate.academy.onlinebookstoreproject.dto.book;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import lombok.Data;
import org.hibernate.validator.constraints.ISBN;

@Data
public class CreateBookRequestDto {
    private static final String CANNOT_BE_NULL_MSG = "cannot be null or blank";
    @NotBlank(message = CANNOT_BE_NULL_MSG)
    private String title;
    @NotBlank (message = CANNOT_BE_NULL_MSG)
    private String author;
    @NotBlank (message = CANNOT_BE_NULL_MSG)
    @ISBN
    private String isbn;
    @NotBlank (message = CANNOT_BE_NULL_MSG)
    @Min(0)
    private BigDecimal price;
    @NotEmpty
    private String description;
    @NotEmpty
    private String coverImage;
}
