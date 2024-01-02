package mate.academy.onlinebookstoreproject.dto.category;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateCategoryRequestDto {
    @NotBlank(message = "cannot be null or blank")
    private String name;
    private String description;
}
