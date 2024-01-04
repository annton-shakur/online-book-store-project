package mate.academy.onlinebookstoreproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.onlinebookstoreproject.dto.order.OrderItemResponseDto;
import mate.academy.onlinebookstoreproject.dto.order.OrderRequestDto;
import mate.academy.onlinebookstoreproject.dto.order.OrderResponseDto;
import mate.academy.onlinebookstoreproject.dto.order.OrderStatusRequestDto;
import mate.academy.onlinebookstoreproject.model.User;
import mate.academy.onlinebookstoreproject.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Order management", description = "Endpoints for managing orders")
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Get all orders",
            description = "Get all orders for the logged in user")
    public List<OrderResponseDto> getAll(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return orderService.getAllOrders(user.getId());
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Place an order",
            description = "Create an order based on a shopping cart")
    public OrderResponseDto createOrder(
            Authentication authentication,
            @RequestBody @Valid OrderRequestDto requestDto
    ) {
        User user = (User) authentication.getPrincipal();
        return orderService.createOrder(requestDto, user.getId());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    @Operation(summary = "Update order status",
            description = "Update order status from admins account")
    public OrderResponseDto updateStatus(
            @PathVariable Long id,
            @RequestBody @Valid OrderStatusRequestDto requestDto
    ) {
        return orderService.updateStatus(id, requestDto);
    }

    @GetMapping("/{orderId}/items")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Get order items by id",
            description = "Get all the items of the order of the logged in user by order ID")
    public List<OrderItemResponseDto> getOrderItemsById(
            @PathVariable Long orderId,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();
        return orderService.getOrderItemsById(orderId, user.getId());
    }

    @GetMapping("/{orderId}/items/{itemId}")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Get order items by its and orders ids",
            description = "Get a certain order item using its, users and orders id")
    public OrderItemResponseDto getOrderItemById(
            @PathVariable Long orderId,
            @PathVariable Long itemId,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();
        return orderService.getOrderItemById(orderId, itemId, user.getId());
    }
}
