package mate.academy.onlinebookstoreproject.dto.cart;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class CartItemUpdateDto {
    @Min(0)
    private int quantity;
}
