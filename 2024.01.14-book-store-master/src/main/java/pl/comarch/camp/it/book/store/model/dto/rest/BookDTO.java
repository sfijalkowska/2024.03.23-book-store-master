package pl.comarch.camp.it.book.store.model.dto.rest;

import lombok.*;
import pl.comarch.camp.it.book.store.dao.IUserDAO;
import pl.comarch.camp.it.book.store.exceptions.UserNotExistException;
import pl.comarch.camp.it.book.store.model.Book;
import pl.comarch.camp.it.book.store.model.User;

import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BookDTO {
    private int id;
    private String title;
    private String author;
    private String isbn;
    private double price;
    private int quantity;
    private int creatorId;

    public BookDTO(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.isbn = book.getIsbn();
        this.price = book.getPrice().doubleValue();
        this.quantity = book.getQuantity();
        this.creatorId = book.getCreator().getId();
    }

    public Book toBook(IUserDAO userDAO) {
        Book book = new Book();
        book.setTitle(this.getTitle());
        book.setAuthor(this.getAuthor());
        book.setIsbn(this.getIsbn());
        book.setPrice(this.getPrice());
        book.setQuantity(this.getQuantity());
        book.setCreator(userDAO.getById(this.getCreatorId())
                .orElseThrow(UserNotExistException::new));

        return book;
    }
}
