package mate.academy.onlinebookstoreproject.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import mate.academy.onlinebookstoreproject.validation.FieldMatch;

@FieldMatch(field = "password",
        fieldMatch = "repeatPassword",
        message = "passwords do not match!")
@Data
public class UserRegistrationRequestDto {
    private static final String CANNOT_BE_NULL_OR_BLANK_MSG = "cannot be null or blank";
    @NotBlank(message = CANNOT_BE_NULL_OR_BLANK_MSG)
    private String email;
    @NotBlank (message = CANNOT_BE_NULL_OR_BLANK_MSG)
    private String password;
    @NotBlank (message = CANNOT_BE_NULL_OR_BLANK_MSG)
    private String repeatPassword;
    @NotBlank (message = CANNOT_BE_NULL_OR_BLANK_MSG)
    private String firstName;
    @NotBlank (message = CANNOT_BE_NULL_OR_BLANK_MSG)
    private String lastName;
    private String shippingAddress;
}
