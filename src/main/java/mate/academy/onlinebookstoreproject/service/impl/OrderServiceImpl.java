package mate.academy.onlinebookstoreproject.service.impl;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import mate.academy.onlinebookstoreproject.dto.order.OrderItemResponseDto;
import mate.academy.onlinebookstoreproject.dto.order.OrderRequestDto;
import mate.academy.onlinebookstoreproject.dto.order.OrderResponseDto;
import mate.academy.onlinebookstoreproject.dto.order.OrderStatusRequestDto;
import mate.academy.onlinebookstoreproject.exception.EntityNotFoundException;
import mate.academy.onlinebookstoreproject.mapper.OrderItemMapper;
import mate.academy.onlinebookstoreproject.mapper.OrderMapper;
import mate.academy.onlinebookstoreproject.model.CartItem;
import mate.academy.onlinebookstoreproject.model.Order;
import mate.academy.onlinebookstoreproject.model.OrderItem;
import mate.academy.onlinebookstoreproject.model.ShoppingCart;
import mate.academy.onlinebookstoreproject.model.User;
import mate.academy.onlinebookstoreproject.repository.OrderItemRepository;
import mate.academy.onlinebookstoreproject.repository.OrderRepository;
import mate.academy.onlinebookstoreproject.repository.ShoppingCartRepository;
import mate.academy.onlinebookstoreproject.repository.UserRepository;
import mate.academy.onlinebookstoreproject.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private static final String CANNOT_FIND_CART_BY_USER_ID_MSG = "Cannot find cart by user ID: ";
    private static final String CANNOT_FIND_ORDER_BY_ID_MSG = "Cannot find order by ID: ";
    private static final String CANNOT_FIND_USER_BY_ID_MSG = "Cannot find user by ID: ";
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;

    @Override
    public List<OrderResponseDto> getAllOrders(Long userId) {
        List<Order> ordersFromDB = orderRepository.findAllByUserId(userId);
        return ordersFromDB.stream()
                .map(orderMapper::toResponseDto).toList();
    }

    @Override
    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto requestDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(
                        () -> new EntityNotFoundException(CANNOT_FIND_USER_BY_ID_MSG + userId));
        ShoppingCart shoppingCartFromDB = shoppingCartRepository.findByUserId(user.getId())
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                CANNOT_FIND_CART_BY_USER_ID_MSG + user.getId()));
        Order order = new Order();
        BigDecimal total = shoppingCartFromDB.getCartItems().stream()
                .map(c -> c.getBook().getPrice().multiply(BigDecimal.valueOf(c.getQuantity())))
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setStatus(Order.Status.PENDING);
        order.setUser(user);
        order.setShippingAddress(requestDto.getShippingAddress());
        order.setTotal(total);
        orderRepository.save(order);
        Set<CartItem> cartItems = shoppingCartFromDB.getCartItems();
        order.setOrderItems(setOrderItems(cartItems, order));
        return orderMapper.toResponseDto(orderRepository.save(order));
    }

    @Override
    @Transactional
    public OrderResponseDto updateStatus(Long id, OrderStatusRequestDto requestDto) {
        Order order = orderRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException(CANNOT_FIND_ORDER_BY_ID_MSG + id));
        order.setStatus(requestDto.status());
        return orderMapper.toResponseDto(orderRepository.save(order));
    }

    @Override
    public List<OrderItemResponseDto> getOrderItemsById(Long orderId, Long userId) {
        return orderItemRepository.findAllByOrderId(orderId, userId).stream()
                .map(orderItemMapper::toResponseDto)
                .toList();
    }

    @Override
    public OrderItemResponseDto getOrderItemById(Long orderId, Long itemId, Long userId) {
        return orderItemMapper
                .toResponseDto(orderItemRepository.findByOrderIdAndId(orderId, itemId, userId));
    }

    private Set<OrderItem> setOrderItems(Set<CartItem> cartItems, Order order) {
        Set<OrderItem> orderItems = new HashSet<>();
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setBook(cartItem.getBook());
            orderItem.setPrice(cartItem.getBook().getPrice()
                    .multiply(BigDecimal.valueOf(cartItem.getQuantity())));
            orderItem.setQuantity(cartItem.getQuantity());
            orderItemRepository.save(orderItem);
            orderItems.add(orderItem);
        }
        order.setOrderItems(orderItems);
        return orderItems;
    }
}
