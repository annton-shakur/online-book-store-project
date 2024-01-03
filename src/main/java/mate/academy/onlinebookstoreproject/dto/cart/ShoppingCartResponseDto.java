package mate.academy.onlinebookstoreproject.dto.cart;

import java.util.Set;
import lombok.Data;

@Data
public class ShoppingCartResponseDto {
    private Long id;
    private Long userId;
    private Set<CartItemResponseDto> cartItems;
}
