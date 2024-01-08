package mate.academy.onlinebookstoreproject.dto.order;

import jakarta.validation.constraints.NotNull;
import mate.academy.onlinebookstoreproject.model.Order;

public record OrderStatusRequestDto(@NotNull Order.Status status) {
}
