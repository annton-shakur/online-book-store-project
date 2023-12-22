package mate.academy.onlinebookstoreproject.service.impl;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import mate.academy.onlinebookstoreproject.dto.user.UserRegistrationRequestDto;
import mate.academy.onlinebookstoreproject.dto.user.UserResponseDto;
import mate.academy.onlinebookstoreproject.exception.RegistrationException;
import mate.academy.onlinebookstoreproject.mapper.UserMapper;
import mate.academy.onlinebookstoreproject.model.Role;
import mate.academy.onlinebookstoreproject.model.User;
import mate.academy.onlinebookstoreproject.repository.RoleRepository;
import mate.academy.onlinebookstoreproject.repository.UserRepository;
import mate.academy.onlinebookstoreproject.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    public static final String EMAIL_EXIST_MSG
            = "The user with the provided email already exists!";
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public UserResponseDto save(UserRegistrationRequestDto requestDto) {
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new RegistrationException(EMAIL_EXIST_MSG);
        }
        User user = userMapper.toModel(requestDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByRoleName(Role.RoleName.USER);
        user.setRoles(Set.of(userRole));
        userRepository.save(user);
        return userMapper.toDto(user);
    }
}
