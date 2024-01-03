package mate.academy.onlinebookstoreproject.dto.cart;

import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class AddToCartRequestDto {
    private Long bookId;
    @Positive(message = "must be positive")
    private int quantity;
}
