package mate.academy.onlinebookstoreproject.service;

import java.util.List;
import mate.academy.onlinebookstoreproject.dto.category.CategoryDto;
import mate.academy.onlinebookstoreproject.dto.category.CreateCategoryRequestDto;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    List<CategoryDto> getAll(Pageable pageable);

    CategoryDto findById(Long id);

    CategoryDto save(CreateCategoryRequestDto requestDto);

    CategoryDto updateById(Long id, CreateCategoryRequestDto requestDto);

    void deleteById(Long id);
}
