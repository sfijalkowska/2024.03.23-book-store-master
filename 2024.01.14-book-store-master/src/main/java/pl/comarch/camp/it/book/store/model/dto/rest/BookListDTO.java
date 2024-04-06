package pl.comarch.camp.it.book.store.model.dto.rest;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
public class BookListDTO {
    private final List<BookDTO> books = new ArrayList<>();
}
