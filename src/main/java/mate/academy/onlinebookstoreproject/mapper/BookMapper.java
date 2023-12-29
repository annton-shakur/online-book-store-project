package mate.academy.onlinebookstoreproject.mapper;

import java.util.stream.Collectors;
import mate.academy.onlinebookstoreproject.config.MapperConfig;
import mate.academy.onlinebookstoreproject.dto.book.BookDto;
import mate.academy.onlinebookstoreproject.dto.book.BookDtoWithoutCategoryIds;
import mate.academy.onlinebookstoreproject.dto.book.CreateBookRequestDto;
import mate.academy.onlinebookstoreproject.model.Book;
import mate.academy.onlinebookstoreproject.model.Category;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    @Mapping(target = "categoryIds", ignore = true)
    BookDto toDto(Book book);

    @Mapping(target = "categories", ignore = true)
    Book toModel(CreateBookRequestDto requestDto);

    BookDtoWithoutCategoryIds toBookDtoWithoutCategoryIds(Book book);

    @AfterMapping
    default void setCategories(@MappingTarget Book book,
                               CreateBookRequestDto bookDto) {
        book.setCategories(bookDto.getCategoryIds().stream()
                .map(Category::new)
                .collect(Collectors.toSet()));
    }

    @AfterMapping
    default void setCategories(@MappingTarget BookDto bookDto, Book book) {
        if (!book.getCategories().isEmpty()) {
            bookDto.setCategoryIds(book.getCategories().stream()
                    .map(Category::getId)
                    .collect(Collectors.toSet()));
        }
    }
}
