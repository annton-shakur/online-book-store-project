package mate.academy.onlinebookstoreproject.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import mate.academy.onlinebookstoreproject.validation.FieldMatch;

@FieldMatch(field = "password",
        fieldMatch = "repeatPassword",
        message = "passwords do not match!")
@Data
public class UserRegistrationRequestDto {
    private static final String CANNOT_BE_NULL_MSG = "cannot be null";
    @NotNull(message = CANNOT_BE_NULL_MSG)
    private String email;
    @NotNull (message = CANNOT_BE_NULL_MSG)
    private String password;
    @NotNull (message = CANNOT_BE_NULL_MSG)
    private String repeatPassword;
    @NotNull (message = CANNOT_BE_NULL_MSG)
    private String firstName;
    @NotNull (message = CANNOT_BE_NULL_MSG)
    private String lastName;
    private String shippingAddress;
}
