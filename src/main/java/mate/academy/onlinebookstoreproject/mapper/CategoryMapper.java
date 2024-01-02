package mate.academy.onlinebookstoreproject.mapper;

import mate.academy.onlinebookstoreproject.config.MapperConfig;
import mate.academy.onlinebookstoreproject.dto.category.CategoryDto;
import mate.academy.onlinebookstoreproject.dto.category.CreateCategoryRequestDto;
import mate.academy.onlinebookstoreproject.model.Category;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface CategoryMapper {
    CategoryDto toDto(Category category);

    Category toModel(CreateCategoryRequestDto categoryDto);
}
