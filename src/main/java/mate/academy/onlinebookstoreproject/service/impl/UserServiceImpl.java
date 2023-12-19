package mate.academy.onlinebookstoreproject.service.impl;

import lombok.RequiredArgsConstructor;
import mate.academy.onlinebookstoreproject.dto.user.UserRegistrationRequestDto;
import mate.academy.onlinebookstoreproject.dto.user.UserResponseDto;
import mate.academy.onlinebookstoreproject.exception.RegistrationException;
import mate.academy.onlinebookstoreproject.mapper.UserMapper;
import mate.academy.onlinebookstoreproject.model.User;
import mate.academy.onlinebookstoreproject.repository.UserRepository;
import mate.academy.onlinebookstoreproject.service.UserService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    public static final String EMAIL_EXIST_MSG
            = "The user with the provided email already exists!";
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto save(UserRegistrationRequestDto requestDto) {
        if (userRepository.findUserByEmail(requestDto.getEmail()) != null) {
            throw new RegistrationException(EMAIL_EXIST_MSG);
        }
        User user = userMapper.toModel(requestDto);
        userRepository.save(user);
        return userMapper.toDto(user);
    }
}
