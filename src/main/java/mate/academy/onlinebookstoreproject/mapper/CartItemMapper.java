package mate.academy.onlinebookstoreproject.mapper;

import mate.academy.onlinebookstoreproject.config.MapperConfig;
import mate.academy.onlinebookstoreproject.dto.cart.CartItemResponseDto;
import mate.academy.onlinebookstoreproject.model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface CartItemMapper {

    @Mapping(target = "bookId", source = "book.id")
    @Mapping(target = "bookTitle", source = "book.title")
    CartItemResponseDto toResponseDto(CartItem cartItem);
}
