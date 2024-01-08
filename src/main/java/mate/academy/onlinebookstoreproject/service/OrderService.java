package mate.academy.onlinebookstoreproject.service;

import java.util.List;
import mate.academy.onlinebookstoreproject.dto.order.OrderItemResponseDto;
import mate.academy.onlinebookstoreproject.dto.order.OrderRequestDto;
import mate.academy.onlinebookstoreproject.dto.order.OrderResponseDto;
import mate.academy.onlinebookstoreproject.dto.order.OrderStatusRequestDto;

public interface OrderService {
    List<OrderResponseDto> getAllOrders(Long userId);

    OrderResponseDto createOrder(OrderRequestDto requestDto, Long userId);

    OrderResponseDto updateStatus(Long id, OrderStatusRequestDto requestDto);

    List<OrderItemResponseDto> getOrderItemsById(Long orderId, Long userId);

    OrderItemResponseDto getOrderItemById(Long orderId, Long itemId, Long userId);
}
