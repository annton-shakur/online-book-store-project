package mate.academy.onlinebookstoreproject.dto.cart;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class AddToCartRequestDto {
    private Long bookId;
    @Min(value = 1, message = "cannot be less than 1")
    private int quantity;
}
