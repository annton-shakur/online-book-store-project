package mate.academy.onlinebookstoreproject.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserLoginRequestDto(
        @NotBlank (message = "cannot be null or blank!")
        @Size(min = 8, max = 20)
        @Email
        String email,
        @NotBlank (message = CANNOT_BE_NULL_OR_BLANK_MSG)
        @Size(min = 8, max = 20)
        String password
) {
    private static final String CANNOT_BE_NULL_OR_BLANK_MSG = "cannot be blank!";
}
