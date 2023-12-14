package mate.academy.onlinebookstoreproject.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Data;
import org.hibernate.validator.constraints.ISBN;

@Data
public class CreateBookRequestDto {
    private static final String CANNOT_BE_NULL_MSG = "cannot be null";
    @NotNull (message = CANNOT_BE_NULL_MSG)
    private String title;
    @NotNull (message = CANNOT_BE_NULL_MSG)
    private String author;
    @NotNull (message = CANNOT_BE_NULL_MSG)
    @ISBN
    private String isbn;
    @NotNull (message = CANNOT_BE_NULL_MSG)
    @Min(0)
    private BigDecimal price;
    @NotEmpty
    private String description;
    @NotEmpty
    private String coverImage;
}
