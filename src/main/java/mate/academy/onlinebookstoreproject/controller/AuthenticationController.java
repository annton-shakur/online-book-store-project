package mate.academy.onlinebookstoreproject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mate.academy.onlinebookstoreproject.dto.user.UserRegistrationRequestDto;
import mate.academy.onlinebookstoreproject.dto.user.UserResponseDto;
import mate.academy.onlinebookstoreproject.exception.RegistrationException;
import mate.academy.onlinebookstoreproject.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final UserService userService;

    @PostMapping("/registration")
    public UserResponseDto register(@RequestBody @Valid UserRegistrationRequestDto request)
            throws RegistrationException {
        return userService.save(request);
    }
}
