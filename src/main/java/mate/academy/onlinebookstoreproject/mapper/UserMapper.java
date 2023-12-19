package mate.academy.onlinebookstoreproject.mapper;

import mate.academy.onlinebookstoreproject.config.MapperConfig;
import mate.academy.onlinebookstoreproject.dto.user.UserRegistrationRequestDto;
import mate.academy.onlinebookstoreproject.dto.user.UserResponseDto;
import mate.academy.onlinebookstoreproject.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserResponseDto toDto(User user);

    User toModel(UserRegistrationRequestDto requestDto);
}
