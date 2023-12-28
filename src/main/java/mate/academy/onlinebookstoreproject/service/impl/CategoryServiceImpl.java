package mate.academy.onlinebookstoreproject.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.onlinebookstoreproject.dto.category.CategoryDto;
import mate.academy.onlinebookstoreproject.dto.category.CreateCategoryRequestDto;
import mate.academy.onlinebookstoreproject.exception.EntityNotFoundException;
import mate.academy.onlinebookstoreproject.mapper.CategoryMapper;
import mate.academy.onlinebookstoreproject.model.Category;
import mate.academy.onlinebookstoreproject.repository.CategoryRepository;
import mate.academy.onlinebookstoreproject.service.CategoryService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    public static final String CANNOT_FIND_CATEGORY_BY_ID_MSG =
            "Cannot find the category with this id: ";
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryDto> getAll(Pageable pageable) {
        return categoryRepository.findAll(pageable).stream()
                .map(categoryMapper::toDto).toList();
    }

    @Override
    public CategoryDto findById(Long id) {
        return categoryRepository.findById(id)
                .map(categoryMapper::toDto)
                .orElseThrow(
                        () -> new EntityNotFoundException(CANNOT_FIND_CATEGORY_BY_ID_MSG + id));
    }

    @Override
    public CategoryDto save(CreateCategoryRequestDto requestDto) {
        Category category = categoryMapper.toModel(requestDto);
        categoryRepository.save(category);
        return categoryMapper.toDto(category);
    }

    @Override
    public CategoryDto updateById(Long id, CreateCategoryRequestDto requestDto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException(CANNOT_FIND_CATEGORY_BY_ID_MSG + id));
        category.setDescription(requestDto.getDescription());
        category.setName(requestDto.getName());
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}
