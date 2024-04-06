package pl.comarch.camp.it.book.store.model.dto.rest;

import lombok.*;
import pl.comarch.camp.it.book.store.dao.IBookDAO;
import pl.comarch.camp.it.book.store.exceptions.BookNotExistException;
import pl.comarch.camp.it.book.store.model.Position;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PositionDTO {
    private int id;
    private int bookId;
    private int quantity;

    public PositionDTO(Position position) {
        this.id = position.getId();
        this.bookId = position.getBook().getId();
        this.quantity = position.getQuantity();
    }

    public Position toPosition(IBookDAO bookDAO) {
        Position position = new Position();
        position.setId(this.id);
        position.setBook(bookDAO.getById(this.bookId).orElseThrow(BookNotExistException::new));
        position.setQuantity(this.quantity);
        return position;
    }
}
