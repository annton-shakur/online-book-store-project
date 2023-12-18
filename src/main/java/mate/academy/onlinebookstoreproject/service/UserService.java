package mate.academy.onlinebookstoreproject.service;

import mate.academy.onlinebookstoreproject.dto.user.UserRegistrationRequestDto;
import mate.academy.onlinebookstoreproject.dto.user.UserResponseDto;

public interface UserService {
    UserResponseDto save(UserRegistrationRequestDto requestDto);
}
