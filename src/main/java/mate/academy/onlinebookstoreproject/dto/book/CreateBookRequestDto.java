package mate.academy.onlinebookstoreproject.dto.book;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.Set;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.ISBN;

@Data
@Accessors(chain = true)
public class CreateBookRequestDto {
    private static final String CANNOT_BE_NULL_MSG = "cannot be null or blank";
    @NotBlank(message = CANNOT_BE_NULL_MSG)
    private String title;
    @NotBlank (message = CANNOT_BE_NULL_MSG)
    private String author;
    @NotBlank (message = CANNOT_BE_NULL_MSG)
    @ISBN
    private String isbn;
    @DecimalMin(value = "0", inclusive = false)
    private BigDecimal price;
    @NotEmpty
    private String description;
    @NotEmpty
    private String coverImage;
    private Set<Long> categoryIds;
}
