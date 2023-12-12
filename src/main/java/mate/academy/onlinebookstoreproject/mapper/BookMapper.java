package mate.academy.onlinebookstoreproject.mapper;

import mate.academy.onlinebookstoreproject.config.MapperConfig;
import mate.academy.onlinebookstoreproject.dto.BookDto;
import mate.academy.onlinebookstoreproject.dto.CreateBookRequestDto;
import mate.academy.onlinebookstoreproject.model.Book;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    Book toModel(CreateBookRequestDto requestDto);
}
